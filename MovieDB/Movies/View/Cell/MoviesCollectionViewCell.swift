//
//  MoviesCollectionViewCell.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 15.01.2023.
//

import UIKit
import SnapKit

protocol MoviesCollectionViewCellProtocol: AnyObject {
    func didTapCell()
}

final class MoviesCollectionViewCell: UICollectionViewCell {
    
    weak var delegate: MoviesCollectionViewCellProtocol?
    
    private var movieModel: MovieModel!
    private var movieImageView = UIImageView()
    private let foregroundView = UIView()
    private let titleLabel = UILabel()
    private let imageActivityIndicatorView = UIActivityIndicatorView()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        setupView()
        setupHierarchy()
        setupLayout()
    }
    
    private func setupView() {
        imageActivityIndicatorView.startAnimatingAsync()
        
        backgroundColor = .bg
        layer.masksToBounds = true
        layer.cornerRadius = 10
        
        movieImageView.layer.masksToBounds = true
        movieImageView.layer.cornerRadius = 10
        
        foregroundView.backgroundColor = .bg2
        foregroundView.layer.masksToBounds = true
        foregroundView.layer.cornerRadius = 10
        
        titleLabel.textAlignment = .center
        titleLabel.numberOfLines = 0
        titleLabel.textColor = .text
        titleLabel.font = .boldSystemFont(ofSize: 10)
    }
    
    private func setupHierarchy() {
        contentView.addSubview(movieImageView)
        contentView.addSubview(foregroundView)
        contentView.addSubview(imageActivityIndicatorView)
        foregroundView.addSubview(titleLabel)
    }
    
    private func setupLayout() {
        movieImageView.snp.makeConstraints { make in
            make.edges.equalToSuperview()
        }
        
        foregroundView.snp.makeConstraints { make in
            make.leading.trailing.bottom.equalToSuperview()
            make.height.equalToSuperview().multipliedBy(0.2)
        }
        
        titleLabel.snp.makeConstraints { make in
            make.edges.equalToSuperview()
        }
        
        imageActivityIndicatorView.snp.makeConstraints { make in
            make.center.equalToSuperview()
        }
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}


// MARK: - MoviesCollectionViewCell configuration
extension MoviesCollectionViewCell {
    
    func configure(movieModel: MovieModel) {
        self.movieModel = movieModel
        setData()
    }

    private func setData() {
        self.titleLabel.text = self.movieModel.title
        
        guard let imagePath = self.movieModel.poster_path,
              let imageUrl = URL(string: [
                Constant.Api.Url.baseImageUrl,
                Constant.Api.Url.pathImage,
                imagePath].joined(separator: "/")) else {
            self.movieImageView.image = UIImage(named: "defaultPosterVertical")
            imageActivityIndicatorView.stopAnimatingAsync()
            return
        }
        imageUrl.fetchImage { res in
            switch res {
            case .success(let image):
                DispatchQueue.main.async { [weak self] in
                    self?.movieImageView.image = image
                }
            case .failure(_):
                self.movieImageView.image = UIImage(named: "defaultPosterVertical")
            }
            self.imageActivityIndicatorView.stopAnimatingAsync()
        }
    }
}
