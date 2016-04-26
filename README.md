# CommonAdapter
包括：
* 通用适配器`CommonAdapter<T>`
* 多布局类型的通用适配器`MultiTypeCommonAdapter<T>`
* ExpandableListView的通用适配器`ExpandableListCommonAdapter<T>`
* 分类列表的通用适配器`SectionCommonAdapter<T>`
* 第一个列表项为HeaderView的通用适配器<font color=red>(不建议使用)</font>`WithHeaderAdapter<T>`

## CommonAdapter<T>
简单通用适配器。封装了convertView复用及findViewById()，提供静态通用ViewHolder。对于只有一种布局文件，且其适配器只用一次，就无需新建适配器类，可采用匿名类的方式
实现`onBindViewHolder(CommonViewHolder viewHolder, T data)`方法即可。
例子（详细示例请详见源码的sample）：
```java
        listView = (ListView) findViewById(R.id.lv_1);
        gameBeanAdapter = new CommonAdapter<GameBean>(this, gameBeanList, R.layout.listitem_game) {
            @Override
            public void onBindViewHolder(CommonViewHolder viewHolder, GameBean data) {
                viewHolder.setImageResource(R.id.iv_logo, Integer.valueOf(data.getImg_url()));
                viewHolder.setText(R.id.tv_name, data.getName());
            }
        };
        listView.setAdapter(gameBeanAdapter);
```
>viewHolder：列表项View的Holder，可通过`CommonViewHolder#getView(int)`获取当前列表项中ID为viewId的View对象，可通过`CommonViewHolder#getPosition()`获取当前列表项的position.

>data：数据列表中该位置的数据实体
     
## MultiTypeCommonAdapter<T>
多类型布局适配器。简化多种布局文件的View操作。

>建议：将所有列表项数据规范为一种类型，以便通过对象辨别列表项类型：

>{所有布局类型的类型标志常量，表明当前类型的变量，数据对象}

例如：
```java
public class TimelineBean {

    public static final String TYPE_GAME = "game";
    public static final String TYPE_VIDEO = "video";

    private String eventType;
    
    private GameBean gameBean;
    private VideoBean videoBean;
    
    //Getter/Setter...
}
```
组装好的实体类List并实现以下方法：
* `getItemViewTypeCount()`. 返回布局类型个数
* `getItemViewType(int layoutId, int position, T data)`. 返回[0,getItemViewTypeCount()-1]的整数(由layoutId或position决定具体返回值）
* `getLayoutId(int position, T data)`. 返回布局文件id(由position或data.getType()决定具体返回值）
* `onBindViewHolder(CommonViewHolder holder, T data)`. 绑定ViewHolder(由holder.getLayoutId()或data.getType()决定具体View绑定）

如：
```java
    @Override
    public int getItemViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int layoutId, int position, TimelineBean data) {
        switch (layoutId) {
            case R.layout.listitem_game:
                return 0;
            case R.layout.listitem_video:
                return 1;
        }
        return 0;
    }

    @Override
    public int getLayoutId(int position, TimelineBean data) {
        switch (data.getEventType()) {
            case TimelineBean.TYPE_GAME:
                return R.layout.listitem_game;
            case TimelineBean.TYPE_VIDEO:
                return R.layout.listitem_video;
        }
        return R.layout.listitem_game;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, TimelineBean data) {
        switch (holder.getLayoutId()) {
            case R.layout.listitem_game:
                holder.setImageResource(R.id.iv_logo, Integer.valueOf(data.getGameBean().getImg_url()));
                holder.setText(R.id.tv_name, data.getGameBean().getName());
                break;
            case R.layout.listitem_video:
                holder.setImageResource(R.id.iv_video, Integer.valueOf(data.getVideoBean().getVideo_logo_url()));
                holder.setText(R.id.tv_name, data.getVideoBean().getName());
                holder.setText(R.id.tv_desc, data.getVideoBean().getDesc());
                break;
        }
    }
```
## ExpandableListCommonAdapter<T>
ExpandableListView的通用适配器。封装了convertView复用及findViewById()。
子类只须实现：
* `onBindGroupViewHolder(int groupPosition, CommonAdapter.CommonViewHolder viewHolder, T groupData)`
* `onBindChildViewHolder(int groupPosition, int childPosition, CommonAdapter.CommonViewHolder viewHolder, T groupData)`
* `getChildrenCount(int groupPosition, T groupData)`
* `getChild(int groupPosition, int childPosition, T groupData)`

## SectionCommonAdapter<T>
分类列表的通用适配器。即将列表分类并给每一类添加一个分类Bar。只需实现`getSectionTitle(T data)`方法即可。构造器(Context context,BaseAdapter listAdapter,int sectionLayoutId,int sectionTitleId)，即包裹列表原来的构造器，因此设置监听器时需使用该监听器并使用该监听器的`notifyDataSetChanged()`方法刷新数据：
```java
        listView = (ListView) findViewById(R.id.lv_1);
        timelineBeanAdapter = new MultiTypeAdapter(this, timelineBeanList);
        sectionCommonAdapter = new SectionCommonAdapter<TimelineBean>(this, timelineBeanAdapter, R.layout.listitem_section, R.id.tv_section) {
            @Override
            public String getSectionTitle(TimelineBean data) {
                if (TimelineBean.TYPE_GAME.equals(data.getEventType())) {
                    return "All Game";
                } else{
                    return "All Video";
                }
            }
        };
        listView.setAdapter(sectionCommonAdapter);
```
## WithHeaderAdapter<T><font color=gray>(不建议使用)</font>
第一个列表项为HeaderView的通用适配器。建议使用`ListView#addHeaderView(View)`替代，而不是使用该适配器。<br/>
实现`onBindHeader(CommonViewHolder)`方法初始化Header,实现`onBindViewHolder(CommonViewHolder, Object)`方法绑定数据。
```java
        listView = (ListView) findViewById(R.id.lv_1);
        gameBeanAdapter = new WithHeaderAdapter<GameBean>(this, R.layout.listitem_header,
                R.layout.listitem_game, gameBeanList) {

            @Override
            public void onBindHeader(CommonViewHolder viewHolder) {
                viewHolder.setImageResource(R.id.iv_header, android.R.drawable.ic_menu_camera);
                viewHolder.setText(R.id.tv_header, "I am HeaderView!");
            }

            @Override
            public void onBindViewHolder(CommonViewHolder viewHolder, GameBean data) {
                viewHolder.setImageResource(R.id.iv_logo, Integer.valueOf(data.getImg_url()));
                viewHolder.setText(R.id.tv_name, data.getName());
            }
        };
        listView.setAdapter(gameBeanAdapter);
```
## 感谢
- [JoanZapata/base-adapter-helper](https://github.com/JoanZapata/base-adapter-helper)
- [ragunathjawahar/simple-section-adapter](https://github.com/ragunathjawahar/simple-section-adapter)
