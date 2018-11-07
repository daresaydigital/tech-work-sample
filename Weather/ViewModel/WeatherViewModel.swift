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
    var weather = Dynamic("")
    var temperature: Dynamic<Double> = Dynamic(0)
    var summary = Dynamic("")
    var dayTime: Dynamic<DayTime> = Dynamic(DayTime.morning)
    var dayString = Dynamic("")
    var temperatureMin: Dynamic<Double> = Dynamic(0)
    var temperatureMax: Dynamic<Double> = Dynamic(0)
    var sunrise = Dynamic("00:00")
    var sunset = Dynamic("00:00")
    var pressure = Dynamic("")
    var humidity = Dynamic("")
    
    
    lazy var networkManager: WeatherNetworkManager = NetworkManager(apiKey: Constants.apiKey, environment: Constants.networkEnvironment)
    private var locationManager = LocationManager()
    
    weak var forecastListViewModel: ForecastListViewModel? {
        didSet {
            forecastListViewModel?.networkManager = networkManager
        }
    }
    weak var forecastDailyListViewModel: ForecastDailyListViewModel? {
        didSet {
            forecastDailyListViewModel?.networkManager = networkManager
        }
    }
    
    var updateWeatherDataClosure: (()->())?
    var finishedFetchingWeatherClosure: (()->())?
    
    

}

//MARK: - public func
extension WeatherViewModel {
    func startWeatherForcasting() {
        initLocationManager()
        locationManager.requestLocation()
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
            self.weather.value = response.weather[0].main
            self.temperature.value = response.main.temperature
            self.summary.value = String(format: "Today: %@ currently. It's %d°; the high today was forecast as %d°.", response.weather[0].main, Int(response.main.temperature), Int(response.main.temperatureMax))
            
            self.dayTime.value = .night
            self.dayString.value = DateConverter.timeIntervalToDayString(response.dayTime)
            self.temperatureMin.value = response.main.temperatureMin
            self.temperatureMax.value = response.main.temperatureMax
            self.sunrise.value = DateConverter.timeIntervalToHourMinuteString(response.system.sunrise)
            self.sunset.value = DateConverter.timeIntervalToHourMinuteString(response.system.sunset)
            self.pressure.value = String(format: "%.2f hPa", response.main.pressure)
            self.humidity.value = String(format: "%d%", response.main.humidity)
            self.updateWeatherDataClosure?()
            self.finishedFetchingWeatherClosure?()
        }
    }
}
