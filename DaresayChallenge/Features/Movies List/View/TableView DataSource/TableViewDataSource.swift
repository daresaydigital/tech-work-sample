//
//  TableViewDataSource.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-28.
//

import UIKit

final class TableViewDataSourceProvider: NSObject {
    
    // MARK: - Variables
    private var tableView: UITableView
    private var viewModel: ListViewModelable
    
    public var didSelectItem: ((_ item: MoviesModel) -> Void)?
    
    // Reuse identifier
    private let movieCellID = String(describing: MovieTableViewCell.self)
    
    // MARK: - Init
    init(tableView: UITableView, viewModel: ListViewModelable) {
        self.tableView = tableView
        self.viewModel = viewModel
        super.init()
        
        setupTableView()
    }
    
    // MARK: - Public methods
    public func append(_ newItems: [MoviesModel]) {
        guard !newItems.isEmpty else {
            print("WARNING: YOU ARE ADDING EMPTY LIST TO TABLE VIEW")
            return
        }
        
        guard viewModel.totalCount != viewModel.itemsCount else { return }
        
        if viewModel.totalCount == 0 {
            viewModel.totalCount = viewModel.itemsCount
            tableView.reloadData()
        } else {
            let startIndex = viewModel.totalCount
            let endIndex = viewModel.itemsCount
            let indexPathsToAdd = (startIndex..<endIndex).map {IndexPath(row: $0, section: 0)} as [IndexPath]
            viewModel.totalCount = viewModel.itemsCount
            
            self.tableView.performBatchUpdates({
                self.tableView.insertRows(at: indexPathsToAdd, with: .none)
            }, completion: nil)
            
        }
    }
    
    public func refresh() {
        tableView.reloadData()
    }
}

// MARK: - UITableView Delegate
extension TableViewDataSourceProvider: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let item = viewModel.item(at: indexPath.row)
        didSelectItem?(item)
        
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

// MARK: - UITableView DataSource
extension TableViewDataSourceProvider: UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        viewModel.itemsCount
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = generateCell(MovieTableViewCell.self, indexPath: indexPath)
        
        let item = viewModel.item(at: indexPath.row)
        
        let favoriteMovies = UserDefaultsData.favoriteList
        
        var isFavorite = false
        for movie in favoriteMovies {
            if movie.movieID == item.movieID {
                isFavorite = true
                item.isFavorite = true 
                break
            }
        }
        
        cell.configureCell(with: item, isFavorite: isFavorite)
        
        return cell
    }
}

// MARK: - UITableView DataSource Prefetching
extension TableViewDataSourceProvider: UITableViewDataSourcePrefetching {
    func tableView(_ tableView: UITableView, prefetchRowsAt indexPaths: [IndexPath]) {
        if indexPaths.contains(where: viewModel.isLoadingCell) && !viewModel.isFinished {
            print("==========get new data======")
            viewModel.prefetchData()
        }
    }
}

// MARK: - Helpers
private extension TableViewDataSourceProvider {
    func generateCell<T: UITableViewCell>(_ cell: T.Type, indexPath: IndexPath) -> T {
        let identifier = String(describing: T.self)
        guard let cell = tableView.dequeueReusableCell(withIdentifier: identifier, for: indexPath) as? T else {
            return T()
        }
        
        return cell
    }
    
    func setupTableView() {
        tableView.register(MovieTableViewCell.self, forCellReuseIdentifier: movieCellID)
        tableView.tableFooterView = UIView(frame: .zero)
        tableView.estimatedRowHeight = 60
        tableView.rowHeight = UITableView.automaticDimension
    }
}
