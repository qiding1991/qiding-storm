package com.qiding.storm;

import org.apache.log4j.Logger;
import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBatchBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class CountAggregator extends BaseBatchBolt {
    static Logger logger = Logger.getLogger(CountAggregator.class);

    BatchOutputCollector collector;
    Object id;
    int count=0;

    @Override
    public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, Object id) {
         this.collector=collector;
         this.id=id;
    }

    @Override
    public void execute(Tuple tuple) {
        logger.info("CountAggregator 收到请求"+tuple.getValue(1));
        count=count+tuple.getInteger(1);
    }

    @Override
    public void finishBatch() {
        logger.info("请求处理完成");
        collector.emit(new Values(id,count));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("id","reach"));
    }
}
