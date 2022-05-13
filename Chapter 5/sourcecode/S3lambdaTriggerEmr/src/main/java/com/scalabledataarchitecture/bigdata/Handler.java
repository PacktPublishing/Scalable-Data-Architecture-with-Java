package com.scalabledataarchitecture.bigdata;

import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClientBuilder;
import com.amazonaws.services.elasticmapreduce.model.*;
import com.amazonaws.services.elasticmapreduce.util.StepFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;


import java.util.HashSet;
import java.util.Set;

public class Handler implements RequestHandler<S3Event, Integer> {
    @Override
    public Integer handleRequest(S3Event s3Event, Context context) {
        LambdaLogger logger = context.getLogger();
        Set<String> s3ObjectNames = new HashSet<>();
        for (S3EventNotificationRecord record:
                s3Event.getRecords()) {
            String s3Key = record.getS3().getObject().getKey();
            String s3Bucket = record.getS3().getBucket().getName();
            s3ObjectNames.add("s3://"+s3Bucket+"/"+s3Key);
        }

        s3ObjectNames.forEach(inputS3path ->{
            createClusterAndRunJob(inputS3path,logger);
        });

        return 201;
    }

    private void createClusterAndRunJob(String inputS3path, LambdaLogger logger) {
        //Create a EMR object using AWS SDK
        AmazonElasticMapReduce emr = AmazonElasticMapReduceClientBuilder.standard()
                .withRegion("us-east-2")
                .build();

        // create a step to submit spark Job in the EMR cluster to be used by runJobflow request object
        StepFactory stepFactory = new StepFactory();

        HadoopJarStepConfig sparkStepConf = new HadoopJarStepConfig()
                .withJar("command-runner.jar")
                .withArgs("spark-submit","--deploy-mode","cluster","--class","com.scalabledataarchitecture.bigdata.EcomAnalysisDriver","s3://jarandconfigs/EcommerceAnalysis-1.0-SNAPSHOT.jar",inputS3path,"s3://scalabledataarch/output");

        StepConfig sparksubmitStep = new StepConfig()
                .withName("Spark Step")
                .withActionOnFailure("TERMINATE_CLUSTER")
                .withHadoopJarStep(sparkStepConf);

        //Create an application object to be used by runJobflow request object
        Application spark = new Application().withName("Spark");

        //Create a runjobflow request object
        RunJobFlowRequest request = new RunJobFlowRequest()
                .withName("chap5_test_auto")
                .withReleaseLabel("emr-6.4.0")
                .withSteps(sparksubmitStep)
                .withApplications(spark)
                .withLogUri("s3n://aws-logs-627443126298-us-east-2/elasticmapreduce/")
                .withServiceRole("EMR_DefaultRole")
                .withJobFlowRole("EMR_EC2_DefaultRole")
                .withScaleDownBehavior(ScaleDownBehavior.TERMINATE_AT_TASK_COMPLETION) //tells that the EMR cluster is transient in Nature
                .withInstances(new JobFlowInstancesConfig()
                        .withKeepJobFlowAliveWhenNoSteps(false)
                        .withEc2SubnetId("subnet-e927d782")
                        .withEmrManagedMasterSecurityGroup("sg-0303501d2efbd1e5f")
                        .withEmrManagedSlaveSecurityGroup("sg-0af40754aa9123dde")
                        .withInstanceGroups(new InstanceGroupConfig()
                                .withInstanceCount(2)
                                .withInstanceType("m5.xlarge")
                                .withInstanceRole(InstanceRoleType.CORE)
                                .withName("Core Instance Group")
                                .withEbsConfiguration(new EbsConfiguration()
                                                    .withEbsBlockDeviceConfigs(new EbsBlockDeviceConfig()
                                                                        .withVolumeSpecification(new VolumeSpecification()
                                                                                .withVolumeType("gp2")
                                                                                .withSizeInGB(32)
                                                                        )
                                                                        .withVolumesPerInstance(2)
                                                    )
                                ),
                                new InstanceGroupConfig()
                                        .withInstanceCount(1)
                                        .withInstanceType("m5.xlarge")
                                        .withInstanceRole(InstanceRoleType.MASTER)
                                        .withName("Master Instance Group")
                                        .withEbsConfiguration(new EbsConfiguration()
                                                .withEbsBlockDeviceConfigs(new EbsBlockDeviceConfig()
                                                        .withVolumeSpecification(new VolumeSpecification()
                                                                .withVolumeType("gp2")
                                                                .withSizeInGB(32)
                                                        )
                                                        .withVolumesPerInstance(2)
                                                )
                                        )
                        )
                );
        //Create and run a  new cluster using runJobFlow method
        RunJobFlowResult result = emr.runJobFlow(request);
        logger.log("The cluster ID is " + result.toString());
    }
}
