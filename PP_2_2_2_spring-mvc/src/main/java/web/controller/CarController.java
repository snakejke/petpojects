package web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Car;
import web.service.CarService;

@Controller
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public String getAllCars(Model model,
            @RequestParam(name = "count", defaultValue = "-1") int count) {
        List<Car> allCars = carService.listCars();
        List<Car> cars = getCarsByCount(allCars, count);
        model.addAttribute("cars", cars);
        return "cars";
    }

    private List<Car> getCarsByCount(List<Car> allCars, int count) {
        if (count > 0 && count < 5) {
            int min = Math.min(count, allCars.size());
            return allCars.subList(0, min);
        } else {
            return allCars;
        }
    }
}
