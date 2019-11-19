package com.qiding.storm;

import org.apache.storm.Config;
import org.apache.storm.thrift.TException;
import org.apache.storm.thrift.transport.TTransportException;
import org.apache.storm.utils.DRPCClient;

public class DrpcTest {
    public static void main(String[] args) throws TException {

        Config conf = new Config();
        conf.setDebug(false);
        conf.put("storm.thrift.transport", "org.apache.storm.security.auth.SimpleTransportPlugin");
        conf.put(Config.STORM_NIMBUS_RETRY_TIMES, 3);
        conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL, 10);
        conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL_CEILING, 20);
        conf.put(Config.DRPC_MAX_BUFFER_SIZE, 1048576);
        DRPCClient drpcClient = new DRPCClient(conf, "172.28.61.30", 3772);
        String result=drpcClient.execute("helloWorld", "tech.backtype.com/blog/123");
        System.out.println("========"+result);




    }
}
