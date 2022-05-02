//
//  MovieInfoContentView.swift
//  MovieInfoContent
//
//  Created by Mohanna Zakizadeh on 4/29/22.
//

import UIKit

final class MovieInfoContentView: UIViewController, ViewInterface {
	
	var presenter: MovieInfoContentPresenterViewInterface!
	
	// MARK: - Properties
    var imageView: UIImageView!
    var addToWatchListButton: UIButton!
    
    var movie: MovieDetail!
	
	// MARK: - Initialize

	
	// MARK: - Lifecycle
	
	override func viewDidLoad() {
		super.viewDidLoad()
        
        addToWatchListButton = setupButton()
        imageView = setupImageView()
        
        setupView()
		
		self.applyTheme()
		self.presenter.viewDidLoad()
	}
	
	
	// MARK: - Theme
	
	func applyTheme() {
        view.backgroundColor = .secondarySystemBackground
        addToWatchListButton.backgroundColor = .secondaryLabel
        addToWatchListButton.tintColor = .systemBackground
	}
	
	// MARK: - Private functions
    private func setupImageView() -> UIImageView {
        let image = presenter.getMovieImage(path: movie.poster ?? "")
        let imageView = UIImageView(image: image)
        imageView.clipsToBounds = true
        imageView.layer.cornerRadius = 14
        imageView.translatesAutoresizingMaskIntoConstraints = false
        
        return imageView
    }
    
    private func setupButton() -> UIButton {
        let button = UIButton()
        button.addTarget(self, action: #selector(addToWatchListTapped), for: .touchUpInside)
        button.setBackgroundImage(UIImage(systemName: "bookmark.circle"), for: .normal)
        button.layer.cornerRadius = 20
        return button
    }
    
    private func setupView() {
        view.addSubview(imageView)
        view.addSubview(addToWatchListButton)
        
        imageView.translatesAutoresizingMaskIntoConstraints = false
        addToWatchListButton.translatesAutoresizingMaskIntoConstraints = false
        
        NSLayoutConstraint.activate([
            imageView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            imageView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            imageView.topAnchor.constraint(equalTo: view.topAnchor, constant: 32),
            imageView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 45),
            imageView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -45),
            imageView.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: -((UIScreen.main.bounds.height / 2.2) + 32)),
            
            addToWatchListButton.bottomAnchor.constraint(equalTo: imageView.bottomAnchor, constant: -15),
            addToWatchListButton.trailingAnchor.constraint(equalTo: imageView.trailingAnchor, constant: -15),
            addToWatchListButton.heightAnchor.constraint(equalToConstant: 40),
            addToWatchListButton.widthAnchor.constraint(equalToConstant: 40)
        ])
    }
    
	// MARK: - Actions
    @objc func addToWatchListTapped() {
        presenter.addToWatchListTapped(movie: movie)
    }
	
}

extension MovieInfoContentView: MovieInfoContentViewInterface {
	
}
