//
//  MovieDetailImageView.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-28.
//

import UIKit

class MovieDetailImageView: UIImageView {

    override init(frame: CGRect) {
        super.init(frame: frame)
        configure()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    convenience init(viewmodel: MovieViewModel) {
        self.init(frame: .zero)
        load(url: viewmodel.backdropURL)
    }
    
    private func configure() {
        layer.cornerRadius = 4
        clipsToBounds = true
        widthAnchor.constraint(equalTo: widthAnchor).isActive = true
        heightAnchor.constraint(equalTo: self.widthAnchor, multiplier: 0.55).isActive = true
    }
}
