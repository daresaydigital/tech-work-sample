//
//  HomeDescriptionTableViewCell.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 23/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import UIKit

class HomeDescriptionTableViewCell: UITableViewCell {

    
    @IBOutlet var lblDescription : baseUiLabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func configCell(mod : weatherModelviaLocation){
        if let weather = mod.weather , let data = weather.first  {
            lblDescription.text = "DESCRIPTION: " + (data.descriptionField?.localizedUppercase ?? "")

        }
    }
    
}
