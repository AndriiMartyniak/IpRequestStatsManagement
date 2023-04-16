package ua.martyniak.service;

import ua.martyniak.model.IpRequests;
import ua.martyniak.model.IpRequestsResponse;
import ua.martyniak.util.IpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

public class IpRequestService {

    private final TreeSet<IpRequests> ipSet;
    private final HashMap<Integer, IpRequests> ipMap;
    private final IpUtil ipUtil;

    public IpRequestService(IpUtil ipUtil){
        ipSet = new TreeSet<>();
        ipMap = new HashMap<>();
        this.ipUtil = ipUtil;
    }

    public void requestHandled(String ip_address){
        int ip = ipUtil.getIntFromString(ip_address);

        if (ipMap.containsKey(ip)) {
            IpRequests reqToAdd = ipMap.get(ip);
            IpRequests ceiling = ipSet.ceiling(reqToAdd);
            ipSet.remove(ceiling);
            reqToAdd.requestCount().incrementAndGet();
            ipSet.add(reqToAdd);
            ipMap.put(ip, reqToAdd);
        } else {
            IpRequests req = new IpRequests(ip, new AtomicInteger(1));
            ipMap.put(ip, req);
            ipSet.add(req);
        }

    }

    public List<IpRequestsResponse> top100(){
        List<IpRequestsResponse> top100Elements = new ArrayList<>();
        int count = 0;
        for (IpRequests ipRequests : ipSet.descendingSet()){
            top100Elements.add(new IpRequestsResponse(ipUtil.getStringFromInteger(ipRequests.ip()), ipRequests.requestCount()));
            count++;
            System.out.println(count + ") " + ipUtil.getStringFromInteger(ipRequests.ip()) + " | " + ipRequests.requestCount());
            if (count == 100){
                break;
            }
        }
        return top100Elements;
    }

    public void clear(){
        ipMap.clear();
        ipSet.clear();
    }
}
