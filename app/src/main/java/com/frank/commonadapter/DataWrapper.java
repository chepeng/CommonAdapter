package com.frank.commonadapter;

public abstract class DataWrapper<T> {

    private T mData;

    public DataWrapper(T data) {
        this.mData = data;
    }

    public T getData() {
        return this.mData;
    }

    public abstract  void getLayoutId();
}
