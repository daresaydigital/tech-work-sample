//
//  MovieCell.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/28/22.
//

import UIKit

final class MovieCell: UICollectionViewCell {

    let movieImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFill
        imageView.clipsToBounds = true
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.layer.cornerRadius = 10
        return imageView
    }()

    override init(frame: CGRect) {
        super.init(frame: frame)

        self.contentView.addSubview(movieImageView)

        NSLayoutConstraint.activate([
            movieImageView.topAnchor
                .constraint(equalTo: self.contentView.topAnchor),
            movieImageView.leftAnchor
                .constraint(equalTo: self.contentView.leftAnchor),
            movieImageView.rightAnchor
                .constraint(equalTo: self.contentView.rightAnchor),
            movieImageView.bottomAnchor
                .constraint(equalTo: self.contentView.bottomAnchor)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func prepareForReuse() {
        movieImageView.image = nil
    }

}

extension MovieCell: MovieCollectionViewCell {
    typealias CellViewModel = Movie
}
