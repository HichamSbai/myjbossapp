// very simple hello world app
package org.example;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HelloHazelcast extends HttpServlet {
    private HazelcastInstance instance = Hazelcast.newHazelcastInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IMap<Object, Object> hzMap = instance.getMap("hzMap");
        String pathInfo = req.getPathInfo();
        if ("/get".equalsIgnoreCase(pathInfo)) {
            String name = req.getParameter("name");
            String value = (String) hzMap.get(name);
            resp.getWriter().print(String.format("%s | Get name %s: %s", InetAddress.getLocalHost(), name, value));
        } else if ("/put".equalsIgnoreCase(pathInfo)) {
            String name = req.getParameter("name");
            String value = req.getParameter("value");
            hzMap.put(name, value);
            resp.getWriter().print(String.format("%s | Put to name: %s: %s", InetAddress.getLocalHost(), name, value));
        }
    }
}
