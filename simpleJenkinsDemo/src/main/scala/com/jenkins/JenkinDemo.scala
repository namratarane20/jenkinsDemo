package com.jenkins

import org.apache.spark
import org.apache.spark.SparkContext
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

class SimpleDemo{
  val sparkSession: SparkSession = SparkSession.builder ( )
    .master ( "local[*]" )
    .appName ( "jenkins Demo" )
    .getOrCreate ( )
  val sparkContext: SparkContext = sparkSession.sparkContext
  def call(): Unit ={




    val data = Seq(
      Row("Prince", "24", "P10", "prince@gamial.com", "10000"),
      Row("James", "30", "J20", "james222gmail.com", "30000") ,
      Row("martin", "69", "M30", " martinscala@scala.com", "80000"),
      Row("John", "90", "J50", "johnjava.com", "90000") ,

      Row("Prince", "24", "P10", "prince@gamial.com", "10000"),
      Row("James", "30", "J20", "james222gmail.com", "30000"),
      Row("Prince", "24", "P10", "prince@gamial.com", "10000"),
      Row("James", "30", "J20", "james222gmail.com", "30000")
    )

    val schema = StructType(
      List(
        StructField("NAME", StringType, true),
        StructField("AGE", StringType, true),
        StructField("EMPID", StringType, true),
        StructField("E-MAIL", StringType, true),
        StructField("SALARY", StringType, true)
      )
    )
    val df = sparkSession.createDataFrame(
      sparkSession.sparkContext.parallelize(data), schema
    )


    /*val rowRDD=rdd.map(element => Row(element))
    val df= spark.createDataFrame(rowRDD,schema)*/
    df.printSchema();
    df.show()




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
