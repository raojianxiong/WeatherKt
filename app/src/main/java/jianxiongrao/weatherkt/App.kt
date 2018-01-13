package jianxiongrao.weatherkt

import android.app.Application
import jianxiongrao.weatherkt.extensions.DelegatesExt

/**
 * author: Jianxiong Rao
 * email:1272670593@qq.com
 * on 2018/1/12
 */
class App : Application(){
    companion object{
        var instance :App by DelegatesExt.notNullSingleValueVar()
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}