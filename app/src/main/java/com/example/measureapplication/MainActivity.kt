package com.example.measureapplication

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.TextView
import com.google.zxing.integration.android.IntentIntegrator
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    private fun initRealm(){
        val realmconfiguration = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().schemaVersion(0).build()
        realm = Realm.getInstance(realmconfiguration)
    }

    override fun onBackPressed() {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //realmのインスタンス取得
       initRealm()

        //測るボタン
        measureButton.setOnClickListener {
            //インテント
            //var intent = Intent(this, selectCategoryActivity::class.java)
            val intent = Intent(this,AnimationActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        //初期化ボタン
        initButton.setOnClickListener {
            //データ削除
               val delete = realm.where(Beverage::class.java).findAll()
                realm.executeTransaction {
                    delete.deleteAllFromRealm()
                }
            //累積初期化
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val editor = sharedPreferences.edit()
            editor.putFloat("SUGAR", 0f)
            editor.putFloat("CAFFEINE",0f)
            editor.apply()
            //テキスト1リセット
            count1Text.text = "糖質 : 約0.0%"
            count1Text.setTextColor(Color.parseColor("#000000"))
            ObjectAnimator.ofFloat(count1Text,"alpha",1.0f,1.0f).apply{
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                duration = 500
                start()
            }
            //テキスト2リセット
            count2Text.text = "カフェイン : 約0.0%"
            count2Text.setTextColor(Color.parseColor("#000000"))
            ObjectAnimator.ofFloat(count2Text,"alpha",1.0f,1.0f).apply{
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                duration = 500
                start()
            }
        }

        //リセットボタン
        deleteButton.setOnClickListener{
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val editor = sharedPreferences.edit()
            editor.putFloat("SUGAR", 0f)
            editor.putFloat("CAFFEINE",0f)
            editor.apply()
            //テキスト1リセット
            count1Text.text = "糖質 : 約0.0%"
            count1Text.setTextColor(Color.parseColor("#000000"))
            ObjectAnimator.ofFloat(count1Text,"alpha",1.0f,1.0f).apply{
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                duration = 500
                start()
            }
            //テキスト2リセット
            count2Text.text = "カフェイン : 約0.0%"
            count2Text.setTextColor(Color.parseColor("#000000"))
            ObjectAnimator.ofFloat(count2Text,"alpha",1.0f,1.0f).apply{
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                duration = 500
                start()
            }
        }

        var pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        var sugar = pref.getFloat("SUGAR", 0f)
        var caffeine = pref.getFloat("CAFFEINE",0f)
        if(sugar <= 25) {
            var percent1 = (kotlin.math.round(((sugar / 25) * 100)*1000))/1000
            count1Text.text = "糖質 : 約" + percent1.toString() + "%"
        }else{
            count1Text.text = "糖質を取りすぎています！"
            count1Text.setTextColor(Color.RED)
            ObjectAnimator.ofFloat(count1Text,"alpha",1.0f,0.0f).apply{
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                duration = 500
                start()
            }
        }
        if(caffeine <= 400) {
            var percent2 = (kotlin.math.round(((caffeine / 400) * 100) * 1000)) /1000
            count2Text.text = "カフェイン : 約" + percent2.toString() + "%"
        }else{
            count2Text.text = "カフェインを取りすぎています！"
            count2Text.setTextColor(Color.RED)
            ObjectAnimator.ofFloat(count2Text,"alpha",1.0f,0.0f).apply{
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                duration = 500
                start()
            }
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        realm.close()
    }
}
