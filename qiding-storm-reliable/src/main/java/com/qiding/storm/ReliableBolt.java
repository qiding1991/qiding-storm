package com.qiding.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;
import java.util.Random;

public class ReliableBolt extends BaseRichBolt {

    private static final Integer MAX_PERCENT_FAIL = 80;
    Random random = new Random();
    private OutputCollector collector;

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        System.out.println(input.getMessageId());
        System.out.println(input.getStringByField("transaction"));
        if (random.nextInt(100) > MAX_PERCENT_FAIL) {
            collector.fail(input);
        } else {
            collector.ack(input);
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
          return;
    }
}
