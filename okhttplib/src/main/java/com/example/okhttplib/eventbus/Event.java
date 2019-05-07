package com.example.okhttplib.eventbus;

/**
 * 通过泛型<T>指定事件通信过程中的数据类型，code为事件码，使用的时候给不同的事件类型指定不同的code。
 *
 * @param <T>
 */
public class Event<T> {
    private int code;
    private T data;

    public Event(int code) {
        this.code = code;
    }

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}