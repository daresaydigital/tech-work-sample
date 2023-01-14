//
//  TrendingCollectionViewCell.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class TrendingCollectionViewCell: UICollectionViewCell {

    static let identifier = "TrendingCollectionViewCell"

    private var movieId: Int64?

    private lazy var favoriteImageView: UIImageView = {
        let image = UIImage(systemName: "star.fill")
        let imageView = UIImageView(image: image)
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.tintColor = .systemYellow
        return imageView
    }()

    private lazy var favoriteBackground: UIView = {
        let view = UIView()
        view.backgroundColor = .systemRed
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }()

    private lazy var containerView: UIView = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }()

    private lazy var posterImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.isUserInteractionEnabled = false
        return imageView
    }()

    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .label
        label.font = UIFont.systemFont(ofSize: 18)
        label.numberOfLines = 3
        return label
    }()

    override init(frame: CGRect) {
        super.init(frame: CGRect())

        setup()
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    private func setup() {
        self.backgroundColor = .systemBackground

        setupContainerView()
    }

    private func setupContainerView() {
        addSubview(containerView)

        NSLayoutConstraint.activate([
            containerView.topAnchor.constraint(equalTo: contentView.topAnchor),
            containerView.leadingAnchor.constraint(equalTo: contentView.leadingAnchor),
            containerView.trailingAnchor.constraint(equalTo: contentView.trailingAnchor),
            containerView.bottomAnchor.constraint(equalTo: contentView.bottomAnchor)
        ])

        setupPosterImage()
    }

    private func setupPosterImage() {
        containerView.addSubview(posterImageView)

        NSLayoutConstraint.activate([
            posterImageView.topAnchor.constraint(equalTo: containerView.topAnchor),
            posterImageView.leadingAnchor.constraint(equalTo: containerView.leadingAnchor),
            posterImageView.trailingAnchor.constraint(equalTo: containerView.trailingAnchor),
            posterImageView.widthAnchor.constraint(equalToConstant: 154),
            posterImageView.heightAnchor.constraint(equalToConstant: 231)
        ])

        setupTitleLabel()
    }

    private func setupTitleLabel() {
        containerView.addSubview(titleLabel)

        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: posterImageView.bottomAnchor, constant: 5),
            titleLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 2),
            titleLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -2)
        ])

        setupFavoriteButton()
    }

    private func setupFavoriteButton() {
        favoriteBackground.addSubview(favoriteImageView)
        addSubview(favoriteBackground)

        NSLayoutConstraint.activate([
            favoriteBackground.topAnchor.constraint(equalTo: safeAreaLayoutGuide.topAnchor),
            favoriteBackground.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor),
            favoriteBackground.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor),
            favoriteImageView.centerXAnchor.constraint(equalTo: favoriteBackground.centerXAnchor),
            favoriteImageView.centerYAnchor.constraint(equalTo: favoriteBackground.centerYAnchor)
        ])
    }

    func setupData(model: TrendingViewModel) {
        titleLabel.text = model.title
        movieId = model.movieId
        if let url = model.posterImageURL {
            posterImageView.loadImage(at: url)
        }
    }

    func getMovieId() -> Int64 {
        return movieId ?? 0
    }

    func animateFavorite() {
        var frame = containerView.frame

        frame.origin.y += 75.0

        UIView.animate(withDuration: 0.30) {
            self.containerView.frame = frame
            self.favoriteBackground.frame.size.height = 75
            self.favoriteImageView.frame.origin.y = 50
        }

        frame.origin.y -= 75.0

        UIView.animate(withDuration: 0.30, delay: 0.30) {
            self.containerView.frame = frame
            self.favoriteBackground.frame.size.height = 0
            self.favoriteImageView.frame.origin.y = 0
        }
    }

    override func prepareForReuse() {
        posterImageView.image = nil
        posterImageView.cancelImageLoad()
    }
}
