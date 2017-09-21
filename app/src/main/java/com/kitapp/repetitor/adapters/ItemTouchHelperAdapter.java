package com.kitapp.repetitor.adapters;

/**
 * Created by denis on 9/21/17.
 */

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
