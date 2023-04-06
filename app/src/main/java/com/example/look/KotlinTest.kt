package com.example.look

class KotlinTest {

}

fun main() {

    val datas: MutableList<String> = mutableListOf<String>()
    datas.add("test01")

    val letResult = datas.let {
        it.add("test02")
    }
    println("测试let函数返回值$letResult")



}