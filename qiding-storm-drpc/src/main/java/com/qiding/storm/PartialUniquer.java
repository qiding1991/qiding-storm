package com.qiding.storm;

import org.apache.log4j.Logger;
import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.topology.base.BaseBatchBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PartialUniquer extends BaseBatchBolt {
    static Logger logger = Logger.getLogger(CountAggregator.class);

    BatchOutputCollector collector;
    Object id;
    Set<String> followers = new HashSet<String>();

    @Override
    public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, Object id) {
            this.collector=collector;
            this.id=id;
    }

    @Override
    public void execute(Tuple tuple) {
        logger.info("PartialUniquer===="+tuple.getString(1));
        followers.add(tuple.getString(1));
    }

    @Override
    public void finishBatch() {
        collector.emit(new Values(id,followers.size()));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("id","partial-count"));
    }
}
