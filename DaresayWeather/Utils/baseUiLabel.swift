//
//  baseUiLabel.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 22/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import UIKit

open class baseUiLabel: UILabel {

    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */

    
    @IBInspectable open var isTemparture : Bool = false {
        didSet{
//            addDegreeForTempTextField()
        }
    }
    
    @IBInspectable var fontSize : Int  = 12{
        didSet{
            configureFonts()
        }
    }
    var formatMode: apptheme = apptheme.darkMode {
        didSet{
            Utility.changeLabelColorBasedOnTheme(lbl: self)
        }
    }
    var addTempValue : String = "" {
        didSet{
            if isTemparture {
            addDegreeForTempTextField()
            }
        }
    }
    
    
    
    
    open override func awakeFromNib() {
        super.awakeFromNib();
    }
    
    open override func layoutSubviews() {
        super.layoutSubviews();
        
    }
    
    
    func addDegreeForTempTextField(){
        if isTemparture {
            
            if self.addTempValue != "" || self.addTempValue != " "{
                self.text = self.addTempValue + "ºc"
            }
        }
    }
    
    
    func configureFonts() {
        let resizedFontSize = DesignUtility.getFontSize(fSize: CGFloat(fontSize))
        
        let fnt = UIFont.init(name: self.font.fontName, size: resizedFontSize);
        
        self.font = fnt
    }
    
}




