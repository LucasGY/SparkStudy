package com.sparkStudy

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object basic {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._
    val df = spark.read.json("./src/main/resources/atguiguData/user.json")
//    df.show()
    //SQL
    df.createOrReplaceTempView("user")
    spark.sql("select * from user").show()



  }

}
