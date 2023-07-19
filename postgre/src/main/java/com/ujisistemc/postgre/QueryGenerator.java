package com.ujisistemc.postgre;

import java.util.Map;
import java.util.stream.Collectors;

public class QueryGenerator {
    public String buildInsertQuery(String tableName, Map<String, Object> data) {
        String columns = String.join(", ", data.keySet());
        String values = data.values().stream()
                .map(value -> {
                    if (value instanceof String) {
                        return "'" + value + "'";
                    } else {
                        return value.toString();
                    }
                })
                .collect(Collectors.joining(", "));

        return String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, columns, values);
    }
}

