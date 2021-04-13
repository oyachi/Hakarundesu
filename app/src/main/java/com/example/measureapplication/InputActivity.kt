package com.example.measureapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_input.*

class InputActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    private fun initRealm() {
        val realmconfiguration =
            RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().schemaVersion(0).build()
        realm = Realm.getInstance(realmconfiguration)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        initRealm()

        var JAL = intent.getLongExtra("JAL", 0L)
        var data = Data()
        var category = 0

        JALText.text = "JAN CODE : " + JAL.toString()

        //スピナー配列
        var item: String = "お茶"
        var category_id = 0
        val spinnerItems = arrayOf("お茶", "ジュース", "コーヒー", "エナジードリンク")
        val adapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item))
        spinner.adapter = adapter
        //リスナー登録
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                item = "お茶"
            }

            //アイテムが選択されたとき
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinnerParent = parent as Spinner
                item = spinnerParent.selectedItem as String
                when (item) {
                    "お茶" -> {
                        category_id = data.tea
                    }
                    "ジュース" -> {
                        category_id = data.juice
                    }
                    "コーヒー" -> {
                        category_id = data.coffee
                    }
                    else -> {
                        category_id = data.energy
                    }
                }
            }
        }

        saveButton.setOnClickListener {view:View ->
            var name = nameEdit.text.toString()
            var caffeine = caffeineEdit.text.toString()
            var sugar = sugarEdit.text.toString()

            if((name.length != 0) && (caffeine.length != 0) && (sugar.length != 0)) {
              realm.executeTransaction { db: Realm ->
                    val maxId = db.where<Beverage>().max("id")
                    val nextId = (maxId?.toLong() ?: 0) + 1
                    val beverage = db.createObject<Beverage>(nextId)
                    beverage.data_id = nextId.toInt()
                    beverage.category_id = category_id
                    beverage.JAL_code = JAL
                    beverage.name = name
                    beverage.caffeine = caffeine.toInt()
                    beverage.sugar = sugar.toInt()
                    beverage.vitamin = 0

                    Toast.makeText(applicationContext, "データを追加しました", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, selectCategoryActivity::class.java)
                    intent.putExtra("DATA_ID", beverage.data_id)
                    startActivity(intent)
                }
            }else{
                Toast.makeText(applicationContext, "入力されていない項目があります", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
