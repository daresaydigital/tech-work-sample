//
//  ReviewView.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation
import SnapKit
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
        view.backgroundColor = .lightGray
        return view
    }()
    
    lazy var authorLabel: UILabel = {
        let label = UILabel()
        label.textColor = .black
        label.font.withSize(16)
        return label
    }()
    
    lazy var descriptionLabel: UILabel = {
        let label = UILabel()
        label.textColor = .darkGray
        label.numberOfLines = 0
        label.lineBreakMode = .byTruncatingTail
        label.font.withSize(6)
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

        containerView.snp.makeConstraints { make in
            make.edges.equalToSuperview()
        }
      
        authorLabel.snp.makeConstraints { make in
            make.top.equalToSuperview().offset(4)
            make.horizontalEdges.equalToSuperview().inset(4)
        }

        descriptionLabel.snp.makeConstraints { make in
            make.top.equalTo(authorLabel.snp.bottom).offset(4)
            make.horizontalEdges.equalToSuperview().inset(4)
            make.bottom.equalToSuperview().offset(-4)
        }
        
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
