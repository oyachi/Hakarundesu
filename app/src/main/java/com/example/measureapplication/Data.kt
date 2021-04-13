package com.example.measureapplication

open class Data {
    //beverage_idデータ
    val tea = 0
    val juice = 1
    val coffee = 2
    val energy = 3

    //1日の摂取量データ
    var caffeien_count = 0
    var sugar_count = 0
    val caffeine_limit = 500
    val sugar_limit = 500

    //teaデータ
    val tea_name : Array<String>  = arrayOf("生茶","伊右衛門","特茶","午後の紅茶 無糖","午後の紅茶 ミルクティー","お〜いお茶","アイスティー微糖")
    val tea_caffeine = arrayOf(43,75,90,47,100,68,75)
    val tea_sugar = arrayOf(0,0,0,0,39,0,0)
    val tea_vitamin = arrayOf(0,0,0,0,0,0,0)
    val tea_JAL = longArrayOf(4909411069063,4901777119215,4901777247680,4909411079321,4909411076856,4901085192009,4582409183813)
    val tea_category = arrayOf(0,0,0,0,0,0,0)
    var tea_id : MutableList<Int> = mutableListOf()

    //juiceデータ
    val juice_name : Array<String> = arrayOf("コカ・コーラ","ペプシコーラ","三ツ矢サイダー","CCレモン","カルピスウォーター","ポカリスエット","アクエリアス")
    val juice_caffeine = arrayOf(50,50,0,0,0,0,0)
    val juice_sugar = arrayOf(57,60,47,50,55,31,24)
    val juice_vitamin = arrayOf(0,0,0,0,0,0,0)
    val juice_JAL = longArrayOf(4902102072618,4901777052444,4514603347814,4901777232310,4901340689213,4987035332602,4902102069359)
    val juice_category = arrayOf(1,1,1,1,1,1,1)
    var juice_id : MutableList<Int> = mutableListOf()

    //coffeeデータ
    val coffee_name : Array<String> = arrayOf("CRAFT BOSS Black","CRAFT BOSS latte","UCC BLACK","MAX COFFEE","エメラルドマウンテン","ワンダ モーニングショット","ワンダ 金の微糖")
    val coffee_caffeine = arrayOf(200,150,300,100,110,110,130)
    val coffee_sugar = arrayOf(1,25,0,25,13,12,6)
    val coffee_vitamin = arrayOf(0,0,0,0,0,0,0)
    val coffee_JAL = longArrayOf(4901777300521,4901777300545,4901201208096,4902102009331,4902102107341,4514603284317,4514603360912)
    val coffee_category = arrayOf(2,2,2,2,2,2,2)
    var coffee_id : MutableList<Int> = mutableListOf()

    //energyデータ
    val energy_name : Array<String> = arrayOf("RED BULL","RAIZIN DRY","リポビタンD","MONSTER ENERGY","MONSTER ENERGY KAHOS","MONSTER ENERGY ULTRA","MONSTER ENERGY ABSOLUTERYZERO","MONSTER ENERGY CUBA-LIBRE")
    val energy_caffeine = arrayOf(80,50,50,142,142,142,142,142)
    val energy_sugar = arrayOf(27,20,17,46,32,3,4,36)
    val energy_vitamin = arrayOf(0,0,0,0,0,0,0,0)
    val energy_JAL = longArrayOf(4560292290092,4987306013117,4987306003477,4897036690017,4897036691083,4897036690895,4897036690062,4897036691953)
    val energy_category = arrayOf(3,3,3,3,3,3,3,3)
    var energy_id : MutableList<Int> = mutableListOf()
}