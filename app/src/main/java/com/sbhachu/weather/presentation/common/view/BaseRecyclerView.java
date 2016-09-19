package com.sbhachu.weather.presentation.common.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseRecyclerView<V extends View> extends RecyclerView.ViewHolder {

    private final V view;

    public BaseRecyclerView(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}
