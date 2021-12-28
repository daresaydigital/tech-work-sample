//
//  WideButton.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-28.
//

import UIKit

class WideButton: UIButton {

    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    convenience init(title: String, image: UIImage) {
        self.init()
        self.setTitle(title, for: .normal)
        self.setImage(image, for: .normal)
        configure()
    }
    
    func configure() {
        tintColor = .white
        setTitleColor(.white, for: .normal)
        titleLabel?.font = UIFont.boldSystemFont(ofSize: 19)
        contentHorizontalAlignment = .center
        backgroundColor = UIColor(white: 0.2, alpha: 1)
        anchor(height: 44)
        layer.cornerRadius = 4
        clipsToBounds = true
    }
}
