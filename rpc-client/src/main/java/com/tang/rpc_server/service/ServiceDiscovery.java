package com.tang.rpc_server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceDiscovery {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDiscovery.class);
	 
    private CountDownLatch latch = new CountDownLatch(1);
 
    private volatile List<String> dataList = new ArrayList<String>();
 
    private String registryAddress;
 
    public ServiceDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;
 
        ZooKeeper zk = connectServer();
        if (zk != null) {
            watchNode(zk);
        }
    }
 
    public String discover() {
        String data = null;
        int size = dataList.size();
        if (size > 0) {
            if (size == 1) {
                data = (String) dataList.get(0);
                LOGGER.debug("using only data: {}", data);
            } else {
                data = (String) dataList.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.debug("using random data: {}", data);
            }
        }
        return data;
    }
 
    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return zk;
    }
 
    private void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren("/registry", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        watchNode(zk);
                    }
                }
            });
            List<String> dataList = new ArrayList<String>();
            for (String node : nodeList) {
                byte[] bytes = zk.getData("/registry/" + node, false, null);
                dataList.add(new String(bytes));
            }
            LOGGER.debug("node data: {}", dataList);
            this.dataList = dataList;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }
}
