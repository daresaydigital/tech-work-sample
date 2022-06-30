//
//  MovieDetailViewController.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-29.
//

import UIKit

protocol ReloadFavoritesDelegate: AnyObject {
    func refresh()
}

final class MovieDetailViewController: UIViewController {
    
    // MARK: - Variables
    private lazy var backgroundImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFill
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.accessibilityIdentifier = AccessibilityIdentifiers.movieDetailBackgroundImageView.rawValue
        return imageView
    }()
    
    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.minimumScaleFactor = 0.5
        label.adjustsFontSizeToFitWidth = true
        label.font = .boldSystemFont(ofSize: 24)
        label.setContentHuggingPriority(.defaultHigh, for: .vertical)
        label.accessibilityIdentifier = AccessibilityIdentifiers.movieDetailTitleLabel.rawValue
        return label
    }()
    
    private lazy var descriptionLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 0
        label.font = .systemFont(ofSize: 16, weight: .light)
        label.setContentHuggingPriority(.defaultLow, for: .vertical)
        label.accessibilityIdentifier = AccessibilityIdentifiers.movieDetailDescLabel.rawValue
        return label
    }()
    
    private lazy var ratingLabel: UILabel = {
        let label = UILabel()
        label.font = .systemFont(ofSize: 16, weight: .medium)
        label.setContentHuggingPriority(.defaultHigh, for: .vertical)
        label.accessibilityIdentifier = AccessibilityIdentifiers.movieDetailRatingLabel.rawValue
        return label
    }()
    
    private lazy var containerView: UIView = {
        let view = UIView()
        view.layer.cornerRadius = 15
        view.backgroundColor = .systemBackground
        view.translatesAutoresizingMaskIntoConstraints = false
        view.accessibilityIdentifier = AccessibilityIdentifiers.movieDetailContainerView.rawValue
        return view
    }()
    
    private var favoriteButton: UIButton = {
        var config = UIButton.Configuration.tinted()
        config.imagePadding = 8
        
        let button = UIButton(configuration: config)
        button.accessibilityIdentifier = AccessibilityIdentifiers.movieDetailFavoriteButton.rawValue
        return button
    }()
    
    private var isFavorite: Bool! {
        didSet {
            selectedMovie.isFavorite = isFavorite
            favoriteButton.configuration?.image = isFavorite ? favoriteImage : unfavoriteImage
            favoriteButton.configuration?.title = isFavorite ? removeFavoriteTitle : favoriteTile
        }
    }
    
    private var selectedMovie: MoviesModel
    private let favoriteTile = "Add to favorites"
    private let removeFavoriteTitle = "Remove from favorites"
    private lazy var placeHolderImage: UIImage = { UIImage(systemName: "film")! }()
    private lazy var unfavoriteImage: UIImage = { UIImage(systemName: "heart")! }()
    private lazy var favoriteImage: UIImage = { UIImage(systemName: "heart.fill")! }()
    
    public weak var delegate: ReloadFavoritesDelegate?
    
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
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        navigationController?.navigationBar.tintColor = .white
    }
    
    // MARK: - Actions
    private func favoriteButtonTapped() {
        isFavorite = !isFavorite
        isFavorite ? FavoriteMoviesHandler.shared.addToFavorites(selectedMovie) : FavoriteMoviesHandler.shared.removeFromFavorites(selectedMovie)
        delegate?.refresh()
    }
}

// MARK: - Helpers
private extension MovieDetailViewController {
    func setupUI() {
        view.backgroundColor = .systemBackground
        
        setupBackgroundImageView()
        setupContainerView()
        setupButtonAction()
        
        let labelsStackView = UIStackView(arrangedSubviews: [titleLabel, ratingLabel, descriptionLabel])
        labelsStackView.axis = .vertical
        labelsStackView.spacing = 16
        
        let stackView = UIStackView(arrangedSubviews: [labelsStackView, favoriteButton, UIView()])
        stackView.translatesAutoresizingMaskIntoConstraints = false
        stackView.axis = .vertical
        stackView.alignment = .center
        stackView.spacing = 50
        
        containerView.addSubview(stackView)
        
        stackView.leftAnchor.constraint(equalTo: containerView.leftAnchor, constant: 16).isActive = true
        stackView.rightAnchor.constraint(equalTo: containerView.rightAnchor, constant: -16).isActive = true
        stackView.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 30).isActive = true
        stackView.bottomAnchor.constraint(equalTo: containerView.bottomAnchor, constant: 16).isActive = true
        
        favoriteButton.heightAnchor.constraint(equalToConstant: 45).isActive = true
        favoriteButton.widthAnchor.constraint(equalToConstant: 240).isActive = true
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
        isFavorite = selectedMovie.isFavorite
        
        if let rating = selectedMovie.voteAverage {
            ratingLabel.text = "Rating: \(String(describing: rating * 10))%"
        }
        
        if let imageURL = selectedMovie.backgroundImageURL {
            backgroundImageView.load(url: imageURL, placeholder: placeHolderImage)
        }
    }
    
    func setupButtonAction() {
        favoriteButton.addAction(UIAction(handler: { [weak self] _ in
            guard let self = self else { return }
            self.favoriteButtonTapped()
        }), for: .touchUpInside)
    }
}
