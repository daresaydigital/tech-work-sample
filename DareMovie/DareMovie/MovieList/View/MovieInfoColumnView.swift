//
//  MovieInfoColumnView.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import UIKit

class MovieInfoColumnView: UIView {
    private var movieModel: MovieInfoModel
    
    init(frame: CGRect, movieModel: MovieInfoModel) {
        self.movieModel = movieModel
        super.init(frame: frame)
        self.backgroundColor = .clear
        addViewsToStackView()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private lazy var stackView: UIStackView = {
        let stackView = UIStackView(frame: .zero)
        stackView.translatesAutoresizingMaskIntoConstraints = false
        stackView.axis = .vertical
        stackView.distribution = .equalCentering
        
        self.addSubview(stackView)
        stackView.topAnchor.constraint(equalTo: self.topAnchor, constant: 0).isActive = true
        stackView.bottomAnchor.constraint(equalTo: self.bottomAnchor, constant: 0).isActive = true
        stackView.leadingAnchor.constraint(equalTo: self.leadingAnchor, constant: 0).isActive = true
        stackView.trailingAnchor.constraint(equalTo: self.trailingAnchor, constant: 0).isActive = true
        return stackView
    }()
    
    func addViewsToStackView() {
        stackView.addArrangedSubview(UIView())
        stackView.addArrangedSubview(arrangedViewForTitleAndSubtitle(movieModel.title, subtitle: movieModel.overview))
        stackView.addArrangedSubview(UIView())
    }
    
    func arrangedViewForTitleAndSubtitle(_ title: String, subtitle: String) -> UIView {
        let containerView = UIStackView(frame: .zero)
        containerView.translatesAutoresizingMaskIntoConstraints = false
        containerView.axis = .vertical
        containerView.distribution = .equalSpacing
        containerView.addArrangedSubview(titleViewForText(title))
        containerView.addArrangedSubview(separatorView())
        containerView.addArrangedSubview(subtitleViewForText(subtitle))
        
        return containerView
    }
    
    func titleViewForText(_ text: String) -> UIView {
        let label = UILabel()
        label.text = text
        label.font = UIFont.preferredFont(forTextStyle: .title2).bold()
        label.minimumScaleFactor = 0.75
        label.adjustsFontForContentSizeCategory = true
        label.adjustsFontSizeToFitWidth = true
        label.textColor = .darkGray
        label.backgroundColor = .clear
        label.numberOfLines = 1
        return label
    }
    
    func subtitleViewForText(_ text: String) -> UIView {
        let label = UILabel()
        label.text = text
        label.font = UIFont.preferredFont(forTextStyle: .body)
        label.adjustsFontForContentSizeCategory = true
        label.textColor = .gray
        label.backgroundColor = .clear
        label.numberOfLines = 2
        return label
    }
    
    func separatorView() -> UIView {
        let separator = UIView()
        separator.heightAnchor.constraint(equalToConstant: 5).isActive = true
        return separator
    }
}
