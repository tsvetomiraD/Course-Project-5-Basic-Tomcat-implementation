package Servlet;

import java.io.*;
import java.util.*;

public class ServletContext  {
    Map<String, RequestDispatcher> map = new HashMap<>();

    public void addDispatcher(RequestDispatcher requestDispatcher, String path) {
        map.put(path, requestDispatcher);
    }

    RequestDispatcher getRequestDispatcher(String path) {
        return map.get(path);
    }

    public List<RequestDispatcher> getAllRequestDispatcher() {
        List<RequestDispatcher> list = new ArrayList<>();
        for (var m: map.entrySet()) {
            list.add(m.getValue());
        }
        return list;
    }

}
