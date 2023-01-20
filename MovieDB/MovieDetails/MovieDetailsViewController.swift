//
//  MovieDetailsViewController.swift
//  MovieDB
//
//  Created by Sinan Ulusoy on 15.01.2023.
//

import UIKit
import SnapKit

final class MovieDetailsViewController: UIViewController {
    
    private var movieModel: MovieModel!
    private let scrollView = UIScrollView()
    private let titleLabel = UILabel()
    private let backdropImageView = UIImageView()
    private let overviewTextView = UITextView()
    private let detailBackgroudView = UIView()
    private let releaseDateLabel = UILabel()
    private let voteLabel = UILabel()
    private let imageActivityIndicatorView = UIActivityIndicatorView()

    convenience init(movieModel: MovieModel) {
        self.init()
        self.movieModel = movieModel
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupView()
        setupHierarchy()
        setupLayout()
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()

        let dis = Helper.distanceBetween(bottomOf: overviewTextView, andTopOf: backdropImageView)
        scrollView.contentSize.height = dis * 1.2
        scrollView.contentSize.width = view.frame.size.width
    }
    
    private func setupView() {
        imageActivityIndicatorView.startAnimatingAsync()
        
        view.backgroundColor = .bg
        title = movieModel.title
        navigationController?.navigationBar.prefersLargeTitles = false
        navigationController?.navigationBar.tintColor = .text
        
        scrollView.alwaysBounceVertical = true
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        
        titleLabel.text = movieModel.title
        titleLabel.font = .boldSystemFont(ofSize: 25)
        titleLabel.numberOfLines = 0
        titleLabel.textColor = .text
        
        overviewTextView.text = movieModel.overview
        overviewTextView.textColor = .text
        overviewTextView.font = .systemFont(ofSize: 12)
        overviewTextView.backgroundColor = .bg
        overviewTextView.isScrollEnabled = false
        overviewTextView.isUserInteractionEnabled = false
        overviewTextView.layer.masksToBounds = true
        overviewTextView.layer.cornerRadius = 10
        overviewTextView.layer.borderWidth = 1
        overviewTextView.layer.borderColor = UIColor.text.cgColor

        detailBackgroudView.backgroundColor = .bg
        detailBackgroudView.layer.masksToBounds = true
        detailBackgroudView.layer.cornerRadius = 10
        detailBackgroudView.layer.borderWidth = 1
        detailBackgroudView.layer.borderColor = UIColor.text.cgColor
    
        releaseDateLabel.text = movieModel.release_date
        releaseDateLabel.textColor = .text
        
        backdropImageView.clipsToBounds = true
        backdropImageView.contentMode = .scaleAspectFit
        backdropImageView.layer.masksToBounds = true
        backdropImageView.layer.cornerRadius = 10
        
        voteLabel.textColor = .text
        if let vote = movieModel.vote_average {
            voteLabel.text = String(describing: vote.rounded(toPlaces: 1)) + " / 10"
        }
        
        guard let imagePath = self.movieModel.backdrop_path,
              let imageUrl = URL(string: [
                Constant.Api.Url.baseImageUrl,
                Constant.Api.Url.pathImage,
                imagePath].joined(separator: "/")) else {
            return
        }
        imageUrl.fetchImage { [weak self] res in
            switch res {
            case .success(let image):
                DispatchQueue.main.async { [weak self] in
                    self?.backdropImageView.image = image
                }
            case .failure(_):
                self?.backdropImageView.image = UIImage(named: "defaultPosterHorizontal")
            }
            self?.imageActivityIndicatorView.stopAnimatingAsync()
        }
    }
    
    private func setupHierarchy() {
        view.addSubview(scrollView)
        scrollView.addSubview(backdropImageView)
        scrollView.addSubview(overviewTextView)
        scrollView.addSubview(detailBackgroudView)
        detailBackgroudView.addSubview(releaseDateLabel)
        detailBackgroudView.addSubview(voteLabel)
        backdropImageView.addSubview(imageActivityIndicatorView)
    }
    
    private func setupLayout() {
        scrollView.snp.makeConstraints { make in
            make.edges.equalTo(view.safeAreaLayoutGuide)
        }

        backdropImageView.snp.makeConstraints { make in
            make.top.equalToSuperview().offset(20)
            make.leading.equalTo(view.safeAreaLayoutGuide.snp.leading).offset(20)
            make.trailing.equalTo(view.safeAreaLayoutGuide.snp.trailing).offset(-20)
            make.height.equalToSuperview().multipliedBy(0.3)
        }
        
        imageActivityIndicatorView.snp.makeConstraints { make in
            make.center.equalToSuperview()
        }
        
        detailBackgroudView.snp.makeConstraints { make in
            make.top.equalTo(backdropImageView.snp.bottom).offset(20)
            make.leading.equalTo(view.safeAreaLayoutGuide.snp.leading).offset(20)
            make.trailing.equalTo(view.safeAreaLayoutGuide.snp.trailing).offset(-20)
            make.height.equalTo(50)
        }
        
        releaseDateLabel.snp.makeConstraints { make in
            make.centerY.equalToSuperview()
            make.leading.equalToSuperview().offset(10)
        }
        
        voteLabel.snp.makeConstraints { make in
            make.centerY.equalToSuperview()
            make.trailing.equalToSuperview().offset(-10)
        }
        
        overviewTextView.snp.makeConstraints { make in
            let sizeThatFits = overviewTextView.sizeThatFits(
                CGSize(width: view.frame.width, height: CGFloat(MAXFLOAT)))
            make.top.equalTo(detailBackgroudView.snp.bottom).offset(20)
            make.leading.equalTo(view.safeAreaLayoutGuide.snp.leading).offset(20)
            make.trailing.equalTo(view.safeAreaLayoutGuide.snp.trailing).offset(-20)
            make.height.equalTo(sizeThatFits)
        }
    }
}
