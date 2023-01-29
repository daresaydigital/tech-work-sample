//
//  MovieDetailViewController.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.

import Combine
import Foundation
import UIKit

fileprivate extension Layout {
    static let bannersSectionGroupWidthInset: CGFloat = 48
    static let contentScrollViewContentInsetBottom: CGFloat = 100
    static let reviewSectionContentInsetBottom: CGFloat = 40
    static let reviewGroupHeight: CGFloat = 200
    static let bannerHeight: CGFloat = 160
    
}

fileprivate extension PageSection {
    static let reviewSection =  Self.init(id: "section-review")
}

final class MovieDetailViewController: UIViewController, BaseSceneViewController {
    
    // MARK: - Variables
    
    let router: MovieDetailRouterProtocol?
    let viewModel: any ViewModel<MovieDetail.State, MovieDetail.Action>
    var cancellables = Set<AnyCancellable>()

    var sections: [PageSection] {
        [.reviewSection]
    }
    
    // MARK: - UI Components
    
    lazy var imageView: UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFill
        imageView.clipsToBounds = true
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    
    lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.textColor = .onBackgroundPrimary
        label.font = UIFont.boldSystemFont(ofSize: 16.0)
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    lazy var popularityLabel: UILabel = {
        let label = UILabel()
        label.textColor = .positiveGreen
        label.font.withSize(12)
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    lazy var overviewLabel: UILabel = {
        let label = UILabel()
        label.textColor = .onBackground
        label.font.withSize(10)
        label.numberOfLines = 0
        label.translatesAutoresizingMaskIntoConstraints = false

        return label
    }()
    
    lazy var collectionView: UICollectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: collectionViewLayout())

        collectionView.showsHorizontalScrollIndicator = false
        collectionView.showsVerticalScrollIndicator = false
        collectionView.semanticContentAttribute = .forceLeftToRight
        collectionView.backgroundColor = .background
        collectionView.contentInset.bottom = Layout.contentScrollViewContentInsetBottom
        collectionView.dataSource = self
        collectionView.translatesAutoresizingMaskIntoConstraints = false

        let cellReuseIdentifiers = [ PageSection.reviewSection.cellReuseIdentifier ]

        for reuseIdentifier in cellReuseIdentifiers {
            collectionView.register(UICollectionViewCell.self, forCellWithReuseIdentifier: reuseIdentifier)
        }

        return collectionView
    }()
    
    // MARK: - Initialization
    
    init(viewModel: any ViewModel<MovieDetail.State, MovieDetail.Action>, router: MovieDetailRouterProtocol?) {
        self.viewModel = viewModel
        self.router = router
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError()
    }
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        
        prepareUI()
        
        bind()
        
        self.viewModel.handle(action: .fetchDetail)
        
        self.viewModel.handle(action: .fetchReviews)
    }
    
    // MARK: - Prepare UI
    
    func prepareUI() {
        view.backgroundColor = .background
        // add subviews
        view.addSubview(imageView)
        view.addSubview(titleLabel)
        view.addSubview(popularityLabel)
        view.addSubview(overviewLabel)
        view.addSubview(collectionView)
        setConstraints()
    }
    
    // MARK: - Prepare Layout
    
    func collectionViewLayout() -> UICollectionViewLayout {
        UICollectionViewCompositionalLayout.init { [unowned self] sectionIndex, _ in
            
            let section =  self.sections[sectionIndex]
            switch section {
            case .reviewSection:
                return getReviewCollectionLayoutSection()
            default:
                fatalError("Invalid section: \(section)")
            }
        }
    }
    
    private func getReviewCollectionLayoutSection() -> NSCollectionLayoutSection {
        let width = UIScreen.main.bounds.size.width - Layout.bannersSectionGroupWidthInset
        
        let itemSize = NSCollectionLayoutSize(
            widthDimension: .fractionalWidth(1),
            heightDimension: .fractionalHeight(1)
        )
        let item = NSCollectionLayoutItem(layoutSize: itemSize)
        
        let groupSize = NSCollectionLayoutSize(
            widthDimension: .absolute(width),
            heightDimension: .absolute(Layout.reviewGroupHeight)
        )
        
        let group = NSCollectionLayoutGroup.vertical(layoutSize: groupSize, repeatingSubitem: item, count: 1)
        group.contentInsets.trailing = 16
        
        let section = NSCollectionLayoutSection(group: group)
        section.orthogonalScrollingBehavior = .groupPaging
        section.contentInsets.bottom = Layout.reviewSectionContentInsetBottom
        return section
    }
    
    // MARK: - Constraints
    
    func setConstraints() {
     
        let imageViewConstraints = [
            imageView.topAnchor.constraint(equalTo: self.view.safeAreaLayoutGuide.topAnchor, constant: 4),
            imageView.leadingAnchor.constraint(equalTo: self.view.leadingAnchor),
            imageView.trailingAnchor.constraint(equalTo: self.view.trailingAnchor),
            imageView.heightAnchor.constraint(equalToConstant: Layout.bannerHeight)
        ]

        NSLayoutConstraint.activate(imageViewConstraints)
        
        let titleLabelConstraints = [
            titleLabel.topAnchor.constraint(equalTo: imageView.bottomAnchor, constant: 8),
            titleLabel.leadingAnchor.constraint(equalTo: self.view.leadingAnchor, constant: 16),
            titleLabel.trailingAnchor.constraint(equalTo: self.view.trailingAnchor, constant: -16)
        ]

        NSLayoutConstraint.activate(titleLabelConstraints)
        
        let popularityLabelConstraints = [
            popularityLabel.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 8),
            popularityLabel.leadingAnchor.constraint(equalTo: self.view.leadingAnchor, constant: 16),
            popularityLabel.trailingAnchor.constraint(equalTo: self.view.trailingAnchor, constant: -16)
        ]

        NSLayoutConstraint.activate(popularityLabelConstraints)
        
        let overviewLabelConstraints = [
            overviewLabel.topAnchor.constraint(equalTo: popularityLabel.bottomAnchor, constant: 8),
            overviewLabel.leadingAnchor.constraint(equalTo: self.view.leadingAnchor, constant: 16),
            overviewLabel.trailingAnchor.constraint(equalTo: self.view.trailingAnchor, constant: -16)
        ]

        NSLayoutConstraint.activate(overviewLabelConstraints)
        
        let collectionViewConstraints = [
            collectionView.topAnchor.constraint(equalTo: overviewLabel.bottomAnchor, constant: 16),
            collectionView.leadingAnchor.constraint(equalTo: self.view.leadingAnchor, constant: 8),
            collectionView.bottomAnchor.constraint(equalTo: self.view.safeAreaLayoutGuide.bottomAnchor, constant: 8),
            collectionView.trailingAnchor.constraint(equalTo: self.view.trailingAnchor)
        ]

        NSLayoutConstraint.activate(collectionViewConstraints)
        
    }
    
    // MARK: - Bind
    
    func bind() {
        viewModel.statePublisher.compactMap(\.movie).receive(on: DispatchQueue.main).sink(receiveValue: { [weak self] movie in
            guard let self else { return }
         
            self.titleLabel.text = movie.title
            let formattedText = String(format: "%.2f", movie.popularity)
            self.popularityLabel.text = "Popularity: \(formattedText)%"
            self.overviewLabel.text = movie.overview
            self.setImageView(nestedURL: movie.backdropPath)
          
        }).store(in: &cancellables)
        
        viewModel.statePublisher.compactMap(\.reviewList).receive(on: DispatchQueue.main).sink(receiveValue: { [weak self] _ in
            guard let self else { return }
         
            self.releadList()
            
        }).store(in: &cancellables)
        
        viewModel.statePublisher.compactMap(\.error).receive(on: DispatchQueue.main).sink(receiveValue: { [weak self] error in
            guard let self else { return }
         
            self.router?.showErrorAlert(message: error.localizedDescription)
            
        }).store(in: &cancellables)
        
    }
    
    func setImageView(nestedURL: String?) {
        if let nestedURL = nestedURL {
            do {
                let url = try URL.getFullPath(sizeType: .poster(.w780) ,nestedURLString: nestedURL)
                
                ImageLoader.shared.loadImage(from: url).sinkToResult { result in
                    
                    guard case .success(let image) = result else {
                        return
                    }
                    self.imageView.image = image
                    
                }.store(in: &self.cancellables)
            } catch {
                self.imageView.image = UIImage(named: "placeholder")
            }
        }
    }
    // MARK: - Actions
    
    func releadList(completion: (() -> Void)? = nil) {
        collectionView.reloadData()
        completion?()
    }
}

// MARK: - UICollectionViewDataSource

extension MovieDetailViewController: UICollectionViewDataSource {
   
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        self.sections.count
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        
        let section = self.sections[section]
        
        switch section {
        case .reviewSection:
            return self.viewModel.state.reviewList?.count ?? 0
        default:
            return 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let section = self.sections[indexPath.section]
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: self.sections[indexPath.section].cellReuseIdentifier, for: indexPath)
        
        switch section {
        case .reviewSection:
            
            guard let reviewList = self.viewModel.state.reviewList else { return UICollectionViewCell() }
            
            cell.contentConfiguration = ReviewView.Configuration(author: reviewList[indexPath.row].author, description: reviewList[indexPath.row].content)
        default:
            return UICollectionViewCell()
        }
        return cell
    }
}
