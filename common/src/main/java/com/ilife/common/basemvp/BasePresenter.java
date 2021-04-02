package com.ilife.common.basemvp;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IBaseView, M extends BaseModel, CONTRACT> {
    protected M model;

    //Keep a weak reference of view in presenter so we won't leak the view such as Activity or Fragment
    private WeakReference<V> weakReferenceView;

    public BasePresenter() {
        this.model = getModel();
    }

    public V getView() {
        if (weakReferenceView != null) {
            return weakReferenceView.get();
        }

        return null;
    }

    public abstract CONTRACT getContract();

    public abstract M getModel();

    public void bindView(V view) {
        weakReferenceView = new WeakReference<>(view);
    }

    public void unBindView() {
        if (weakReferenceView != null) {
            weakReferenceView.clear();
            weakReferenceView = null;
        }
    }
}
