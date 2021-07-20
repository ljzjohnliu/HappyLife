package com.ilife.happy.testKotlin

/**
 * 首先会按顺序执行类中init代码块，然后再执行构造方法里代码，并且我可以在init代码块中使用类声明的属性
 * 加入伴生对象后，那肯定就是伴生对象中的代码先执行了。
 */
class Person() {

    /*属性*/
    private var gender: Boolean = true

    /*次构造方法*/
    constructor(name: String, gender: Boolean) : this() {
        println("constructor")
    }

    /*初始化代码块*/
    init {
        println("Person init 2,gender:${gender}")
    }

    /*初始化代码块*/
    init {
        println("Person init 1")
    }

    companion object {
        /**
         * 首先伴生对象中的代码是在类加载时就会执行，此时会先顺序的执行伴生对象中的init代码块，但是由于instance是懒加载的，所以只有当我们代码出现Person.instance时，才会执行instance中委托的代码。
        此时会去调用指定的构造函数，而执行构造函数时就和最上面的那种执行顺序是一致的了，先执行类中的init代码块，再执行构造函数原本的代码
         */
//        val instance by lazy {
//            println("Person lazy instance")
//            Person("yzq",false)
//        }

        /**
         * 如果伴生对象里的instance不是懒加载的
         * Kotlin中的伴生对象相当于Java中的Static关键字。
        伴生对象里的init代码块就相当于Java中的静态代码块。在类加载的时候会优先执行且只会执行一次。
         */
        val instance = Person("yzq", false)

        /*伴生对象中的初始化代码*/
        init {
            println("companion init 1")
        }

        init {
            println("companion init 2")
        }
    }
}