package me.kingtux.phphideout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Util {

  public static <Key, Value extends Comparable<? super Value>> LinkedHashMap<Key, Value> sortByValue(
      Map<Key, Value> map) {
    List<Entry<Key, Value>> list = new LinkedList<Entry<Key, Value>>(map.entrySet());
    Collections.sort(list, new Comparator<Entry<Key, Value>>() {
      public int compare(Map.Entry<Key, Value> o1, Map.Entry<Key, Value> o2) {
        // Switch these two if you want ascending
        return (o2.getValue()).compareTo(o1.getValue());
      }
    });

    LinkedHashMap<Key, Value> result = new LinkedHashMap<Key, Value>();
    for (Map.Entry<Key, Value> entry : list) {
      result.put(entry.getKey(), entry.getValue());
    }
    return result;
  }

  public static <K, V> List<Map<K, V>> split(int batchSize, Map<K, V> map) {
    List<Map<K, V>> result = new LinkedList<>();

    int count = 1;
    Map<K, V> batchMap = new LinkedHashMap<>();
    for (Entry<K, V> e : map.entrySet()) {
      batchMap.put(e.getKey(), e.getValue());

      if (count == batchSize) {
        result.add(batchMap);
        batchMap = new LinkedHashMap<>();
        count = 0;
      }

      count++;
    }

    if (!batchMap.isEmpty()) {
      result.add(batchMap);
    }

    return result;
  }
}
