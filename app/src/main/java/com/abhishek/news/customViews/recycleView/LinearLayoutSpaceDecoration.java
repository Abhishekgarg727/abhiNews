package com.abhishek.news.customViews.recycleView;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.news.MainApplication;
import com.abhishek.news.utils.ClassUtility;


public class LinearLayoutSpaceDecoration extends RecyclerView.ItemDecoration {

    private int itemPadding;
    private int firstItemTopPadding;
    private int lastItemBottomPadding;


    public LinearLayoutSpaceDecoration(int itemPadding, int firstItemTopPadding, int lastItemBottomPadding) {
        this.itemPadding = ClassUtility.getValueInDP(MainApplication.getAppContext(), itemPadding);
        this.firstItemTopPadding = ClassUtility.getValueInDP(MainApplication.getAppContext(), firstItemTopPadding);
        this.lastItemBottomPadding = ClassUtility.getValueInDP(MainApplication.getAppContext(), lastItemBottomPadding);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }
        int orientation = getOrientation(parent);
        final int itemCount = state.getItemCount();

        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        // HORIZONTAL
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            // all positions
            left = itemPadding;
            right = itemPadding;

            // first position
            if (itemPosition == 0) {
                left = left + firstItemTopPadding;
            }
            // last position
            else if (itemCount > 0 && itemPosition == itemCount - 1) {
                right = right + lastItemBottomPadding;
            }
        }
        // VERTICAL
        else {
            // all positions
            top = itemPadding;
            bottom = itemPadding;

            // first position
            if (itemPosition == 0) {
                top += firstItemTopPadding;
            }
            // last position
            else if (itemCount > 0 && itemPosition == itemCount - 1) {
                bottom += lastItemBottomPadding;
            }
        }

        if (!isReverseLayout(parent)) {
            outRect.set(left, top, right, bottom);
        } else {
            outRect.set(right, bottom, left, top);
        }
    }

    private boolean isReverseLayout(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getReverseLayout();
        } else {
            throw new IllegalStateException("PaddingItemDecoration can only be used with a LinearLayoutManager.");
        }
    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else {
            throw new IllegalStateException("PaddingItemDecoration can only be used with a LinearLayoutManager.");
        }
    }
}
