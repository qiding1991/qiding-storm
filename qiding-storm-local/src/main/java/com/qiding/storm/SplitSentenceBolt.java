package com.qiding.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class SplitSentenceBolt extends BaseRichBolt {
    private OutputCollector collector;
    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
       this.collector=collector;
    }

    @Override
    public void execute(Tuple input) {
         String sentence=input.getStringByField("sentence");
         String []words=sentence.split(" ");
         for(String word:words){
             this.collector.emit(new Values(word));
         }
        // System.out.println(input.getMessageId());
         //collector.ack(input);

        System.out.println("====thread===="+Thread.currentThread().getName());
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }


}
