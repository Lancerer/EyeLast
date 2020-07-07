
#### 数据仓库model包内(分两块:dataBase(数据库)，api(网络接口))

- class MainPageRepository private constructor(private val mainPageDao: MainPageDao, private val eyeLastNetWork: EyeLastNetWork){}

- 网络请求步骤(数据库类似，最终都会在MainPageRepository这个数据仓库中集合供viewModel调用)
    1. 在MainPageApiService接口文件中定义retrofit的请求接口
    2. 在EysLastNetWork文件中注册对应接口方法(EysLastNetWork类主要是管理网络请求接口类)
    3. 在MainPageRepository中就是真的数据仓库，里面包含网络数据和数据库数据，其中网络数据返回一个Observable
    4. 对应的viewModel构造器传入参数MainPageRepository，也就是用得到的数据，在viewModel中请求数据
    5. 对应view(activity,fragment)中使用viewModel获得的数据做处理
    6. MainPageApiService->EyeLastNetWork->MainPageRepository->viwModel->fragment,activity
    
    
    

#### 第三方库使用以及一些知识点
- Fragment懒加载(旧版使用setUserVisibleHint，FragmentPageAdapter中的behavior设置为BEHAVIOR_SET_USER_VISIBLE_HINT，新版使用setMaxLifecycle，
FragmentPagerAdapter中的behavior设置为BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

- SmartRefreshLayout的使用
- BaseRecyclerViewAdapterHelper的使用
- RxJava和RxAndroid的使用
- Glide的使用
- viewModel的使用
- multiple-status_view使用：