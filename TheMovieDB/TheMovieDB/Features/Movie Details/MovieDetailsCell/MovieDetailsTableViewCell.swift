//
//  MovieDetailsTableViewCell.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import UIKit

class MovieDetailsTableViewCell: UITableViewCell {


    @IBOutlet private weak var backdropImageView: UIImageView!
    @IBOutlet private weak var posterImageView: UIImageView!
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var overviewLabel: UILabel!
    @IBOutlet private weak var genresLabel: UILabel!
    @IBOutlet private weak var titleView: UIView!
    @IBOutlet private weak var genresView: UIView!

    static let identifier = "MovieDetailsCell"

    override func awakeFromNib() {
        super.awakeFromNib()
        configureUI()
    }
    
    func configureUI() {
        self.layoutIfNeeded()
        titleView.layer.cornerRadius = 15
        genresView.layer.cornerRadius = 15
        
        posterImageView.layer.cornerRadius = 12
        posterImageView.layer.borderWidth = 3
        posterImageView.layer.borderColor = UIColor.white.cgColor
        posterImageView.layer.masksToBounds = true
        
        backdropImageView.layer.cornerRadius = 15
        backdropImageView.layer.masksToBounds = true
    }

    /// Configure your cell
    /// - Parameter movieDatails: The data model required to configue the cell
    func configure(movieDatails: MovieDetails) {
        
        titleLabel.text = movieDatails.title
        overviewLabel.text = movieDatails.overview

        posterImageView.sd_setImage(with: movieDatails.posterURL, placeholderImage: UIImage(named: "placeholder.png"))
        backdropImageView.sd_setImage(with: movieDatails.backdropURL, placeholderImage: UIImage(named: "placeholder.png"))
    
        let genres: String = movieDatails.genres.map { $0.name }
                                                .joined(separator: ", ")
        
        genresLabel.text = genres
    }
    
}
