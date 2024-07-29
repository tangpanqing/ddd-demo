package com.example.test_ddd.utils;

import java.util.concurrent.ThreadLocalRandom;

 class SnowflakeIdGenerator {

    private static final long workerIdBits = 5L;
    private static final long datacenterIdBits = 5L;
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private static final long sequenceBits = 12L;
    private static final long workerIdShift = sequenceBits;
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);
    private static final long twepoch = 1288834974657L;

    private static long workerId;
    private static long datacenterId;
    private static long sequence = 0L;
    private static long lastTimestamp = -1L;

    static {
        workerId = ThreadLocalRandom.current().nextLong(0, maxWorkerId + 1);
        datacenterId = ThreadLocalRandom.current().nextLong(0, maxDatacenterId + 1);
    }

    private SnowflakeIdGenerator() {}

    public static synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }
}

public class SnowflakeUtil {

    public static Long genId(){
        return SnowflakeIdGenerator.nextId();
    }
}
