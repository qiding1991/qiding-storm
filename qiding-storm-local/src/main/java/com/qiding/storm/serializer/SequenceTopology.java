package com.qiding.storm.serializer;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class SequenceTopology {
    public static void main(String[] args) throws Exception {

        TopologyBuilder builder=new TopologyBuilder();
        builder.setSpout("spout",new SequenceSpout(),2).setNumTasks(4);
        builder.setBolt("bolt",new SequenceBolt(),3).setNumTasks(6).shuffleGrouping("spout");
        Config config=new Config();

        LocalCluster cluster=new LocalCluster();
        cluster.submitTopology("hello-world",config,builder.createTopology());
        Utils.sleep(10000);
        cluster.killTopology("hello-world");
        cluster.shutdown();




    }
}
