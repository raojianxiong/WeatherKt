package jianxiongrao.weatherkt.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import jianxiongrao.weatherkt.R
import jianxiongrao.weatherkt.domain.model.Forecast
import jianxiongrao.weatherkt.domain.model.ForecastList
import jianxiongrao.weatherkt.extensions.toDateString
import kotlinx.android.synthetic.main.item_forecast.view.*

/**
 * author: Jianxiong Rao
 * email:1272670593@qq.com
 * on 2018/1/13
 */
class ForecastListAdapter(private val weekForecast:ForecastList,
                          private val itemClick:(Forecast) -> Unit):
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_forecast,parent,false),itemClick)
    }

    override fun getItemCount(): Int {
        return weekForecast.size
    }

    class ViewHolder(view:View,private val itemClick: (Forecast) -> Unit) :RecyclerView.ViewHolder(view){
        fun bindForecast(forecast: Forecast){
            with(forecast){
                Picasso.with(itemView.context).load(iconUrl).into(itemView.icon)
                itemView.date.text = date.toDateString()
                itemView.description.text = description
                itemView.maxTemperature.text = "${high}"
                itemView.minTemperature.text = "${low}"
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
