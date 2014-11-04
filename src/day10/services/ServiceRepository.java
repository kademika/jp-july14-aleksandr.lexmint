package day10.services;

import java.util.ArrayList;

/**
 * Created by lexmint on 27.06.14.
 */
public class ServiceRepository<T extends Service> {
    private ArrayList<T> serviceList = new ArrayList<T>();

    public T get(int index) { return serviceList.get(index); }

    public void remove(int index) { serviceList.remove(index); }

    public void remove(T service) { serviceList.remove(service); }

    public void add(T service) { serviceList.add(service); }

    public void set(T service, int index) { serviceList.set(index, service); }
}
