package com.leander.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leander.global.net.request.GetWeatherRequest
import com.leander.homework.model.Weather
import com.leander.homework.viewmodel.WeatherViewModel
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkIsFirstOpen()
        initVm()
//        init()
    }

//    override val model: WeatherViewModel by activityViewModels() {
//        CardSelectionViewModelFactory(
//            paymentRepository,
//            ioDispatcher
//        )
//    }

    private fun initVm(){
        var model = ViewModelProvider(this)[WeatherViewModel::class.java]


        if (!model.weatherLiveData.hasObservers()) {
            model.weatherLiveData.observe(this) { weatherList ->
                init(weatherList)
            }
        }

        model.getWeather(GetWeatherRequest(
            "CWB-332C7ED8-C83A-42A8-9F3F-CB1C78CB8BAF",
            "100","0","JSON","臺北市","MinT")
        )

    }

    /**是否首次開啟*/
    private fun checkIsFirstOpen() {
        val pref = getSharedPreferences(Companion.PREF_SETTING, MODE_PRIVATE)
        if(pref.getBoolean(IS_FIRST_OPEN, true)){
            val edit = pref.edit()
            edit.putBoolean(IS_FIRST_OPEN, false).apply()
        } else{
            Toast.makeText(this, "歡迎回來", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init(data: ArrayList<Weather>){
        val rvWeather: RecyclerView = findViewById(R.id.rvWeather)
        val adapter = WeatherListAdapter(this,data)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvWeather.layoutManager = linearLayoutManager
        rvWeather.adapter = adapter

        adapter.setOnItemClickListener(object : WeatherListAdapter.OnItemClickListener {
            override fun onItemClick(obj: Weather, position: Int) {
                toDetail(obj)
            }
        })
    }

    fun toDetail(obj: Weather){
        val intent = Intent(this,DetailActivity::class.java)
        var text = obj.startTime+"\n"+
                obj.endTime+"\n"+
                obj.temperature+obj.type
        intent.putExtra("text",text)

        startActivity(intent)

    }

    companion object {
        const val PREF_SETTING = "PREF_SETTING"
        const val IS_FIRST_OPEN = "IS_FIRST_OPEN"
    }

}