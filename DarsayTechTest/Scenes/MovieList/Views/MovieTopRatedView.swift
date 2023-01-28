//
//  MovieTopRatedView.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import Foundation
import SnapKit
import UIKit
import Combine

class MovieTopRatedView: UIView, UIContentView {
    
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
        label.minimumScaleFactor = 0.5
        label.lineBreakMode = .byTruncatingTail
        label.font = UIFont.boldSystemFont(ofSize: 16.0)
        return label
    }()
    
    lazy var voteRate: UILabel = {
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
        containerView.addSubview(voteRate)
        
        containerView.snp.makeConstraints { make in
            make.edges.equalToSuperview()
        }
        
        imageView.snp.makeConstraints { make in
            make.trailing.equalToSuperview()
            make.top.bottom.equalToSuperview()
            make.width.equalTo(100).priority(.high)
        }
        
        titleLabel.snp.makeConstraints { make in
            make.leading.equalToSuperview().inset(4)
            make.width.equalTo(180)
            make.top.equalToSuperview().inset(8)
        }
        
        voteRate.snp.makeConstraints { make in
            make.leading.equalToSuperview().inset(4)
            make.top.equalTo(titleLabel.snp.bottom).offset(8)
            make.bottom.equalToSuperview().offset(8).priority(.low)
        }
        
        updateData()
    }
    
    private func updateData() {
        guard let configuration = configuration as? Configuration else { return }
        titleLabel.text = configuration.title
        voteRate.text = String(format: "%.2f 👍🏻", configuration.voteRate)
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
        var voteRate: Double
        var nestedURLString: String?
        
        init(title: String, voteRate: Double, nestedURLString: String?) {
            self.title = title
            self.voteRate = voteRate
            self.nestedURLString = nestedURLString
        }
        
        func makeContentView() -> UIView & UIContentView {
            MovieTopRatedView(configuration: self)
        }
        
        func updated(for state: UIConfigurationState) -> MovieTopRatedView.Configuration {
            return self
        }
    }
}
