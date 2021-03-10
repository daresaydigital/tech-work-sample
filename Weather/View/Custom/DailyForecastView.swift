//
//  DailyForecastView.swift
//  Weather
//
//  Created by Christian  Huang on 03/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit

class DailyForecastView: UIView {
    @IBOutlet private weak var dayLabel: UILabel!
    @IBOutlet private weak var todayLabel: UILabel!
    @IBOutlet private weak var weatherImageView: UIImageView!
    @IBOutlet private weak var maxLabel: UILabel!
    @IBOutlet private weak var minLabel: UILabel!
    
    
    class func instanceFromNib() -> DailyForecastView {
        let instance = UINib(nibName: "DailyForecastView", bundle: nil).instantiate(withOwner: nil, options: nil)[0] as! DailyForecastView
        instance.translatesAutoresizingMaskIntoConstraints = false
        instance.heightAnchor.constraint(equalToConstant: instance.bounds.height).isActive = true
        return instance
    }

    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */
    
    func configureTodayView(weatherViewModel: WeatherViewModel) {
        dayLabel.text = weatherViewModel.dayString
        todayLabel.alpha = 1
        weatherImageView.image = nil
        maxLabel.text = "\(Int(weatherViewModel.temperatureMax))°"
        minLabel.text = "\(Int(weatherViewModel.temperatureMin))°"
    }
    
    func configureTodayView(forecastDailyViewModel: ForecastDailyViewModel) {
        
        dayLabel.text = forecastDailyViewModel.dayString
        todayLabel.alpha = 0
        weatherImageView.kf.setImage(with: URL(string: forecastDailyViewModel.icon))
        maxLabel.text = "\(Int(forecastDailyViewModel.temperatureMax))°"
        minLabel.text = "\(Int(forecastDailyViewModel.temperatureMin))°"
    }

}
