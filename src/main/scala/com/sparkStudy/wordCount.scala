package com.sparkStudy

import org.apache.spark.SparkConf


object wordCount {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")


  }

}
