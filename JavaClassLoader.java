package Servlet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class JavaClassLoader extends ClassLoader {
    Map<String, HttpServlet> instClasses = new HashMap<>();
    Map<String, Class> loaded = new HashMap<>();

    public HttpServlet invokeClass(String name) throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();

        Class loadedMyClass = classLoader.loadClass(name);
        loaded.put(name, loadedMyClass);
        if (instClasses.containsKey(name)) {
            return instClasses.get(name);
        }
        Constructor constructor = loadedMyClass.getConstructor();
        HttpServlet myClassObject = (HttpServlet) constructor.newInstance();
        instClasses.put(name, myClassObject);

        return myClassObject;
    }

    public HttpServlet invokeDefaultClass() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();

        Class loadedMyClass = classLoader.loadClass("Servlet.DefaultServlet");

        if (instClasses.containsKey("Servlet.DefaultServlet")) {
            return instClasses.get("Servlet.DefaultServlet");
        }
        Constructor constructor = loadedMyClass.getConstructor();
        HttpServlet myClassObject = (HttpServlet) constructor.newInstance();
        instClasses.put("Servlet.DefaultServlet", myClassObject);

        return myClassObject;
    }

    public void loadMethod(String name, String methodName) throws Exception {
        Class loadedMyClass = loaded.get(name);
        Method method = loadedMyClass.getMethod(methodName);
        System.out.println("Invoked method name: " + method.getName());
        HttpServlet myClassObject = instClasses.get(name);
        method.invoke(myClassObject);
    }
}
