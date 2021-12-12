//
//  MovieTableViewCell.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import UIKit
import SDWebImage

class MovieTableViewCell: UITableViewCell {

    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var posterImageView: UIImageView!
    @IBOutlet private weak var releaseDateLabel: UILabel!
    @IBOutlet private weak var rateLabel: UILabel!
    @IBOutlet private weak var roundedView: UIView!
    
    static let identifier = "MovieCell"

    override func awakeFromNib() {
        super.awakeFromNib()
        configureUI()
    }
    
    /// Configure your cell
    /// - Parameter movieData: The data model required to configue the cell
    func configure(movieData: Movie) {
        
        titleLabel.text = movieData.title
        releaseDateLabel.text = movieData.releaseDate.releaseFormattedDate
        rateLabel.text = "\(movieData.voteAverage)"
        posterImageView.sd_setImage(with: movieData.posterURL, placeholderImage: UIImage(named: "placeholder.png"))
    }
    
    // MARK: - Private
    
    private func configureUI() {
        self.layoutIfNeeded()
        roundedView.layer.cornerRadius = 15
        posterImageView.layer.cornerRadius = 12
        posterImageView.layer.masksToBounds = true
    }
}

private extension Date {
    var releaseFormattedDate: String {
        let dateformat = DateFormatter()
        dateformat.dateFormat = "MMM d, yyyy"
        return dateformat.string(from: self)
    }
}

