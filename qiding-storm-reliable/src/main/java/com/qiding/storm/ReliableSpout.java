package com.qiding.storm;

import example.streaming.banktransactions.TransactionsSpouts;
import org.apache.log4j.Logger;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class ReliableSpout extends BaseRichSpout {

    static Logger LOG = Logger.getLogger(TransactionsSpouts.class);
    private static final Integer MAX_FAILS = 2;
    private SpoutOutputCollector collector;
    private Map<Integer, String> messages;
    private Map<Integer, Integer> failCounts;
    private Map<Integer, String> sendMsg;


    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        messages = new HashMap<>(100, 1);
        failCounts = new HashMap<>(100, 1);
        sendMsg = new HashMap<>(100, 1);
        for (int i = 0; i < 100; i++) {
            failCounts.put(i, 0);
            messages.put(i, "transaction" + i);
        }
        sendMsg.putAll(messages);

    }

    @Override
    public void nextTuple() {
        if (!sendMsg.isEmpty()) {
            sendMsg.forEach((key, value) -> collector.emit(new Values(value), key));
            sendMsg.clear();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("transaction"));
    }

    @Override
    public void ack(Object msgId) {
        messages.remove(msgId);
        LOG.info("Message fully processed [" + msgId + "]");
    }

    @Override
    public void fail(Object msgId) {
        //super.fail(msgId);
        Integer key = (Integer) msgId;
        sendMsg.put(key, messages.get(key));
        int failures = failCounts.computeIfPresent(key, (index, value) -> value + 1);
        if (failures >= MAX_FAILS) {
            //If exceeds the max fails will go down the topology
            throw new RuntimeException("Error, transaction id [" + sendMsg.get(key) + "] has had many errors [" + failures + "]");
        }
    }
}
