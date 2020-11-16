package com.mytech.basiclayoutmanagerexample.custom;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLinearSnapLayoutManager extends LinearLayoutManager {
    private final float shrinkAmount = 0.15f;
    private final float shrinkDistance = 0.9f;
    Context context;
    //Make an instance variable at the top of you     LayoutManager
    private static final float MILLISECONDS_PER_INCH = 50f;

    public CustomLinearSnapLayoutManager(Context context) {
        super(context);
        this.context = context;
    }

    public CustomLinearSnapLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.context = context;
    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = getOrientation();
        if (orientation == VERTICAL) {
            int scrolled = super.scrollVerticallyBy(dy, recycler, state);
            float midpoint = getHeight() / 2.f;
            float d0 = 0.f;
            float d1 = shrinkDistance * midpoint;
            float s0 = 1.f;
            float s1 = 1.f - shrinkAmount;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                float childMidpoint =
                        (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.f;
                float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scale);
                child.setScaleY(scale);
                if (scale > .9f) {
                    child.setAlpha(1f);
                } else {
                    child.setAlpha(0.5f);
                }
            }
            return scrolled;
        } else {
            return 0;
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = getOrientation();
        if (orientation == HORIZONTAL) {
            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);

            float midpoint = getWidth() / 2.f;
            float d0 = 0.f;
            float d1 = shrinkDistance * midpoint;
            float s0 = 1.f;
            float s1 = 1.f - shrinkAmount;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                float childMidpoint =
                        (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
                float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scale);
                child.setScaleY(scale);
                if (scale > .9f) {
                    child.setAlpha(1f);
                } else {
                    child.setAlpha(0.5f);
                }
            }
            return scrolled;
        } else {
            return 0;
        }

    }


    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        //Create     RecyclerView.SmoothScroller instance? Check.
        LinearSmoothScroller smoothScroller =
                new LinearSmoothScroller(context) {

                    //Automatically implements     method on instantiation.
                    //This controls the direction in     smoothScroll looks for your
//list item
                    @Override
                    public PointF computeScrollVectorForPosition
                    (int targetPosition) {
                        //What is PointF? A class         holds two float coordinates.
                        //Accepts a (x , y)
                        //for y: use -1 for up direction, 1 for down direction.
                        //for x (did not test): use -1 for     direction, 1 for right
                        //direction.
                        //We let our custom LinearLayoutManager calculate PointF for us
                        return CustomLinearSnapLayoutManager.this.computeScrollVectorForPosition
                                (targetPosition);
                    }

                    //The holy grail of smooth scrolling
                    //returns the milliseconds it takes to scroll one pixel.
                    @Override
                    protected float calculateSpeedPerPixel
                    (DisplayMetrics displayMetrics) {
                        return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                    }

                    @Override
                    protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };

        //Docs do not tell us anything     this,
        //but we need to set the position we   to scroll to.
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }
}
