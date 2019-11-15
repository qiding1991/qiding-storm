package com.qiding.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class WordCountBlot extends BaseRichBolt {
    private OutputCollector collector;
    private HashMap<String, Long> counts = null;

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector=collector;
        this.counts=new HashMap<>();
    }

    @Override
    public void execute(Tuple input) {
          String word=input.getStringByField("word");
          Long count= this.counts.getOrDefault(word,0L);
          count++;
          this.counts.put(word,count);
          this.collector.emit(new Values(word,count));

          System.out.println("====thread===="+Thread.currentThread().getName());
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word","count"));
    }
}
