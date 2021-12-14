//
//  UIView+Radius.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import UIKit

// MARK: - corner Radius
extension UIView {
    
    @IBInspectable
    var cornerRadius: CGFloat {
        get{
            return layer.cornerRadius
        }
        set {
            setCornerRadius(newValue)
        }
    }
    
    func setCornerRadius(_ radius: CGFloat) {
        self.clipsToBounds = true
        self.layer.masksToBounds = true
        self.layer.cornerRadius = radius
        self.layoutIfNeeded()
    }
}
