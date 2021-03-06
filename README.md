
### 数据仓库model包内(分两块:dataBase(数据库)，api(网络接口))

- class MainPageRepository private constructor(private val mainPageDao: MainPageDao, private val eyeLastNetWork: EyeLastNetWork){}

- 网络请求步骤(数据库类似，最终都会在MainPageRepository这个数据仓库中集合供viewModel调用)
    1. 在MainPageApiService接口文件中定义retrofit的请求接口
    2. 在EysLastNetWork文件中注册对应接口方法(EysLastNetWork类主要是管理网络请求接口类)
    3. 在MainPageRepository中就是真的数据仓库，里面包含网络数据和数据库数据，其中网络数据返回一个Observable
    4. 对应的viewModel构造器传入参数MainPageRepository，也就是用得到的数据，在viewModel中请求数据
    5. 对应view(activity,fragment)中使用viewModel获得的数据做处理
    6. MainPageApiService->EyeLastNetWork->MainPageRepository->viwModel->fragment,activity
    
    
    

### 第三方库使用以及一些知识点

#### Fragment懒加载
- (旧版使用setUserVisibleHint，FragmentPageAdapter中的behavior设置为BEHAVIOR_SET_USER_VISIBLE_HINT，新版使用setMaxLifecycle，
FragmentPagerAdapter中的behavior设置为BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

#### SmartRefreshLayout的使用
- setOnRefreshListener:下拉刷新监听方法
- setOnLoadMoreListener:上拉加载更多监听方法
- RefreshLayout.state:None: 正常无状态
- RefreshLayout.state:Refreshing: 正在刷新
- RefreshLayout.state:Loading: 加载更多
- 头布局，脚布局设置
```kotlin
 //在Application中设置全局统一头布局和角布局，优先级最低，
  init {
        SmartRefreshLayout.setDefaultRefreshInitializer { context, layout ->
            layout.setEnableLoadMore(true)
            layout.setEnableLoadMoreWhenContentNotFull(true)
        }
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setEnableHeaderTranslationContent(true)
            ClassicsHeader(context).setDrawableSize(20f)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setEnableFooterFollowWhenNoMoreData(true)
            layout.setEnableFooterTranslationContent(true)
            layout.setFooterHeight(153f)
            layout.setFooterTriggerRate(0.6f)
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

```
```xml
   //XML布局文件指定
<com.scwang.smart.refresh.layout.SmartRefreshLayout
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/refreshLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#444444"
    app:srlPrimaryColor="#444444"
    app:srlAccentColor="@android:color/white"
    app:srlEnablePreviewInEditMode="true">
    <!--srlAccentColor srlPrimaryColor 将会改变 Header 和 Footer 的主题颜色-->
    <!--srlEnablePreviewInEditMode 可以开启和关闭预览功能-->
    <com.scwang.smart.refresh.header.ClassicsHeader
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="@dimen/dimenPaddingCommon"
        android:background="@android:color/white"
        android:text="@string/description_define_in_xml"/>
    <com.scwang.smart.refresh.footer.ClassicsFooter
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
</com.scwang.smart.refresh.layout.SmartRefreshLayout>

```
```java
//代码设置
final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
//设置 Header 为 贝塞尔雷达 样式
refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
//设置 Footer 为 球脉冲 样式
refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
```



#### BaseRecyclerViewAdapterHelper的使用
- 多布局BaseProviderMultiAdapter
```kotlin
class DiscoveryAdapter(fragment: DiscoveryFragment) : BaseProviderMultiAdapter<Discovery.Item>() {
    init {
        addItemProvider(TextCardViewHeader5ViewHolder(fragment))
        addItemProvider(TextCardViewHeader7ViewHolder(fragment))
        addItemProvider(TextCardViewHeader8ViewHolder(fragment))
        addItemProvider(TextCardViewFooter2ViewHolder(fragment))
        addItemProvider(TextCardViewFooter3ViewHolder(fragment))
        addItemProvider(FollowCardViewHolder(fragment))
        addItemProvider(HorizontalScrollCardViewHolder(fragment))
        addItemProvider(SpecialSquareCardCollectionViewHolder(fragment))
        addItemProvider(ColumnCardListViewHolder(fragment))
        addItemProvider(BannerViewHolder(fragment))
        addItemProvider(Banner3ViewHolder(fragment))
        addItemProvider(VideoSmallCardViewHolder(fragment))
        addItemProvider(TagBriefCardViewHolder(fragment))
        addItemProvider(TopicBriefCardViewHolder(fragment))
        addItemProvider(AutoPlayVideoAdViewHolder(fragment))
    }

    override fun getItemType(data: List<Discovery.Item>, position: Int): Int {
        return RecyclerViewHelp.getItemViewType(data[position])
    }


    inner class TextCardViewHeader5ViewHolder(val fragment: DiscoveryFragment) :
        BaseItemProvider<Discovery.Item>() {
        override val itemViewType: Int
            get() = Const.ItemViewType.TEXT_CARD_HEADER5
        override val layoutId: Int
            get() = R.layout.item_text_card_type_header_five

        override fun convert(helper: BaseViewHolder, item: Discovery.Item) {
            helper.setText(R.id.tvTitle5, item.data.text)

            if (item.data.actionUrl != null) helper.setVisible(R.id.ivInto5, true)
            else helper.setVisible(R.id.ivInto5, false)

            if (item.data.follow != null) helper.setVisible(R.id.tvFollow, true)
            else helper.setVisible(R.id.tvFollow, false)

            val follow = helper.getView<TextView>(R.id.tvFollow)
            follow.setOnClickListener {
                //TODO
            }
        }
    }
}
```

#### RxJava和RxAndroid的使用

#### Glide的使用

#### viewModel的使用

#### multiple-status_view使用
- 外层就是一个RelativeLayout，将他作为最外层的布局然后包裹内容即可，然后就是设置各种状态的布局了

#### GSYVideoPlayer
![](https://raw.githubusercontent.com/Lancerer/GSYVideoPlayer/master/img/StructureChart2.jpg)
- 具体详见PlayerActivity

#### android状态栏(immersionBar)
- **情况一:当我们把ActionBar隐藏后那么整个布局就只剩下：layout+statusBar，这时候我们想要statusBar和Layout颜色一致这样布局才看的舒服**
![](https://upload-images.jianshu.io/upload_images/7048342-cbbb1a88e3ee8260.png?imageMogr2/auto-orient/strip|imageView2/2/w/1080/format/webp)
- **情况二:直接隐藏StatusBar和ActionBar，就只剩下Layout，这样就是全屏模式，一般会出现在视频播放界面**
![](https://upload-images.jianshu.io/upload_images/7048342-3841cfb78965626b.png?imageMogr2/auto-orient/strip|imageView2/2/w/1080/format/webp)
- **两个问题**
    - 当我们的布局是浅色时候，statusBar的颜色也会是变成浅色，但是statusBar的字体是白色的，这样会导致statusBar中的文字看不清楚使用**statusBarDarkFont**解决
    - 当只剩下StatusBar和Layout的时候，可能Layout的布局会和StatusBar布局重叠，使用**fitsSystemWindows**解决
    - 要想设置透明的statusBar使用属性**transparentBar** 即可

```xml
<!--透明状态栏-->
<style name="AppTheme" parent="BaseTheme">
    <!--设置透明状态栏-->
    <item name="android:windowTranslucentStatus">true</item>
    <!--解决视图延伸导致重叠问题-->
    <item name="android:fitsSystemWindows">true</item>
</style>

<!--全屏模式-->
<style name="AppTheme" parent="BaseTheme">
      <item name="android:windowTranslucentStatus">true</item>
        //如果要取消标题栏
      <item name="windowActionBar">false</item>
       <item name="windowNoTitle">true</item>
</style>
```

#### 图片选择器(PictureSelector)
- 摄像头获取照片方式:openCamera()，具体返回的数据在回调forResult中，返回LocalMedia数据的List，里面封装有各种属性，使用即可
- 相册后去照片方式:openGallery(),具体返回的数据在回调forResult中，返回LocalMedia数据的List，可以返回多个对象实现微信九宫格效果

#### 使用到的技术 
- 头像选择:原生方案
- 图片选择，多图选择 :PictureSelector
- 状态栏:immersionBar
- webView
- GSYVideoPlayer
- fileProvider
- 权限管理:RxPermission
- animation使用:LoginActivity
- 社区模块-推荐列表中的viewPager2
- photoView


### TODO 
- 加载问题，现在切换界面后原来的界面整个界面全部更新了，需要查看原因
- 全局view状态的管理(empty,loading,error,networkError)

