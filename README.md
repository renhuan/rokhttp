
## 使用

一、引入

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
	dependencies {
	        implementation 'com.github.renhuan2015:MyOkHttp:1.0.0'
	}
	

二、首先你需要在你的项目中新建一个BaseOkHttp类继承RBaseOkHttp类。
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
      
