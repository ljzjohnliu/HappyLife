package com.ilife.happy.testKotlin

import retrofit2.Retrofit

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
//        val person = Person("ljz",false)
        val person = Person.instance

        makeApiClient(Person::class.java)

//        println("Person init 2,gender: " + (person == Person::class.java))
    }

    @JvmStatic
    fun <T : Any> makeApiClient(cls: Class<T>?) {
        println("Test makeApiClient -----1111------cls = " + cls)
        println("Test makeApiClient -----2222------    = " + (cls == Person::class.java))
        println("Test makeApiClient -----3333------    = " + (cls === Person::class.java))
//        println("Test makeApiClient -----3333------    = " + (cls is Person))
        if (cls == Person::class.java) {
            println("Test makeApiClient -----4444----------")
        } else if (cls == Person::class) {
            println("Test makeApiClient -----5555----------")
        } else {
            println("Test makeApiClient -----6666----------")
            throw UnsupportedOperationException("Unsupported Api class Type")
        }
    }
}