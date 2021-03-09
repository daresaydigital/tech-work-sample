//
//  MovieCell.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 09/03/2021.
//

import UIKit

class MovieCell: UICollectionViewCell {

    //MARK: - Outlets
    @IBOutlet weak var cellBackgroundView: UIView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var releaseDateLabel: UILabel!
    @IBOutlet weak var scoreLabel: UILabel!
    @IBOutlet weak var backgroundImageView: UIImageView!
    
    //MARK: - Properties
    static let resuseId = "MovieCell"
    
    var movieViewModel: MoviesViewModel? {
        didSet{
            titleLabel.text = movieViewModel?.title
            releaseDateLabel.text = movieViewModel?.release_date
            scoreLabel.text = "\(movieViewModel?.vote_average ?? 0.0)"
            guard let url = movieViewModel?.posterURL else { return }
            loadImage(fromURL: url)
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        cellBackgroundView.layer.masksToBounds = true
        cellBackgroundView.layer.cornerRadius = 10
        layer.shadowRadius = 4
        layer.shadowColor = UIColor.black.cgColor
        layer.shadowOffset = CGSize(width: 0, height: 0.5)
        layer.shadowOpacity = 0.5
    }
    override func prepareForReuse() {
        self.backgroundImageView.image = Images.emptyImage
    }
    private func loadImage(fromURL: String) {
        MovieService.shared.getImage(from: fromURL, completed: { image in
            DispatchQueue.main.async {
                self.backgroundImageView.image = image != nil ? image : Images.emptyImage
            }
        })
    }
}
