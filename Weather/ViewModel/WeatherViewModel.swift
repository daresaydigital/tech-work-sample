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
    var location: Coordinate?
    var area = ""
    var country = ""
    var weather = ""
    var temperature: Double = 0
    var summary = ""
    var dayTime: DayTime = .morning
    var timeString = "00:00"
    var dayString = ""
    var dayShortString = ""
    var temperatureMin: Double = 0
    var temperatureMax: Double = 0
    var sunrise = "00:00"
    var sunriseTime: TimeInterval = 0
    var sunset = "00:00"
    var sunsetTime: TimeInterval = 0
    var windSpeed = ""
    var windDegree: Double? = 0
    var pressure = ""
    var humidity = ""
    var icon = ""
    var status: String? = nil
    
    
    var networkManager: WeatherNetworkManager = NetworkManager(apiKey: Constants.apiKey, environment: Constants.networkEnvironment)
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
    
    func fetchWeather() {
        guard let location = location else {
            print("Location is not avaliable")
            status = "Location is not avaliable"
            return
        }
        
        networkManager.fetchWeather(latitude: location.latitude, longitude: location.longitude) { (weatherResponse, error) in
            if let error = error {
                print("Network Error: \(error)")
                self.status = error
                
                DispatchQueue.main.async {
                    let _ = Timer.scheduledTimer(withTimeInterval: 2, repeats: false, block: { _ in
                        self.fetchWeather()
                    })
                }
            } else {
                self.status = nil
                self.processWeatherResponse(weatherResponse!)
            }
        }
    }
}

//MARK: - WeatherDetailProtocol
extension WeatherViewModel: WeatherDetailProtocol {
    
    func updateWeatherData(forecast: Forecast) {
        
        weather = forecast.weather[0].main
        temperature = forecast.main.temperature
        summary = ""
        timeString = DateConverter.timeIntervalToHourMinuteString(forecast.dayTime)
        dayString = DateConverter.timeIntervalToDayString(forecast.dayTime)
        dayShortString = DateConverter.timeIntervalToDayShortString(forecast.dayTime)
        dayTime = self.calcDayTime(timeInterval: forecast.dayTime)
        temperatureMin = forecast.main.temperatureMin
        temperatureMax = forecast.main.temperatureMax
        windSpeed = "\(forecast.wind.speed) mps"
        windDegree = forecast.wind.degree
        pressure = String(format: "%d hPa", Int(forecast.main.pressure))
        humidity = String(format: "%d %%", forecast.main.humidity)
        icon = "http://openweathermap.org/img/w/\(forecast.weather[0].icon).png"
        
        updateWeatherDataClosure?()
    }
    
    func updateWeatherData(forecastDaily: ForecastDaily) {
        weather = forecastDaily.weather[0].main
        temperature = forecastDaily.temperature.day
        summary = ""
        timeString = DateConverter.timeIntervalToHourMinuteString(forecastDaily.dayTime)
        dayString = DateConverter.timeIntervalToDayString(forecastDaily.dayTime)
        dayShortString = DateConverter.timeIntervalToDayShortString(forecastDaily.dayTime)
        dayTime = self.calcDayTime(timeInterval: forecastDaily.dayTime)
        temperatureMin = forecastDaily.temperature.min
        temperatureMax = forecastDaily.temperature.max
        windSpeed = "\(forecastDaily.speed) mps"
        windDegree = forecastDaily.degree
        pressure = String(format: "%d hPa", Int(forecastDaily.pressure))
        humidity = String(format: "%d %%", forecastDaily.humidity)
        icon = "http://openweathermap.org/img/w/\(forecastDaily.weather[0].icon).png"
        
        updateWeatherDataClosure?()
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
    
    private func processWeatherResponse(_ response: WeatherResponse) {
        area = response.name
        country = response.system.country
        weather = response.weather[0].main
        temperature = response.main.temperature
        summary = String(format: "Today: %@ currently. It's %d°; The high today was forecast as %d°.", response.weather[0].main, Int(response.main.temperature), Int(response.main.temperatureMax))
        
        timeString = DateConverter.timeIntervalToHourMinuteString(response.dayTime)
        dayString = DateConverter.timeIntervalToDayString(response.dayTime)
        dayShortString = DateConverter.timeIntervalToDayShortString(response.dayTime)
        temperatureMin = response.main.temperatureMin
        temperatureMax = response.main.temperatureMax
        sunrise = DateConverter.timeIntervalToHourMinuteString(response.system.sunrise)
        sunriseTime = DateConverter.timeIntervalToDayTimeInterval(response.system.sunrise)
        sunset = DateConverter.timeIntervalToHourMinuteString(response.system.sunset)
        sunsetTime = DateConverter.timeIntervalToDayTimeInterval(response.system.sunset)
        dayTime = self.calcDayTime(timeInterval: response.dayTime)
        windSpeed = "\(response.wind.speed) mps"
        windDegree = response.wind.degree
        pressure = String(format: "%d hPa", Int(response.main.pressure))
        humidity = String(format: "%d %%", response.main.humidity)
        icon = "http://openweathermap.org/img/w/\(response.weather[0].icon).png"
        
        updateWeatherDataClosure?()
        finishedFetchingWeatherClosure?()
    }
    
    private func calcDayTime(timeInterval: TimeInterval)-> DayTime {
        let time = DateConverter.timeIntervalToDayTimeInterval(timeInterval)
        var adjustment: TimeInterval = 7200
        if sunriseTime + adjustment > sunsetTime - adjustment {
            adjustment = (sunsetTime - sunriseTime) / 4
        }
        switch time {
        case 0..<sunriseTime:
            return .night
        case sunriseTime..<(sunriseTime + adjustment):
            return .morning
        case (sunriseTime + adjustment)..<(sunsetTime - adjustment):
            return .day
        case (sunsetTime - adjustment)..<sunsetTime:
            return .evening
        default:
            return .night
        }
    }
}
