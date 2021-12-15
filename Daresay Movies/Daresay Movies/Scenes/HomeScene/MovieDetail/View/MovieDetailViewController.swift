//
//  MovieDetailViewController.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/15/21.
//

import UIKit

class MovieDetailViewController: UIViewController, Storyboarded {
    
    // MARK: - Coordinator
    weak var coordinator: HomeCoordinator?
    
    // MARK: - IBOutlets
    @IBOutlet var BGImageView: UIImageView!
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var posterImgView: UIImageView!
    @IBOutlet var movieDescLabel: UILabel!
    @IBOutlet var rateLabel: UILabel!
    @IBOutlet var favBtn: UIButton!
    @IBOutlet var detailStack: UIStackView!
    
    // MARK: - Properties
    var movie: MovieModel!
    private var isFaved: Bool! {
        didSet {
            movie.isFaved = isFaved
            favBtn.imageView?.image = isFaved ? favedImage : unfavedImage
        }
    }
    
    private let favedImage: UIImage = UIImage(systemName: "heart.fill")!
    private let unfavedImage: UIImage = UIImage(systemName: "heart")!
    
    // MARK: - LifeCycle
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    // MARK: - View
    private func setupView() {
        guard movie != nil else {
            assertionFailure("Fill movie model")
            return
        }
        favBtn.addTarget(self, action: #selector(favButtonPressed), for: .touchUpInside)
        detailStack.setCornerRadius(15)
        posterImgView.addShadow()
        posterImgView.setCornerRadius(10)
        fillView()
    }
    
    private func fillView() {
        self.title = movie.title
        self.titleLabel.text = movie.originalTitle
        self.movieDescLabel.text = movie.overview
        self.isFaved = movie.isFaved
        if let rate = movie.voteAverage {
            self.rateLabel.text = "\(String(describing: rate*10))%"
        }
        
        if let img = imageURL(movie.backdropPath, typeAndSize: .backDrop(.w780)) {
            BGImageView.load(url: img)
        }
        
        if let img = imageURL(movie.posterPath, typeAndSize: .poster(.w342)) {
            posterImgView.load(url: img, placeholder: UIImage(systemName: "film"))
        }
    }
    
    private func imageURL(_ url: String?, typeAndSize: ImageTypes) -> URL? {
        guard let url = url else { return nil }
        let urlBuilder = ImageBaseUrlBuilder(forTypeAndSize: typeAndSize)
        let fullUrl = urlBuilder.createURL(filePath: url)
        return fullUrl
    }
    
    @objc private func favButtonPressed(_ button: UIButton) {
        isFaved = !isFaved
        isFaved ? FavoriteMoviesHandler.shared.fave(movie) : FavoriteMoviesHandler.shared.unfave(movie)
    }
}
