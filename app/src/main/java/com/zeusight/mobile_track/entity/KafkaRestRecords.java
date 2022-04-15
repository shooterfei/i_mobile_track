package com.zeusight.mobile_track.entity;

import java.util.List;

public class KafkaRestRecords {
    private List<ProduceRecord<String, Object>> records;

    public KafkaRestRecords() {
    }

    public KafkaRestRecords(List<ProduceRecord<String, Object>> records) {
        this.records = records;
    }

    public List<ProduceRecord<String, Object>> getRecords() {
        return records;
    }

    public void setRecords(List<ProduceRecord<String, Object>> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "KafkaRestRecords{" +
                "records=" + records +
                '}';
    }
}
