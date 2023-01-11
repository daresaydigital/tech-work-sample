//
//  TrendingView.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class TrendingView: UIView {

    private lazy var trendingLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .label
        label.font = UIFont.boldSystemFont(ofSize: 24)
        return label
    }()

    private lazy var trendingCollectionView: UICollectionView = {
        let layoutConfig = UICollectionLayoutListConfiguration(appearance: UICollectionLayoutListConfiguration.Appearance.insetGrouped)
        let listLayout = UICollectionViewCompositionalLayout.list(using: layoutConfig)
        let collectionView = UICollectionView(frame: CGRect(), collectionViewLayout: listLayout)
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.register(TrendingCollectionViewCell.self, forCellWithReuseIdentifier: "TrendingCollectionViewCell")
        return collectionView
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
            trendingLabel.topAnchor.constraint(equalTo: safeAreaLayoutGuide.topAnchor),
            trendingLabel.leadingAnchor.constraint(equalTo: safeAreaLayoutGuide.leadingAnchor)
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
    }
}
