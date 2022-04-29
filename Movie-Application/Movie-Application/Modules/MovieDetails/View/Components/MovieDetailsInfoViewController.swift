//
//  MovieDetailsInfoViewController.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import Foundation
import UIKit


final class MovieDetailsInfoViewController: UIViewController {
    
    // MARK: - Properties
    var movieTitleLabel: UILabel!
    var movieDetailsLabel : UILabel!
    var movieTitleStackView: UIStackView!
    
    var addToWatchListButton: UIButton!
    
    var releaseDateLabel: UILabel!
    var releaseTitleLabel: UILabel!
    var releaseStackView: UIStackView!
    
    var userScoreLabel: UILabel!
    var userScoreTitleLabel: UILabel!
    var userScoreStackView: UIStackView!
    
    var reviewsLabel: UILabel!
    var reviewsTitleLabel: UILabel!
    var reviewsStackView: UIStackView!
    
    var movieInfoStackView: UIStackView!
    
    var overViewTitleLabel: UILabel!
    var overViewLabel: UILabel!
    var overViewStackView: UIStackView!
    
    var totalStackView: UIStackView!
    var movie: MovieDetail!
    
    // MARK: - Initialize
   
    // MARK: - Lifecycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        movieTitleLabel = setupLabel(with: movie.title)
        movieDetailsLabel = setupLabel(with: getMoviesDetails())
        movieTitleStackView = setupStackView(with: [movieTitleLabel, movieDetailsLabel], spacing: 13, axis: .vertical)
        
        releaseDateLabel = setupLabel(with: movie.releaseDate.components(separatedBy: "-").first)
        releaseTitleLabel = setupLabel(with: "Release")
        releaseStackView = setupStackView(with: [releaseDateLabel, releaseTitleLabel], spacing: 8, axis: .vertical)
        
        userScoreLabel = setupLabel(with: "\(movie.popularity * 100.0)")
        userScoreTitleLabel = setupLabel(with: "User Score")
        userScoreStackView = setupStackView(with: [userScoreLabel, userScoreTitleLabel], spacing: 8, axis: .vertical)
        
        reviewsLabel = setupLabel(with: "\(movie.reviewsCount)")
        reviewsTitleLabel = setupLabel(with: "Reviews")
        reviewsStackView = setupStackView(with: [reviewsLabel, reviewsTitleLabel], spacing: 8, axis: .vertical)
        
        movieInfoStackView = setupStackView(with: [releaseStackView, userScoreStackView, reviewsStackView], spacing: 0, axis: .horizontal)
        movieInfoStackView.layer.cornerRadius = 14
        
        overViewTitleLabel = setupLabel(with: "Overview")
        overViewLabel = setupLabel(with: movie.overview)
        overViewStackView = setupStackView(with: [overViewTitleLabel, overViewLabel], spacing: 16, axis: .vertical)
        
        totalStackView = setupStackView(with: [movieTitleStackView, movieInfoStackView, overViewStackView], spacing: 32, axis: .vertical)
        
        setTotalStackView(in: self.view)
        
        self.applyTheme()
    }
    
    
    // MARK: - Theme
    
    func applyTheme() {
        movieTitleLabel.font = UIFont(name: "Title 2", size: 22)
        movieDetailsLabel.font = UIFont(name: "System", size: 15)
        movieDetailsLabel.textColor = .secondaryLabel
        
        
        releaseDateLabel.font = UIFont(name: "Title 2", size: 22)
        releaseTitleLabel.font = UIFont(name: "System", size: 15)
        releaseTitleLabel.textColor = .secondaryLabel
        
        reviewsLabel.font = UIFont(name: "Title 2", size: 22)
        reviewsTitleLabel.font = UIFont(name: "System", size: 15)
        reviewsTitleLabel.textColor = .secondaryLabel
        
        userScoreLabel.font = UIFont(name: "Title 2", size: 22)
        userScoreTitleLabel.font = UIFont(name: "System", size: 15)
        userScoreTitleLabel.textColor = .secondaryLabel
        movieInfoStackView.backgroundColor = .secondarySystemBackground
        
        overViewTitleLabel.font = UIFont(name: "Title 1", size: 28)
    }
    
    //MARK: - Private functions
    private func getMoviesDetails() -> String {
        let dateYear = movie.releaseDate.components(separatedBy: "-").first
        let genre = movie.genres.first?.name
        return (dateYear ?? "") + " . " + (genre ?? "")
    }
    
    private func setupLabel(with text: String?) -> UILabel {
        let label = UILabel()
        label.text = text
        label.numberOfLines = 0
        return label
    }
    
    private func setupStackView(with views: [UIView], spacing: CGFloat, axis: NSLayoutConstraint.Axis) -> UIStackView {
        let stackView = UIStackView(arrangedSubviews: views)
        stackView.axis = axis
        stackView.spacing = spacing
        stackView.alignment = .center
        stackView.distribution = .fill
        return stackView
    }
    
    private func setTotalStackView(in view: UIView? = nil) {
        if let view = view {
            view.addSubview(totalStackView)
            
            NSLayoutConstraint.activate([
                totalStackView.topAnchor.constraint(equalTo: view.topAnchor),
                totalStackView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
                totalStackView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
                totalStackView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            ])
        }
    }
}
