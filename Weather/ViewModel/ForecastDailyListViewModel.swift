//
//  ForecastDailyListViewModel.swift
//  Weather
//
//  Created by Christian  Huang on 06/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation


class ForecastDailyListViewModel: NSObject {
    var forecastDailyList: Dynamic<[ForecastDailyViewModel]> = Dynamic([])
    var forecastDailyCount: Int {
        return forecastDailyList.value.count
    }
    
    var networkManager: WeatherNetworkManager!
}

extension ForecastDailyListViewModel {
    func fetchForecastDaily(coordinate: Coordinate) {
        networkManager.fetchForecastDaily(latitude: coordinate.latitude, longitude: coordinate.longitude) { (forecastDailyResponse, error) in
            if let error = error {
                print("Network Error: \(error)")
            } else {
                self.processForecastDailyResponse(forecastDailyResponse!)
            }
        }
    }
    
    func getForecastDailyViewModel(index: Int) -> ForecastDailyViewModel {
        return forecastDailyList.value[index]
    }
}

extension ForecastDailyListViewModel {
    private func processForecastDailyResponse(_ forecastDailyResponse: ForecastDailyResponse) {
        forecastDailyList.value = forecastDailyResponse.list.map({ forecastDaily -> ForecastDailyViewModel in
            return ForecastDailyViewModel(forecastDaily: forecastDaily)
        })
        
    }
}
