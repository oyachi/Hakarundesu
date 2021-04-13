package com.example.measureapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_choose2.*

class Choose2Activity : AppCompatActivity() {

    val data = Data()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose2)

        //インテント
        var beverage_id = intent.getIntExtra("BEVERAGE",0)

        //ListView
        var listView = ListView(this)
        setContentView(listView)
        var data_list:Array<String>

        when(beverage_id) {
            data.tea -> {
                data_list = data.tea_name
            }
            data.juice -> {
                data_list = data.juice_name
            }
            data.coffee -> {
                data_list = data.coffee_name
            }
            else -> {
                data_list = data.energy_name
            }
        }
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data_list)

        listView.setAdapter(adapter)

        //ListViewをタップしたときの処理
        listView.setOnItemClickListener { parent, view, position, id ->
            /*var listId = id
            var intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("LIST_ID",listId.toInt())
            startActivity(intent)*/
            var listId = id
            var intent = Intent(this,PrintActivity::class.java)
            intent.putExtra("LIST_ID",listId.toInt())
            startActivity(intent)

        }
    }
}

