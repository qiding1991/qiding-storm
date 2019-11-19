package com.qiding.storm.serializer;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;


public class SequenceBolt extends BaseRichBolt {



    static Logger LOG = Logger.getLogger(SequenceBolt.class);

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {

    }

    @Override
    public void execute(Tuple input) {
       Object object= input.getValueByField("streamObject");
        LOG.info(object);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
