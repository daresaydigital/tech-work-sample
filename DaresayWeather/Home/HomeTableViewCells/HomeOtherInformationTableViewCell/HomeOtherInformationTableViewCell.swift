//
//  HomeOtherInformationTableViewCell.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 23/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import UIKit

class HomeOtherInformationTableViewCell: UITableViewCell {

    @IBOutlet var lblPressure : baseUiLabel!
    @IBOutlet var lblSeaLevel : baseUiLabel!
    @IBOutlet var lblGroundLevel : baseUiLabel!
    @IBOutlet var lblHumidity : baseUiLabel!

    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func configCell(mod : forecastModelviaLocation){
        
        if let arrList = mod.list, let mainData = arrList.first{
        lblPressure.text = "Pressure:  " + String(mainData.main?.pressure ?? 0.0)
            lblSeaLevel.text = "Sea Level:  " + String(mainData.main?.seaLevel ?? 0.0)
            lblGroundLevel.text = "Ground Level:  " + String(mainData.main?.grndLevel ?? 0.0)
            lblHumidity.text = "Humidity:  " + String(mainData.main?.humidity ?? 0.0) + "%"

    }
    }
}
