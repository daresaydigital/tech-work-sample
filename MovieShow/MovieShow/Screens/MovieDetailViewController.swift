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
    
    let scrollView = UIScrollView()
    let contentView = UIView()
    lazy var imageView = MovieDetailImageView(viewmodel: viewmodel)
    let titleLabel = TextLabel(font: 30, weight: .semibold)
    let releaseYearLabel = TextLabel(font: 18, weight: .regular)
    let durationLabel = TextLabel(font: 18, weight: .regular)
    let separatorDot = TextLabel(font: 25, weight: .heavy)
    let overviewLabel = TextLabel(font: 18, weight: .thin)
    let downloadButton = WideButton(title: "Download", image: SFSymbols.downloaded)
    let reviewButton = WideButton(title: "Read Reviews")

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
        view.addSubview(scrollView)
        scrollView.addSubview(contentView)
        scrollView.pinToEdges(of: view)
        contentView.pinToEdges(of: scrollView)
        contentView.heightAnchor.constraint(equalToConstant: 800).isActive = true
        contentView.widthAnchor.constraint(equalToConstant: UIScreen.main.bounds.width).isActive = true
        let innerStack = UIStackView(arrangedSubviews: [releaseYearLabel, separatorDot, durationLabel])
        releaseYearLabel.setContentHuggingPriority(.defaultHigh, for: .horizontal)
        separatorDot.setContentHuggingPriority(.defaultHigh, for: .horizontal)
        innerStack.axis = .horizontal
        innerStack.spacing = 15
        innerStack.distribution = .fill
        innerStack.tintColor = .white
        
        let outerStack = UIStackView(arrangedSubviews: [imageView, titleLabel, innerStack, downloadButton, reviewButton, overviewLabel])
        outerStack.axis = .vertical
        outerStack.setCustomSpacing(20, after: innerStack)
        outerStack.setCustomSpacing(10, after: downloadButton)
        outerStack.setCustomSpacing(20, after: reviewButton)
        
        contentView.addSubview(outerStack)
        outerStack.anchor(top: contentView.topAnchor, left: contentView.leftAnchor, right: contentView.rightAnchor, paddingLeft: 10, paddingRight: 10)
    }
}
