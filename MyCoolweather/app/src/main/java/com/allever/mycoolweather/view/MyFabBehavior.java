package com.allever.mycoolweather.view;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * Created by allever on 17-6-9.
 */
public class MyFabBehavior extends FloatingActionButton.Behavior {
    private static final FastOutSlowInInterpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private float viewY ;//距离底部的距离
    private boolean isAnim = false;

    public MyFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        if(child.getVisibility() == View.VISIBLE && viewY == 0){
            viewY = coordinatorLayout.getHeight() - child.getY();
        }
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);//判断是否竖直滚动;
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        if (dyConsumed >=0 && child.getVisibility() == View.VISIBLE && !isAnim) {
            hide(child);
        } else  if(dyConsumed < 0 && child.getVisibility() == View.INVISIBLE && !isAnim){
            show(child);
        }
    }

    private void show(final FloatingActionButton child) {
        ViewPropertyAnimator animator = child.animate().translationY(0).setInterpolator(INTERPOLATOR);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                child.setVisibility(View.VISIBLE);
                isAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnim = false;
                child.hide();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();
    }

    private void hide(final FloatingActionButton child) {
        ViewPropertyAnimator animator = child.animate().translationY(viewY).setInterpolator(INTERPOLATOR);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                child.setVisibility(View.INVISIBLE);
                isAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnim = false;
                child.show();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}