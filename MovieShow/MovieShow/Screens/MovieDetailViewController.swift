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
    let titleLabel = MovieTextLabel(font: 30, weight: .semibold)
    let releaseYearLabel = MovieTextLabel(font: 18, weight: .regular)
    let durationLabel = MovieTextLabel(font: 18, weight: .regular)
    let separatorDot = MovieTextLabel(font: 25, weight: .heavy)
    let overviewLabel = MovieTextLabel(font: 18, weight: .thin)
    let downloadButton = WideButton(title: "Download", image: SFSymbols.downloaded, target: self, action: #selector(downloadButtonTapped))
    let reviewButton = WideButton(title: "Read Reviews", target: self, action: #selector(reviewButtonTapped))

    //MARK: Lifecycle
    init(viewmodel: MovieViewModel) {
        super.init(nibName: nil, bundle: nil)
        self.viewmodel = viewmodel
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        navigationController?.navigationBar.isHidden = true
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .black
        configure()
        configureUI()
    }
    
    //MARK: Selectors
    
    @objc func downloadButtonTapped() {
       print("DEBUG: Download button tapped")
    }
    
    @objc func reviewButtonTapped() {
        let vc = MovieReviewVC()
        getReviews(vc: vc)
        navigationController?.present(vc, animated: true, completion: nil)
    }
    
    //MARK: Helpers
    
    private func getReviews(vc: MovieReviewVC) {
        NetworkManager.shared.fetchReviews(for: viewmodel) { [weak self] result in
            guard let self = self else { return }
            switch result {
            case .success(let reviews):
                vc.reviews = reviews.results
            case .failure(let err): self.presentErrorMessageAlert(title: "Something wrong", message: err.localizedDescription)
            }
        }
    }
    
    
    private func configure() {
        titleLabel.text = viewmodel.title
        releaseYearLabel.text = viewmodel.releaseYear
        durationLabel.text = viewmodel.durationText
        overviewLabel.text = viewmodel.overview
        separatorDot.text = "•"
    }
    
    private func configureUI() {
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
