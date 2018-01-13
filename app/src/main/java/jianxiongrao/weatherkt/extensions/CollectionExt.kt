package jianxiongrao.weatherkt.extensions

import android.icu.text.DateFormat
import java.util.*

/**
 * author: Jianxiong Rao
 * email:1272670593@qq.com
 * on 2018/1/13
 */
fun <K,V:Any> Map<K,V?>.toVarargArray():Array<out Pair<K,V>> =
        map { Pair(it.key,it.value!!) }.toTypedArray()
inline fun <T,R:Any> Iterable<T>.firstResult(predicate:(T)->R?):R{
    for(element in this){
        val result = predicate(element)
        if(result != null){
            return result
        }
    }
    throw NoSuchElementException("no element matching predicate")
}

fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}