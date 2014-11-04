package day10.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by lexmint on 24.06.14.
 */
public class CarBox<T extends Car> {
    private ArrayList<T> carList = new ArrayList<T>();

    public T get(int index) { return carList.get(index); }

    public void remove(int index) { carList.remove(index); }

    public void remove(T car) { carList.remove(car); }

    public void add(T car) { carList.add(car); }

    public void set(T car, int index) { carList.set(index, car); }

    public void sort() { Collections.sort(carList, new Comparator<T>() {
     public int compare(T car1, T car2) {
         if (car1.getPrice() > car2.getPrice()) {
             return 1;
         } else if (car1.getPrice() < car2.getPrice()) {
             return -1;
         } else {
             return 0;
         }
     }
    }); }

    public void copy(ArrayList<? extends Car> cars, ArrayList<? super Car> mainCars) {
        mainCars.addAll(cars);
        return;
    }


}
