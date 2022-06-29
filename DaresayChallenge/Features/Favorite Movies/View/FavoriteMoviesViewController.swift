//
//  FavoriteMoviesViewController.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-29.
//

import UIKit

final class FavoriteMoviesViewController: UIViewController {

    // MARK: - Variables
    private let viewModel: FavoriteMoviesViewModel = FavoriteMoviesViewModel()
    private var dataSourceProvider: TableViewDataSourceProvider!
    
    private lazy var tableView: UITableView = {
        let tableView = UITableView()
        tableView.translatesAutoresizingMaskIntoConstraints = false
        return tableView
    }()
    
    // MARK: - View Lifecycle
    override func viewDidLoad() {
        super.viewDidLoad()

        setupUI()
    }
}

// MARK: - Helpers
private extension FavoriteMoviesViewController {
    func setupUI() {
        
        view.backgroundColor = .systemBackground
        navigationItem.title = "Favorites"
        
        dataSourceProvider = TableViewDataSourceProvider(tableView: tableView, viewModel: viewModel)
        
        tableView.delegate = dataSourceProvider
        tableView.dataSource = dataSourceProvider
        
        view.addSubview(tableView)
        
        tableView.leftAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leftAnchor).isActive = true
        tableView.rightAnchor.constraint(equalTo: view.safeAreaLayoutGuide.rightAnchor).isActive = true
        tableView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor).isActive = true
        tableView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor).isActive = true
        
        dataSourceProvider.append(viewModel.favoriteMovies)
    }
}

extension FavoriteMoviesViewController: ReloadFavoritesDelegate {
    func refresh() {
        dataSourceProvider.refresh()
    }
}
