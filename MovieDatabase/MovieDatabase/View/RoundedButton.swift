//
//  RoundedButton.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-18.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

class RoundedButton: UIButton {

    var fromColor: UIColor = #colorLiteral(red: 0.2549019754, green: 0.2745098174, blue: 0.3019607961, alpha: 1)
    var toColor: UIColor = #colorLiteral(red: 0.501960814, green: 0.501960814, blue: 0.501960814, alpha: 1)
    var cornerRadius: CGFloat = 12.0

    override func awakeFromNib() {
        setNeedsDisplay()
    }

    override func draw(_ rect: CGRect) {
        let gradientLayer = CAGradientLayer()
        gradientLayer.colors = [fromColor.cgColor, toColor.cgColor]
        gradientLayer.startPoint = CGPoint(x: 0.0, y: 0.5)
        gradientLayer.endPoint = CGPoint(x: 1.0, y: 0.5)
        gradientLayer.frame = self.bounds
        gradientLayer.cornerRadius = cornerRadius
        layer.insertSublayer(gradientLayer, at: 0)
    }
}
