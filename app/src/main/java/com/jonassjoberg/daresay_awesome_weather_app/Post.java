package com.jonassjoberg.daresay_awesome_weather_app;

import java.util.List;

public class Post {

    public int id;
    public String name;
    public int cod;

    public int dt;
    public int visibility;
    public String base;

    public Coord coord;
    public List<Weather> weather;
    public Main main;
    public Wind wind;
    public Clouds clouds;
    public Sys sys;

    public Post() {

    }

    public class Coord {

        public double lon;
        public double lat;

        public Coord() {

        }

        public String toString() {
            return "coord: {\n"
                + "lon: " + lon + ",\n"
                + "lat: " + lat + "\n"
                + "}";
        }
    }

    public class Weather {

        public int id;
        public String main;
        public String description;
        public String icon;

        public Weather() {

        }

        public String toString() {
            return "{\n"
                    + "id: " + id + ",\n"
                    + "main: " + main + ",\n"
                    + "description: " + description + ",\n"
                    + "icon: " + icon + "\n"
                    + "}";
        }
    }

    public class Main {

        public float temp;
        public float pressure;
        public float humidity;
        public float temp_min;
        public float temp_max;

        public Main() {

        }

        public String toString() {
            return "main: {\n"
                    + "temp: " + temp + ",\n"
                    + "pressure: " + pressure + ",\n"
                    + "humidity: " + humidity + ",\n"
                    + "temp_min: " + temp_min + ",\n"
                    + "temp_max: " + temp_max + "\n"
                    + "}";
        }
    }

    public class Wind {

        public float speed;
        public float deg;
        public float gust;

        public Wind() {

        }

        public String toString() {
            return "wind: {\n"
                    + "speed: " + speed + ",\n"
                    + "deg: " + deg + ",\n"
                    + "gust: " + gust + "\n"
                    + "}";
        }
    }

    public class Clouds {

        public int all;

        public Clouds() {

        }

        public String toString() {
            return "clouds: {\n"
                    + "all: " + all + "\n"
                    + "}";
        }
    }

    public class Sys {

        public int type;
        public int id;
        public float message;
        public String country;
        public int sunrise;
        public int sunset;

        public Sys() {

        }

        public String toString() {
            return "sys: {\n"
                    + "type: " + type + ",\n"
                    + "id: " + this.id + ",\n"
                    + "message: " + message + ",\n"
                    + "country: " + country + ",\n"
                    + "sunrise: " + sunrise + ",\n"
                    + "sunset: " + sunset + "\n"
                    + "}";
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        stringBuilder.append(coord.toString());
        stringBuilder.append(",\n");

        stringBuilder.append("weather: [");
        for (Weather w: weather) {
            stringBuilder.append(w.toString());
        }
        stringBuilder.append("],\n");

        stringBuilder.append(main.toString());
        stringBuilder.append(",\n");

        stringBuilder.append(wind.toString());
        stringBuilder.append(",\n");

        stringBuilder.append(clouds.toString());
        stringBuilder.append(",\n");

        stringBuilder.append(sys.toString());
        stringBuilder.append(",\n");

        stringBuilder.append("id: " + id + ",\n");
        stringBuilder.append("name: " + name + ",\n");
        stringBuilder.append("cod: " + cod + ",\n");
        stringBuilder.append("dt: " + dt + ",\n");
        stringBuilder.append("visibility: " + visibility + ",\n");
        stringBuilder.append("base: " + base + "\n");
        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}
