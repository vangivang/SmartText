package com.msgme.msgme.customViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.msgme.msgme.R;
import com.msgme.msgme.database.TriggerWordEntity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/**
 * Created by alonm on 9/11/14.
 */
public class RoundedLayout extends RelativeLayout implements View.OnClickListener{

    public interface OnCouponDialogClosedListener{
        public void onCouponDialogClosedEvent();
    }

    private OnCouponDialogClosedListener mOnCouponDialogClosedListener;
    private View mInflatedView;
    private RelativeLayout mRootView;
    private ImageView mCouponImage;

    private enum ViewItems {VIEW_CONTAINER_EXPAND, VIEW_CONTAINER_COLLAPSE, HEADER, FOOTER, COUPON_TEXT, COUPON_IMAGE}

    public static final int HEADER_FOOTER_ANIM_DURATION = 350;
    private View mHeaderView;
    private View mFooterView;
    private TextView mCouponText;
    private String mCouponString;

    public RoundedLayout(Context context) {
        super(context);
        if (!isInEditMode()) {
            init();
        }
    }

    public RoundedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init();
            setAttributes(attrs);
        }
    }

    public RoundedLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            init();
            setAttributes(attrs);
        }
    }

    private void setAttributes(AttributeSet attrs) {

    }

    private void init() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflatedView = layoutInflater.inflate(R.layout.custom_rounded_layout, this, true);
        mHeaderView = mInflatedView.findViewById(R.id.header);
        mFooterView = mInflatedView.findViewById(R.id.footer);
        mCouponText = (TextView) mInflatedView.findViewById(R.id.coupon_text);
        mRootView = (RelativeLayout) mInflatedView.findViewById(R.id.root_view);
        mCouponImage = (ImageView) mInflatedView.findViewById(R.id.coupon_image);
        mInflatedView.findViewById(R.id.header_cancel).setOnClickListener(this);
    }

    public void setCouponString(String couponText) {
        mCouponString = couponText;
    }

    public void animateView(final TriggerWordEntity triggerWordData) {


        animationControl(ViewItems.VIEW_CONTAINER_EXPAND, mRootView, HEADER_FOOTER_ANIM_DURATION,
                new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getCouponImageFromServer(triggerWordData.getImageUrl());
                animationControl(ViewItems.HEADER, mHeaderView, HEADER_FOOTER_ANIM_DURATION, null);
                animationControl(ViewItems.FOOTER, mFooterView, HEADER_FOOTER_ANIM_DURATION,
                        new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mCouponText.setVisibility(VISIBLE);
                        String couponText = triggerWordData.getCouponText();

                        mCouponText.setText(couponText.replace("%1$s", triggerWordData.getText()));
                        animationControl(ViewItems.COUPON_TEXT, mCouponText, HEADER_FOOTER_ANIM_DURATION, null);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void getCouponImageFromServer(String couponUrl) {
        ImageLoader.getInstance().displayImage(couponUrl, mCouponImage, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                mCouponImage.setVisibility(VISIBLE);
            }
        });
    }

    private void animationControl(final ViewItems itemToAnimate, final View viewToAnimate, long animationDuration,
                                  Animation.AnimationListener animationListener) {

        final MarginLayoutParams params = (MarginLayoutParams) viewToAnimate.getLayoutParams();
        final int topMargin = params.topMargin;
        final int bottomMargin = params.bottomMargin;
        final float viewWidth = getResources().getDimension(R.dimen.popup_width);
        final float viewHeight = getResources().getDimension(R.dimen.popup_height);

        Animation appliedAnimation = new Animation(getContext(), null) {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                switch (itemToAnimate) {
                    case VIEW_CONTAINER_EXPAND:
                        params.width = 0;
                        params.height = 0;
                        viewToAnimate.getLayoutParams().height = (int) (viewHeight * interpolatedTime);
                        viewToAnimate.getLayoutParams().width = (int) (viewWidth * interpolatedTime);
                        viewToAnimate.requestLayout();
                        break;
                    case VIEW_CONTAINER_COLLAPSE:
                        viewToAnimate.getLayoutParams().width = (int) (viewWidth - (viewWidth * interpolatedTime));
                        viewToAnimate.getLayoutParams().height = (int) (viewHeight - (viewHeight * interpolatedTime));
                        viewToAnimate.requestLayout();
                    case HEADER:
                        params.setMargins(0, (int) (topMargin * (1 - interpolatedTime)), 0, 0);
                        viewToAnimate.requestLayout();
                        break;
                    case FOOTER:
                        params.setMargins(0, 0, 0, (int) (bottomMargin * (1 - interpolatedTime)));
                        viewToAnimate.requestLayout();
                        break;
                    case COUPON_TEXT:
                        params.setMargins(0, (int) (topMargin * (1 - interpolatedTime)), 0, 0);
                        mCouponText.requestLayout();
                        break;
                    case COUPON_IMAGE:
                        break;
                }
            }
        };

        if (animationListener != null) {
            appliedAnimation.setAnimationListener(animationListener);
        }

        appliedAnimation.setDuration(animationDuration);
        appliedAnimation.setFillAfter(true);
        appliedAnimation.setInterpolator(new DecelerateInterpolator());

        viewToAnimate.startAnimation(appliedAnimation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_cancel:
                mHeaderView.clearAnimation();
                mHeaderView.setVisibility(INVISIBLE);

                mFooterView.clearAnimation();
                mFooterView.setVisibility(INVISIBLE);

                mCouponImage.setVisibility(INVISIBLE);

                mCouponText.clearAnimation();
                mCouponText.setVisibility(INVISIBLE);

                animationControl(ViewItems.VIEW_CONTAINER_COLLAPSE, mRootView, HEADER_FOOTER_ANIM_DURATION, new Animation.AnimationListener() {


                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mOnCouponDialogClosedListener.onCouponDialogClosedEvent();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
        }
    }

    public void setOnCouponDialogClosedListener(OnCouponDialogClosedListener onCouponDialogClosedListener) {
        mOnCouponDialogClosedListener = onCouponDialogClosedListener;
    }
}
