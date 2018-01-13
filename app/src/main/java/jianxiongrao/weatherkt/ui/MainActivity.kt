package jianxiongrao.weatherkt.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import jianxiongrao.weatherkt.R
import jianxiongrao.weatherkt.domain.commands.RequestForecastCommand
import jianxiongrao.weatherkt.domain.model.ForecastList
import jianxiongrao.weatherkt.extensions.DelegatesExt
import jianxiongrao.weatherkt.ui.adapter.ForecastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private val zipCode: Long by DelegatesExt.preferences(this, SettingActivity.ZIP_CODE, SettingActivity.DEFAULT_ZIP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() = async(UI) {
        val result = bg { RequestForecastCommand(zipCode).execute() }
        Log.i("rjx","result "+result.toString());
        updateUI(result.await())
    }

    private fun updateUI(weekForecast: ForecastList) {
        Log.i("rjx","result3 ");
        val adapter = ForecastListAdapter(weekForecast) {
            startActivity<DetailActivity>(DetailActivity.ID to it.id,
                    DetailActivity.CITY_NAME to weekForecast.city)
        }
        recyclerView.adapter = adapter
        Log.i("rjx","adapter "+(adapter==null));
        toolbar.title = "${weekForecast.city} (${weekForecast.country})"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_settings){
            startActivity<SettingActivity>()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
