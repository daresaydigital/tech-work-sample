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
    
    var indexPath: IndexPath!
    var data: GroupForecast!
    
    let dateFormatter = DateFormatter()
    let selectedBgView = UIView()
    
    func setup(at indexPath: IndexPath, with data: GroupForecast){
        self.indexPath = indexPath
        self.data = data
        self.detailSectionSV.isHidden = !data.weather.showingDetail
        
        selectedBgView.backgroundColor = UIColor.clear
        
        self.selectedBackgroundView = selectedBgView
        let date =  Date(timeIntervalSince1970: data.weather.date)
        print(date)
        dayLabel.text = dateFormatter.weekdaySymbols[Calendar.current.component(.weekday, from: date) - 1]
        
    }

}
