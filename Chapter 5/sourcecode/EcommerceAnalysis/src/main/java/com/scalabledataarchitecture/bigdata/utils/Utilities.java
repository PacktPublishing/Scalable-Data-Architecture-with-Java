package com.scalabledataarchitecture.bigdata.utils;

import org.apache.spark.sql.SparkSession;

import java.util.Map;
import java.util.Properties;

public enum Utilities {
    INSTANCE;

    public SparkSession createSparkSession(String appName){
        return SparkSession.builder()
                .appName(appName)
                .getOrCreate();
    }
}
