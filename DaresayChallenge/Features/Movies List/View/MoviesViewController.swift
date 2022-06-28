//
//  MoviesViewController.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import UIKit

class MoviesViewController: UIViewController {

    // MARK: - Variables
    private let viewModel: MoviesViewModel = MoviesViewModel(moviesService: MoviesService.shared)
    
    private lazy var tableView: UITableView = {
        let tableView = UITableView()
        tableView.translatesAutoresizingMaskIntoConstraints = false
        return tableView
    }()
    
    private var dataSourceProvider: TableViewDataSourceProvider!
    
    // MARK: - View Lifecycle
    override func viewDidLoad() {
        super.viewDidLoad()

        setupUI()
        setupBindings()
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            self.populate()
        }
    }
}

// MARK: - Helpers
private extension MoviesViewController {
    func setupUI() {
        view.backgroundColor = .systemBackground
    }
    
    func populate() {
        viewModel.populate()
    }
    
    func setupBindings() {
        viewModel.delegate = self
    }
    
    func setupTableView() {
        dataSourceProvider = TableViewDataSourceProvider(tableView: tableView, viewModel: viewModel)
        
        tableView.delegate = dataSourceProvider
        tableView.dataSource = dataSourceProvider
        tableView.prefetchDataSource = dataSourceProvider
        
        view.addSubview(tableView)
        
        tableView.leftAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leftAnchor).isActive = true
        tableView.rightAnchor.constraint(equalTo: view.safeAreaLayoutGuide.rightAnchor).isActive = true
        tableView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor).isActive = true
        tableView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor).isActive = true
    }
}

// MARK: -
extension MoviesViewController: MoviesViewModelDelegate {
    func populate(displayState: DisplayState<[MoviesModel]>) {
        switch displayState {
        case .loading:
            view.animateActivityIndicator()
        case .success(let results):
            print(results)
            setupTableView()
            dataSourceProvider.append(results)
            view.removeActivityIndicator()
        case .failure:
            view.removeActivityIndicator()
        }
    }
    
    func displayMovies(displayState: DisplayState<[MoviesModel]>) {
        switch displayState {
        case .success(let results):
            dataSourceProvider.append(results)
        default:
            break
        }
    }
}
