# CommonAdapter
- CommonAdapter<T>
通用适配器，对于只有一种布局文件，且其适配器只用一次，就无需新建适配器类，可采用匿名类的方式
实现bindData(CommonViewHolder viewHolder, T data)方法即可。
例子：

        listView = (ListView) findViewById(R.id.lv_1);
        gameBeanAdapter = new CommonAdapter<GameBean>(this, gameBeanList, R.layout.listitem_game) {
            @Override
            public void onBindViewHolder(CommonViewHolder holder, GameBean gameBean) {
                holder.setImageResource(R.id.iv_logo, Integer.valueOf(gameBean.getImg_url()));
                holder.setText(R.id.tv_name, gameBean.getName());
            }
        };
        listView.setAdapter(gameBeanAdapter);
        
- MultiTypeCommonAdapter<T>
多类型布局适配器<br/>
建议：先将所有类型数据统一为一个实体类：{所有布局类型的类型标志常量，表明当前类型的变量，数据对象}，
如：<br/>
        public static final String TYPE_GAME = "game";<br/>
        public static final String TYPE_VIDEO = "video";<br/>
        private String eventType;<br/>
        private GameBean gameBean;<br/>
        private VideoBean videoBean;<br/>
然后实现3个方法：<br/>
①getViewTypeCount()，返回布局类型数量<br/>
②getItemViewType(int position, T data)，返回0~ getViewTypeCount()-1的整数(可由position或data.getType()决定具体返回值）<br/>
③getLayoutId(int position, T data)，返回布局文件id(可由position或data.getType()决定具体返回值）
- WithHeaderAdapter<T>
带头布局列表项的通用适配器<br/>
需实现{@link CommonAdapter#onBindViewHolder(CommonViewHolder, Object)}方法,
如果Object为 {@code null} 表明该列表项是HeaderView
 
