package com.example.measureapplication

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Beverage : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var name: String=""
    var caffeine: Int = 0
    var vitamin: Int = 0
    var sugar: Int =0
    var category_id = 0
    var JAL_code: Long = 0
    var data_id:Int = 0
}