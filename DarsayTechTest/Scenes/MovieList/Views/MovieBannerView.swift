//
//  MovieBannerView.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation
import SnapKit
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
        return view
    }()
    
    lazy var imageView: UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFill
        imageView.clipsToBounds = true
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
        return label
    }()
    
    lazy var popularityRateLabel: UILabel = {
        let label = UILabel()
        label.textColor = .darkGray
        label.font.withSize(14)
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
        containerView.addSubview(imageView)
        containerView.addSubview(titleLabel)
        containerView.addSubview(popularityRateLabel)
        
        containerView.snp.makeConstraints { make in
            make.edges.equalToSuperview()
        }
        
        imageView.snp.makeConstraints { make in
            make.edges.equalToSuperview()
        }
        
        titleLabel.snp.makeConstraints { make in
            make.horizontalEdges.equalToSuperview()
            make.bottom.equalToSuperview()
        }
        
        updateData()
    }
    
    private func updateData() {
        guard let configuration = configuration as? Configuration else { return }
        titleLabel.text = configuration.title
        popularityRateLabel.text = String(format: "%.2f", configuration.popularityRate/100.0)
        
        updateImageView(nestedURLString: configuration.nestedURLString)
    }
    
    private func updateImageView(nestedURLString: String?) {
        do {
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
    
    struct Configuration: UIContentConfiguration {
        
        var title: String
        var popularityRate: Double
        var nestedURLString: String?
        
        init(title: String, popularityRate: Double, nestedURLString: String?) {
            self.title = title
            self.popularityRate = popularityRate
            self.nestedURLString = nestedURLString
        }
        
        func makeContentView() -> UIView & UIContentView {
            MovieBannerView(configuration: self)
        }
        
        func updated(for state: UIConfigurationState) -> MovieBannerView.Configuration {
            return self
        }
    }
}
