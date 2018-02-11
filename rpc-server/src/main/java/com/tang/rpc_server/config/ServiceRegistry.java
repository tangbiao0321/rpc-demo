package com.tang.rpc_server.config;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceRegistry {
	 private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);
	 
	    private CountDownLatch latch = new CountDownLatch(1);
	 
	    private String registryAddress;
	 
	    public ServiceRegistry(String registryAddress) {
	        this.registryAddress = registryAddress;
	    }
	 
	    public void register(String data) {
	        if (data != null) {
	            ZooKeeper zk = connectServer();
	            if (zk != null) {
	                createNode(zk, data);
	            }
	        }
	    }
	 
	    private ZooKeeper connectServer() {
	        ZooKeeper zk = null;
	        try {
	            zk = new ZooKeeper(registryAddress, 5000, new Watcher() {
	                @Override
	                public void process(WatchedEvent event) {
	                    // 判断是否已连接ZK,连接后计数器递减.
	                    if (event.getState() == Event.KeeperState.SyncConnected) {
	                        latch.countDown();
	                    }
	                }
	            });
	 
	            // 若计数器不为0,则等待.
	            latch.await();
	        } catch (IOException | InterruptedException e) {
	            LOGGER.error("", e);
	        }
	        return zk;
	    }
	 
	    private void createNode(ZooKeeper zk, String data) {
	        try {
	            byte[] bytes = data.getBytes();
	            if(null == zk.exists("/registry", false)){
	            	zk.create("/registry", "registry-data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	            }
	            if(null == zk.exists("/registry/data", false))
	            	zk.create("/registry/data", bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//	            LOGGER.debug("create zookeeper node ({} => {})", path, data);
	        } catch (KeeperException | InterruptedException e) {
	            LOGGER.error("", e);
	        }
	    }
	    
	    public static void main(String[] args) {
	    	ServiceRegistry sr = new ServiceRegistry("127.0.0.1:2181");
	    	sr.register("127.0.0.1:8088");
		}
}
