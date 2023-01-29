//
//  ReviewView.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation
import UIKit
import Combine

class ReviewView: UIView, UIContentView {
    
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
        view.backgroundColor = .highlightPositive
        view.translatesAutoresizingMaskIntoConstraints = false

        return view
    }()
    
    lazy var authorLabel: UILabel = {
        let label = UILabel()
        label.textColor = .onBackgroundPrimary
        label.font.withSize(16)
        label.translatesAutoresizingMaskIntoConstraints = false

        return label
    }()
    
    lazy var descriptionLabel: UILabel = {
        let label = UILabel()
        label.textColor = .onHighlightPositive
        label.numberOfLines = 0
        label.lineBreakMode = .byTruncatingTail
        label.font.withSize(6)
        label.translatesAutoresizingMaskIntoConstraints = false

        return label
    }()
    
    // MARK: - Initialization
    
    init(configuration: Configuration) {
        self.configuration = configuration
        super.init(frame: .zero)
        setupView()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setupView() {

        // add subviews here
        self.addSubview(containerView)
        containerView.addSubview(authorLabel)
        containerView.addSubview(descriptionLabel)

        let containerViewConstraints = [
            containerView.topAnchor.constraint(equalTo: self.topAnchor),
            containerView.leadingAnchor.constraint(equalTo: self.leadingAnchor),
            containerView.bottomAnchor.constraint(equalTo: self.bottomAnchor),
            containerView.trailingAnchor.constraint(equalTo: self.trailingAnchor)
        ]

        NSLayoutConstraint.activate(containerViewConstraints)
    
        let authorLabelConstraints = [
            authorLabel.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 4),
            authorLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 4),
            authorLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -4)
        ]

        NSLayoutConstraint.activate(authorLabelConstraints)
       
        let descriptionLabelConstraints = [
            descriptionLabel.topAnchor.constraint(equalTo: authorLabel.bottomAnchor, constant: 4),
            descriptionLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 4),
            descriptionLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -4),
            descriptionLabel.bottomAnchor.constraint(equalTo: containerView.bottomAnchor, constant: -4)
        ]

        NSLayoutConstraint.activate(descriptionLabelConstraints)
    
        updateData()
    }
    
    private func updateData() {
        guard let configuration = configuration as? Configuration else { return }
        authorLabel.text = configuration.author.isEmpty ? "" : "\(configuration.author):"
        descriptionLabel.text = configuration.description
    }
    
    // MARK: - Configuration
    
    struct Configuration: UIContentConfiguration {
        
        var author: String
        var description: String
        
        init(author: String, description: String) {
            self.author = author
            self.description = description
        }
        
        func makeContentView() -> UIView & UIContentView {
            ReviewView(configuration: self)
        }
        
        func updated(for state: UIConfigurationState) -> ReviewView.Configuration {
            return self
        }
    }
}
