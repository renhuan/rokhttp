## 1、引入

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

## 2、使用

- Fragment继承RBaseFragment，Activity继承RBaseActivity，Application继承RApp
- [RxHttp ，比Retrofit 更优雅的协程体验]( https://juejin.im/post/6844904100090347528#heading-16 )
- 封装的一些使用的静态方法[Renhuan]( https://github.com/renhuan/rokhttp/blob/master/okhttplib/src/main/java/com/renhuan/okhttplib/utils/Renhuan.kt )和一些扩展函数[RExtension]( https://github.com/renhuan/rokhttp/blob/master/okhttplib/src/main/java/com/renhuan/okhttplib/utils/RExtension.kt )
- mmkv使用非常简单[ MmkvTestActivity]( https://github.com/renhuan/rokhttp/blob/master/app/src/main/java/com/renhuan/administrator/myokhttp/MmkvTestActivity.kt )
- [Android布局适配]( https://juejin.im/post/6844903942812516360 )

## 3、说明

        /*** 网络请求 */
        api 'com.ljx.rxhttp:rxhttp:2.2.7'
        api 'com.squareup.okhttp3:okhttp:4.7.2'
        kapt 'com.ljx.rxhttp:rxhttp-compiler:2.2.7'
        api 'com.ljx.rxlife:rxlife-coroutine:2.0.0'
        api 'io.reactivex.rxjava3:rxjava:3.0.2'
        api 'io.reactivex.rxjava3:rxandroid:3.0.0'
        api 'com.ljx.rxlife3:rxlife-rxjava:3.0.0'
    
        /*** 组件通信 */
        api 'org.greenrobot:eventbus:3.1.1'
    
        /*** 工具类 */
        api 'com.blankj:utilcodex:1.29.0'
    
        /*** RecyclerView */
        api 'com.github.youlookwhat:ByRecyclerView:1.1.2'
    
        /*** 圆形头像 */
        api 'de.hdodenhof:circleimageview:3.0.1'
    
        /*** glide图片加载 */
        api 'com.github.bumptech.glide:glide:4.10.0'
        annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'
    
        /*** 跳转页面startActivity */
        api 'com.wuyr:activitymessenger:1.2.0'
    
        /*** 信息持久化存储 */
        api 'com.tencent:mmkv:1.0.24'
    
        /*** 各种弹窗 */
        api 'com.lxj:xpopup:2.0.6'
    
        /*** toolbar */
        api 'com.hjq:titlebar:6.0'
    
        /**
         * 测试专用
         */
        debugImplementation "com.simple:spiderman:1.1.5"
        releaseImplementation "com.simple:spiderman-no-op:1.1.4"
        debugImplementation 'com.readystatesoftware.chuck:library:1.1.0'
        releaseImplementation 'com.readystatesoftware.chuck:library-no-op:1.1.0'
        debugImplementation 'com.squareup.leakcanary:leakcanary-android:2.4'
