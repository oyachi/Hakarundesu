package com.example.measureapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_print.*

class PrintActivity : AppCompatActivity() {

    private fun savePreferences(_key: String,_sugar:Float,_key2:String,_caffeine:Float) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editor = sharedPreferences.edit()
        var sugar_cnt = sharedPreferences.getFloat(_key,0f)
        var caffeine_cnt = sharedPreferences.getFloat(_key2,0f)
        sugar_cnt = sugar_cnt + _sugar
        caffeine_cnt = caffeine_cnt + _caffeine
        editor.putFloat(_key, sugar_cnt)
        editor.putFloat(_key2,caffeine_cnt)
        editor.apply()
    }

    //カフェイン用
    var nut_bar = 0
    var nut_second_bar = 0
    var nut_count:Double = 0.0
    var amount:Double = 0.0

    //糖質用
    var nut_bar2 = 0
    var nut_second_bar2 = 0
    var nut_count2:Double = 0.0
    var amount2 = 0.0

    //var count2 = 0
    //var count = 0

    private lateinit var realm: Realm

    private fun initRealm(){
        val realmconfiguration = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().schemaVersion(0).build()
        realm = Realm.getInstance(realmconfiguration)
    }


    inner class CaffeineCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() {

        }

        override fun onTick(millisUntilFinished: Long) {
            progressBar.max = 3000
            nut_bar += (amount).toInt()
           // nut_second_bar += 3
            nut_count += amount/10

            //if(nut_second_bar <= 3000){
              //  progressBar.secondaryProgress = nut_second_bar
            //}
            if(nut_bar <= amount*100) {
                progressBar.progress = nut_bar
            }
            if(nut_count-amount <= amount*10) {
                if(nut_count > amount*10){
                    nut_count = amount*10
                }
                //count += 1
                //caffeineText.text = "update:" + count.toString() + "  cafeine:" + kotlin.math.round(nut_count).toInt().toString() + "amount:" + (amount*10).toString()
                caffeineText.text = kotlin.math.round(nut_count).toInt().toString() + "mg"
            }

        }
    }

    inner class SugarCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() {

        }

        override fun onTick(millisUntilFinished: Long) {
            progressBar2.max = 1000
            nut_bar2 += (amount2).toInt()
            //nut_second_bar2 += 1
            nut_count2 += amount2/10
            /*
            if(nut_second_bar2 <= 1000){
                progressBar2.secondaryProgress = nut_second_bar2
            }*/
            if(nut_bar2 <= amount2*100) {
                progressBar2.progress = nut_bar2
            }
            if(nut_count2-amount2 <= amount2*10) {
                if(nut_count2 > amount2*10){
                    nut_count2 = amount2*10
                }
                //count2 += 1
                //sugarText.text = "update:" + count2.toString() + "  sugar:" + kotlin.math.round(nut_count2).toInt().toString() + "amount2:" + (amount2*10).toString()
                sugarText.text = kotlin.math.round(nut_count2).toInt().toString() + "g"
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)

        //Realm初期化
        initRealm()

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

        //データの取得
        val data_id = intent.getIntExtra("DATA_ID",0)
        val beverage = realm.where<Beverage>().equalTo("data_id",data_id).findFirst()
        val id = beverage?.id?.toInt()
        val name = beverage?.name
        val caffeine = beverage?.caffeine?.toDouble()
        val sugar = beverage?.sugar?.toDouble()
        val vitamin = beverage?.vitamin

        //商品名
        nameText.text = name
        if(nameText.length() <= 24){
            nameText.setTextSize(24f)
        }else{
            nameText.setTextSize(18f)
        }

        //カフェイン表示
        if (caffeine != null) {
            amount = caffeine /10

            if (caffeine != 0.0) {
                val timer = CaffeineCountDownTimer(2 * 1000, 10)
                if (nut_count > caffeine.toInt()) {
                    timer.cancel()
                } else {
                    timer.start()
                }
            }else{
                caffeineText.text = "0mg"
            }
        }
        //糖質表示
        if (sugar != null) {
            amount2 = sugar / 10
            }
        if(sugar != 0.0){
            val timer2 = SugarCountDownTimer(2 * 1000, 1)
            if (nut_bar >= 100) {
                timer2.cancel()
            } else {
                timer2.start()
            }
        }else{
            sugarText.text = "0g"
        }

        deleteButton.setOnClickListener {
            val delete = realm.where<Beverage>().equalTo("data_id", data_id).findFirst()
            realm.executeTransaction {
                if(data_id >= tea_num + juice_num + coffee_num + energy_num) {
                    delete?.deleteFromRealm()
                    Toast.makeText(applicationContext,"データを削除しました",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext,"初期データは削除できません",Toast.LENGTH_SHORT).show()
                }
            }
            if(data_id >= tea_num + juice_num + coffee_num + energy_num) {
                val intent = Intent(this, selectCategoryActivity::class.java)
                startActivity(intent)
            }
        }

        addButton.setOnClickListener {
            if((sugar != null) && (caffeine != null))
                savePreferences("SUGAR",sugar.toFloat(),"CAFFEINE",caffeine.toFloat())
            Toast.makeText(applicationContext,"`" + name + "`を飲みました",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
