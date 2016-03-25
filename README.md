# CommonAdapter
包括简单通用适配器`CommonAdapter<T>`、多布局类型的适配器`MultiTypeCommonAdapter<T>`、第一个列表项为HeaderView的适配器`WithHeaderAdapter<T>`。
## CommonAdapter<T>
简单通用适配器.对于只有一种布局文件，且其适配器只用一次，就无需新建适配器类，可采用匿名类的方式
实现`bindData(CommonViewHolder viewHolder, T data)`方法即可。
例子：
```java
        listView = (ListView) findViewById(R.id.lv_1);
        gameBeanAdapter = new CommonAdapter<GameBean>(this, gameBeanList, R.layout.listitem_game) {
            @Override
            public void bindData(CommonViewHolder viewHolder, GameBean data) {
                viewHolder.setImageResource(R.id.iv_logo, Integer.valueOf(data.getImg_url()));
                viewHolder.setText(R.id.tv_name, data.getName());
            }
        };
        listView.setAdapter(gameBeanAdapter);
```
> @param viewHolder  列表项View的Holder，可通过`CommonViewHolder#getView(int)`获取当前列表项中id为viewId的View对象，
可通过`CommonViewHolder#getPosition()`获取当前列表项的position.<br/>
> @param data  数据列表中该位置的数据实体
     
## MultiTypeCommonAdapter<T>
多类型布局适配器。简化多种布局文件的View操作。

> 建议：将所有列表项数据统一，以便通过对象辨别列表项类型：</br>{所有布局类型的类型标志常量，表明当前类型的变量，数据对象}

如：
```java
    public static final String TYPE_GAME = "game";
    public static final String TYPE_VIDEO = "video";

    private String eventType;
    private GameBean gameBean;
    private VideoBean videoBean;
```
然后实现3个方法：
- `getViewTypeCount()`，返回布局类型数量
- `getItemViewType(int position, T data)`，返回0~getViewTypeCount()-1之间的整数(可由position或data.getType()决定具体返回值）
- `getLayoutId(int position, T data)`，返回布局文件id(可由position或data.getType()决定具体返回值）

## WithHeaderAdapter<T>
第一个列表项为HeaderView的通用适配器。建议使用`ListView#addHeaderView(View)`替代，而不是使用该适配器。
需实现`CommonAdapter#bindData(CommonViewHolder, Object)`方法,
如果data为`null`表明该列表项是HeaderView

> 建议：`bindData(CommonViewHolder, Object)`方法的代码格式为：

```java
        if(data == null) {
                //初始化HeaderView...
                return;
        }
        //正常数据绑定...
```
 
