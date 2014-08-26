package com.msgme.msgme.customViews;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.msgme.msgme.R;

/**
 * Created by alonm on 8/26/14.
 */
public class CustomPopupButton extends RelativeLayout {

    private ImageView mPopUpButtonBG;
    private ImageView mPopUpButtonCoupon;
    private RelativeLayout mPopUpButtonRootView;

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
    }

    public void setOnClickListenerToRootView(View.OnClickListener onClickListener){
        mPopUpButtonRootView.setOnClickListener(onClickListener);
    }

    public void onTriggerWordFound(final int timeToResetButtonDrawable, final int popupTransitionDuration){
        final TransitionDrawable popupBgTransitionDrawable = (TransitionDrawable) mPopUpButtonBG.getDrawable();
        final TransitionDrawable popupCouponTransitionDrawable = (TransitionDrawable) mPopUpButtonCoupon.getDrawable();

        popupCouponTransitionDrawable.setCrossFadeEnabled(true);
        popupBgTransitionDrawable.setCrossFadeEnabled(true);

        popupCouponTransitionDrawable.startTransition(popupTransitionDuration);
        popupBgTransitionDrawable.startTransition(popupTransitionDuration);

        //TODO: animate pulsating coupon icon....

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popupBgTransitionDrawable.reverseTransition(popupTransitionDuration);
                popupCouponTransitionDrawable.reverseTransition(popupTransitionDuration);
                mPopUpButtonRootView.setOnClickListener(null);

            }
        }, 350 + timeToResetButtonDrawable);
    }


}
