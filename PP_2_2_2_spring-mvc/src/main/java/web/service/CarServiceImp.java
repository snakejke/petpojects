package web.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import web.model.Car;

@Service
public class CarServiceImp implements CarService {

    @Override
    public List<Car> listCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Ferrari", 11, 1940));
        cars.add(new Car("Камаз", 1671, 1790));
        cars.add(new Car("Урал", 1991, 2000));
        cars.add(new Car("Bentley", 14341, 1790));
        cars.add(new Car("ВАЗ", 154, 1990));
        return cars;
    }
}
