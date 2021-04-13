package com.example.measureapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_choose1.*

class Choose1Activity : AppCompatActivity() {
    private lateinit var realm: Realm

    private fun initRealm(){
        val realmconfiguration = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().schemaVersion(0).build()
        realm = Realm.getInstance(realmconfiguration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose1)

        var data_num:Int

        var intent:Intent

        //データクラスインスタンス生成
        val data = Data()

        //realmのインスタンス生成
        initRealm()

        /***************teaボタン***************/
        teaButton.setOnClickListener {

            //データ削除
            val delete = realm.where(Beverage::class.java).findAll()
            realm.executeTransaction{
                delete.deleteAllFromRealm()
            }

            data_num = data.tea_name.count()
         //データベースへ保存
            for(i in 0..data_num-1) {
                realm.executeTransaction { db: Realm ->
                    val beverage = db.createObject<Beverage>(i)
                    beverage.name = data.tea_name[i]
                    beverage.caffeine = data.tea_caffeine[i]
                    beverage.sugar = data.tea_sugar[i]
                    beverage.vitamin = data.tea_vitamin[i]
                    beverage.JAL_code = data.tea_JAL[i]
                }
            }
            intent = Intent(this,Choose2Activity::class.java)
            intent.putExtra("BEVERAGE",data.tea)
            startActivity(intent)
        }
        /****************************************/

        /***************juiceボタン***************/
        juiceButton.setOnClickListener {

            //データ削除
            val delete = realm.where(Beverage::class.java).findAll()
            realm.executeTransaction{
                delete.deleteAllFromRealm()
            }

            data_num = data.juice_name.count()
            //データベースへ保存
            for(i in 0..data_num-1) {
                realm.executeTransaction { db: Realm ->
                    val beverage = db.createObject<Beverage>(i)
                    beverage.name = data.juice_name[i]
                    beverage.caffeine = data.juice_caffeine[i]
                    beverage.sugar = data.juice_sugar[i]
                    beverage.vitamin = data.juice_vitamin[i]
                    beverage.JAL_code = data.juice_JAL[i]
                }
            }
            intent = Intent(this,Choose2Activity::class.java)
            intent.putExtra("BEVERAGE",data.juice)
            startActivity(intent)
        }
        /****************************************/

        /***************coffeeボタン***************/
        coffeeButton.setOnClickListener {

            //データ削除
            val delete = realm.where(Beverage::class.java).findAll()
            realm.executeTransaction{
                delete.deleteAllFromRealm()
            }

            data_num = data.coffee_name.count()
            //データベースへ保存
            for(i in 0..data_num-1) {
                realm.executeTransaction { db: Realm ->
                    val beverage = db.createObject<Beverage>(i)
                    beverage.name = data.coffee_name[i]
                    beverage.caffeine = data.coffee_caffeine[i]
                    beverage.sugar = data.coffee_sugar[i]
                    beverage.vitamin = data.coffee_vitamin[i]
                    beverage.JAL_code = data.coffee_JAL[i]
                }
            }
            intent = Intent(this,Choose2Activity::class.java)
            intent.putExtra("BEVERAGE",data.coffee)
            startActivity(intent)
        }
        /****************************************/

        /***************energyボタン***************/
        energyButton.setOnClickListener {

            //データ削除
            val delete = realm.where(Beverage::class.java).findAll()
            realm.executeTransaction{
                delete.deleteAllFromRealm()
            }

            data_num = data.energy_name.count()
            //データベースへ保存
            for(i in 0..data_num-1) {
                realm.executeTransaction { db: Realm ->
                    val beverage = db.createObject<Beverage>(i)
                    beverage.name = data.energy_name[i]
                    beverage.caffeine = data.energy_caffeine[i]
                    beverage.sugar = data.energy_sugar[i]
                    beverage.vitamin = data.energy_vitamin[i]
                    beverage.JAL_code = data.energy_JAL[i]
                }
            }
            intent = Intent(this,Choose2Activity::class.java)
            intent.putExtra("BEVERAGE",data.energy)
            startActivity(intent)
        }
        /****************************************/

    }

    override fun onDestroy(){
        super.onDestroy()
        realm.close()
    }
}
