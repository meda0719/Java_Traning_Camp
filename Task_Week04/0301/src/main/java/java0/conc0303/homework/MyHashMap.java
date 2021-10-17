package java0.conc0303.homework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHashMap {
    private static Map<String, Integer> result = new ConcurrentHashMap<>();

    public static void addResult(Integer rr){
        Integer zeroValue = new Integer(0);
        Integer re = result.putIfAbsent("result", rr);
        if (null == re) re = zeroValue;


    }

    public static Integer getResult(){

        Integer rr = result.get("result");
        if (null == rr) {
            Integer zeroValue = new Integer(0);
            rr = result.putIfAbsent("result", zeroValue);
            if (null == rr) {
                rr = zeroValue;
            }
        }
        return rr;
    }
}
