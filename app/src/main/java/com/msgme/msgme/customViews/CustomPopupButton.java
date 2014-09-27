package com.msgme.msgme.customViews;

import android.content.Context;
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

    private ImageView mCouponIcon;

    public enum PopupButtonSide {LEFT, RIGHT}

    public static final String POPUP_ICON_ON_DURATION = "popup_icon_on_duration";
    public static final int ROOT_VIEW_FADE_OUT_DURATION = 450;

    private OnPopupButtonDurationPassedListener mOnPopupButtonDurationPassedListener;

    private RelativeLayout mPopUpButtonRootView;
    private int mPopupButtonOnDuration;

    public interface OnPopupButtonDurationPassedListener {
        public void onPopupButtonDurationPassedEvent();
    }

    public CustomPopupButton(Context context) {
        super(context);
        if(!isInEditMode()){
            init();
        }
    }

    public CustomPopupButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            init();
        }
    }

    private void init() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_popup_button_layout, this, true);
        mPopUpButtonRootView = (RelativeLayout) view.findViewById(R.id.popup_root);
        mCouponIcon = (ImageView) getRootView().findViewById(R.id.popupButtonCoupon);
        mPopupButtonOnDuration = ((MainApplication) getContext().getApplicationContext()).getPref().getInt
                (POPUP_ICON_ON_DURATION, 5000);
    }

    public void setOnClickListenerToRootView(View.OnClickListener onClickListener) {
        mPopUpButtonRootView.setOnClickListener(onClickListener);
    }

    public void onTriggerWordFound() {

        // Show the button
        setButtonVisibility(true);

        // Start transition
        // Start coupon pulse animation
        startPulseAnimation(mCouponIcon);

        // When popup duration finishes + 350ms:
        // 1. Stop coupon pulse animation
        // 2. Later, start reverse transition of popup
        // 3. When all of that is done (750 ms later), call activity to animate list view down
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopPulseAnimation(mCouponIcon); // 400 ms

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

//                        setButtonVisibility(false);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPopUpButtonRootView.setOnClickListener(null);
                                mOnPopupButtonDurationPassedListener.onPopupButtonDurationPassedEvent();
                            }
                        }, ROOT_VIEW_FADE_OUT_DURATION);
                    }
                }, 400);
            }
        }, 350 + mPopupButtonOnDuration);
    }

    public void startPulseAnimation(final ImageView view) {
        final Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse_animation_on);
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

    public void setButtonVisibility(boolean isVisible) {
        if (isVisible) {
            mPopUpButtonRootView.post(new Runnable() {
                @Override
                public void run() {
                    mPopUpButtonRootView.setVisibility(VISIBLE);
                    com.nineoldandroids.view.ViewPropertyAnimator.animate(mPopUpButtonRootView).setDuration
                            (ROOT_VIEW_FADE_OUT_DURATION).alpha(1.0f);
                }
            });
        } else {
            com.nineoldandroids.view.ViewPropertyAnimator.animate(mPopUpButtonRootView).setDuration
                    (ROOT_VIEW_FADE_OUT_DURATION).alpha(0.0f);
        }
    }


}
