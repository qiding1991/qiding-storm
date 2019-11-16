package com.qiding.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class MyCountBolt extends BaseRichBolt {

    private OutputCollector collector;
    private Map<String, Integer> countMap;


    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        this.countMap = new HashMap<>();
    }

    @Override
    public void execute(Tuple input) {
        String word=input.getStringByField("word");
        Integer num=input.getIntegerByField("num");
        Integer number= countMap.getOrDefault(word,0);
        countMap.put(word,num+number);
        //输出结果
        System.out.println("count:" + countMap);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
