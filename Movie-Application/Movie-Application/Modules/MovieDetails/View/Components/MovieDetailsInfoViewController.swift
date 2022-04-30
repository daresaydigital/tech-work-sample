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
    var scrollView: UIScrollView!
    
    var movie: MovieDetail!
    
    // MARK: - Initialize
   
    // MARK: - Lifecycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        movieTitleLabel = setupLabel(with: movie.title)
        movieDetailsLabel = setupLabel(with: getMoviesDetails())
        movieTitleStackView = setupStackView(with: [movieTitleLabel, movieDetailsLabel], spacing: 13, axis: .vertical)
        movieTitleStackView.distribution = .equalSpacing
        
        releaseDateLabel = setupLabel(with: movie.releaseDate.components(separatedBy: "-").first)
        releaseTitleLabel = setupLabel(with: "Release")
        releaseStackView = setupStackView(with: [releaseDateLabel, releaseTitleLabel], spacing: 8, axis: .vertical)
        releaseStackView.alignment = .center
        
        userScoreLabel = setupLabel(with: String(format: "%.0f", movie.voteAverage*10) + "%")
        userScoreTitleLabel = setupLabel(with: "User Score")
        userScoreStackView = setupStackView(with: [userScoreLabel, userScoreTitleLabel], spacing: 8, axis: .vertical)
        userScoreStackView.alignment = .center
        
        reviewsLabel = setupLabel(with: "\(movie.reviewsCount)")
        reviewsTitleLabel = setupLabel(with: "Reviews")
        reviewsStackView = setupStackView(with: [reviewsLabel, reviewsTitleLabel], spacing: 8, axis: .vertical)
        reviewsStackView.alignment = .center
        
        movieInfoStackView = setupStackView(with: [releaseStackView, userScoreStackView, reviewsStackView], spacing: 0, axis: .horizontal)
        movieInfoStackView.layer.cornerRadius = 14
        movieInfoStackView.alignment = .center
        
        overViewTitleLabel = setupLabel(with: "Overview")
        overViewLabel = setupLabel(with: movie.overview)
        overViewStackView = setupStackView(with: [overViewTitleLabel, overViewLabel], spacing: 16, axis: .vertical)
        overViewStackView.distribution = .fillProportionally
        
        totalStackView = setupStackView(with: [movieTitleStackView, movieInfoStackView, overViewStackView], spacing: 32, axis: .vertical)
        
        scrollView = setupScrollView()
        setTotalStackView(in: scrollView)
        
        setScrollView(in: self.view)
        
        self.applyTheme()
    }
    
    
    // MARK: - Theme
    
    func applyTheme() {
        view.backgroundColor = .systemBackground
        view.layer.cornerRadius = 14
        
        movieTitleLabel.font = UIFont.boldSystemFont(ofSize: 22)
        movieDetailsLabel.font = UIFont(descriptor: .preferredFontDescriptor(withTextStyle: .body), size: 15)
        movieDetailsLabel.textColor = .secondaryLabel
        
        
        releaseDateLabel.font = UIFont.boldSystemFont(ofSize: 22)
        releaseTitleLabel.font = UIFont(descriptor: .preferredFontDescriptor(withTextStyle: .body), size: 15)
        releaseTitleLabel.textColor = .secondaryLabel
        
        reviewsLabel.font = UIFont.boldSystemFont(ofSize: 22)
        reviewsTitleLabel.font = UIFont(descriptor: .preferredFontDescriptor(withTextStyle: .body), size: 15)
        reviewsTitleLabel.textColor = .secondaryLabel
        
        userScoreLabel.font = UIFont.boldSystemFont(ofSize: 22)
        userScoreTitleLabel.font = UIFont(descriptor: .preferredFontDescriptor(withTextStyle: .body), size: 15)
        userScoreTitleLabel.textColor = .secondaryLabel
        movieInfoStackView.backgroundColor = .secondarySystemBackground
        
        
        overViewTitleLabel.font = UIFont.boldSystemFont(ofSize: 28)
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
        stackView.alignment = .leading
        stackView.distribution = .fillProportionally
        return stackView
    }
    
    private func setupScrollView() -> UIScrollView {
        let scrollView = UIScrollView()
        scrollView.contentSize.width = scrollView.frame.size.width
        scrollView.frame = self.view.bounds
        return scrollView
    }
    
    private func setTotalStackView(in view: UIView? = nil) {
        if let view = view {
            view.addSubview(totalStackView)
            totalStackView.alignment = .leading
            totalStackView.translatesAutoresizingMaskIntoConstraints = false
            totalStackView.layer.cornerRadius = 14
            
            NSLayoutConstraint.activate([
                totalStackView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
                totalStackView.leftAnchor.constraint(equalTo: view.leftAnchor),
                totalStackView.widthAnchor.constraint(equalTo: view.widthAnchor),
                
                movieInfoStackView.heightAnchor.constraint(equalToConstant: 85),
                movieInfoStackView.leadingAnchor.constraint(equalTo: totalStackView.leadingAnchor, constant: 16),
                movieInfoStackView.trailingAnchor.constraint(equalTo: totalStackView.trailingAnchor, constant: -16),
                
                movieTitleStackView.leadingAnchor.constraint(equalTo: totalStackView.leadingAnchor, constant: 16),
                movieTitleStackView.trailingAnchor.constraint(equalTo: totalStackView.trailingAnchor, constant: -16),
                
                overViewStackView.leadingAnchor.constraint(equalTo: totalStackView.leadingAnchor, constant: 16),
                overViewStackView.trailingAnchor.constraint(equalTo: totalStackView.trailingAnchor, constant: -16)
            ])
        }
    }
    
    private func setScrollView(in view: UIView? = nil) {
        if let view = view {
            view.addSubview(scrollView)
            
            scrollView.translatesAutoresizingMaskIntoConstraints = false
            
            NSLayoutConstraint.activate([
                scrollView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 32),
                scrollView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -32),
                scrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
                scrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
                scrollView.widthAnchor.constraint(equalTo: view.widthAnchor)
            ])
        }
    }
}
