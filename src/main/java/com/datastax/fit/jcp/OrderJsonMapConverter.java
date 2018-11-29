/*
 * Copyright (c) 2016 JCPenney Co. All rights reserved.
 *
 */

package com.datastax.fit.jcp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.io.IOException;

public class OrderJsonMapConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderJsonMapConverter.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String,List<String>> createMapList(String json){
        Map<String,List<String>> map = new HashMap<>();
        try {
            addKeysList("", objectMapper.readTree(json), map);
        } catch (IOException e) {
            LOGGER.error("Error in processing ", e.getMessage());
        }
        return filterMap(map, true);
    }

    private static Map<String,List<String>> filterMap(Map<String,List<String>> map, boolean filter) {
        if (filter) {
            Map<String,List<String>> indexingMap = new HashMap<>();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                if (isIndexable(entry.getKey())) {
                    indexingMap.put(entry.getKey(), entry.getValue());
                }
            }
            return indexingMap;
        }
        return map;
    }

  /**
   * Indicates the fields in Json which needs to be indexed.
   * @param key
   * @return
   */
    private static boolean isIndexable(String key) {
        return OrderJSONUtility.getIndexableKeys().contains(key);
    }

    private static void addKeysList(String currentPath, JsonNode jsonNode, Map<String, List<String>> map) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";

            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                addKeysList(pathPrefix + entry.getKey(), entry.getValue(), map);
            }
        } else if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            for (int i = 0; i < arrayNode.size(); i++) {
                addKeysList(currentPath + "_", arrayNode.get(i), map);
            }
        } else if (jsonNode.isValueNode()) {
            ValueNode valueNode = (ValueNode) jsonNode;
            List<String> currentValue = map.get(currentPath);
            if (currentValue == null) {
                map.put(currentPath, new ArrayList<String>());
                currentValue = map.get(currentPath);
            }
            currentValue.add(valueNode.asText());
        }
    }
}
