package com.renhuan.administrator.myokhttp

import javax.inject.Inject
import javax.inject.Singleton

data class MeiziModel(
        val _id: String = "",
        var author: String = "",
        val category: String,
        val createdAt: String,
        val desc: String,
        val images: List<String>?,
        val likeCounts: Int,
        val publishedAt: String,
        val stars: Int,
        val title: String,
        val type: String,
        val url: String,
        val views: Int
)