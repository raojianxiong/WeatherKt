package jianxiongrao.weatherkt.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.appcompat.R.attr.color
import android.widget.TextView
import com.squareup.picasso.Picasso

import jianxiongrao.weatherkt.R
import jianxiongrao.weatherkt.domain.commands.RequestDayForecastCommand
import jianxiongrao.weatherkt.domain.model.Forecast
import jianxiongrao.weatherkt.extensions.toDateString
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailActivity : AppCompatActivity() {

    companion object{
        val ID = "DetailActivity:id"
        val CITY_NAME = "DetailActivity:cityNmae"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()
        async(UI){
            val result = bg{RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()}
            bindForecast(result.await())
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar2)
        toolbar2.title = intent.getStringExtra(CITY_NAME)
        toolbar2.setNavigationOnClickListener { finish() }
    }
    private fun bindForecast(forecast:Forecast) = with(forecast){
        Picasso.with(this@DetailActivity).load(iconUrl).into(icon)
        toolbar2.subtitle = date.toDateString()
        weatherDescription.text = description
        bindWeather(high to maxTemperature,low to minTemperature)
    }
    private fun bindWeather(vararg views:Pair<Int,TextView>) = views.forEach {
        it.second.text = "${it.first}"
        it.second.setTextColor(when(it.first){
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })
    }
}
