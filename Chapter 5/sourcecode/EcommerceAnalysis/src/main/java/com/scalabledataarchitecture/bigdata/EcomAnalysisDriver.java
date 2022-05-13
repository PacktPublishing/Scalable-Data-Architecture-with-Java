package com.scalabledataarchitecture.bigdata;

import com.scalabledataarchitecture.bigdata.utils.Utilities;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions.*;
import org.apache.log4j.Logger;
import org.apache.spark.storage.StorageLevel;

public class EcomAnalysisDriver {

    private static final String EVENT_SCHEMA = "event_time TIMESTAMP,event_type STRING,product_id LONG,category_id LONG,category_code STRING,brand STRING,price DOUBLE,user_id LONG,user_session STRING";

    public static void main(String[] args) {
        Logger logger = Logger.getRootLogger();
        if(args.length<2){
            logger.error("Program arguments less than 2");
            System.exit(-1);
        }
        String inputPath = args[0];
        String outputDirectory = args[1];
        SparkSession spark = Utilities.INSTANCE.createSparkSession("EcomAnalyzer");
        Dataset<Row> ecommerceEventDf = spark.read().option("header","true").schema(EVENT_SCHEMA).csv(inputPath);
        ecommerceEventDf.persist(StorageLevel.MEMORY_AND_DISK());// performance optimization
        ecommerceEventDf.createOrReplaceTempView("ecommerceEventDf");
        logger.info("ecommerceEventDf loaded and temp view created");
        //ecommerceEventDf.show()
        //Dataset<Row> describeDf = ecommerceEventDf.describe();
        //describeDf.show(false);
        //Dataset<Row> corrDf = spark.sql("select corr(product_id,category_id) as prod_cat_corr from ecommerceEventDf");
        //corrDf.show();
        Dataset<Row> countAggDf = spark.sql("select year(event_time) as year,month(event_time) as month,category_id,product_id,count_if(event_type='purchase') as tot_sales,count_if(event_type='view') as tot_onlyview from ecommerceEventDf where event_type!='cart' group by year,month,category_id,product_id ");
        countAggDf.createOrReplaceTempView("countAggDf");
        logger.info("countAggDf loaded and temp view created");

        Dataset<Row> revenueAggDf = spark.sql("select year(event_time) as year,month(event_time) as month,category_id,product_id,sum(price) as sales_rev from ecommerceEventDf where event_type='purchase' group by year,month,category_id,product_id");
        revenueAggDf.createOrReplaceTempView("revenueAggDf");
        logger.info("revenueAggDf loaded and temp view created");

        Dataset<Row> combinedAggDf = spark.sql("select cadf.year,cadf.month,cadf.category_id,cadf.category_id,cadf.product_id,tot_sales,tot_onlyview,sales_rev from countAggDf cadf LEFT OUTER JOIN revenueAggDf radf ON cadf.year==radf.year AND cadf.month== radf.month AND cadf.category_id== radf.category_id AND cadf.product_id == radf.product_id");
        Dataset<Row> combinedEnrichAggDf = combinedAggDf.na().fill(0.0,new String[]{"sales_rev"});
        combinedEnrichAggDf.createOrReplaceTempView("combinedEnrichAggDf");
        logger.info("combinedEnrichAggDf loaded and temp view created");

        Dataset<Row> finalTransformedDf = spark.sql("select year,month,category_id,product_id,tot_sales,tot_onlyview,sales_rev,dense_rank() over (PARTITION BY category_id ORDER BY sales_rev DESC) as rank_by_revenue,dense_rank() over (PARTITION BY category_id ORDER BY tot_sales DESC) as rank_by_sales from combinedEnrichAggDf");
        logger.info("finalTransformedDf created and going to write to output bucket"+outputDirectory);
        finalTransformedDf.printSchema();
        finalTransformedDf.show();
        //finalTransformedDf.write().mode(SaveMode.Append).partitionBy("year","month").parquet(outputDirectory);
    }
}
