//
//  ForecastListViewModel.swift
//  Weather
//
//  Created by Christian  Huang on 04/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit

class ForecastListViewModel: NSObject {
    private var forecastRawList = [Forecast]()
    var forecastList: Dynamic<[ForecastViewModel]> = Dynamic([])
    var forecastCount: Int {
        return forecastList.value.count
    }
    var status: String? = nil
    
    var networkManager: WeatherNetworkManager!
    var weatherDetail: WeatherDetailProtocol?
    
}

extension ForecastListViewModel {
    func fetchForecast(coordinate: Coordinate) {
        networkManager.fetchForecast(latitude: coordinate.latitude, longitude: coordinate.longitude) { (forecastResponse, error) in
            if let error = error {
                print("Network Error: \(error)")
                self.status = error
                
                DispatchQueue.main.async {
                    let _ = Timer.scheduledTimer(withTimeInterval: 2, repeats: false, block: { _ in
                        self.fetchForecast(coordinate: coordinate)
                    })
                }
            } else {
                self.status = nil
                self.processForecastResponse(forecastResponse!)
            }
        }
    }
    
    func getForecastViewModel(_ index: Int) -> ForecastViewModel {
        return forecastList.value[index]
    }
    
    func userSelectedForecast(_ index: Int){
        weatherDetail?.updateWeatherData(forecast: forecastRawList[index])
    }
}

extension ForecastListViewModel {
    private func processForecastResponse(_ forecastResponse: ForecastResponse) {
        forecastRawList = forecastResponse.list
        forecastList.value = forecastResponse.list.map({ forecast -> ForecastViewModel in
            return ForecastViewModel(forecast: forecast)
        })
        
    }
}
