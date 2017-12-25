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
        val js="""
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
    fun testString2(){
        val strings= arrayListOf<String>("abc","def","ghi")
        println("First content is $strings")
        println("First content is ${strings[0]}")
        println("First content is ${if (strings.size>0) strings[0] else "null"}")

    }

    @Test
    fun showDoll(){
        println("First content is \$strings")
        println("First content is ${'$'}strings")
    }

    fun String.lastChar(): Char=this.get(this.length-1)

    @Test
    fun testFunExtension(){
        val str="test function fun"
        println(str.lastChar())
    }
}