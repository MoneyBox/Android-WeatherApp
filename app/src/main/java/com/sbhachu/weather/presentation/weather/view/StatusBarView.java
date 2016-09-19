package com.sbhachu.weather.presentation.weather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbhachu.weather.R;

public class StatusBarView extends LinearLayout implements View.OnClickListener {

    private TextView updatedDateTextView;
    private ImageView refreshImageView;

    private Actions actions;

    public StatusBarView(Context context) {
        this(context, null, 0);
    }

    public StatusBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialiseView();
    }

    private void initialiseView() {
        final View root = LayoutInflater.from(getContext()).inflate(R.layout.view_status_bar, this);

        updatedDateTextView = (TextView) root.findViewById(R.id.tv_updated);
        refreshImageView = (ImageView) root.findViewById(R.id.iv_refresh);

        refreshImageView.setOnClickListener(this);
    }

    public void bindData(final String lastUpdatedDate) {
        updatedDateTextView.setText(getResources().getString(R.string.last_updated, lastUpdatedDate));
    }

    @Override
    public void onClick(View view) {
        if (view.equals(refreshImageView) && actions != null) {
            actions.refresh();
        }
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public interface Actions {
        void refresh();
    }
}
