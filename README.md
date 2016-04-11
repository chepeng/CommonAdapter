# CommonAdapter
包括：
* 通用适配器`CommonAdapter<T>`
* 多布局类型的适配器`MultiTypeCommonAdapter<T>`
* 第一个列表项为HeaderView的适配器（不建议使用）`WithHeaderAdapter<T>`
* ExpandableListView的通用适配器`ExpandableListCommonAdapter<T>`

## CommonAdapter<T>
通用适配器。封装了convertView复用及findViewById()，提供静态通用ViewHolder。对于只有一种布局文件，且其适配器只用一次，就无需新建适配器类，可采用匿名类的方式
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
子类需实现4个方法：
* `getItemViewTypeCount()`，返回布局类型数量
* `getItemViewType(int position, T data)`，返回0~getViewTypeCount()-1之间的整数(可由position或data.getType()决定具体返回值）
* `getLayoutId(int position, T data)`，返回布局文件id(可由position或data.getType()决定具体返回值）
* `onBindViewHolder(CommonViewHolder viewHolder, T data)`
在`onBindViewHolder(CommonViewHolder viewHolder, T data)`方法中，通过`switch (holder.getLayoutId()){}`可根据不同布局文件id进行不同的数据绑定（详细示例请详见源码的sample）：
```java
    @Override
    public int getLayoutId(int position, TimelineBean data) {
        switch (data.getEventType()) {
            case TimelineBean.TYPE_GAME:
                return R.layout.listitem_game;
            case TimelineBean.TYPE_VIDEO:
                return R.layout.listitem_video;
            default:
                return R.layout.listitem_game;
        }
    }

    @Override
    public int getItemViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position, TimelineBean data) {
        switch (getLayoutId(position, data)) {
            case R.layout.listitem_game:
                return 0;
            case R.layout.listitem_video:
                return 1;
        }
        return 0;
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
## WithHeaderAdapter<T>
第一个列表项为HeaderView的通用适配器。建议使用`ListView#addHeaderView(View)`替代，而不是使用该适配器。<br/>
只需实现`onBindViewHolder(CommonViewHolder viewHolder, T data)`方法,如果data为`null`表明该列表项是HeaderView。

> 建议：`onBindViewHolder(CommonViewHolder viewHolder, T data)`方法的代码格式为（详细示例请详见源码的sample）：

```java
        if(data == null) {
                //绑定HeaderView...
                viewHolder.setText(R.id.tv_header, "Header View!");
                return;
        }
        //正常数据绑定...
        viewHolder.setText(R.id.tv_name, data.getName());
```
## ExpandableListCommonAdapter<T>
ExpandableListView的通用适配器。封装了convertView复用及findViewById()。
子类只须实现：
* `onBindViewHolder(int groupPosition, int childPosition, CommonAdapter.CommonViewHolder viewHolder, T data, boolean isGroup)`
* `getChildrenCount(int groupPosition, T groupData)`
* `getChild(int groupPosition, int childPosition, T groupData)`

三个方法。
在`onBindViewHolder(int groupPosition, int childPosition, CommonAdapter.CommonViewHolder viewHolder, T data, boolean isGroup)`方法
中，可通过isGroup判断是Group还是Child（详细示例请详见源码的sample）：
```java
    public void onBindViewHolder(int groupPosition, int childPosition, CommonAdapter.CommonViewHolder viewHolder, GameTypeBean data, boolean isGroup) {
        if (isGroup) {
            //绑定组的数据...
            viewHolder.setText(R.id.tv_game_type, data.getName());
        } else {
            //绑定子项的数据...
            viewHolder.setText(R.id.tv_game_name, data.getGameBeanList().get(childPosition).getName());
        }
    }
```
## 感谢
[hongyangAndroid/base-adapter](https://github.com/hongyangAndroid/base-adapter)
