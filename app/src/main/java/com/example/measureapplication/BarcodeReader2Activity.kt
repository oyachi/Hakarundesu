package com.example.measureapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.integration.android.IntentIntegrator
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_barcode_reader.*

class BarcodeReader2Activity : AppCompatActivity() {
    /*private lateinit var realm: Realm

    private fun initRealm(){
        val realmconfiguration = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().schemaVersion(0).build()
        realm = Realm.getInstance(realmconfiguration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_reader)

        var JAL = intent.getLongExtra("JAL",0L)
        //var JAL: Int = 501

        //データ数
        var tea_num: Int
        var juice_num: Int
        var coffee_num: Int
        var energy_num: Int

        //データクラス
        val data = Data()
        //realm初期化
        initRealm()


        //データ削除
        val delete = realm.where(Beverage::class.java).findAll()
        realm.executeTransaction {
            delete.deleteAllFromRealm()
        }

        tea_num = data.tea_name.count()
        juice_num = data.juice_name.count()
        coffee_num = data.coffee_name.count()
        energy_num = data.energy_name.count()

        //データベースへ保存
            for (i in 0..tea_num - 1) {
                realm.executeTransaction { db: Realm ->
                    var beverage = db.createObject<Beverage>(i)
                    beverage.name = data.tea_name[i]
                    beverage.caffeine = data.tea_caffeine[i]
                    beverage.vitamin = data.tea_vitamin[i]
                    beverage.JAL_code = data.tea_JAL[i]
                }
            }
            for (i in 0..juice_num - 1) {
                realm.executeTransaction { db: Realm ->
                    var beverage = db.createObject<Beverage>(tea_num + i)
                    beverage.name = data.juice_name[i]
                    beverage.caffeine = data.juice_caffeine[i]
                    beverage.vitamin = data.juice_vitamin[i]
                    beverage.JAL_code = data.juice_JAL[i]
                }
            }
        for (i in 0..coffee_num - 1) {
            realm.executeTransaction { db: Realm ->
                var beverage = db.createObject<Beverage>(tea_num + juice_num +i)
                beverage.name = data.coffee_name[i]
                beverage.caffeine = data.coffee_caffeine[i]
                beverage.vitamin = data.coffee_vitamin[i]
                beverage.JAL_code = data.coffee_JAL[i]
            }
        }
        for (i in 0..energy_num - 1) {
            realm.executeTransaction { db: Realm ->
                var beverage = db.createObject<Beverage>(tea_num + juice_num + coffee_num + i)
                beverage.name = data.energy_name[i]
                beverage.caffeine = data.energy_caffeine[i]
                beverage.vitamin = data.energy_vitamin[i]
                beverage.JAL_code = data.energy_JAL[i]
            }
        }

            val test = realm.where(Beverage::class.java).equalTo("JAL_code", JAL).findFirst()
            if (test == null) {
                testText.text = "JALCODE:" + JAL.toString() +"   " +  "該当するデータがありません"
            } else {
                testText.text =
                    "商品名:" + test?.name + "　カフェイン:" + test?.caffeine.toString() + "g　ビタミン:" + test?.vitamin.toString() + "g　JAL:" + test?.JAL_code.toString()
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            realm.close()
        }*/
}