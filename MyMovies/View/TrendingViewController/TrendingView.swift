//
//  TrendingView.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

protocol FavoriteDelegate: AnyObject {
    func favorite(_ row: Int)
}

class TrendingView: UIView {

    private lazy var trendingLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .label
        label.font = UIFont.boldSystemFont(ofSize: 24)
        return label
    }()

    private lazy var trendingCollectionView: UICollectionView = {
        let layout = UICollectionViewFlowLayout()
        layout.scrollDirection = .horizontal
        layout.itemSize = CGSize(width: 154, height: 310)
        layout.minimumLineSpacing = 4
        layout.minimumInteritemSpacing = 4
        let collectionView = UICollectionView(frame: CGRect(), collectionViewLayout: layout)
        collectionView.showsHorizontalScrollIndicator = false
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.register(TrendingCollectionViewCell.self, forCellWithReuseIdentifier: TrendingCollectionViewCell.identifier)
        return collectionView
    }()

    private lazy var activityIndicator: UIActivityIndicatorView = {
        let activityIndicator = UIActivityIndicatorView(style: .large)
        activityIndicator.translatesAutoresizingMaskIntoConstraints = false
        activityIndicator.hidesWhenStopped = true
        return activityIndicator
    }()

    private lazy var errorLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .label
        label.font = UIFont.systemFont(ofSize: 28)
        label.text = "Error when loading Trending information"
        label.numberOfLines = 0
        label.textAlignment = .center
        return label
    }()

    weak var collectionViewDelegate: UICollectionViewDelegate? {
        get {
            self.trendingCollectionView.delegate
        }
        set {
            self.trendingCollectionView.delegate = newValue
        }
    }

    weak var collectionDataSource: UICollectionViewDataSource? {
        get {
            self.trendingCollectionView.dataSource
        }
        set {
            self.trendingCollectionView.dataSource = newValue
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }

    required init?(coder: NSCoder) {
        fatalError()
    }

    private func setup() {
        self.backgroundColor = .systemBackground

        setupTrendingLabel()
    }

    private func setupTrendingLabel() {
        addSubview(trendingLabel)

        NSLayoutConstraint.activate([
            trendingLabel.topAnchor.constraint(equalTo: safeAreaLayoutGuide.topAnchor, constant: 10),
            trendingLabel.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor, constant: 10)
        ])

        setupTrendingCollectionView()
    }

    private func setupTrendingCollectionView() {
        addSubview(trendingCollectionView)

        NSLayoutConstraint.activate([
            trendingCollectionView.topAnchor.constraint(equalTo: trendingLabel.bottomAnchor, constant: 10),
            trendingCollectionView.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor),
            trendingCollectionView.trailingAnchor.constraint(equalTo: safeAreaLayoutGuide.trailingAnchor),
            trendingCollectionView.bottomAnchor.constraint(equalTo: safeAreaLayoutGuide.bottomAnchor)
        ])

        setupActivityIndicator()
    }

    private func setupActivityIndicator() {
        addSubview(activityIndicator)

        NSLayoutConstraint.activate([
            activityIndicator.centerXAnchor.constraint(equalTo: safeAreaLayoutGuide.centerXAnchor),
            activityIndicator.centerYAnchor.constraint(equalTo: safeAreaLayoutGuide.centerYAnchor)
        ])

        setupErrorLabel()
    }

    private func setupErrorLabel() {
        addSubview(errorLabel)

        NSLayoutConstraint.activate([
            errorLabel.centerXAnchor.constraint(equalTo: safeAreaLayoutGuide.centerXAnchor),
            errorLabel.centerYAnchor.constraint(equalTo: safeAreaLayoutGuide.centerYAnchor)
        ])
    }

    func renderLoadingState() {
        self.trendingCollectionView.isHidden = true
        self.errorLabel.isHidden = true
        self.activityIndicator.startAnimating()
    }

    func renderErrorState() {
        self.activityIndicator.stopAnimating()
        self.trendingCollectionView.isHidden = true
        self.trendingLabel.isHidden = true
        self.errorLabel.isHidden = false
    }

    func renderSuccessState(with title: String) {
        self.activityIndicator.stopAnimating()
        self.errorLabel.isHidden = true
        self.trendingLabel.isHidden = false
        self.trendingLabel.text = title
        self.trendingCollectionView.isHidden = false
        self.trendingCollectionView.reloadData()
    }

    func favoriteTrending(for row: Int) {
        print("Yaaay!")
//        self.delegate.favorite(row)
    }
}
