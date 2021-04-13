package com.example.measureapplication

class Ball {
    var ball_num:Int = 0
    var radius = 50.0f  //ボールの半径を表す定数
    val coef = 1000.0f  //ボールの移動量を調整するための計数

    var ballX: Float = 0f   //ボールの現在のx座標
    var ballY: Float = 0f   //ボールの現在のy座標
    var vx: Float = 0f      //ボールのx方向への加速度
    var vy: Float = 0f      //ボールのy方向への加速度
    var time: Long = 0L     //前回時間の保持
}