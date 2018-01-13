package jianxiongrao.weatherkt.extensions

import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder

/**
 * author: Jianxiong Rao
 * email:1272670593@qq.com
 * on 2018/1/13
 */
fun <T:Any> SelectQueryBuilder.parseList(parser:(Map<String,Any?>)-> T):List<T> {
    return parseList(object : MapRowParser<T> {
        override fun parseRow(columns: Map<String, Any?>): T {
            //将每一行的列名映射成key 值value -->>解析成map了
            return parser(columns)
        }
    })
}
fun <T:Any> SelectQueryBuilder.parseOpt(parser:((Map<String, Any?>))->T):T?{
    return parseOpt(object :MapRowParser<T>{
        override fun parseRow(columns: Map<String, Any?>): T {
            return parser(columns)
        }
    })
}
fun SQLiteDatabase.clear(tableName:String){
    execSQL("delete from $tableName")
}