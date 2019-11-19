package com.qiding.storm.drpc;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.drpc.LinearDRPCTopologyBuilder;

public class DrpcMain {
    public static void main(String[] args) throws Exception {

        LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("helloWorld");
        builder.addBolt(new ExclaimBolt(),1);

        LocalDRPC localDRPC=new LocalDRPC();
        LocalCluster localCluster=new LocalCluster();
        localCluster.submitTopology("drpc-demo",new Config(),builder.createLocalTopology(localDRPC));
        System.out.println("Results for 'hello':" + localDRPC.execute("helloWorld", "hello"));
        localCluster.shutdown();
        localDRPC.shutdown();
    }
}
