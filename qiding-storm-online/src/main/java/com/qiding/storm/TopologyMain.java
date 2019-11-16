package com.qiding.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Hello world!
 *
 */
public class TopologyMain
{
    public static void main( String[] args ) throws Exception {
        System.out.println( "Topology online success!" );

        TopologyBuilder  topologyBuilder=new TopologyBuilder();

        //设置数据源
        topologyBuilder.setSpout("mySpout",new MySpout(),1);
        //分割句子
        topologyBuilder.setBolt("mySplit",new MySplitBolt(),1)
                .shuffleGrouping("mySpout");

        //对单词累加
        topologyBuilder.setBolt("myCount",new MyCountBolt(),1)
                .fieldsGrouping("mySplit",new Fields("word"));

        //配置信息
        Config config=new Config();
        config.setNumWorkers(3);

        //本地执行
//        LocalCluster cluster=new LocalCluster();
//        cluster.submitTopology("myWordCount",config,topologyBuilder.createTopology());

        //服务器执行
        StormSubmitter.submitTopology("myWordCount",config,topologyBuilder.createTopology());
    }
}
