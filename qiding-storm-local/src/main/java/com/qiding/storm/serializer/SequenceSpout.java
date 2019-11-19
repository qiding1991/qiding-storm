package com.qiding.storm.serializer;

import org.apache.log4j.Logger;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;

public class SequenceSpout extends BaseRichSpout {

    static Logger LOG = Logger.getLogger(SequenceBolt.class);
    private StreamObject streamObject = new StreamObject("qiding", 1000);

    private SpoutOutputCollector collector;

    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        //todo 数据来源
        this.collector.emit(new Values(streamObject));
        Utils.sleep(1000);
        LOG.info("spout===="+streamObject);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("streamObject"));
    }
}
