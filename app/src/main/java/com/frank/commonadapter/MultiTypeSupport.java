package com.frank.commonadapter;

/**
 * 多类型复杂布局列表的适配器必须实现的三个方法
 * @param <T> 数据实体类型
 */
public interface MultiTypeSupport<T> {

    int getLayoutId(int position, T data);

    int getViewTypeCount();

    int getItemViewType(int position, T data);
}
