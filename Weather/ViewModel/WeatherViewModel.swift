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
        area.value = response.name
        weather.value = response.weather[0].main
        temperature.value = response.main.temperature
        summary.value = String(format: "Today: %@ currently. It's %d°; the high today was forecast as %d°.", response.weather[0].main, Int(response.main.temperature), Int(response.main.temperatureMax))
        
        dayTime.value = .night
        dayString.value = "Sunday"
        temperatureMin.value = response.main.temperatureMin
        temperatureMax.value = response.main.temperatureMax
        sunrise.value = "06:00"
        sunset.value = "17:45"
        pressure.value = String(format: "%d hPa", response.main.pressure)
        humidity.value = String(format: "%d%", response.main.humidity)
    }
}
