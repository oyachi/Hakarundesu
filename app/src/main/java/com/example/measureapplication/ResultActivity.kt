package com.example.measureapplication

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity(),SensorEventListener , SurfaceHolder.Callback{

    private var surfaceWidth : Int = 0   //サーフェスビューの幅
    private var surfaceHeight : Int = 0     //サーフェスビューの高さ
    //private var bmp :Bitmap = null

    val ball_num = 1

    var ball : MutableList<Ball> = mutableListOf()

    private lateinit var realm: Realm
    private fun initRealm(){
        val realmconfiguration = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().schemaVersion(0).build()
        realm = Realm.getInstance(realmconfiguration)
    }


    private fun drawCanvas(num:Int){
        val canvas = surfaceView.holder.lockCanvas()
        canvas.drawColor(Color.WHITE)
        canvas.drawCircle(ball[num].ballX,ball[num].ballY,ball[num].radius, Paint().apply{
            color = Color.MAGENTA
        })
        var paint:Paint = Paint()
        paint.setFilterBitmap(true)

        var bmp :Bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.lemon)
        var bmp2 :Bitmap = Bitmap.createScaledBitmap(bmp,800,800,false)
        canvas.drawBitmap(bmp2,ball[num].ballX,ball[num].ballY,paint)
       /* canvas.drawCircle(surfaceWidth/2.toFloat(),surfaceHeight/2.toFloat() ,100f,Paint().apply{
            color = Color.MAGENTA
        })*/

        surfaceView.holder.unlockCanvasAndPost(canvas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //realm = Realm.getDefaultInstance()
        initRealm()

        //インテント
        val list_id = intent.getIntExtra("LIST_ID",0)
        val beverage = realm.where<Beverage>().equalTo("id",list_id).findFirst()
        val id = beverage?.id?.toInt()
        val name = beverage?.name
        val caffeine = beverage?.caffeine
        val vitamin = beverage?.vitamin
        resultText.text = "ID:" + list_id.toString()+" ,name:"+name.toString()+" ,caffeine:"+caffeine.toString()+" ,vitamin:"+ vitamin.toString()


        val holder = surfaceView.holder
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder?,format:Int,width:Int,height:Int) {
        surfaceWidth = width
        surfaceHeight = height

        for(i in 0..ball_num-1){
            ball.add(Ball())
        }

        for(i in 0..ball_num-1) {
           // ball[i].ballX = (width+ball[i].radius-Math.random()*width+ball[i].radius).toFloat()
            ball[i].ballX = (width / 2).toFloat()
            ball[i].ballY = 0f
            ball[i].radius = (width/2).toFloat()
           // ball[i].radius = 100f
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER )
        sensorManager.registerListener(this,accSensor,SensorManager.SENSOR_DELAY_GAME)

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event == null ) return

        for(i in 0..ball_num-1) {
            //while(ball[i].ballY<surfaceHeight/0.5) {
                if (ball[i].time == 0L) ball[i].time = System.currentTimeMillis()
                if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    var x = -event.values[0]
                    var y = event.values[1]

                    var t = (System.currentTimeMillis() - ball[i].time).toFloat()
                    ball[i].time = System.currentTimeMillis()
                    t /= 1000.0f

                    var dx = ball[i].vx * t + x * t * t / 2.0f
                    var dy = ball[i].vy * t + y * t * t / 2.0f
                    ball[i].ballX += dx * ball[i].coef
                    ball[i].ballY += dy * ball[i].coef
                    ball[i].vx += x * t
                    ball[i].vy += y * t

                    if (ball[i].ballX - ball[i].radius < 0 && ball[i].vx < 0) {
                        ball[i].vx = -ball[i].vx / 1.5f
                        ball[i].ballX = ball[i].radius
                    } else if (ball[i].ballX + ball[i].radius > surfaceWidth && ball[i].vx > 0) {
                        ball[i].vx = -ball[i].vx / 1.5f
                        ball[i].ballX = surfaceWidth - ball[i].radius
                    }
                    if (ball[i].ballY - ball[i].radius < 0 && ball[i].vy < 0) {
                        ball[i].vy = -ball[i].vy / 1.5f
                        ball[i].ballY = ball[i].radius
                    } else if (ball[i].ballY + ball[i].radius > surfaceHeight && ball[i].vy > 0) {
                        ball[i].vy = -ball[i].vy / 1.5f
                        ball[i].ballY = surfaceHeight - ball[i].radius
                    }
                   //drawBack()
                   //drawCanvas(i)


                }
           // }

        }
       for(i in 0..ball_num-1){
            drawCanvas(i)
        }
    }



    override fun onDestroy(){
        super.onDestroy()
        realm.close()
    }
}
