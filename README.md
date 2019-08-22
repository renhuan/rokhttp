
## 使用

一、引入

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
	dependencies {
	        implementation 'com.github.renhuan2015:MyOkHttp:1.0.8'
	}
	

二、首先你需要在你的项目中新建一个[BaseOkHttp](https://github.com/renhuan2015/MyOkHttp/blob/master/app/src/main/java/com/example/administrator/myokhttp/config/BaseOkHttp.java)
类继承RBaseOkHttp类。
作用是：
1. 配置全局的请求头部或者全局公共上传参数
2. 处理成功回调的全局状态，如code=1，表示请求成功，开始成功回调，否则，失败回调处理
3. 显示隐藏全局Loading等

三、API调用

      
        HashMap<String, String> hashMap = new HashMap<>();
        new BaseOkHttp()
            .setUrl(BuildConfig.BASE_URL + "address/list")
            .setOkhttpImp(okhttpIml)
            .setParameter(hashMap)
            .get();
      
 ## 说明
 
 此库用到的第三方库，加上一些常用的封装，如eventBus等，crash等
 
     //okgo  okhttp
    api 'com.lzy.net:okgo:3.0.4'
    //按钮shape
    api 'com.noober.background:core:1.4.0'
    //eventBus
    api 'org.greenrobot:eventbus:3.1.1'
    //utils
    api 'com.blankj:utilcode:1.22.0'
    //baseadapter
    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.30'

    //圆形头像
    api 'de.hdodenhof:circleimageview:2.2.0'

    api 'com.alibaba:fastjson:1.2.57'
    api 'com.github.bumptech.glide:glide:4.9.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'
    api 'me.jessyan:autosize:1.1.2'
    api 'com.scwang.smartrefresh:SmartRefreshLayout:1.1.0-alpha-26'

    api 'com.billy.android:gloading:1.0.1'
