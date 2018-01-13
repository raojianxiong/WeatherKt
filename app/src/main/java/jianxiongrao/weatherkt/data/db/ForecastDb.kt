package jianxiongrao.weatherkt.data.db

import jianxiongrao.weatherkt.domain.datasource.ForecastDataSource
import jianxiongrao.weatherkt.domain.model.Forecast
import jianxiongrao.weatherkt.domain.model.ForecastList
import jianxiongrao.weatherkt.extensions.clear
import jianxiongrao.weatherkt.extensions.parseList
import jianxiongrao.weatherkt.extensions.parseOpt
import jianxiongrao.weatherkt.extensions.toVarargArray
import kotlinx.coroutines.experimental.selects.select
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * author: Jianxiong Rao
 * email:1272670593@qq.com
 * on 2018/1/13
 */
class ForecastDb(private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 private val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

    override fun requestDayForecast(id: Long): Forecast? {
           return forecastDbHelper.use {
               val forecast = select(DayForecastTable.NAME)
                       .whereSimple("_id = ?",id.toString())
                       .parseOpt { DayForecast(HashMap(it)) }
               forecast?.let { dataMapper.convertDayToDomain(it) }
           }
    }

    //use  代替了用getReadableDatabase等打开数据库操作
    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }//查询到的每一个返回的Map转换到HashMap

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }//这个函数返回一个可null的对象
        city?.let { dataMapper.convertToDomain(it) }
    }

    /**
     * 保存实体类到数据库，需要转换
     */
    fun saveForecast(forecast:ForecastList)=forecastDbHelper.use{
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)
        with(dataMapper.convertFromDomain(forecast)){
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach {  insert(DayForecastTable.NAME,*it.map.toVarargArray())}
        }
    }
}
