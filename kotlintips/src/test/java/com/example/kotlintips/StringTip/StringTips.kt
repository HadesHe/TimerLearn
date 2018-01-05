package com.example.kotlintips.StringTip

import org.junit.Test

/**
 * Created by zhanghehe on 2017/12/24.
 */
class StringTipsTest {

    @Test
    fun testString() {
        val str1 = "abc"
        val str2 = """line1\n
                    line2
                    line3
                    """
        val js = """
            function myFunction()
            {
                document.getElementById("demo")
            }
            """
        println(str1)
        println(str2)
        println(js)

    }

    @Test
    fun testString2() {
        val strings = arrayListOf<String>("abc", "def", "ghi")
        println("First content is $strings")
        println("First content is ${strings[0]}")
        println("First content is ${if (strings.size > 0) strings[0] else "null"}")

    }

    @Test
    fun showDoll() {
        println("First content is \$strings")
        println("First content is ${'$'}strings")
    }

    fun String.lastChar(): Char = this.get(this.length - 1)


    @Test
    fun testFunExtension() {
        //        val str="test function fun"
//        println(str.lastChar())
        fun testIf() = if (false) 1 else null
        println(testIf())
    }


    val s = with(StringBuilder()) {
        append("TAG")
        for (i in 'A'..'Z') {
            append(i)
        }
        this
    }


    data class User(var name: String, var password: String)

    @Test
    fun testIfFunction() {
        var user = User("111", "2222")
        println(user)

        with(user) {
            name = "2222"
            password = "3333"

        }
        println(user)

        with(user,{
            name = "333"
            password = "4444"

        })

        println(user)



    }

//    public inline fun <T>  T.apply(block: T.() → Unit):T
//   public inline fun <T, R> with(receiver: T) {block: T.() → R}: R
//   public inline fun <T, R> with(receiver: T, block: T.() → R): R

    enum class Delivery{
        STANDARD, EXPEROTED
    }

    fun getShippingConstCalculator(delivery: Delivery): (Int) -> Double{
        if(delivery == Delivery.EXPEROTED){
            return {it -> 6+2.1*it}
        }
        return {it->1.3 * it}
    }

    @Test
    fun test05(){
        val calculator1 = getShippingConstCalculator(Delivery.EXPEROTED)
        val calculator2 = getShippingConstCalculator(Delivery.STANDARD)
        println("Ex costs ${calculator1(5)}")
        println("Ex costs ${calculator2(5)}")

    }

    class Worker(private val strategy: () -> Unit){
        fun work(){
            println("START")
            strategy.invoke()
            println("END")
        }
    }

    @Test
    fun testStrategy(){
//        val worker1=Worker({ println("Do A Strategy")})
//        val worker2=Worker({ println("Do B Strategy")})
//
//        worker1.work()
//        worker2.work()

        var user:User?=null

        user?: println("null")
    }

    fun <T> T?.safety(action:T.() -> Any?) = try {
        this?.action()
    }catch (ex:Exception){
        ex.printStackTrace()
    }

    fun <T,R> T?.safetyRun(action: T.() -> R) : R? = try {
        this?.action()
    }catch (ex :Exception){
        ex.printStackTrace()
        null
    }







}

