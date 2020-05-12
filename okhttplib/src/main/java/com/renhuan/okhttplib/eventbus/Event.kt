package com.renhuan.okhttplib.eventbus

/**
 * 通过泛型<T>指定事件通信过程中的数据类型，code为事件码，使用的时候给不同的事件类型指定不同的code。
 *
 * @param <T>
</T></T> */
class Event<T>(var code: Int, var data: T? = null)