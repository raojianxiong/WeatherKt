package jianxiongrao.weatherkt.extensions

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * author: Jianxiong Rao
 * email:1272670593@qq.com
 * on 2018/1/12
 */
object DelegatesExt {
    /**
     * not null 委托 只被赋值一次
     */
    fun <T:Any> notNullSingleValueVar() :ReadWriteProperty<Any?,T> = NotNullSingleValueVar()

    fun <T:Any> preferences(context: Context,name: String,value:T):ReadWriteProperty<Any?,T> = Preference(context,name,value)
    private class NotNullSingleValueVar<T>:ReadWriteProperty<Any?,T>{
        private var value:T? = null
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
                return value?:throw IllegalStateException("not initialized")
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = if(this.value == null) value else throw IllegalStateException("already initialized")
        }
    }
    private class Preference<T>(context:Context,val name:String,val  default:T):ReadWriteProperty<Any?,T>{
        val prefs:SharedPreferences by lazy {
            context.getSharedPreferences("default",Context.MODE_PRIVATE)
        }
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return findPreference(name,default);
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
              putPreference(name,value)
        }

        private fun findPreference(name: String, default: T): T {
                    return with(prefs){
                       val res :Any = when(default){
                           is Long -> getLong(name,default)
                           is  Float -> getFloat(name,default)
                           is String -> getString(name,default)
                           is Boolean -> getBoolean(name,default)
                           is Int -> getInt(name,default)
                           else -> throw IllegalArgumentException("this type has not save to it")
                       }
                        return res as T
                    }
        }
        private fun <T> putPreference(name:String,value:T){
                with(prefs.edit()){
                    when(value){
                        is Int -> putInt(name,value)
                        is Long -> putLong(name,value)
                        is Float -> putFloat(name,value)
                        is String -> putString(name,value)
                        is Boolean -> putBoolean(name,value)
                        else -> throw IllegalArgumentException("this type has not save to it")
                    }
                }
        }
    }

}