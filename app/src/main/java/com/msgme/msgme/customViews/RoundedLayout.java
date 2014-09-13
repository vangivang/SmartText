package com.msgme.msgme.customViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.msgme.msgme.R;
import com.nineoldandroids.view.animation.AnimatorProxy;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/**
 * Created by alonm on 9/11/14.
 */
public class RoundedLayout extends RelativeLayout {


    private View mInflatedView;
    private RelativeLayout mRootView;

    private enum ViewItems {VIEW_CONTAINER, HEADER, FOOTER, COUPON_TEXT, COUPON_IMAGE}

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
    }

    public void setCouponString(String couponText) {
        mCouponString = couponText;
    }

    public void animateView() {


        animationControl(ViewItems.VIEW_CONTAINER, mRootView, HEADER_FOOTER_ANIM_DURATION,
                new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animateCouponImage();
                animationControl(ViewItems.HEADER, mHeaderView, HEADER_FOOTER_ANIM_DURATION, null);
                animationControl(ViewItems.FOOTER, mFooterView, HEADER_FOOTER_ANIM_DURATION,
                        new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mCouponText.setVisibility(VISIBLE);
//                mCouponText.setText(mCouponString);
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

    private void animateCouponImage() {
        final ImageView couponImage = (ImageView) mInflatedView.findViewById(R.id.coupon_image);
        ImageLoader.getInstance().displayImage("http://smartxt.me/images/Dinner.png", couponImage,
                new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                couponImage.setVisibility(VISIBLE);
            }
        });
    }

    private void animationControl(final ViewItems itemToAnimate, final View viewToAnimate, long animationDuration,
                                  Animation.AnimationListener animationListener) {

        final MarginLayoutParams params = (MarginLayoutParams) viewToAnimate.getLayoutParams();
        final int topMargin = params.topMargin;
        final int bottomMargin = params.bottomMargin;

        Animation appliedAnimation = new Animation(getContext(), null) {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                switch (itemToAnimate) {
                    case VIEW_CONTAINER:
                        float viewWidth = getResources().getDimension(R.dimen.popup_width);
                        float viewHeight = getResources().getDimension(R.dimen.popup_height);
                        params.width = 0;
                        params.height = 0;
//                        viewToAnimate.setVisibility(VISIBLE);
                        viewToAnimate.getLayoutParams().height = (int) (viewHeight * interpolatedTime);
                        viewToAnimate.getLayoutParams().width = (int) (viewWidth * interpolatedTime);
                        viewToAnimate.requestLayout();
                        break;
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
}
