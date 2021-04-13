package com.example.measureapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where

class SelectBeverageActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    private fun initRealm(){
        val realmconfiguration = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().schemaVersion(0).build()
        realm = Realm.getInstance(realmconfiguration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_beverage)

        initRealm()

        var data_name : MutableList<String> = mutableListOf()


        //カテゴリID取得
        var category_id = intent.getIntExtra("BEVERAGE",0)
        //maxID取得
        var maxId:Number? = 0
        realm.executeTransaction { db: Realm ->
            maxId = db.where<Beverage>().max("data_id")
        }

        //取得したカテゴリIDに一致するデータをリスト化する
        var count:Int? = maxId?.toInt()
        if (count != null) {
            for (i in 0..count.toInt()) {
                realm.executeTransaction { db: Realm ->
                    val beverage = realm.where<Beverage>().equalTo("data_id", i).findFirst()
                    var id = beverage?.category_id
                    var name: String = beverage?.name.toString()
                    if (category_id == id) {
                        data_name.add(name)
                    }
                }
            }
        }
        
        //ListView
        /*
        var listView = ListView(this)
        setContentView(listView)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data_name)
        listView.setAdapter(adapter)
        */

        val listView = findViewById(R.id.teaList) as ListView
        val dataArray = data_name
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataArray)
        listView.adapter = adapter

        listView.setOnItemClickListener{adapterView, _,position, _ ->
            val list_name = adapterView.getItemAtPosition(position) as String
            realm.executeTransaction { db:Realm ->
                val beverage = realm.where<Beverage>().equalTo("name", list_name).findFirst()
                var beverage_id = beverage?.data_id
                intent = Intent(this, PrintActivity::class.java)
                intent.putExtra("DATA_ID", beverage_id)
                startActivity(intent)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
