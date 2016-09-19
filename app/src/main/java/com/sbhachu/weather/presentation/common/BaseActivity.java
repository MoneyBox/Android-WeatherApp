package com.sbhachu.weather.presentation.common;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.sbhachu.weather.R;
import com.sbhachu.weather.model.state.LocalStore;

public abstract class BaseActivity<V extends BasePresenter> extends AppCompatActivity {

    protected Snackbar snackbar;
    protected LinearLayout snackbarContainer;
    protected Toolbar toolbar;
    private V presenter;
    private Bundle extras;
    private Dialog dialog;
    private LocalStore localStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        extras = loadExtras();
        handleExtras();

        localStore = new LocalStore(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        presenter = createPresenter();

        initialiseViews();
        initialiseViewListeners();

        snackbarContainer = (LinearLayout) findViewById(R.id.snackbar_container);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null && presenter.getView() != null) {
            presenter.detachView();
            presenter = null;
        }

        super.onDestroy();
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    protected abstract void initialiseViews();

    protected abstract void initialiseViewListeners();

    protected abstract V createPresenter();

    protected abstract int getLayout();

    protected V getPresenter() {
        return presenter;
    }

    @Nullable
    private Bundle loadExtras() {
        final Intent intent = getIntent();
        if (intent != null) {
            return intent.getExtras();
        }
        return null;
    }

    protected Bundle getExtras() {
        return extras;
    }

    protected abstract void handleExtras();

    protected void showDialog(AlertDialog.Builder dialogBuilder) {
        dialog = dialogBuilder.create();
        dialog.show();
    }

    protected void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
