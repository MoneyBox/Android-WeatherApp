package com.sbhachu.weather.presentation.common.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HorizontalDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;
    private int orientation;

    public HorizontalDividerItemDecoration(Drawable divider) {
        this.divider = divider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }

        orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.left = divider.getIntrinsicWidth();
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.top = divider.getIntrinsicHeight();
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            final int parentTop = parent.getPaddingTop();
            final int parentBottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                final View child = parent.getChildAt(i);

                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                final int parentLeft = child.getRight() + params.rightMargin;
                final int parentRight = parentLeft + divider.getIntrinsicWidth();

                divider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
                divider.draw(canvas);
            }
        }
    }
}
