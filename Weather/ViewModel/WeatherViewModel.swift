//
//  WeatherViewModel.swift
//  Weather
//
//  Created by Christian  Huang on 04/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit
import CoreLocation

class WeatherViewModel: NSObject {
    private var location: Coordinate?
    var area = Dynamic("")
    var country = Dynamic("")
    var weather = Dynamic("")
    var temperature: Dynamic<Double> = Dynamic(0)
    var summary = Dynamic("")
    var dayTime: Dynamic<DayTime> = Dynamic(DayTime.morning)
    var timeString = Dynamic("00:00")
    var dayString = Dynamic("")
    var dayShortString = Dynamic("")
    var temperatureMin: Dynamic<Double> = Dynamic(0)
    var temperatureMax: Dynamic<Double> = Dynamic(0)
    var sunrise = Dynamic("00:00")
    var sunriseTime: Dynamic<TimeInterval> = Dynamic(0)
    var sunset = Dynamic("00:00")
    var sunsetTime: Dynamic<TimeInterval> = Dynamic(0)
    var windSpeed = Dynamic("")
    var pressure = Dynamic("")
    var humidity = Dynamic("")
    var icon = Dynamic("")
    
    
    lazy var networkManager: WeatherNetworkManager = NetworkManager(apiKey: Constants.apiKey, environment: Constants.networkEnvironment)
    private var locationManager = LocationManager()
    
    weak var forecastListViewModel: ForecastListViewModel? {
        didSet {
            forecastListViewModel?.networkManager = networkManager
            forecastListViewModel?.weatherDetail = self
        }
    }
    weak var forecastDailyListViewModel: ForecastDailyListViewModel? {
        didSet {
            forecastDailyListViewModel?.networkManager = networkManager
            forecastDailyListViewModel?.weatherDetail = self
        }
    }
    
    var startFetchingWeatherClosure: (()->())?
    var updateWeatherDataClosure: (()->())?
    var finishedFetchingWeatherClosure: (()->())?
    
    

}

//MARK: - public func
extension WeatherViewModel {
    func startWeatherForecasting() {
        initLocationManager()
        locationManager.requestLocation()
        startFetchingWeatherClosure?()
    }
}

//MARK: - WeatherDetailProtocol
extension WeatherViewModel: WeatherDetailProtocol {
    
    func updateWeatherData(forecast: Forecast) {
        DispatchQueue.main.async {
            self.weather.value = forecast.weather[0].main
            self.temperature.value = forecast.main.temperature
            self.summary.value = ""
            self.timeString.value = DateConverter.timeIntervalToHourMinuteString(forecast.dayTime)
            self.dayString.value = DateConverter.timeIntervalToDayString(forecast.dayTime)
            self.dayShortString.value = DateConverter.timeIntervalToDayShortString(forecast.dayTime)
            self.dayTime.value = self.calcDayTime(timeInterval: forecast.dayTime)
            self.temperatureMin.value = forecast.main.temperatureMin
            self.temperatureMax.value = forecast.main.temperatureMax
            self.windSpeed.value = "\(forecast.wind.speed) mps"
            self.pressure.value = String(format: "%d hPa", Int(forecast.main.pressure))
            self.humidity.value = String(format: "%d%", forecast.main.humidity)
            self.icon.value = "http://openweathermap.org/img/w/\(forecast.weather[0].icon).png"
        }
    }
    
    func updateWeatherData(forecastDaily: ForecastDaily) {
        DispatchQueue.main.async {
            self.weather.value = forecastDaily.weather[0].main
            self.temperature.value = forecastDaily.temperature.day
            self.summary.value = ""
            self.timeString.value = DateConverter.timeIntervalToHourMinuteString(forecastDaily.dayTime)
            self.dayString.value = DateConverter.timeIntervalToDayString(forecastDaily.dayTime)
            self.dayShortString.value = DateConverter.timeIntervalToDayShortString(forecastDaily.dayTime)
            self.dayTime.value = self.calcDayTime(timeInterval: forecastDaily.dayTime)
            self.temperatureMin.value = forecastDaily.temperature.min
            self.temperatureMax.value = forecastDaily.temperature.max
            self.windSpeed.value = "\(forecastDaily.speed) mps"
            self.pressure.value = String(format: "%d hPa", Int(forecastDaily.pressure))
            self.humidity.value = String(format: "%d%", forecastDaily.humidity)
            self.icon.value = "http://openweathermap.org/img/w/\(forecastDaily.weather[0].icon).png"
        }
    }
}

//MARK: - private func
extension WeatherViewModel {
    private func initLocationManager() {
        if locationManager.locationUpdateClosure == nil {
            locationManager.locationUpdateClosure = { location, placemark in
                if let location = location {
                    print("Location: \(location.coordinate.latitude) - \(location.coordinate.longitude)")
                    self.location = Coordinate(longitude: location.coordinate.longitude, latitude: location.coordinate.latitude)
                    self.fetchWeather()
                    self.forecastListViewModel?.fetchForecast(coordinate: self.location!)
                    self.forecastDailyListViewModel?.fetchForecastDaily(coordinate: self.location!)
                } else {
                    // cannot get user location
                }
            }
        }
    }
    
    private func fetchWeather() {
        guard let location = location else {
            print("Location is not avaliable")
            return
        }
        
        networkManager.fetchWeather(latitude: location.latitude, longitude: location.longitude) { (weatherResponse, error) in
            if let error = error {
                print("Network Error: \(error)")
            } else {
                self.processWeatherResponse(weatherResponse!)
            }
        }
    }
    
    private func processWeatherResponse(_ response: WeatherResponse) {
        DispatchQueue.main.async {
            self.area.value = response.name
            self.country.value = response.system.country
            self.weather.value = response.weather[0].main
            self.temperature.value = response.main.temperature
            self.summary.value = String(format: "Today: %@ currently. It's %d°; the high today was forecast as %d°.", response.weather[0].main, Int(response.main.temperature), Int(response.main.temperatureMax))
            
            self.timeString.value = DateConverter.timeIntervalToHourMinuteString(response.dayTime)
            self.dayString.value = DateConverter.timeIntervalToDayString(response.dayTime)
            self.dayShortString.value = DateConverter.timeIntervalToDayShortString(response.dayTime)
            self.temperatureMin.value = response.main.temperatureMin
            self.temperatureMax.value = response.main.temperatureMax
            self.sunrise.value = DateConverter.timeIntervalToHourMinuteString(response.system.sunrise)
            self.sunriseTime.value = DateConverter.timeIntervalToDayTimeInterval(response.system.sunrise)
            self.sunset.value = DateConverter.timeIntervalToHourMinuteString(response.system.sunset)
            self.sunsetTime.value = DateConverter.timeIntervalToDayTimeInterval(response.system.sunset)
            self.dayTime.value = self.calcDayTime(timeInterval: response.dayTime)
            self.windSpeed.value = "\(response.wind.speed) mps"
            self.pressure.value = String(format: "%d hPa", Int(response.main.pressure))
            self.humidity.value = String(format: "%d %%", response.main.humidity)
            self.icon.value = "http://openweathermap.org/img/w/\(response.weather[0].icon).png"
            self.updateWeatherDataClosure?()
            self.finishedFetchingWeatherClosure?()
        }
    }
    
    private func calcDayTime(timeInterval: TimeInterval)-> DayTime {
        let time = DateConverter.timeIntervalToDayTimeInterval(timeInterval)
        switch time {
        case 0..<sunriseTime.value:
            return .night
        case sunriseTime.value..<(sunriseTime.value + 7200):
            return .morning
        case (sunriseTime.value + 7200)..<(sunsetTime.value - 7200):
            return .day
        case (sunsetTime.value - 7200)..<sunsetTime.value:
            return .evening
        default:
            return .night
        }
    }
}
