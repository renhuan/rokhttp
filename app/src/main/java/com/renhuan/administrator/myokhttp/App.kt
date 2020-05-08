package com.renhuan.administrator.myokhttp

import com.renhuan.okhttplib.RApp
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

class App : RApp() {
    override fun init() {
        AutoSizeConfig.getInstance()
                .unitsManager
                .setSupportDP(false)
                .supportSubunits = Subunits.MM
    }
}