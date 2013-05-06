package sample.pojo.service;

import sample.pojo.data.Weather;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 06.05.13 09:14
 */
public interface WeatherServiceSupplier
{

    Weather getWeather(String location);
}
