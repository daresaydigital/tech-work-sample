//
//  HourlyForeCastCollectionViewCell.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 21/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import UIKit
class HourlyForeCastCollectionViewCell: UICollectionViewCell {

    
    
    @IBOutlet var lblTime : baseUiLabel!
    @IBOutlet var imgIcon : UIImageView!
    @IBOutlet var lblTemp : baseUiLabel!
    @IBOutlet var viusalEffectView : UIVisualEffectView!

//    var forecastModel : forecastModelList!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        setTheme()
    }
    

    
    func configCell(model : forecastModelList){
//        forecastModel = model
        lblTemp.addTempValue = String(Int(model.main?.temp ?? 0))
        lblTime.text = Utility.getHourFromDate(strDate: model.dtTxt ?? "")
        if let weather = model.weather {
            
            if let strIconURL = weather.first?.icon{
                imgIcon.image = Utility.getIconFromCode(strImageCode: strIconURL)
            }
        }
        
    }
    
    func setTheme(){
        lblTime.formatMode = Utility.getCurrentTheme()
        lblTemp.formatMode = Utility.getCurrentTheme()
        
    }
}
