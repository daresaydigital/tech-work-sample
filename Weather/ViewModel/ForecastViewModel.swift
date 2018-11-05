//
//  ForecastViewModel.swift
//  Weather
//
//  Created by Christian  Huang on 06/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation


class ForecastViewModel: NSObject {
    var time: String
    var temperature: String
    
    init(forecast: Forecast) {
        time = String(forecast.dayTimeText[11...12])
        temperature = "\(Int(forecast.main.temperature))°"
    }
}
