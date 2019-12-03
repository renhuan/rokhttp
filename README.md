
## 使用

一、引入

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
	dependencies {
	        implementation 'com.github.renhuan2015:MyOkHttp:2.0.0'
	}
	

二、首先你需要在你的项目中新建一个[BaseOkHttp](https://github.com/renhuan2015/MyOkHttp/blob/master/app/src/main/java/com/example/administrator/myokhttp/config/BaseOkHttp.kt)
类继承RBaseOkHttp类。
作用是：
1. 配置全局的请求头部或者全局公共上传参数
2. 处理成功回调的全局状态，如code=1，表示请求成功，开始成功回调，否则，失败回调处理
3. 显示隐藏全局Loading等

三、API调用 [demo](https://github.com/renhuan2015/MyOkHttp/blob/master/app/src/main/java/com/example/administrator/myokhttp/config/Api.kt)

      
        fun sendRegisterVerify(phone: String, baseCall: RBaseOkHttpImp) {
        BaseOkHttp.newInstance()
                .setUrl(BuildConfig.BASE_URL + "/app/user/register/verify")
                .setCallBack(baseCall, Constants.GET_REGISTERVERIFY)
                .setParameter(hashMapOf(
                        "country" to "86",
                        "phone" to phone
                ))
                .postAsJson()
    }
      
 ## 说明
 
 此库用到的第三方库，加上一些常用的封装，如eventBus，crash,RecyclerView等
 
    api 'androidx.appcompat:appcompat:1.1.0'
    api 'com.google.android.material:material:1.0.0'
    //okgo
    api 'com.lzy.net:okgo:3.0.4'
    //按钮shape
    api 'com.noober.background:core:1.6.0'
    //eventBus
    api 'org.greenrobot:eventbus:3.1.1'
    //utils
    api 'com.blankj:utilcodex:1.25.9'
    //baseadapter
    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.47'
    //圆形头像
    api 'de.hdodenhof:circleimageview:3.0.1'
    //glide
    api 'com.github.bumptech.glide:glide:4.10.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'
    //布局适配
    api 'me.jessyan:autosize:1.1.2'
    //相册选择
    api 'com.zhihu.android:matisse:0.5.3-beta1'
    //相册选择后裁剪
    api 'com.github.yalantis:ucrop:2.2.3'
    //gson
    api 'com.google.code.gson:gson:2.8.6'

    api "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

    api 'com.wuyr:activitymessenger:1.0.3'
