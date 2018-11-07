//
//  ForecastDailyViewModel.swift
//  Weather
//
//  Created by Christian  Huang on 06/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation


class ForecastDailyViewModel: NSObject {
    let dayString: String
    let temperatureMax: Double
    let temperatureMin: Double
    
    init(forecastDaily: ForecastDaily) {
        dayString = DateConverter.timeIntervalToDayString(forecastDaily.dayTime)
        temperatureMax = forecastDaily.temperature.max
        temperatureMin = forecastDaily.temperature.min
    }
}
