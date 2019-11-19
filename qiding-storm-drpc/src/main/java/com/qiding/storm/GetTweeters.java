package com.qiding.storm;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import javax.naming.event.ObjectChangeListener;
import java.util.*;

public class GetTweeters extends BaseBasicBolt{

    public static Map<String, List<String>> TWEETERS_DB = new HashMap<String,List<String>>() {{
        put("foo.com/blog/1", Arrays.asList("sally", "bob", "tim", "george", "nathan"));
        put("engineering.twitter.com/blog/5", Arrays.asList("adam", "david", "sally", "nathan"));
        put("tech.backtype.com/blog/123", Arrays.asList("tim", "mike", "john"));
    }};


    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Object id=input.getValue(0);
        String url=input.getString(1);
        List<String> tweeters=TWEETERS_DB.getOrDefault(url,new ArrayList<>());
        for(String tweeter:tweeters){
            collector.emit(new Values(id,tweeter));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("id","tweeter"));
    }

}
