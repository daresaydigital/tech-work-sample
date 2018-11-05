//
//  HourlyForecastCell.swift
//  Weather
//
//  Created by Christian  Huang on 03/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit

class HourlyForecastCell: UICollectionViewCell {
    @IBOutlet private weak var hourLabel: UILabel!
    @IBOutlet private weak var chanceLabel: UILabel!
    @IBOutlet private weak var temperatureLabel: UILabel!
    
    func configureCell(forecastViewModel: ForecastViewModel, isNow: Bool = false) {
        hourLabel.text = isNow ? "Now" : forecastViewModel.time
        chanceLabel.text = nil
        temperatureLabel.text = forecastViewModel.temperature
    }
    
}
