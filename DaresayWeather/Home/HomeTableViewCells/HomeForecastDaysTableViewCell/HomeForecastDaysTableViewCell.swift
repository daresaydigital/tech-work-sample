//
//  HomeForecastDaysTableViewCell.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 21/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import UIKit

class HomeForecastDaysTableViewCell: UITableViewCell {

    
    @IBOutlet var lblDay : baseUiLabel!
    @IBOutlet var lblMaxTemp : baseUiLabel!
    @IBOutlet var imgIcon : UIImageView!


    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        setTheme()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func configCell(data : forecastModelList){
        
        lblDay.text = Utility.getDayNameBy(stringDate:  data.dtTxt ?? "")
        lblMaxTemp.addTempValue = String(Int(data.main?.tempMax ?? 0))
        if let weather = data.weather {
            
            if let strIconURL = weather.first?.icon{
                imgIcon.image = Utility.getIconFromCode(strImageCode: strIconURL)
            }
        }
        
    }
    
    func setTheme(){
        lblDay.formatMode = Utility.getCurrentTheme()
        lblMaxTemp.formatMode = Utility.getCurrentTheme()

    }
    
}
