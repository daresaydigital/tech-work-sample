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
    
    lazy var imageView = MovieDetailImageView(viewmodel: viewmodel)
    let titleLabel = TextLabel(font: 30, weight: .semibold)
    let releaseYearLabel = TextLabel(font: 18, weight: .regular)
    let durationLabel = TextLabel(font: 18, weight: .regular)
    let separatorDot = TextLabel(font: 25, weight: .heavy)
    let overviewLabel = TextLabel(font: 18, weight: .thin)
    let downloadButton: UIButton = {
        let button = UIButton(type: .system)
        button.setImage(SFSymbols.downloaded, for: .normal)
        button.tintColor = .white
        button.setTitle("Dowload", for: .normal)
        button.setTitleColor(.white, for: .normal)
        button.titleLabel?.font = UIFont.boldSystemFont(ofSize: 16)
        button.contentHorizontalAlignment = .center
        button.backgroundColor = UIColor(white: 0.2, alpha: 1)
        button.anchor(height: 44)
        button.layer.cornerRadius = 4
        button.clipsToBounds = true
        return button
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
        titleLabel.text = viewmodel.title
        releaseYearLabel.text = viewmodel.releaseYear
        durationLabel.text = viewmodel.durationText
        overviewLabel.text = viewmodel.overview
        separatorDot.text = "•"
    }
    
    func configureUI() {
        view.addSubview(imageView)
        imageView.anchor(top: view.safeAreaLayoutGuide.topAnchor, left: view.leftAnchor, right: view.rightAnchor)
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
