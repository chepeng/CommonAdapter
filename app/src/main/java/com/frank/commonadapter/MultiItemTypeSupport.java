package com.frank.commonadapter;

/**
 * 多类型复杂布局列表的适配器必须实现的三个方法
 * @param <T>
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int position, T t);

    int getViewTypeCount();

    int getItemViewType(int postion, T t);
}
