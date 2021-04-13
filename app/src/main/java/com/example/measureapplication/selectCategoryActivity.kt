package com.example.measureapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.zxing.integration.android.IntentIntegrator
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_select_category.*

class selectCategoryActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    private fun initRealm(){
        val realmconfiguration = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().schemaVersion(0).build()
        realm = Realm.getInstance(realmconfiguration)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)

        //データ数
        var tea_num: Int
        var juice_num: Int
        var coffee_num: Int
        var energy_num: Int

        //データクラス
        val data = Data()

        tea_num = data.tea_name.count()
        juice_num = data.juice_name.count()
        coffee_num = data.coffee_name.count()
        energy_num = data.energy_name.count()


        initRealm()

        //MaxID取得
        var maxId:Number? = null
        realm.executeTransaction { db: Realm ->
            maxId = db.where<Beverage>().max("id")?.toInt()
        }

        //データID格納
        for (i in 0..tea_num - 1) {
            data.tea_id.add(i)
            }
        for (i in tea_num ..(tea_num +juice_num - 1) ){
            data.juice_id.add(i)
            }
        for (i in (tea_num + juice_num)..(tea_num + juice_num + coffee_num - 1)) {
            data.coffee_id.add(i)
            }
        for (i in (tea_num + juice_num + coffee_num).. (tea_num + juice_num + coffee_num +energy_num - 1)) {
            data.energy_id.add(i)
            }

        //RealmがNullならばデータを格納する
        if(maxId == null) {
            for (i in 0..tea_num - 1) {
                realm.executeTransaction { db: Realm ->
                    var beverage = db.createObject<Beverage>(i)
                    beverage.name = data.tea_name[i]
                    beverage.caffeine = data.tea_caffeine[i]
                    beverage.sugar = data.tea_sugar[i]
                    beverage.vitamin = data.tea_vitamin[i]
                    beverage.JAL_code = data.tea_JAL[i]
                    beverage.category_id = data.tea_category[i]
                    beverage.data_id = data.tea_id[i]
                }
            }
            for (i in 0..juice_num - 1) {
                realm.executeTransaction { db: Realm ->
                    var beverage = db.createObject<Beverage>(tea_num + i)
                    beverage.name = data.juice_name[i]
                    beverage.caffeine = data.juice_caffeine[i]
                    beverage.sugar = data.juice_sugar[i]
                    beverage.vitamin = data.juice_vitamin[i]
                    beverage.JAL_code = data.juice_JAL[i]
                    beverage.category_id = data.juice_category[i]
                    beverage.data_id = data.juice_id[i]
                }
            }
            for (i in 0..coffee_num - 1) {
                realm.executeTransaction { db: Realm ->
                    var beverage = db.createObject<Beverage>(tea_num + juice_num + i)
                    beverage.name = data.coffee_name[i]
                    beverage.caffeine = data.coffee_caffeine[i]
                    beverage.sugar = data.coffee_sugar[i]
                    beverage.vitamin = data.coffee_vitamin[i]
                    beverage.JAL_code = data.coffee_JAL[i]
                    beverage.category_id = data.coffee_category[i]
                    beverage.data_id = data.coffee_id[i]
                }
            }
            for (i in 0..energy_num - 1) {
                realm.executeTransaction { db: Realm ->
                    var beverage = db.createObject<Beverage>(tea_num + juice_num + coffee_num + i)
                    beverage.name = data.energy_name[i]
                    beverage.caffeine = data.energy_caffeine[i]
                    beverage.sugar = data.energy_sugar[i]
                    beverage.vitamin = data.energy_vitamin[i]
                    beverage.JAL_code = data.energy_JAL[i]
                    beverage.category_id = data.energy_category[i]
                    beverage.data_id = data.energy_id[i]
                }
            }
       }


        /***************teaボタン***************/
        teaButton.setOnClickListener {
                intent = Intent(this, SelectBeverageActivity::class.java)
                intent.putExtra("BEVERAGE", data.tea)
                startActivity(intent)
        }
        /****************************************/

        /***************juiceボタン***************/
        juiceButton.setOnClickListener {
            intent = Intent(this,SelectBeverageActivity::class.java)
            intent.putExtra("BEVERAGE",data.juice)
            startActivity(intent)
        }
        /****************************************/

        /***************coffeeボタン***************/
        coffeeButton.setOnClickListener {
            intent = Intent(this,SelectBeverageActivity::class.java)
            intent.putExtra("BEVERAGE",data.coffee)
            startActivity(intent)
        }
        /****************************************/

        /***************energyボタン***************/
        energyButton.setOnClickListener {
            intent = Intent(this,SelectBeverageActivity::class.java)
            intent.putExtra("BEVERAGE",data.energy)
            startActivity(intent)
        }
        /****************************************/

        //バーコードリーダー
        val barcodeButton = findViewById<View>(R.id.barcodeButton)
        barcodeButton?.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }

    }

    //バーコードパース
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)

        //戻るボタンが押された時の処理
        if(scanResult.getContents() == null){
            return
        }

        if (scanResult == null) {
            return
        } else {
            var JAL = scanResult.contents.toLong()
            val intent = Intent(this, Print2Activity::class.java)
            intent.putExtra("JAL",JAL)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
