package com.lvddy.snowflake.common.zookeeper;

import lombok.extern.log4j.Log4j2;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kcz-020 on 2017/5/24.
 */
@Component
@Log4j2
public class ZookeeperHelper {

	@Autowired
    private ZooKeeper zooKeeper;

    /**
     * zookeeper创建父节点
     * @param path 父节点路径
     * @param data 存放在父节点的数据
     * @param acl 权限
     * @param createMode 创建模式
     * @return 创建的实际路径
     */
	public String createParentNode(String path, byte[] data, List<ACL> acl, CreateMode createMode){
        String s = null;
	    try {
            s = zooKeeper.create(path, data, acl, createMode);
        } catch (KeeperException | InterruptedException e) {
            log.error("action=`createParentNode` state=`create ParentNode failed` message=`create path:{}` error", path);
            e.printStackTrace();
        }
        return s;
    }

    /**
     * zookeeper 创建子节点
     * @param parentPath 父亲节点路径
     * @param childrenPath 子节点路径
     * @param data 存放在数据
     * @param acl 权限
     * @param createMode 创建节点的模式
     * @return
     */
    public String createChildrenNode(String parentPath, String childrenPath, byte[] data, List<ACL> acl, CreateMode createMode){
        String s = null;
        try {
            s = zooKeeper.create(parentPath + childrenPath, data, acl, createMode);
        } catch (KeeperException | InterruptedException e) {
            log.error("action=`createChildrenNode` state=`create ChildrenNode failed` message=`create path:{}` error", parentPath + childrenPath);
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 判断节点是否存在
     * @param path 节点路径
     * @param watch 是否watch
     * @return 有->true, 无->false
     */
    public boolean existNode(String path, boolean watch){
        try {
            Stat exists = zooKeeper.exists(path, watch);
            if(exists == null){
                return false;
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("action=`existNode` state=`check Node failed` message=`check path:{}` error", path);
            e.printStackTrace();
        }
        return true;
    }

}
