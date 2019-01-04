//
//  ForecastDailyListViewModel.swift
//  Weather
//
//  Created by Christian  Huang on 06/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation


class ForecastDailyListViewModel: NSObject {
    private var forecastDailyRawList = [ForecastDaily]()
    let forecastDailyList: Dynamic<[ForecastDailyViewModel]> = Dynamic([])
    var forecastDailyCount: Int {
        return forecastDailyList.value.count
    }
    var status: String? = nil
    
    var networkManager: WeatherNetworkManager!
    var weatherDetail: WeatherDetailProtocol?
}

extension ForecastDailyListViewModel {
    func fetchForecastDaily(coordinate: Coordinate) {
        networkManager.fetchForecastDaily(latitude: coordinate.latitude, longitude: coordinate.longitude) { (forecastDailyResponse, error) in
            if let error = error {
                print("Network Error: \(error)")
                self.status = error
                
                DispatchQueue.main.async {
                    let _ = Timer.scheduledTimer(withTimeInterval: 2, repeats: false, block: { _ in
                        self.fetchForecastDaily(coordinate: coordinate)
                    })
                }
            } else {
                self.status = nil
                self.processForecastDailyResponse(forecastDailyResponse!)
            }
        }
    }
    
    func getForecastDailyViewModel(index: Int) -> ForecastDailyViewModel {
        return forecastDailyList.value[index]
    }
    
    func userSelectedForecast(_ index: Int){
        weatherDetail?.updateWeatherData(forecastDaily: forecastDailyRawList[index])
    }
}

extension ForecastDailyListViewModel {
    private func processForecastDailyResponse(_ forecastDailyResponse: ForecastDailyResponse) {
        forecastDailyRawList = forecastDailyResponse.list
        forecastDailyList.value = forecastDailyResponse.list.map({ forecastDaily -> ForecastDailyViewModel in
            return ForecastDailyViewModel(forecastDaily: forecastDaily)
        })
        
    }
}
