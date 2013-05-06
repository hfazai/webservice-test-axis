package sample.pojo.data;

public class Weather
{
    private float temperature;
    private String forecast;
    private boolean rain;
    private float howMuchRain;


    public Weather()
    {
    }

    public Weather(float temperature, String forecast, boolean rain, float howMuchRain)
    {
        this.temperature = temperature;
        this.forecast = forecast;
        this.rain = rain;
        this.howMuchRain = howMuchRain;
    }

    public void setTemperature(float temp)
    {
        temperature = temp;
    }

    public float getTemperature()
    {
        return temperature;
    }

    public void setForecast(String fore)
    {
        forecast = fore;
    }

    public String getForecast()
    {
        return forecast;
    }

    public void setRain(boolean r)
    {
        rain = r;
    }

    public boolean getRain()
    {
        return rain;
    }

    public void setHowMuchRain(float howMuch)
    {
        howMuchRain = howMuch;
    }

    public float getHowMuchRain()
    {
        return howMuchRain;
    }
}