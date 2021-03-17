//
//  baseUITextField.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 22/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import UIKit

open class baseUITextField: UITextField {

    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */
    
    
    
     var formatMode: apptheme = apptheme.darkMode {
        didSet{
            Utility.changeTextFieldColorBasedOnTheme(txtField: self)

        }
    }
    
}


