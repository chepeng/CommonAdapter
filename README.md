# CommonAdapter
通用适配器，对于只有一种布局文件，且其适配器只用一次，就无需新建适配器类，可采用匿名类的方式，
实现bindData(CommonViewHolder holder, T t)方法即可。对于多种类型布局文件的适配器，要继承MultiItemCommonAdapter<T>，
并实现3个方法：</br>
①getViewTypeCount() ，返回类型数量</br>
②getItemViewType(int position, T t)，返回0~size -1的整数(根据position位置值或t.getEventType()业务逻辑）</br>
③getLayoutId(int position, T t)，返回布局文件id(根据position位置值或t.getEventType()业务逻辑）</br>
