package day10.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ApplicationManager {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Integer number = ApplicationManager.getService(Integer.class);
        System.out.println(number);
    }

    public static <T> T getService(Class<T> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException {

        for (Annotation ann : clazz.getAnnotations()) {

            if (ann instanceof Service) {
                // Initialization of the service
                for (Method method : clazz.getClass().getMethods()) {
                    for (Annotation methodAnn : method.getAnnotations()) {
                        if (methodAnn.annotationType().getSimpleName().equals(InitService.class.getSimpleName())) {
                            T serv = clazz.newInstance();
                            method.invoke(serv);
                            return serv;
                        }

                    }

                }
            }

        }
        return null;
    }
}
