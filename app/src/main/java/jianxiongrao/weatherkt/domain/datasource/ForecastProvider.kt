package jianxiongrao.weatherkt.domain.datasource

import jianxiongrao.weatherkt.data.db.ForecastDb
import jianxiongrao.weatherkt.data.server.ForecastServer
import jianxiongrao.weatherkt.domain.model.Forecast
import jianxiongrao.weatherkt.domain.model.ForecastList
import jianxiongrao.weatherkt.extensions.firstResult

/**
 * author: Jianxiong Rao
 * email:1272670593@qq.com
 * on 2018/1/13
 */
class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES){
    companion object{
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24//一天
        val SOURCES by lazy { listOf(ForecastDb(), ForecastServer()) }
    }
    fun requestByZipCode(zipCode:Long,days:Int):ForecastList{
        return requestToSources {
            val res  = it.requestForecastByZipCode(zipCode,todayTimeSpan())
            if(res != null && res.size >= days) res else null
        }
    }
    fun requestForecast(id:Long) :Forecast=requestToSources { it.requestDayForecast(id) }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    //从服务器和数据库查询 数据库第一次为空
    private fun <T:Any> requestToSources(f:(ForecastDataSource) ->T?):T=sources.firstResult {f(it) }
}
