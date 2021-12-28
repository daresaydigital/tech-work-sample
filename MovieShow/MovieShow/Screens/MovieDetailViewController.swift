//
//  MovieDetailViewController.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import UIKit

class MovieDetailViewController: UIViewController {
    //MARK: Properties
    var viewmodel: MovieViewModel! {
        didSet {
            print("DEBUG: did set view model")
        }
    }
    
    let imageView: UIImageView = {
        let iv = UIImageView()
        iv.layer.cornerRadius = 8
        iv.clipsToBounds = true
        return iv
    }()
    let titleLabel: UILabel = {
        let title = UILabel()
        title.textColor = .white
        title.numberOfLines = 0
        title.font = UIFont.boldSystemFont(ofSize: 35)
        return title
    }()
    let releaseYearLabel: UILabel = {
        let label = UILabel()
        label.textColor = .white
        label.textAlignment = .left
        label.font = UIFont.systemFont(ofSize: 25)
        return label
    }()
    
    let durationLabel: UILabel = {
        let label = UILabel()
        label.textColor = .white
        label.textAlignment = .left
        label.font = UIFont.systemFont(ofSize: 25)
        return label
    }()
    
    let separatorDot: UILabel = {
        let label = UILabel()
        label.textColor = .white
        label.font = UIFont.boldSystemFont(ofSize: 25)
        label.text = "•"
        return label
    }()
    
    let downloadButton: UIButton = {
        let button = UIButton(type: .system)
        button.setImage(SFSymbols.downloaded, for: .normal)
        button.tintColor = .white
        button.setTitle("Dowload", for: .normal)
        button.setTitleColor(.white, for: .normal)
        button.titleLabel?.font = UIFont.boldSystemFont(ofSize: 19)
        button.contentHorizontalAlignment = .center
        button.backgroundColor = .systemGray
        button.anchor(height: 44)
        button.layer.cornerRadius = 4
        button.clipsToBounds = true
        return button
    }()
    
    let overviewLabel: UILabel = {
        let label = UILabel()
        label.numberOfLines = 0
        label.textColor = .white
        label.font = UIFont.systemFont(ofSize: 18)
        return label
    }()

    //MARK: Lifecycle
    init(viewmodel: MovieViewModel) {
        super.init(nibName: nil, bundle: nil)
        self.viewmodel = viewmodel
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .black
        configure()
        configureUI()
    }
    
    //MARK: Helpers
    func configure() {
        imageView.load(url: viewmodel.backdropURL)
        titleLabel.text = viewmodel.title
        releaseYearLabel.text = viewmodel.releaseYear
        durationLabel.text = viewmodel.durationText
        overviewLabel.text = viewmodel.overview
    }
    
    func configureUI() {
        view.addSubview(imageView)
        imageView.setContentHuggingPriority(.required, for: .vertical)
        imageView.anchor(top: view.safeAreaLayoutGuide.topAnchor, left: view.leftAnchor, right: view.rightAnchor)
        imageView.heightAnchor.constraint(equalTo: view.widthAnchor, multiplier: 0.55).isActive = true
        let innerStack = UIStackView(arrangedSubviews: [releaseYearLabel, separatorDot, durationLabel])
        releaseYearLabel.setContentHuggingPriority(.defaultHigh, for: .horizontal)
        separatorDot.setContentHuggingPriority(.defaultHigh, for: .horizontal)
        innerStack.axis = .horizontal
        innerStack.spacing = 15
        innerStack.distribution = .fill
        innerStack.tintColor = .white
        let outerStack = UIStackView(arrangedSubviews: [titleLabel, innerStack, downloadButton, overviewLabel])
        outerStack.axis = .vertical
        outerStack.setCustomSpacing(20, after: innerStack)
        outerStack.setCustomSpacing(20, after: downloadButton)
        
        view.addSubview(outerStack)
        outerStack.anchor(top: imageView.bottomAnchor, left: view.leftAnchor, right: view.rightAnchor, paddingLeft: 10, paddingRight: 10)
    }
}
