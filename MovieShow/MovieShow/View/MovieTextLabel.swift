//
//  textLabel.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-28.
//

import UIKit

class MovieTextLabel: UILabel {
  
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    convenience init(font: CGFloat, weight: UIFont.Weight) {
        self.init(frame: .zero)
        self.font = UIFont.systemFont(ofSize: font, weight: weight)
        numberOfLines = 0
        textAlignment = .left
        textColor = .white
    }
}
