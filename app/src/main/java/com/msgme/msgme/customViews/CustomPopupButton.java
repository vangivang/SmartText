package com.msgme.msgme.customViews;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.msgme.msgme.MainApplication;
import com.msgme.msgme.R;

/**
 * Created by alonm on 8/26/14.
 */
public class CustomPopupButton extends RelativeLayout {

    public interface OnPopupButtonDurationPassedListener {
        public void onPopupButtonDurationPassedEvent();
    }

    public static final String POPUP_ICON_ON_DURATION = "popup_icon_on_duration";

    private OnPopupButtonDurationPassedListener mOnPopupButtonDurationPassedListener;

    private ImageView mPopUpButtonBG;
    private ImageView mPopUpButtonCoupon;
    private RelativeLayout mPopUpButtonRootView;
    private int mPopupButtonOnDuration;

    public CustomPopupButton(Context context) {
        super(context);
        init();
    }

    public CustomPopupButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_popup_button_layout, this, true);
        mPopUpButtonBG = (ImageView) view.findViewById(R.id.popupButton);
        mPopUpButtonCoupon = (ImageView) view.findViewById(R.id.popupButtonCoupon);
        mPopUpButtonRootView = (RelativeLayout) view.findViewById(R.id.popup_root);
        mPopupButtonOnDuration = ((MainApplication) getContext().getApplicationContext()).getPref().getInt
                (POPUP_ICON_ON_DURATION, 5000);
    }

    public void setOnClickListenerToRootView(View.OnClickListener onClickListener) {
        mPopUpButtonRootView.setOnClickListener(onClickListener);
    }

    public void onTriggerWordFound(final int popupTransitionDuration) {

        // Get TransitionDrawable set in XML
        final TransitionDrawable popupBgTransitionDrawable = (TransitionDrawable) mPopUpButtonBG.getDrawable();
        final TransitionDrawable popupCouponTransitionDrawable = (TransitionDrawable) mPopUpButtonCoupon.getDrawable();
        final ImageView couponButton = (ImageView) getRootView().findViewById(R.id.popupButtonCoupon);

        // Set true cross fade (upper layer will be totally opaque
        popupCouponTransitionDrawable.setCrossFadeEnabled(true);
        popupBgTransitionDrawable.setCrossFadeEnabled(true);

        // Show the button
        setButtonVisibility(true);

        // Start transition
        popupCouponTransitionDrawable.startTransition(popupTransitionDuration);
        popupBgTransitionDrawable.startTransition(popupTransitionDuration);

        // Start coupon pulse animation
        startPulseAnimation(couponButton);

        // When popup duration finishes + 350ms:
        // 1. Stop coupon pulse animation
        // 2. Later, start reverse transition of popup
        // 3. When all of that is done (750 ms later), call activity to animate list view down
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopPulseAnimation(couponButton);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popupBgTransitionDrawable.reverseTransition(popupTransitionDuration);
                        popupCouponTransitionDrawable.reverseTransition(popupTransitionDuration);
                        mPopUpButtonRootView.setOnClickListener(null);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mOnPopupButtonDurationPassedListener.onPopupButtonDurationPassedEvent();
                                setButtonVisibility(false);
                            }
                        }, 750);

                    }
                }, 250);
            }
        }, 350 + mPopupButtonOnDuration);
    }

    public void startPulseAnimation(ImageView view) {
        Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse_animation_on);
        view.startAnimation(pulse);
    }

    public void stopPulseAnimation(final ImageView view) {
        final Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse_animation_off);

        // Stupid android. Needs to post to UI for this animation to work because this method is called from a Handler
        // weird stuff....
        view.post(new Runnable() {
            @Override
            public void run() {
                view.startAnimation(pulse);
            }
        });
        view.clearAnimation();
    }

    public void setOnPopupButtonDurationPassedListener(OnPopupButtonDurationPassedListener listner) {
        mOnPopupButtonDurationPassedListener = listner;
    }

    private void setButtonVisibility(boolean isVisible) {
        if (isVisible) {
            findViewById(R.id.popup_root).setVisibility(VISIBLE);
        } else {
            findViewById(R.id.popup_root).setVisibility(INVISIBLE);
        }
    }


}
