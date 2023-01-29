//
//  MovieListViewController.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.

import Combine
import Foundation
import SnapKit
import UIKit

fileprivate extension Layout {
    
    static let bannersSectionGroupWidthInset: CGFloat = 48
    static let contentScrollViewContentInsetBottom: CGFloat = 100
    static let topRatedSectionGroupHeight: CGFloat = 72
}

fileprivate extension PageSection {
    static let popularSection =  Self.init(id: "section-popular")
    static let topRatedSection = Self.init(id: "section-top-rated")
}

class MovieListViewController: UIViewController, BaseSceneViewController {
    
    // MARK: - Variables
    
    let router: MovieListRouterProtocol?
    let viewModel: any ViewModel<MovieList.State, MovieList.Action>
    var cancellables = Set<AnyCancellable>()

    var sections: [PageSection] {
        [ .popularSection,
          .topRatedSection
        ]
    }
    // MARK: - Initialization
    
    init(viewModel: any ViewModel<MovieList.State, MovieList.Action>, router: MovieListRouterProtocol?) {
        self.viewModel =  viewModel
        self.router = router
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError()
    }
    
    // MARK: - UI Components
    
    private lazy var collectionView: UICollectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: collectionViewLayout())
        
        collectionView.showsHorizontalScrollIndicator = false
        collectionView.showsVerticalScrollIndicator = false
        collectionView.semanticContentAttribute = .forceLeftToRight
        collectionView.backgroundColor = .white
        collectionView.contentInset.bottom = Layout.contentScrollViewContentInsetBottom
        collectionView.delegate = self
        collectionView.dataSource = self
        
        let cellReuseIdentifiers = [
            PageSection.topRatedSection.cellReuseIdentifier,
            PageSection.popularSection.cellReuseIdentifier
        ]
        
        for reuseIdentifier in cellReuseIdentifiers {
            collectionView.register(UICollectionViewCell.self, forCellWithReuseIdentifier: reuseIdentifier)
        }
        
        return collectionView
    }()
    
    // MARK: - Bind
    
    func bind() {
        viewModel.statePublisher.compactMap(\.popularList).receive(on: DispatchQueue.main).sink(receiveValue: { [weak self] _ in
            guard let self else { return }
         
            self.releadList()
            
        }).store(in: &cancellables)
        
        viewModel.statePublisher.compactMap(\.topRatedList).receive(on: DispatchQueue.main).sink(receiveValue: { [weak self] _ in
            guard let self else { return }
         
            self.releadList()
            
        }).store(in: &cancellables)
        
        viewModel.statePublisher.compactMap(\.error).receive(on: DispatchQueue.main).sink(receiveValue: { [weak self] error in
            guard let self else { return }
         
            self.router?.showErrorAlert(message: error.localizedDescription)
            
        }).store(in: &cancellables)
    }
    
    // MARK: - Prepare UI
    
    func prepareUI() {
        view.backgroundColor = .white
        
        navigationItem.leftBarButtonItem = UIBarButtonItem(image: UIImage(systemName: "star"), style: .plain, target: self, action: #selector(favoriteButtonTapped))
        // add subviews
        view.addSubview(collectionView)
        setConstraints()
    }
    
    // MARK: - Prepare Layout
    
    func collectionViewLayout() -> UICollectionViewLayout {
        UICollectionViewCompositionalLayout.init { [unowned self] sectionIndex, _ in
            
            let section =  self.sections[sectionIndex]
            switch section {
            case .popularSection:
                return getPopularCollectionLayoutSection()
            case .topRatedSection:
                return getTopRatedCollectionLayoutSection()
            default:
                fatalError("Invalid section: \(section)")
            }
        }
    }
    
    private func getPopularCollectionLayoutSection() -> NSCollectionLayoutSection {
        let width = UIScreen.main.bounds.size.width - Layout.bannersSectionGroupWidthInset
        
        let itemSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1), heightDimension: .fractionalHeight(1))
        
        let item = NSCollectionLayoutItem(layoutSize: itemSize)
        
        let groupSize = NSCollectionLayoutSize(widthDimension: .absolute(width), heightDimension: .absolute(220))
        
        let group = NSCollectionLayoutGroup.vertical(layoutSize: groupSize, repeatingSubitem: item, count: 1)
        group.contentInsets.trailing = 16
        
        let section = NSCollectionLayoutSection(group: group)
        section.orthogonalScrollingBehavior = .groupPagingCentered
        section.contentInsets.bottom = 40
        return section
    }
    
    private func getTopRatedCollectionLayoutSection() -> NSCollectionLayoutSection {

        let itemSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1), heightDimension: .absolute(130))
        
        let item = NSCollectionLayoutItem(layoutSize: itemSize)
        item.contentInsets = .init(top: 8, leading: 16, bottom: 8, trailing: 16)
        
        let groupSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1), heightDimension: .absolute(130))
    
        let group = NSCollectionLayoutGroup.horizontal(layoutSize: groupSize, repeatingSubitem: item, count: 1)
        let section = NSCollectionLayoutSection(group: group)

        return section
    }
    
    // MARK: - Constraints
    
    func setConstraints() {
        collectionView.snp.makeConstraints { make in
            make.top.equalTo(self.view.safeAreaLayoutGuide.snp.top)
            make.leading.trailing.equalToSuperview()
            make.bottom.equalTo(self.view.safeAreaLayoutGuide.snp.bottom)
        }
    }
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        prepareUI()
        
        bind()
        
        viewModel.handle(action: .fetchTopRatedMovies)
        viewModel.handle(action: .fetchPopularMovies)
    }
    
    // MARK: - Actions
    
    func releadList(completion: (() -> Void)? = nil) {
        collectionView.reloadData()
        completion?()
    }
    
    @objc func favoriteButtonTapped() {
        self.router?.routeToFavoriteList()
    }
}

// MARK: - UICollectionViewDelegate

extension MovieListViewController: UICollectionViewDelegate {
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
     
        let section = self.sections[indexPath.section]
        
        switch section {
        case .popularSection:
           
            guard let movie = self.viewModel.state.popularList?[indexPath.row] else { return }
            self.router?.routeToDetail(movieID: movie.id)

        case .topRatedSection:
            
            guard let movie = self.viewModel.state.topRatedList?[indexPath.row] else { return }
            self.router?.routeToDetail(movieID: movie.id)
            
        default:
            return
        }
    }
}

// MARK: - UICollectionViewDataSource

extension MovieListViewController: UICollectionViewDataSource {
   
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        self.sections.count
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        
        let section = self.sections[section]
        
        switch section {
        case .popularSection:
            return self.viewModel.state.popularList?.count ?? 0
        case .topRatedSection:
            return self.viewModel.state.topRatedList?.count ?? 0
        default:
            return 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let section = self.sections[indexPath.section]
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: self.sections[indexPath.section].cellReuseIdentifier, for: indexPath)
        
        switch section {
        case .popularSection:
            guard let movieList = self.viewModel.state.popularList else { return UICollectionViewCell() }
            
            cell.contentConfiguration = MovieBannerView.Configuration(title: movieList[indexPath.row].title, popularityRate: movieList[indexPath.row].popularity, nestedURLString: movieList[indexPath.row].posterPath)
        case .topRatedSection:
            guard let movieList = self.viewModel.state.topRatedList else { return UICollectionViewCell() }
            
            cell.contentConfiguration = MovieTopRatedView.Configuration(title: movieList[indexPath.row].title, voteRate: movieList[indexPath.row].voteAverage, nestedURLString: movieList[indexPath.row].backdropPath)
        default:
            return UICollectionViewCell()
        }
        return cell
    }
}
