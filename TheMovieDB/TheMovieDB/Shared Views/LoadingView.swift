//
//  LoadingView.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import Foundation
import UIKit

final class LoadingView: UIView {
    private var loading = UIActivityIndicatorView(style: .large)
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setupView()
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setupView()
    }
    
    private func setupView() {
        self.layer.cornerRadius = 15
        loading.translatesAutoresizingMaskIntoConstraints = false
        loading.startAnimating()
        self.addSubview(loading)

        loading.translatesAutoresizingMaskIntoConstraints = false
        let horizontalConstraint = loading
            .centerXAnchor.constraint(equalTo: self.centerXAnchor)
        let verticalConstraint = loading
            .centerYAnchor.constraint(equalTo: self.centerYAnchor)
        NSLayoutConstraint.activate([horizontalConstraint, verticalConstraint])
    }
    
    func hide() {
        self.loading.stopAnimating()
        self.loading.isHidden = true
        self.isHidden = true
    }
    
    func unHide() {
        self.loading.startAnimating()
        self.loading.isHidden = false
        self.isHidden = false
    }
}
