//
//  UIView+Shadow.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/15/21.
//

import UIKit

extension UIView {
    func addShadow() {
        self.clipsToBounds = false
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.shadowOpacity = 1
        self.layer.shadowOffset = CGSize.zero
        self.layer.shadowRadius = 10
        self.layer.shadowPath = UIBezierPath(roundedRect: self.bounds, cornerRadius: 10).cgPath
    }
}
