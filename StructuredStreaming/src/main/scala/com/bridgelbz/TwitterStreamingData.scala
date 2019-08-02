
// Program  to Read Twitter data from s3 and call machine learning algorithm for prediction
//store it to s3
package com.bridgelbz

import java.io.File
import java.nio.file.Paths

import org.apache.commons.exec.{CommandLine, DefaultExecutor}
import org.apache.commons.io.FileUtils
import org.apache.hadoop.fs.LocalFileSystem
import org.apache.hadoop.hdfs.DistributedFileSystem
import org.apache.log4j.Logger
import org.apache.spark.sql.{SparkSession, functions}

/*

import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.io.FileUtils
import org.apache.hadoop.fs.LocalFileSystem
import org.apache.hadoop.hdfs.DistributedFileSystem
import org.apache.log4j.{Logger, PropertyConfigurator}
import org.apache.spark.sql.{SparkSession, functions}

*/


class  LiveStreaming {

  // Entry point for spark

  /*val logger :Logger = Logger.getLogger("LiveStreaming")
  logger.debug("started spark asession")*/

    //logger.debug("it is debugger=============")
    //logger.info("this is info message===============")

  val sparkSession: SparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("Streaming")
    .getOrCreate()


  val sparkContext = sparkSession.sparkContext
  val accessKey = System.getenv("AWS_ACCESS_KEY_ID")

  val secretKey = System.getenv("AWS_SECRET_ACCESS_KEY")
  println(accessKey + " " + secretKey)

  sparkContext.hadoopConfiguration.set ( "fs.s3a.access.key", accessKey )
  sparkContext.hadoopConfiguration.set ( "fs.s3a.secret.key", secretKey)


  sparkContext.hadoopConfiguration.set("fs.hdfs.impl", classOf[DistributedFileSystem].getName)
  sparkContext.hadoopConfiguration.set("fs.file.impl", classOf[LocalFileSystem].getName)

  sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "s3.ap-south-1.amazonaws.com")
  sparkContext.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")

  println("sparkSession started successfully=========================================================")

  // Function  to read tweeter data from s3==========================================================

  def readFile(AWSPath: String): Unit = {


    val path1 = System.getProperty("user.dir")

    var systemPath = Paths.get(path1 + "/Data")

    val data = sparkSession.read.format("com.databricks.spark.csv" )
      .option("header",true)
      .load(AWSPath)

    val newData= data.withColumn("tweet", functions.regexp_replace(data.col("tweet"), "[,]"," "))
    newData.write.option("header",true).format("com.databricks.spark.csv" ).save(systemPath.toString)

    //val  mainData=newData.write.format("com.databricks.spark.csv" ).save(systemPath)
  //  newData.withColumn("tweet", functions.regexp_replace(data.col("tweet"), "/[^a-zA-Z0-9 ]/g", ""))

  } // end of function readFile
//==============================================================================================
 // function to call python file================================================================

    def searchForCSV(pyPath:String) {

      val path1 = System.getProperty("user.dir")

      var systemPath = Paths.get(path1 + "/Data")


      val file = new File(systemPath.toString)

      val CSVPath = file.listFiles().filter(_.isFile)
        .filter(_.getName.endsWith(".csv"))
        .map(_.getPath).toList

      println("this is our csv file======= " + CSVPath(0))


      val path2 = System.getProperty("user.dir")

      val pythonPath = Paths.get(path2 + "/twitter_pos_neg_train.py")

   // called python file through pipe=============================================================

      val distScript = pythonPath.toString
      //      val distScriptName = "stock_Price_Trainer.py"
      //sc.addFile ( distScript )
      val path3 = System.getProperty("user.dir")
      val modelPath = Paths.get(path3 + "/Model.pkl")

      val l = List(CSVPath(0) + " " + modelPath.toString)
      val ipData = sparkContext.makeRDD(l, 1)
      //val ipData = sparkContext.parallelize(l)
      val opData = ipData.pipe(pyPath)

     // opData.collect().foreach(println)

    }
   // function to store the model to s3============================================================

     def storeToS3(storedPath:String): Unit = {

      /* val path4 = System.getProperty("user.dir")
       val shelsPath= Paths.get(path4 + "/ShellsFile1.sh")
      */

       //"/home/admin1/Namrata/SparkProject/StructuredStreaming/src/main/scala/ShellsFile.sh"

      // println(shelsPath)
       val cmd = new CommandLine("/home/admin1/Namrata/SparkProject/StructuredStreaming/ShellsFile1.sh")

       val path5 = System.getProperty("user.dir")
       val modelPath= Paths.get(path5 +"/Model.pkl")

       cmd.addArgument(modelPath.toString)

       cmd.addArgument("s3://python-spark-demo/")

       val exec = new DefaultExecutor
       exec.setWorkingDirectory(FileUtils.getUserDirectory())
       exec.execute(cmd)

     }




}
  object TwitterStreamingData {

    def main(args: Array[String]): Unit = {
      val obj = new LiveStreaming()
    /*   obj.readFile("s3a://"+args(0)) //bucketname/filename
    //obj.readFile("")
    obj.searchForCSV(args(1))
    obj.storeToS3("s3a://"+args(2))*/

      obj.readFile("s3a://python-spark-demo/")
      obj.searchForCSV("/home/admin1/Namrata/SparkProject/StructuredStreaming/src/main/scala/com/bridgelbz/twitter_pos_neg_train.py")
      obj.storeToS3("s3a://python-spark-demo/")
    }
  }

// python-spark-demo/
//"/home/admin1/Namrata/SparkProject/StructuredStreaming/src/main/scala/com/bridgelbz/twitter_pos_neg_train.py"
// python-spark-demo/