//
//  ViewController.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import UIKit

class MovieVC: UIViewController {
    
    //MARK: Properties
    var collectionView:  UICollectionView!
    var dataSource: UICollectionViewDiffableDataSource<Section, Movie>?
    var sections: [Section] = [] {
        didSet {
            self.reloadData()
        }
    }

    //MARK: Lifecycle
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .green
        print("DEBUG: sections ARE \(sections)")
        getMovies(from: .popular)
        getMovies(from: .topRated)
        cofigureCollectionView()
        createDataSource()
        reloadData()
            }
    
    //MARK: Helpers
    
    func cofigureCollectionView() {
        collectionView = UICollectionView(frame: view.bounds, collectionViewLayout: createCompositionalLayout())
        collectionView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        collectionView.backgroundColor = .black
        view.addSubview(collectionView)
        
        collectionView.register(PopularMovieCell.self, forCellWithReuseIdentifier: PopularMovieCell.reuseID)
        collectionView.register(TopRatedCell.self, forCellWithReuseIdentifier: TopRatedCell.reuseID)
    }
    
    func configure<T: SelfConfiguringCell>(_ cellType: T.Type, with viewmodel: MovieViewModel, for indexPath: IndexPath) -> T {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: cellType.reuseID, for: indexPath) as? T else {
           fatalError("Unable to dequeue \(cellType)")
        }
        cell.configure(with: viewmodel)
        return cell
    }
    
    func createDataSource() {
        dataSource = UICollectionViewDiffableDataSource<Section, Movie>(collectionView: collectionView, cellProvider: { collectionView, indexPath, movie in
            let viewModel = MovieViewModel(movie: movie)
            switch self.sections[indexPath.section].title {
            case "Popular": return self.configure(PopularMovieCell.self, with: viewModel, for: indexPath)
            case "Top Rated": return self.configure(TopRatedCell.self, with: viewModel, for: indexPath)
            default: return self.configure(PopularMovieCell.self, with: viewModel, for: indexPath)
            }
        })
    }
    
    func reloadData() {
        var snapshot = NSDiffableDataSourceSnapshot<Section, Movie>()
        snapshot.appendSections(sections)
        for section in sections {
            snapshot.appendItems(section.movies, toSection: section)
        }
        dataSource?.apply(snapshot)
    }

    
    func createPopularMovieSection(using: Section) -> NSCollectionLayoutSection {
        let itemSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1), heightDimension: .fractionalHeight(1))
        let layoutItem = NSCollectionLayoutItem(layoutSize: itemSize)
        layoutItem.contentInsets = NSDirectionalEdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 10)
        
        let layoutGroupSize = NSCollectionLayoutSize(widthDimension: .estimated(UIScreen.main.bounds.width * 0.47), heightDimension: .estimated(UIScreen.main.bounds.height * 0.33))
        let layoutGroup = NSCollectionLayoutGroup.horizontal(layoutSize: layoutGroupSize, subitems: [layoutItem])
        
        let layoutSection = NSCollectionLayoutSection(group: layoutGroup)
        layoutSection.orthogonalScrollingBehavior = .continuous
        
        return layoutSection
    }
    
    
    func createTopRatedMoviesSection(using: Section) -> NSCollectionLayoutSection {
        let itemSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1), heightDimension: .fractionalHeight(1))
        let layoutItem = NSCollectionLayoutItem(layoutSize: itemSize)
        layoutItem.contentInsets = NSDirectionalEdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 10)
        
        let layoutGroupSize = NSCollectionLayoutSize(widthDimension: .estimated(UIScreen.main.bounds.width * 0.37), heightDimension: .estimated(UIScreen.main.bounds.height * 0.3))
        let layoutGroup = NSCollectionLayoutGroup.horizontal(layoutSize: layoutGroupSize, subitems: [layoutItem])
        
        let layoutSection = NSCollectionLayoutSection(group: layoutGroup)
        layoutSection.orthogonalScrollingBehavior = .continuous
        
        return layoutSection
    }
    
    func createCompositionalLayout() -> UICollectionViewLayout {
        let layout = UICollectionViewCompositionalLayout {
                sectionIndex, layoutEnvironment in
            let section = self.sections[sectionIndex]
            
            switch section.title {
            case "Popular": return self.createPopularMovieSection(using: section)
            case "Top Rated": return self.createTopRatedMoviesSection(using: section)
            default: return self.createPopularMovieSection(using: section)
            }
        }
        let config = UICollectionViewCompositionalLayoutConfiguration()
        config.interSectionSpacing = 20
        layout.configuration = config
        return layout
    }
 
    func getMovies(from endpoint: MovieEndpoing) {
        NetworkManager.shared.fetchMovies(from: endpoint) { [weak self] result  in
            guard let self = self else { return }
            switch result {
            case .success(let movieResponse):
                let movies = movieResponse.results
                switch endpoint {
                case .popular:
                    self.sections.append(Section(title: "Popular", movies: movies))
                case .topRated:
                    self.sections.append(Section(title: "Top Rated", movies: movies))
                }
                print("DEBUG: \(movies)")
            case .failure(let err):
                print("DEBUG: error \(err.localizedDescription)")
            }
        }
    }
}

