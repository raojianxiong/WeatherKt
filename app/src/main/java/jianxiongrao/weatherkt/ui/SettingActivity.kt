package jianxiongrao.weatherkt.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import jianxiongrao.weatherkt.R
import jianxiongrao.weatherkt.extensions.DelegatesExt
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
companion object{
    val ZIP_CODE = "zipCode"
    val DEFAULT_ZIP = 94043L
}
    private var zipCode :Long by DelegatesExt.preferences(this, ZIP_CODE, DEFAULT_ZIP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initToolBar()
        cityCode.setText(zipCode.toString())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        zipCode = cityCode.text.toString().toLong()
    }
    private fun initToolBar() {
        setSupportActionBar(toolbar3)
        toolbar3.setNavigationOnClickListener { onBackPressed() }
    }
}
