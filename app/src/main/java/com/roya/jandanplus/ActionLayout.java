package com.roya.jandanplus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;


public class ActionLayout extends RelativeLayout{

    private static final String TAG = "ActionLayout";
    Context context;
    protected int HIDDEN_TOP = 0;
    protected int HIDDEN_BOTTOM = 1;
    protected int HIDDEN_LEFT = 2;
    protected int HIDDEN_RIGHT = 3;
    int hiddenOrientation = 1;

    private boolean animationNOTRuning = true;

    private float dpi=0;
    private int viewHeight=0;
    private int viewWidth=0;
    private int topMargin = 0;
    private int bottomMargin = 0;
    private int leftMargin = 0;
    private int rightMargin = 0;
    private long animationDuration = 1000;

    public ActionLayout(final Context context) {
        super(context);
        this.context = context;
        this.dpi = context.getResources().getDisplayMetrics().density;

    }
    public ActionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.dpi = context.getResources().getDisplayMetrics().density;
    }

    public ActionLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.dpi = context.getResources().getDisplayMetrics().density;
    }

    public void setAnimationDuration(long millisecond){
        animationDuration = millisecond;
    }

    public void setHiddenOrientation(int HiddenOrientation){
        hiddenOrientation = HiddenOrientation;
    }

    public ActionLayout getActionLayout(){
        return this;
    }

    public void setBottomMargin(int bottomMarginDP){
        this.bottomMargin =bottomMarginDP;
    }
    public void setTopMargin(int topMarginDP){
        this.topMargin = topMarginDP;
    }
    public void setLeftMargin(int leftMarginDP){
        this.leftMargin=leftMarginDP;

    }
    public void setRightMargin(int rightMarginDP){
        this.rightMargin = rightMarginDP;
    }
    public void setViewHeight(int viewHeightDP){
        this.viewHeight=viewHeightDP;
    }
    public void setViewWidth(int viewWidthDP){
        this.viewWidth=viewWidthDP;
    }

    public void hide(){
        if (animationNOTRuning) {

            TranslateAnimation translateAnimation ;

            if (hiddenOrientation == HIDDEN_BOTTOM) {
                translateAnimation = new TranslateAnimation(
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, this.viewHeight * this.dpi + this.bottomMargin * this.dpi);
            }else if (hiddenOrientation == HIDDEN_TOP){
                translateAnimation = new TranslateAnimation(
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, -(this.viewHeight * this.dpi + this.topMargin * this.dpi));
            }else if (hiddenOrientation == HIDDEN_RIGHT) {
                translateAnimation = new TranslateAnimation(
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, this.viewWidth * this.dpi + this.rightMargin * this.dpi,
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f);
            }else {
                translateAnimation = new TranslateAnimation(
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, -(this.viewWidth * this.dpi + this.leftMargin * this.dpi),
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f);
            }

            translateAnimation.setDuration(animationDuration);
            translateAnimation.setFillAfter(true);

            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    animationNOTRuning = false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    animationNOTRuning = true;
                    getActionLayout().setVisibility(INVISIBLE);
                    getActionLayout().clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            this.startAnimation(translateAnimation);

        }
    }

    public void show(){
        if (animationNOTRuning) {

            TranslateAnimation translateAnimation = null;

            if (hiddenOrientation == HIDDEN_BOTTOM) {
                translateAnimation = new TranslateAnimation(
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, this.viewHeight * this.dpi + this.bottomMargin * this.dpi,
                        Animation.ABSOLUTE, 0f);
            }else if (hiddenOrientation == HIDDEN_TOP){
                translateAnimation = new TranslateAnimation(
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, -(this.viewHeight * this.dpi + this.topMargin * this.dpi),
                        Animation.ABSOLUTE, 0f);
            }else if (hiddenOrientation == HIDDEN_RIGHT) {
                translateAnimation = new TranslateAnimation(
                        Animation.ABSOLUTE, this.viewWidth * this.dpi + this.bottomMargin * this.dpi,
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f);
            }else {
                translateAnimation = new TranslateAnimation(
                        Animation.ABSOLUTE, -(this.viewWidth * this.dpi + this.leftMargin * this.dpi),
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f,
                        Animation.ABSOLUTE, 0f);
            }

            translateAnimation.setDuration(animationDuration);
            translateAnimation.setFillAfter(true);

            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    animationNOTRuning = false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    animationNOTRuning = true;
                    getActionLayout().setVisibility(VISIBLE);
                    getActionLayout().clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            this.startAnimation(translateAnimation);

        }
    }
}
