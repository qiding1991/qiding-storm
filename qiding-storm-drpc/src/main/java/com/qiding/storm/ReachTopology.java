package com.qiding.storm;

import clojure.lang.Compiler;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.drpc.LinearDRPCTopologyBuilder;
import org.apache.storm.tuple.Fields;

public class ReachTopology {

    public static void main(String[] args) throws Exception {

        LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("helloWorld");
        builder.addBolt(new GetTweeters(),1);
        builder.addBolt(new GetFollowers(),1).shuffleGrouping();
        builder.addBolt(new PartialUniquer(),2).fieldsGrouping(new Fields("id","follow"));
        builder.addBolt(new CountAggregator(),1).fieldsGrouping(new Fields("id"));

        Config conf=new Config();
       // conf.setNumWorkers(6);

        //本地运行
        LocalCluster cluster=new LocalCluster();
        LocalDRPC drpc=new LocalDRPC();
        cluster.submitTopology("reach-drpc", conf, builder.createLocalTopology(drpc));

        String[] urlsToTry = new String[]{"foo.com/blog/1"};
        for(String url:urlsToTry){
            String result=drpc.execute("helloWorld",url);
            System.out.println("========"+result+"==================");
        }
//
//        drpc.shutdown();
//        cluster.shutdown();
        //提交到服务器运行
//        conf.setNumWorkers(3);
//        StormSubmitter.submitTopology("hello-drpc",conf,builder.createRemoteTopology());

    }



}
