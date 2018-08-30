import { Component } from '@angular/core';
import { WeatherDataService} from './services/weather-data.service';
import { WeatherData } from './models/weatherData';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  constructor(private WeatherDataService: WeatherDataService){}

  API_KEY: string ='5d9bee31109da35e3f6f4eebdf708c66'; // Täby: 78.72.204.103, Norrköping: 193.235.91.227  Linköping: 130.236.86.101
  WEATHER_KEY: string = '62fc4256-8f8c-11e5-8994-feff819cdc9f';
  
  private ip: number;
  
  model = new WeatherData(0,'',0,0,'','','','',0);

  ngOnInit() {
    this.WeatherDataService.getLocationFromIp(this.API_KEY)
    .subscribe(data => {
      this.ip = data['location']['geoname_id'];
      if(!this.ip){
        this.ip = 2673730; //geoname-id for Stockholm
        this.WeatherDataService.getWeatherFromGeoId(this.WEATHER_KEY,this.ip)
        .subscribe(
          data => this.setModel(data), 
          error => console.log('ErrorWeather: ' + error));
          throw('Can not find your location');
      } else {
        this.WeatherDataService.getWeatherFromGeoId(this.WEATHER_KEY,this.ip)
          .subscribe(
            data => this.setModel(data),
            error => console.log('ErrorWeather: ' + error)
      )}
    })
  }

  setModel(object) {
    this.model.city = object.name;
    this.model.lon = object.coord.lon;
    this.model.lat = object.coord.lat;
    this.model.weatherDescription = object.weather['0'].description;
    this.model.weatherCondition = object.weather['0'].main;
    this.model.temp = parseFloat(object.main.temp).toFixed(0);
    this.model.wind = object.wind.speed; 
    this.model.humidity = object.main.humidity;  
    
  }
}