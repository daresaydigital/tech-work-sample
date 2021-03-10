//
//  WeatherDetailProtocol.swift
//  Weather
//
//  Created by Christian  Huang on 11/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation

protocol WeatherDetailProtocol {
    func updateWeatherData(forecast: Forecast)
    func updateWeatherData(forecastDaily: ForecastDaily)
}
