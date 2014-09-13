package com.msgme.msgme.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.msgme.msgme.R;

/**
 * Created by alonm on 9/11/14.
 */
public class RoundedLayout extends RelativeLayout {

    public static final int HEADER_FOOTER_ANIM_DURATION = 350;
    private View mHeaderView;
    private View mFooterView;

    public RoundedLayout(Context context) {
        super(context);
        if (!isInEditMode()){
            init();
        }
    }

    public RoundedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()){
            init();
            setAttributes(attrs);
        }
    }

    public RoundedLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()){
            init();
            setAttributes(attrs);
        }
    }

    private void setAttributes(AttributeSet attrs) {

    }

    private void init() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_rounded_layout, this, true);
        mHeaderView = view.findViewById(R.id.header);
        mFooterView = view.findViewById(R.id.footer);
    }

    public void animateView(){

//        float rootX = this.getX();
//        float rootY = this.getY();
//        int width = this.getWidth();
//        int height= this.getHeight();
//
//        mHeaderView.setY(rootY - mHeaderView.getHeight());
//        com.nineoldandroids.view.ViewPropertyAnimator.animate(mHeaderView).translationYBy(mHeaderView.getHeight()).setDuration(1000);

        animateHeader();
        animateFooter();
    }

    private void animateHeader() {
        Animation moveDownAnim = new TranslateAnimation(getContext(), null){

            MarginLayoutParams params = (MarginLayoutParams) mHeaderView.getLayoutParams();
            int topMargin = params.topMargin;

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                params.setMargins(0, (int) (topMargin * (1 - interpolatedTime)), 0, 0);
                mHeaderView.requestLayout();
            }
        };

        moveDownAnim.setDuration(HEADER_FOOTER_ANIM_DURATION);
        moveDownAnim.setFillAfter(true);
        moveDownAnim.setInterpolator(new DecelerateInterpolator());
        mHeaderView.startAnimation(moveDownAnim);
    }

    private void animateFooter() {
        Animation moveUpAnim = new TranslateAnimation(getContext(), null){

            MarginLayoutParams params = (MarginLayoutParams) mFooterView.getLayoutParams();
            int bottomMargin = params.bottomMargin;

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                params.setMargins(0, 0, 0, (int) (bottomMargin * (1 - interpolatedTime)));
                mFooterView.requestLayout();
            }
        };

        moveUpAnim.setDuration(HEADER_FOOTER_ANIM_DURATION);
        moveUpAnim.setFillAfter(true);
        moveUpAnim.setInterpolator(new DecelerateInterpolator());
        mFooterView.startAnimation(moveUpAnim);
    }


}
