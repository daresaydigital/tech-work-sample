//
//  PopularMovieCell.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import UIKit

class PopularMovieCell: UICollectionViewCell, SelfConfiguringCell {
    let imageView = UIImageView()
    
    var viewmodel: MovieViewModel! {
        didSet {
            configure(with: viewmodel)
        }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        configureUI()
    }
    
    convenience init(viewmodel: MovieViewModel) {
        self.init(frame: .zero)
        self.viewmodel = viewmodel
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func configure(with viewmodel: MovieViewModel) {
        imageView.load(url: viewmodel.postureURL)
    }
    
    func configureUI() {
        addSubview(imageView)
        imageView.layer.cornerRadius = 15
        imageView.clipsToBounds = true
        imageView.anchor(top: topAnchor, left: leftAnchor, bottom: bottomAnchor, right: rightAnchor)
    }
}




