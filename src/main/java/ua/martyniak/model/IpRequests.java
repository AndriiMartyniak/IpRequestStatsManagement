package ua.martyniak.model;


import java.util.concurrent.atomic.AtomicInteger;

public record IpRequests(Integer ip, AtomicInteger requestCount) implements Comparable<IpRequests> {

    @Override
    public int compareTo(IpRequests other) {
        int result = Integer.compare(this.requestCount.get(), other.requestCount.get());
        if (result != 0) {
            return result;
        } else {
            return Integer.compare(this.ip, other.ip);
        }
    }
}
