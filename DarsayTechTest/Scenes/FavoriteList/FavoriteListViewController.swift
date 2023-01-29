//
//  FavoriteListViewController.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/9/1401 AP.

import Combine
import Foundation
import UIKit

fileprivate extension Layout {
    static let contentScrollViewContentInsetBottom: CGFloat = 100
    static let favoriteGroupHeight: CGFloat = 150
}

fileprivate extension PageSection {
    static let favoriteSection =  Self.init(id: "section-favorite")
}

class FavoriteListViewController: UIViewController, BaseSceneViewController {
    
    // MARK: - Variables
    
    private var router: FavoriteListRouterProtocol?
    private var viewModel: any ViewModel<FavoriteList.State, FavoriteList.Action>
    var cancellables = Set<AnyCancellable>()

    var sections: [PageSection] {
        [ .favoriteSection ]
    }
    // MARK: - Initialization
    
    init(viewModel: any ViewModel<FavoriteList.State, FavoriteList.Action>, router: FavoriteListRouterProtocol?) {
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
        collectionView.backgroundColor = .background
        collectionView.contentInset.bottom = Layout.contentScrollViewContentInsetBottom
        collectionView.delegate = self
        collectionView.dataSource = self
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        
        let cellReuseIdentifiers = [
            PageSection.favoriteSection.cellReuseIdentifier ]
        
        for reuseIdentifier in cellReuseIdentifiers {
            collectionView.register(UICollectionViewCell.self, forCellWithReuseIdentifier: reuseIdentifier)
        }
        
        return collectionView
    }()
    
    // MARK: - Prepare Layout
    
    func collectionViewLayout() -> UICollectionViewLayout {
        UICollectionViewCompositionalLayout.init { [unowned self] sectionIndex, _ in
            
            let section =  self.sections[sectionIndex]
            switch section {
            case .favoriteSection:
                return getFavoriteCollectionLayoutSection()
           
            default:
                fatalError("Invalid section: \(section)")
            }
        }
    }
    
    private func getFavoriteCollectionLayoutSection() -> NSCollectionLayoutSection {

        let itemSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1), heightDimension: .fractionalHeight(1))
        
        let item = NSCollectionLayoutItem(layoutSize: itemSize)
        item.contentInsets = .init(top: 8, leading: 16, bottom: 8, trailing: 16)
        
        let groupSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1), heightDimension: .absolute(Layout.favoriteGroupHeight))
    
        let group = NSCollectionLayoutGroup.horizontal(layoutSize: groupSize, repeatingSubitem: item, count: 1)
        let section = NSCollectionLayoutSection(group: group)

        return section
    }
    
    // MARK: - Bind
    
    func bind() {
        viewModel.statePublisher.compactMap(\.favoriteList).receive(on: DispatchQueue.main).sink(receiveValue: { [weak self] _ in
            guard let self else { return }
         
            self.releadList()
            
        }).store(in: &cancellables)
    }
    
    // MARK: - Prepare UI
    
    func prepareUI() {
        
        view.backgroundColor = .background
        title = LocalizeHelper.shared.lookup(.favoriteListTitle)
        // add subviews
        view.addSubview(collectionView)
        setConstraints()
    }
    
    // MARK: - Constraints
    
    func setConstraints() {
        
        let constraints = [
            collectionView.topAnchor.constraint(equalTo: self.view.safeAreaLayoutGuide.topAnchor),
            collectionView.leadingAnchor.constraint(equalTo: self.view.leadingAnchor),
            collectionView.bottomAnchor.constraint(equalTo: self.view.safeAreaLayoutGuide.bottomAnchor),
            collectionView.trailingAnchor.constraint(equalTo: self.view.trailingAnchor)
        ]

        NSLayoutConstraint.activate(constraints)
    }
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        prepareUI()
        
        bind()
        
        viewModel.handle(action: .fetchFavoriteMovies)
    }
    
    // MARK: - Actions
    
    func releadList(completion: (() -> Void)? = nil) {
        collectionView.reloadData()
        completion?()
    }
}

// MARK: - UICollectionViewDelegate

extension FavoriteListViewController: UICollectionViewDelegate {
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
     
        let section = self.sections[indexPath.section]
        switch section {
        case .favoriteSection:
            guard let movie = self.viewModel.state.favoriteList?[indexPath.row] else { return }
            self.router?.routeToDetail(movieID: movie.id)
        default:
            return
        }
    }
}

// MARK: - UICollectionViewDataSource

extension FavoriteListViewController: UICollectionViewDataSource {
   
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        self.sections.count
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        
        let section = self.sections[section]
        
        switch section {
        case .favoriteSection:
            return self.viewModel.state.favoriteList?.count ?? 0
        default:
            return 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let section = self.sections[indexPath.section]
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: self.sections[indexPath.section].cellReuseIdentifier, for: indexPath)
        
        switch section {
        case .favoriteSection:
            guard let movieList = self.viewModel.state.favoriteList else { return UICollectionViewCell() }
            
            cell.contentConfiguration = MovieBannerView.Configuration(movie: movieList[indexPath.row], hasFavoriteButton: false)
        default:
            return UICollectionViewCell()
        }
        return cell
    }
}
