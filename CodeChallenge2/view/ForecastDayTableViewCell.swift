//
//  ForecastDayTableViewCell.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import UIKit

class ForecastDayTableViewCell: UITableViewCell {

    static let identifier = "forecastdaycell"
    
    @IBOutlet weak var principalSectionSV: UIStackView!
    @IBOutlet weak var detailSectionSV: UIStackView!
    @IBOutlet weak var dayLabel: UILabel!
    @IBOutlet weak var degreesLabel: UILabel!
    @IBOutlet weak var weatherIconImageView: UIImageView!
    
    @IBOutlet weak var minTempLabel: UILabel!
    @IBOutlet weak var maxTempLabel: UILabel!
    @IBOutlet weak var windLabel: UILabel!
    @IBOutlet weak var humidityLabel: UILabel!
    
    var indexPath: IndexPath!
    var data: GroupForecast!
    
    let dateFormatter = DateFormatter()
    let selectedBgView = UIView()
    
    
    /// Setups this cell to be rendered
    ///
    /// - Parameters:
    ///   - indexPath: The index path in the table for this cell instance
    ///   - data: The representing data
    func setup(at indexPath: IndexPath, with data: GroupForecast){
        self.indexPath = indexPath
        self.data = data
        self.detailSectionSV.isHidden = !data.weather.showingDetail
        
        selectedBgView.backgroundColor = UIColor.clear
        
        self.selectedBackgroundView = selectedBgView
        let date =  Date(timeIntervalSince1970: data.weather.date)
        print(date)
        dayLabel.text = dateFormatter.weekdaySymbols[Calendar.current.component(.weekday, from: date) - 1]
        degreesLabel.text = "\(Int(data.weather.main.temp))"
        weatherIconImageView.image = UIImage(named: data.weather.weather[0].icon) ?? UIImage(named: "03d")
        
        //detail section
        minTempLabel.text = String.localizedStringWithFormat("forecast.weather.min".localized, "\(Int(data.weather.main.tempMin))")
        maxTempLabel.text = String.localizedStringWithFormat("forecast.weather.max".localized, "\(Int(data.weather.main.tempMax))")
        windLabel.text = String.localizedStringWithFormat("forecast.weather.wind".localized, "\(data.weather.wind.speed)")
        humidityLabel.text = String.localizedStringWithFormat("forecast.weather.humidity".localized, "\(data.weather.main.humidity)")
    }

}
