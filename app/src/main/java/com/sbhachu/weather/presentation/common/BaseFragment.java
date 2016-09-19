package com.sbhachu.weather.presentation.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<V extends BasePresenter> extends Fragment {

    private V presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(getLayout(), container, false);
        initialiseViews(root);
        initialiseViewListeners();
        return root;
    }

    @Override
    public void onDestroy() {
        if (presenter != null && presenter.getView() != null) {
            presenter.detachView();
            presenter = null;
        }

        super.onDestroy();
    }

    protected abstract void initialiseViews(@NonNull View root);

    protected abstract void initialiseViewListeners();

    protected abstract V createPresenter();

    protected V getPresenter() {
        return presenter;
    }

    protected abstract int getLayout();
}
