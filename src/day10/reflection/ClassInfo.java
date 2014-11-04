package day10.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by lexmint on 30.06.14.
 */
public class ClassInfo {
    public static void printClassInfo(Class cl) {

        System.out.print("Имя класса: " + cl.getSimpleName() + " Родители: ");
        Class parent = cl.getSuperclass();
        do {
            System.out.print(parent.getSimpleName() + " ");
            parent = parent.getSuperclass();

        } while (parent != null);
        System.out.println();
        printClassMethods(cl);
        printClassFields(cl);
    }

    public static void printClassMethods(Class cl) {
        System.out.println("Методы класса " + cl.getSimpleName());
        for (Method method : cl.getMethods()) {
            System.out.println("Имя метода: " + method.getName() + " Возвращаемый тип: " + method.getReturnType());
        }
    }

    public static void printClassFields(Class cl) {
        System.out.println("Поля класса " + cl.getSimpleName());
        for (Field field : cl.getFields()) {
            System.out.println("Имя поля: " + field.getName() + " Тип: " + field.getGenericType());
        }

    }


    public static <T> T initClass(Class<T> cl, List<Object> map) {

        Constructor[] constructors = cl.getConstructors();

        for (Constructor constructor : constructors) {
            boolean isSuitableConstructor = false;
            Class[] params = constructor.getParameterTypes();

            for (int i = 0; i < params.length; i++) {
                try {

                    if (params[i].getCanonicalName().equals("int")) {
                        params[i] = java.lang.Integer.class;
                    } else if (params[i].getCanonicalName().equals("double")) {
                        params[i] = java.lang.Double.class;
                    } else if (params[i].getCanonicalName().equals("float")) {
                        params[i] = java.lang.Float.class;
                    } else if (params[i].getCanonicalName().equals("long")) {
                        params[i] = java.lang.Long.class;
                    }

                    if (params[i] == map.get(i).getClass()) {
                        isSuitableConstructor = true;
                    } else {
                        isSuitableConstructor = false;
                    }
                } catch (IndexOutOfBoundsException exc) {
                    System.out.println("IndexOutOfBounsException");
                }
            }

            if (isSuitableConstructor == true) {
                try {
                    return (T) constructor.newInstance(map.toArray());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public static <T> T initClass(Class<T> claz, Map<String, Object> map) {
        try {
            T obj = claz.newInstance();
            for (Method method : claz.getMethods()) {
                String name = method.getName();
                if (name.startsWith("set")) {
                    char first = Character.toLowerCase(name.charAt(3));
                    String field = first + name.substring(4);
                    method.invoke(obj, map.get(field));
                }
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setPrivates(Object obj, Map<String, Object> map) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
