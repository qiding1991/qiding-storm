package com.qiding.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

/**
 * Hello world!
 *
 */
public class Topology
{
    public static void main( String[] args ) throws Exception {

        ReliableSpout reliableSpout=new ReliableSpout();
        ReliableBolt reliableBolt=new ReliableBolt();

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout",reliableSpout,3);
        builder.setBolt("blot",reliableBolt,4).shuffleGrouping("spout");

        Config config=new Config();
        LocalCluster cluster=new LocalCluster();
        cluster.submitTopology("reliable",config,builder.createTopology());


        Utils.sleep(10000);
        cluster.killTopology("reliable");
        cluster.shutdown();
        System.out.println( "Hello World!" );
    }
}
