package com.whackyapps.pallavgrover.daresaydigital.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class WeatherForecast implements Serializable{

    private int cod;

    public City getCity() {
        return city;
    }

    private City city;

    @SerializedName("list")
    private List<WeatherForecastOneDay> weatherForecastOneDay;

    public long getCod() {
        return cod;
    }


    public List<WeatherForecastOneDay> getWeatherForecastOneDay() {
        return weatherForecastOneDay;
    }

    public class City {
        private int id;
        private String name;
        private String country;
        private Coordinates coord;
        private double population;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public Coordinates getCoord() {
            return coord;
        }

        public double getPopulation() {
            return population;
        }

        public class Coordinates {
            @SerializedName("lat")
            private double latitude;
            @SerializedName("lon")
            private double longitude;

            public double getLatitude() {
                return latitude;
            }

            public double getLongitude() {
                return longitude;
            }
        }
    }

    public static class WeatherForecastOneDay implements Serializable{

        @SerializedName("weather")
        private List<WeatherInf> weatherInfs;
        private double humidity;
        private double pressure;
        private float speed;
        private long dt;
        private int clouds;
        private long deg;
        @SerializedName("temp")
        private Temprature temprature;

        public float getRain() {
            return rain;
        }

        private float rain;

        public long getDeg() {
            return deg;
        }



        public long getDt() {
            return dt;
        }

        public float getSpeed() {
            return speed;
        }

        public List<WeatherInf> getWeatherInfs() {
            return weatherInfs;
        }

        public Temprature getTemprature() {
            return temprature;
        }

        public void setWeatherInfs(List<WeatherInf> weatherInfs) {
            this.weatherInfs = weatherInfs;
        }

        public int getHumidity() {
            return (int) humidity;
        }

        public double getPressure() {
            return pressure;
        }
        public static class Temprature implements Serializable{

            private float min;
            private float max;
            private float night;
            private float day;
            private float eve;
            private float morn;

            public float getMin() {
                return min;
            }

            public float getMax() {
                return max;
            }


            public float getNight() {
                return night;
            }


            public float getDay() {
                return day;
            }

            public float getEve() {
                return eve;
            }

            public float getMorn() {
                return morn;
            }

        }

        public static class WeatherInf implements Serializable{
            private int id;
            private String description;
            private String icon;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }



            public String getDescription() {
                return description;
            }

            public void setDescription(String desc) {
                this.description = desc;
            }

            public int getId() {
                return id;
            }
        }
    }

}