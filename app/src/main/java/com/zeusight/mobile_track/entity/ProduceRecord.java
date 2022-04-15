package com.zeusight.mobile_track.entity;

public class ProduceRecord<K,V> {

    private K key;
    private V value;
    ProduceRecord() {
    }

    public ProduceRecord(V value) {
        this.key = null;
        this.value = value;
    }

    public ProduceRecord(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ProduceRecord{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
