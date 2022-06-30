//
//  MovieTableViewCell.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import UIKit

class MovieTableViewCell: UITableViewCell {
    
    // MARK: - Variables
    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 2
        label.font = .boldSystemFont(ofSize: 16)
        label.accessibilityIdentifier = AccessibilityIdentifiers.movieTitleLabel.rawValue
        return label
    }()
    
    private lazy var descriptionLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 4
        label.textColor = .systemGray
        label.font = .systemFont(ofSize: 14)
        label.accessibilityIdentifier = AccessibilityIdentifiers.movieDescriptionLabel.rawValue
        return label
    }()
    
    private lazy var movieImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.tintColor = .systemGray
        imageView.contentMode = .scaleAspectFit
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.accessibilityIdentifier = AccessibilityIdentifiers.movieImageView.rawValue
        return imageView
    }()
    
    private lazy var favoriteImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.tintColor = .red
        imageView.contentMode = .scaleAspectFit
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.accessibilityIdentifier = AccessibilityIdentifiers.movieFavoriteImageView.rawValue
        return imageView
    }()

    private lazy var placeHolderImage: UIImage = {
        UIImage(systemName: "film")!
    }()
    
    // MARK: - Init
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        
        accessibilityIdentifier = AccessibilityIdentifiers.moviesTableViewCell.rawValue
        
        setupUI()
    }
    
    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Helpers
private extension MovieTableViewCell {
    func setupUI() {
        accessoryType = .disclosureIndicator
        
        let stackView = UIStackView(arrangedSubviews: [titleLabel, descriptionLabel])
        stackView.axis = .vertical
        stackView.spacing = 8
        
        stackView.translatesAutoresizingMaskIntoConstraints = false
        contentView.addSubview(stackView)
        contentView.addSubview(movieImageView)
        contentView.addSubview(favoriteImageView)
        
        favoriteImageView.rightAnchor.constraint(equalTo: contentView.rightAnchor, constant: -8).isActive = true
        favoriteImageView.topAnchor.constraint(equalTo: contentView.topAnchor, constant: 8).isActive = true
        
        stackView.leftAnchor.constraint(equalTo: movieImageView.rightAnchor, constant: 8).isActive = true
        stackView.rightAnchor.constraint(equalTo: favoriteImageView.rightAnchor, constant: -12).isActive = true
        stackView.topAnchor.constraint(equalTo: contentView.topAnchor, constant: 8).isActive = true
        stackView.bottomAnchor.constraint(equalTo: contentView.bottomAnchor, constant: -8).isActive = true
        
        movieImageView.centerYAnchor.constraint(equalTo: contentView.centerYAnchor).isActive = true
        movieImageView.leftAnchor.constraint(equalTo: contentView.leftAnchor, constant: 8).isActive = true
        movieImageView.widthAnchor.constraint(equalToConstant: 90).isActive = true
        movieImageView.heightAnchor.constraint(equalToConstant: 90).isActive = true
    }
}

// MARK: - Configuration
extension MovieTableViewCell {
    public func configureCell(with movieModel: MoviesModel, isFavorite: Bool) {
        titleLabel.text = movieModel.title
        descriptionLabel.text = movieModel.overview
        
        if let imageURL = movieModel.posterURL {
            movieImageView.load(url: imageURL, placeholder: placeHolderImage)
        }
        
        if isFavorite {
            favoriteImageView.image = UIImage(systemName: "heart.fill")
        } else {
            favoriteImageView.image = UIImage(systemName: "heart")
        }
    }
}
