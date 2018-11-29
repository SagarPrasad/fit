package com.datastax.fit;

import com.datastax.fit.jcp.OrderJsonMapConverter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

class OrderJsonMapConverterTest {

    @Test
    void createMapforOrderJson() throws IOException, URISyntaxException {
        String json = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("/orderJson.txt").toURI())),
                "UTF-8");
        Map<String,List<String>> map = OrderJsonMapConverter.createMapList(json);
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}