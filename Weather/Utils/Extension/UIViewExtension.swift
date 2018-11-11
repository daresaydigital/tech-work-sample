//
//  UIViewExtension.swift
//  Weather
//
//  Created by Christian  Huang on 11/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit


//MARK:- layer related
extension UIView {
    func setFullRoundedCorner(borderWidth: CGFloat = 0, borderColor: CGColor = UIColor.clear.cgColor) {
        self.layer.cornerRadius = self.bounds.height / 2
        self.layer.borderWidth = borderWidth
        self.layer.borderColor = borderColor
    }
}
