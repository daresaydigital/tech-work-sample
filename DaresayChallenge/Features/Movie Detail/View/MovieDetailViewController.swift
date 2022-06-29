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
        imageView.backgroundColor = .red
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    
    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.minimumScaleFactor = 0.5
        label.adjustsFontSizeToFitWidth = true
        label.font = .boldSystemFont(ofSize: 20)
        return label
    }()
    
    private lazy var descriptionLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 0
        label.font = .systemFont(ofSize: 16, weight: .light)
        return label
    }()
    
    private lazy var ratingLabel: UILabel = {
        let label = UILabel()
        label.font = .systemFont(ofSize: 16, weight: .medium)
        return label
    }()
    
    private lazy var containerView: UIView = {
        let view = UIView()
        view.layer.cornerRadius = 15
        view.backgroundColor = .systemBackground
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
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
    }
    
    // MARK: - Actions
}

// MARK: - Helpers
private extension MovieDetailViewController {
    func setupUI() {
        view.backgroundColor = .systemBackground
        
        view.addSubview(backgroundImageView)
        view.addSubview(containerView)
        
        backgroundImageView.leftAnchor.constraint(equalTo: view.rightAnchor).isActive = true
        backgroundImageView.rightAnchor.constraint(equalTo: view.rightAnchor).isActive = true
        backgroundImageView.topAnchor.constraint(equalTo: view.topAnchor).isActive = true
        backgroundImageView.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
        backgroundImageView.heightAnchor.constraint(equalToConstant: view.frame.height / 2).isActive = true
    }
}
