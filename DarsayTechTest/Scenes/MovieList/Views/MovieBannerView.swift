//
//  MovieBannerView.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation
import UIKit
import Combine

class MovieBannerView: UIView, UIContentView {
    
    // MARK: - Variables
    var cancellables = Set<AnyCancellable>()

    var configuration: UIContentConfiguration {
        didSet {
            updateData()
        }
    }
    
    lazy var containerView: UIView = {
        let view = UIView()
        view.layer.cornerRadius = 8
        view.clipsToBounds = true
        view.backgroundColor = .placeholderText
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }()
    
    lazy var imageView: UIImageView = {
        var imageView = UIImageView()
        imageView.contentMode = .scaleAspectFill
        imageView.clipsToBounds = true
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    
    lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.textColor = .black
        label.numberOfLines = 0
        label.lineBreakMode = .byWordWrapping
        label.font = UIFont.boldSystemFont(ofSize: 18.0)
        label.backgroundColor = .lightText
        label.alpha = 0.7
        label.textAlignment = .center
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    lazy var favoriteButton: UIButton = {
        let button = UIButton()
        button.setImage(getProperImage(), for: .normal)
        button.tintColor = .negative
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()

    // MARK: - Initialization
    
    init(configuration: Configuration) {
        self.configuration = configuration
        super.init(frame: .zero)
        setupView()
        
        favoriteButton.addAction(.init(handler: { [weak self] _ in
            guard let self = self else { return }
            self.handleFavoriteAction()
        }), for: .primaryActionTriggered)
        
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setupView() {
        guard let configuration = configuration as? Configuration else { return }
       
        // add subviews here
        self.addSubview(containerView)
        containerView.addSubview(imageView)
        containerView.addSubview(titleLabel)
        
        let containerViewConstraints = [
            containerView.topAnchor.constraint(equalTo: self.topAnchor),
            containerView.leadingAnchor.constraint(equalTo: self.leadingAnchor),
            containerView.bottomAnchor.constraint(equalTo: self.bottomAnchor),
            containerView.trailingAnchor.constraint(equalTo: self.trailingAnchor)
        ]

        NSLayoutConstraint.activate(containerViewConstraints)
        
        let imageViewConstraints = [
            imageView.topAnchor.constraint(equalTo: containerView.topAnchor),
            imageView.leadingAnchor.constraint(equalTo: containerView.leadingAnchor),
            imageView.bottomAnchor.constraint(equalTo: containerView.bottomAnchor),
            imageView.trailingAnchor.constraint(equalTo: containerView.trailingAnchor)
        ]

        NSLayoutConstraint.activate(imageViewConstraints)
        
        let titleConstraints = [
            titleLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor),
            titleLabel.bottomAnchor.constraint(equalTo: containerView.bottomAnchor),
            titleLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor)
        ]

        NSLayoutConstraint.activate(titleConstraints)
        
        if configuration.hasFavoriteButton {
            addFavorteButton()
        }
        
        updateData()
    }
    
    private func addFavorteButton() {
        containerView.addSubview(favoriteButton)
       
        let favoriteConstraints = [
            favoriteButton.widthAnchor.constraint(equalToConstant: 40),
            favoriteButton.heightAnchor.constraint(equalToConstant: 40),
            favoriteButton.bottomAnchor.constraint(equalTo: containerView.bottomAnchor, constant: 8),
            favoriteButton.trailingAnchor.constraint(equalTo: containerView.trailingAnchor)
        ]

        NSLayoutConstraint.activate(favoriteConstraints)
    }
    
    private func updateData() {
        
        guard let configuration = configuration as? Configuration else { return }
       
        titleLabel.text = configuration.movie.title
        updateImageView(nestedURLString: configuration.movie.backdropPath)
        favoriteButton.setImage(getProperImage(), for: .normal)
    }
    
    private func handleFavoriteAction() {
        guard let configuration = configuration as? Configuration else { return }
        
        configuration.movie.isFaved = !(configuration.movie.isFaved ?? false)
        
        (configuration.movie.isFaved ?? false) ? FavoriteStorage.append(movie: configuration.movie) :
        FavoriteStorage.remove(movie: configuration.movie)
        
        favoriteButton.setImage(getProperImage(), for: .normal)
    }
    
    private func getProperImage() -> UIImage {
        guard let configuration = configuration as? Configuration else { return UIImage() }
       
        let image = (configuration.movie.isFaved ?? false) ? UIImage(symbolicName: .filledFavorite, pointSize: 20) : UIImage(symbolicName: .favorite, pointSize: 20)
        
        return image ?? UIImage()
    }
    
    private func updateImageView(nestedURLString: String?) {
        do {
            imageView.image = nil
            let url = try URL.getFullPath(sizeType: .backDrop(.w300), nestedURLString: nestedURLString ?? "")
            
            ImageLoader.shared.loadImage(from: url).sinkToResult { result in
                
                guard case .success(let image) = result else {
                    self.imageView.image = nil
                    return
                }
                self.imageView.image = image
                
            }.store(in: &cancellables)
        } catch {
            self.imageView.image = nil
        }
    }
    
    // MARK: - Configuration
    
    final class Configuration: UIContentConfiguration {
        
        var movie: Movie
        var hasFavoriteButton: Bool
        
        init(movie: Movie, hasFavoriteButton: Bool = true) {
            self.movie = movie
            self.hasFavoriteButton = hasFavoriteButton
        }
    
        func makeContentView() -> UIView & UIContentView {
            MovieBannerView(configuration: self)
        }
        
        func updated(for state: UIConfigurationState) -> MovieBannerView.Configuration {
            return self
        }
    }
}
