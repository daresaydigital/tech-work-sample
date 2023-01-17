//
//  Button.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/14/23.
//

import UIKit

class Button: UIButton {
    override init(frame: CGRect) {
        super.init(frame: frame)
        commonInit()
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        commonInit()
    }

    private func commonInit() {
        updateBackgroundColor()
        layer.cornerRadius = 15
        setTitleColor(.white, for: .normal)
    }

    override var isHighlighted: Bool {
        didSet {
            updateBackgroundColor()
        }
    }

    private func updateBackgroundColor() {
        if isEnabled {
            if isHighlighted {
                backgroundColor = UIColor(red: 0.10, green: 0.21, blue: 0.56, alpha: 1.00)
            } else {
                backgroundColor = .systemBlue
            }
        }
    }
}
