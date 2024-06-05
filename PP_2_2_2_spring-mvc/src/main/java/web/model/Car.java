package web.model;

public class Car {

    private String model;
    private int series;
    private int releaseDate;


    public Car(String model, int series, int releaseDate) {
        this.model = model;
        this.series = series;
        this.releaseDate = releaseDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", series=" + series +
                ", releaseDate=" + releaseDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }

        Car car = (Car) o;

        if (series != car.series) {
            return false;
        }
        if (releaseDate != car.releaseDate) {
            return false;
        }
        return model.equals(car.model);
    }

    @Override
    public int hashCode() {
        int result = model.hashCode();
        result = 31 * result + series;
        result = 31 * result + releaseDate;
        return result;
    }

}
