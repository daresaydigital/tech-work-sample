//
//  MovieDetailViewController.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-29.
//

import UIKit

final class MovieDetailViewController: UIViewController {
    
    // MARK: - Variables
    private lazy var backgroundImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFill
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    
    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.minimumScaleFactor = 0.5
        label.adjustsFontSizeToFitWidth = true
        label.font = .boldSystemFont(ofSize: 20)
        label.setContentHuggingPriority(.defaultHigh, for: .vertical)
        return label
    }()
    
    private lazy var descriptionLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 0
        label.font = .systemFont(ofSize: 16, weight: .light)
        label.setContentHuggingPriority(.defaultLow, for: .vertical)
        return label
    }()
    
    private lazy var ratingLabel: UILabel = {
        let label = UILabel()
        label.font = .systemFont(ofSize: 16, weight: .medium)
        label.setContentHuggingPriority(.defaultHigh, for: .vertical)
        return label
    }()
    
    private lazy var containerView: UIView = {
        let view = UIView()
        view.layer.cornerRadius = 15
        view.backgroundColor = .systemBackground
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }()
    
    private lazy var placeHolderImage: UIImage = {
        UIImage(systemName: "film")!
    }()
    
    private var selectedMovie: MoviesModel
    
    // MARK: - Init
    init(selectedMovie: MoviesModel) {
        self.selectedMovie = selectedMovie
        super.init(nibName: nil, bundle: nil)
    }
    
    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    // MARK: - View Lifecycle
    override func viewDidLoad() {
        super.viewDidLoad()

        setupUI()
        populate()
    }
    
    // MARK: - Actions
}

// MARK: - Helpers
private extension MovieDetailViewController {
    func setupUI() {
        view.backgroundColor = .systemBackground
        
        setupBackgroundImageView()
        setupContainerView()
        
        let stackView = UIStackView(arrangedSubviews: [titleLabel, ratingLabel, descriptionLabel, UIView()])
        stackView.translatesAutoresizingMaskIntoConstraints = false
        stackView.axis = .vertical
        stackView.spacing = 16
        
        containerView.addSubview(stackView)
        
        stackView.leftAnchor.constraint(equalTo: containerView.leftAnchor, constant: 16).isActive = true
        stackView.rightAnchor.constraint(equalTo: containerView.rightAnchor, constant: -16).isActive = true
        stackView.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 30).isActive = true
        stackView.bottomAnchor.constraint(equalTo: containerView.bottomAnchor, constant: 16).isActive = true
    }
    
    func setupContainerView() {
        view.addSubview(containerView)
        view.bringSubviewToFront(containerView)
        
        containerView.leftAnchor.constraint(equalTo: view.leftAnchor).isActive = true
        containerView.rightAnchor.constraint(equalTo: view.rightAnchor).isActive = true
        containerView.topAnchor.constraint(equalTo: view.topAnchor, constant: 300).isActive = true
        containerView.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
    }
    
    func setupBackgroundImageView() {
        view.addSubview(backgroundImageView)
        
        backgroundImageView.leftAnchor.constraint(equalTo: view.leftAnchor).isActive = true
        backgroundImageView.rightAnchor.constraint(equalTo: view.rightAnchor).isActive = true
        backgroundImageView.topAnchor.constraint(equalTo: view.topAnchor).isActive = true
        backgroundImageView.heightAnchor.constraint(equalToConstant: view.frame.height / 2).isActive = true
    }
    
    func populate() {
        titleLabel.text = selectedMovie.originalTitle
        descriptionLabel.text = selectedMovie.overview
        
        if let rating = selectedMovie.voteAverage {
            ratingLabel.text = "Rating: \(String(describing: rating * 10))%"
        }
        
        if let imageURL = selectedMovie.backgroundImageURL {
            backgroundImageView.load(url: imageURL, placeholder: placeHolderImage)
        }
    }
}
