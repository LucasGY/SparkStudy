package com.sparrowrecsys.offline.spark.featureeng

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, sql}
import org.apache.spark.ml.{Pipeline, PipelineStage}
import org.apache.spark.ml.feature._
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object FeatureEngineering {

  def oneHotEncoderExample(samples: DataFrame): Unit = {
    val sampleWithIdNumber = samples.withColumn("movieIdNumber", col("movieId").cast(sql.types.IntegerType))
    println(sampleWithIdNumber)
    sampleWithIdNumber.show(10)
    sampleWithIdNumber.printSchema()

    val oneHotEncoder = new OneHotEncoderEstimator()
      .setInputCols(Array("movieIdNumber"))
      .setOutputCols(Array("movieIdVector"))
      .setDropLast(false)

    val oneHotEncoderSamples = oneHotEncoder
      .fit(sampleWithIdNumber)
      .transform(sampleWithIdNumber)
    oneHotEncoderSamples.printSchema()
    oneHotEncoderSamples.show(10)


  }
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setMaster("local")  //告诉Spark如何连接到集群上。例子中使用local可以让Spark运行在单机单线程上而无需连接到集群
      .setAppName("featureEngineering")  //当连接到一个集群时，这个值可以帮助我们在集群管理器的用户界面中找到应用
      .set("spark.submit.deployMode", "client")  //spark部署方式

    val spark = SparkSession.builder.config(conf).getOrCreate()

    val movieResourcesPath = this.getClass.getResource("/webroot/sampledata/movies.csv")
    println(movieResourcesPath)
    println(movieResourcesPath.getPath)
    val movieSamples = spark.read.format("csv")
      .option("header","true")
      .load(movieResourcesPath.getPath)
    println("Raw Movie Samples:")
    movieSamples.printSchema()
    movieSamples.show(10)

    oneHotEncoderExample(movieSamples)




  }

}
