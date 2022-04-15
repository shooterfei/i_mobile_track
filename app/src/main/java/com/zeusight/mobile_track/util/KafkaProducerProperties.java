package com.zeusight.mobile_track.util;

import com.google.gson.Gson;
import com.zeusight.mobile_track.entity.KafkaRestRecords;
import com.zeusight.mobile_track.entity.ProduceRecord;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class KafkaProducerProperties {

    private static final Gson gson = new Gson();
    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final List<ProduceRecord<String, Object>> list = new ArrayList<>();
    private static final KafkaRestRecords records = new KafkaRestRecords();

    public synchronized static String sendMSG(Object msg) {
        String res = null;
//        if (list.size() < 1) {
//            list.add(new ProduceRecord<>(msg));
//            records.setRecords(list);
//        } else {
//            list.set(0, new ProduceRecord<>(msg));
//        }
        ArrayList<ProduceRecord<String, Object>> list = new ArrayList<>();
        list.add(new ProduceRecord<>(msg));
        records.setRecords(list);
        System.out.println(gson.toJson(records));
        Request request = new Request.Builder()
                .url("http://192.168.2.91:8082/topics/test")
                .post(RequestBody.create(gson.toJson(records), MediaType.get("application/vnd.kafka.json.v2+json")))
                .build();
        try {
            Response response = okHttpClient.newCall(request)
                    .execute();
            res = Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(sendMSG("test"));
    }
}
