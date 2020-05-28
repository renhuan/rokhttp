
## 使用

一、引入

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
	dependencies {
	        implementation 'com.github.renhuan2015:rokhttp:latestVersion'
	}
	
 latestVersion = [![latestVersion](https://jitpack.io/v/renhuan2015/rokhttp.svg)](https://jitpack.io/#renhuan2015/rokhttp)
 
二、首先你需要在你的项目中新建一个[BaseOkHttp](https://github.com/renhuan2015/rokhttp/blob/master/app/src/main/java/com/renhuan/administrator/myokhttp/config/BaseOkHttp.kt)
类继承RBaseOkHttp类。
作用是：
1. 配置全局的请求头部或者全局公共上传参数
2. 处理成功回调的全局状态，如code=1，表示请求成功，开始成功回调，否则，失败回调处理
3. 显示隐藏全局Loading等

三、API调用 [demo](https://github.com/renhuan2015/rokhttp/blob/master/app/src/main/java/com/renhuan/administrator/myokhttp/config/Api.kt)

 	fun requestSendCodeWithRegister(mobile: String, baseCall: RBaseOkHttpImp) {
       	 object : BaseOkHttp<Any>() {}
                .setUrl("${getBaseUrl()}/send_sms_vcode")
                .setParameter(
                        hashMapOf(
                                "mobile" to mobile,
                                "scene" to "register",
                                "type" to "mobile"
                        )
                )
                .setCallBack(baseCall)
                .post()
    }
      
 四、一些常用的工具[Renhuan](https://github.com/renhuan2015/rokhttp/blob/master/okhttplib/src/main/java/com/renhuan/okhttplib/utils/Renhuan.kt)和一些扩展函数[RExtension](https://github.com/renhuan2015/rokhttp/blob/master/okhttplib/src/main/java/com/renhuan/okhttplib/utils/RExtension.kt)
 
 ## 说明
 
 此库用到的第三方库，加上一些常用的封装，如eventBus，crash等
 
    api 'androidx.appcompat:appcompat:1.1.0'
    api 'androidx.constraintlayout:constraintlayout:2.0.0-beta4'
    api 'com.google.android.material:material:1.1.0'
    api 'androidx.legacy:legacy-support-v4:1.0.0'
    api 'androidx.legacy:legacy-support-core-ui:1.0.0'
    api "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72"
    api "org.jetbrains.kotlin:kotlin-android-extensions-runtime:1.3.72"
    api "org.jetbrains.kotlin:kotlin-reflect:1.3.72"
    api 'androidx.multidex:multidex:2.0.1'
    /*** 网络请求 */
    api 'com.lzy.net:okgo:3.0.4'
    /*** 组件通信 */
    api 'org.greenrobot:eventbus:3.1.1'
    /*** 工具类 */
    api 'com.blankj:utilcodex:1.27.5'
    /*** ByRecyclerView */
    api 'com.github.youlookwhat:ByRecyclerView:1.0.18'
    /*** 圆形头像 */
    api 'de.hdodenhof:circleimageview:3.0.1'
    /*** glide图片加载 */
    api 'com.github.bumptech.glide:glide:4.10.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'
    /*** json数据解析 */
    api 'com.google.code.gson:gson:2.8.6'
    /*** 跳转页面startActivity */
    api 'com.wuyr:activitymessenger:1.2.0'
    /*** 信息持久化存储 */
    api 'com.tencent:mmkv:1.0.24'
    /*** 各种弹窗 */
    api 'com.lxj:xpopup:1.9.0'
    /*** 布局适配 */
    api 'me.jessyan:autosize:1.2.1'
    /*** 上拉刷新 */
    api 'com.scwang.smartrefresh:SmartRefreshLayout:1.1.2'
    /*** toolbar */
    api 'com.hjq:titlebar:6.0'
