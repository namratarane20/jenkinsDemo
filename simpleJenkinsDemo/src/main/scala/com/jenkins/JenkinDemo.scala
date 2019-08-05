package com.jenkins
class SimpleDemo{
  def call(): Unit ={
    for(i <- 0 to 10){
      println("welcome"+i)
    }
  }
}
object JenkinDemo {
  def main(args: Array[String]): Unit = {
    val obj = new SimpleDemo();
    obj.call()

  }

}
