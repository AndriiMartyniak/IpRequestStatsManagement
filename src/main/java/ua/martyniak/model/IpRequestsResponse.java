package ua.martyniak.model;

import java.util.concurrent.atomic.AtomicInteger;

public record IpRequestsResponse(String ip, AtomicInteger requestCount) {
}
