//
//  UIColor+Extension.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 18.01.2023.
//

import UIKit

extension UIColor {
    
    convenience init(red: Int, green: Int, blue: Int, a: CGFloat = 1.0) {
        self.init(
            red: CGFloat(red) / 255.0,
            green: CGFloat(green) / 255.0,
            blue: CGFloat(blue) / 255.0,
            alpha: a
        )
    }

    convenience init(rgb: Int, a: CGFloat = 1.0) {
        self.init(
            red: (rgb >> 16) & 0xFF,
            green: (rgb >> 8) & 0xFF,
            blue: rgb & 0xFF,
            a: a
        )
    }
    
    class var bg: UIColor {
        UIColor(rgb: 0xFFDCA9)
    }

    class var bg2: UIColor {
        UIColor(rgb: 0xFFD8A9).withAlphaComponent(0.9)
    }
    
    class var text: UIColor {
        UIColor(rgb: 0xFF6E31)
    }
}


