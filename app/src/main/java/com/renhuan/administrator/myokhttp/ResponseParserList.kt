package com.renhuan.administrator.myokhttp

import okhttp3.Response
import okio.IOException
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.AbstractParser
import rxhttp.wrapper.utils.convert
import java.lang.reflect.Type

/**
 * created by renhuan
 * time : 2020/6/29 17:02
 * describe :
 */
@Parser(name = "ResponseList")
open class ResponseParserList<T> : AbstractParser<List<T>> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: Response): List<T> {
        val type: Type = ParameterizedTypeImpl[BaseResponseModel::class.java, List::class.java, mType] //获取泛型类型
        val data: BaseResponseModel<List<T>> = response.convert(type)  //获取Response对象
        if (data.status != 100) { //code不等于200，说明数据不正确，抛出异常
            throw ParseException(data.status.toString(), "status!=100", response)
        }
        return data.data
    }
}
