package de.hszg.service.heartbeat;

import de.hszg.model.heartbeat.Heartbeat;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Andre on 03.05.2015.
 *
 * This class is to hold all GCE that are registered to the loadbalancer.
 */
public class SharedMemory {

    private Map<String, HeartbeatModel> memory = null;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock  = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public SharedMemory(){
        memory = Collections.synchronizedMap(new HashMap<String, HeartbeatModel>());
    }

    public void updateMemory(Heartbeat heartbeat){
        long systemTime = System.currentTimeMillis();
        HeartbeatModel heartbeatModel = new HeartbeatModel(heartbeat.getIpAddress(), heartbeat.getLoad(), heartbeat.getNumberJobs(), systemTime);

        if(memory.containsKey(heartbeat.getIpAddress())) {
            memory.replace(heartbeat.getIpAddress(), heartbeatModel);
        }
        else{
            memory.put(heartbeat.getIpAddress(), heartbeatModel);
        }
    }

    public Collection<HeartbeatModel> getAllHeartbeats(){
        Collection<HeartbeatModel> heartbeats = null;

        readLock.lock();
        try{
            heartbeats = memory.values();
        }
        finally {
            readLock.unlock();
        }

        return heartbeats;
    }

    public String getGCEWithLeastLoad() throws IndexOutOfBoundsException{
        if(this.getAllHeartbeats().size()>0){
            List<HeartbeatModel> allHeartbeats = new ArrayList<>(this.getAllHeartbeats());
            Collections.sort(allHeartbeats);
            return allHeartbeats.get(0).getIpAddress();
        }
        else{
            throw new IndexOutOfBoundsException();
        }
    }
}
