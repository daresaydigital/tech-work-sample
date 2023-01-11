//
//  TrendingCollectionViewCell.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class TrendingCollectionViewCell: UICollectionViewCell {

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
        label.font = UIFont.systemFont(ofSize: 15)
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

        setupPosterImage()
    }

    func setupPosterImage() {
        addSubview(posterImageView)

        NSLayoutConstraint.activate([
            posterImageView.topAnchor.constraint(equalTo: safeAreaLayoutGuide.topAnchor),
            posterImageView.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor),
            posterImageView.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor),
            posterImageView.widthAnchor.constraint(equalToConstant: 154),
            posterImageView.heightAnchor.constraint(equalToConstant: 231)
        ])

        setupTitleLabel()
    }

    func setupTitleLabel() {
        addSubview(titleLabel)

        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: posterImageView.bottomAnchor, constant: 10),
            titleLabel.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor, constant: 5),
            titleLabel.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor, constant: -5),
            titleLabel.bottomAnchor.constraint(equalTo: safeAreaLayoutGuide.bottomAnchor),
        ])
    }

    func setupData(model: TrendingViewModel) {
        titleLabel.text = model.title
        if let url = model.posterImageURL {
            posterImageView.loadImage(at: url)
        }
    }

    override func prepareForReuse() {
        posterImageView.image = nil
        posterImageView.cancelImageLoad()
    }
}
