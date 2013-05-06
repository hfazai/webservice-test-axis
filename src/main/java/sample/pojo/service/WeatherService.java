package sample.pojo.service;

import sample.pojo.data.Weather;

public class WeatherService{

    private WeatherServiceSupplier supplier;

    public WeatherService()
    {
    }

    public Weather getWeather(String location){
        return supplier.getWeather(location);
    }
}