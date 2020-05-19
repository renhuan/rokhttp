package com.renhuan.okhttplib.eventbus

/**
 * 通过泛型<T>指定事件通信过程中的数据类型
 * 两种方式传递
 * 1--code 静态常量
 * 2--判断对象 data is obj
 *
 * @param <T>
</T></T> */
class Event<T>(var data: T)