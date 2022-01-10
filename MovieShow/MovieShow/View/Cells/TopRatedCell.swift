//
//  TopRatedCell.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import UIKit

class TopRatedCell: UICollectionViewCell, SelfConfiguringCell {
    
    let imageView: UIImageView = {
        let imageView = UIImageView()
        imageView.layer.cornerRadius = 15
        imageView.clipsToBounds = true
        return imageView
    }()
    
    let title: UILabel = {
        let title = UILabel()
        title.textColor = .white
        title.font = UIFont.boldSystemFont(ofSize: 16)
        title.numberOfLines = 2
        title.textAlignment = .center
        return title
    }()
    
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
        title.text = viewmodel.title
    }
    
    private func configureUI() {
        addSubview(title)
        addSubview(imageView)
        imageView.anchor(top: topAnchor, left: leftAnchor,
                         right: rightAnchor)
        imageView.heightAnchor.constraint(equalTo: heightAnchor, multiplier: 0.8).isActive = true
        title.anchor(top: imageView.bottomAnchor, left: leftAnchor, bottom: bottomAnchor, right: rightAnchor)
        
    }
}
