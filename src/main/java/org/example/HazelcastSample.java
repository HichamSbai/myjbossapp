// very simple hello world app
package org.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HazelcastSample extends HttpServlet {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        IMap map = client.getMap("myMap");
        resp.getWriter().println("Map Size:" + map.size());
        String mapKey = req.getParameter("key");
        if (mapKey != null) {
            Object value = map.get(mapKey);
            resp.getWriter().println(String.format("Value of key %s: %s", mapKey, value));
            String mapValue = req.getParameter("value");
            if (mapValue != null) {
                map.put(mapKey, mapValue);
                resp.getWriter().println(String.format("Set value of key %s: %s -> %s", mapKey, value, mapValue));
            }
        }
    }

}
