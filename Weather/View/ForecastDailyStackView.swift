//
//  ForecastDailyStackView.swift
//  Weather
//
//  Created by Christian  Huang on 06/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit

class ForecastDailyStackView: UIStackView {
    
    var forecastDailyListViewModel = ForecastDailyListViewModel()

}

//MARK:- ViewModel related
extension ForecastDailyStackView {
    func initViewModel() {
        forecastDailyListViewModel.forecastDailyList.listener = { forecastDailyList in
            DispatchQueue.main.async {
                self.reloadData()
            }
        }
    }
}

extension ForecastDailyStackView {
    private func reloadData() {
        let count = forecastDailyListViewModel.forecastDailyCount
        
        if count > self.arrangedSubviews.count {
            for _ in self.arrangedSubviews.count..<count {
                let dailyForecastView = DailyForecastView.instanceFromNib()
                self.addArrangedSubview(dailyForecastView)
            }
        } else {
            for _ in count..<self.arrangedSubviews.count {
                self.removeArrangedSubview(self.arrangedSubviews.last!)
            }
        }
        
        for i in 0..<count {
            let dailyForecastView = self.arrangedSubviews[i] as! DailyForecastView
            dailyForecastView.configureTodayView(forecastDailyViewModel: forecastDailyListViewModel.getForecastDailyViewModel(index: i))
        }
        
    }
}
