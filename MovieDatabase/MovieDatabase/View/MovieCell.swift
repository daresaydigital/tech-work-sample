//
//  MovieCell.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

enum MovieCellConfigureState {
    case data(Movie)
    case loading
}

class MovieCell: UITableViewCell {

    @IBOutlet private weak var posterImageView: UIImageView!
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var ratingLabel: UILabel!
    @IBOutlet private weak var dateLabel: UILabel!
    @IBOutlet private weak var loadingIndicator: UIActivityIndicatorView!
    @IBOutlet private weak var imageLoadingIndicator: UIActivityIndicatorView!
    @IBOutlet private weak var stackView: UIStackView!

    static var reuseIdentifier: String {
        return nibName
    }

    static var nib: UINib {
        return UINib(nibName: nibName, bundle: .main)
    }

    private static var nibName: String {
        return String(describing: self)
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        titleLabel.font = UIFont.preferredFont(forTextStyle: .headline)
        posterImageView.alpha = 0
        stackView.alpha = 0
    }

    override func prepareForReuse() {
        super.prepareForReuse()
        self.posterImageView.image = nil
        self.imageView?.image = nil
        self.titleLabel.text = ""
        self.ratingLabel.text = ""
        self.dateLabel.text = ""
    }

    func configure(for state: MovieCellConfigureState) {
        switch state {
        case .loading:
            posterImageView.alpha = 0
            stackView.alpha = 0
            loadingIndicator.startAnimating()
            imageLoadingIndicator.startAnimating()
        case .data(let movie):
            stackView.alpha = 1
            titleLabel.text = movie.title
            dateLabel.text = "Release Date: \(movie.releaseDate)"
            ratingLabel.text = "Rating: \(movie.rating)"
            loadingIndicator.stopAnimating()
            loadImage(for: movie)
        }
    }

    private func loadImage(for movie: Movie) {
        let imageURL = URL(string: URLStrings.baseImageURL + movie.posterPath)
        ImageService.getImage(url: imageURL) { [weak self](result) in
            switch result {
            case .success(let image):
                DispatchQueue.main.async {
                    self?.posterImageView.image = image
                    self?.posterImageView.alpha = 1
                    self?.imageLoadingIndicator.stopAnimating()
                }
            case .failure(let error):
                self?.posterImageView.image = UIImage(systemName: "burst")
                print(error.localizedDescription)
            }
        }
    }
}

