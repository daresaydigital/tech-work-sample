//
//  ReviewCell.swift
//  MovieDatabase
//
//  Created by Cem Atilgan on 2020-09-16.
//  Copyright © 2020 Cem Atilgan. All rights reserved.
//

import UIKit

enum ReviewCellConfigureState {
    case data(Review)
    case loading
}

class ReviewCell: UITableViewCell {

    @IBOutlet weak var authorNameLabel: UILabel!
    @IBOutlet weak var reviewLabel: UILabel!
    @IBOutlet weak var stackView: UIStackView!
    @IBOutlet weak var loadingIndicator: UIActivityIndicatorView!

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
        authorNameLabel.font = UIFont.preferredFont(for: .subheadline, weight: .semibold)
        reviewLabel.font = UIFont.preferredFont(for: .body, weight: .regular)
        stackView.alpha = 0
    }

    override func prepareForReuse() {
        super.prepareForReuse()
        authorNameLabel.text = ""
        reviewLabel.text = ""
    }

    func configure(for state: ReviewCellConfigureState) {
        switch state {
        case .loading:
            stackView.alpha = 0
            loadingIndicator.startAnimating()
        case .data(let review):
            stackView.alpha = 1
            authorNameLabel.text = review.author
            reviewLabel.text = review.content
            loadingIndicator.stopAnimating()
        }
    }
}
