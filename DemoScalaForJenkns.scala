package com

class ScalaDemo{

  def example(): Unit ={
    for(i <- 0 to 10){
      println("welcome to jenkins=========")
    }
  }
}
object DemoScalaForJenkns {
  def main(args: Array[String]): Unit = {
    val obj = new ScalaDemo()
    obj.example()
  }

}
