//
//  SupplementaryHeaderView.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/10/1401 AP.
//

import Foundation
import UIKit

class SupplementaryHeaderView: UICollectionReusableView {

    override init(frame: CGRect) {
        super.init(frame: frame)
        self.setupViews()
    }

    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
        self.setupViews()
    }
    
    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.font = UIFont.systemFont(ofSize: 20, weight: .heavy)
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    private func setupViews() {
        self.addSubview(titleLabel)
        
        let titleLabelConstraints = [
            titleLabel.centerYAnchor.constraint(equalTo: self.centerYAnchor),
            titleLabel.leadingAnchor.constraint(equalTo: self.leadingAnchor, constant: 16),
            titleLabel.trailingAnchor.constraint(equalTo: self.trailingAnchor, constant: -16)
        ]

        NSLayoutConstraint.activate(titleLabelConstraints)
    }
    
    func setText(_ text: String) {
        titleLabel.text = text
    }
}
