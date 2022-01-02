//
//  MovieListViewController.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import UIKit

protocol MovieListViewControllerProtocol: AnyObject {
    func updateView()
}

class MovieListViewController: UIViewController {
    var viewModel: MovieListViewModelProtocol
    private let cellReuseIdentifier = "MovieTableViewCell"
    
    public init() {
        // sub-class needs to override this variable
        viewModel = DummyMovieListViewModel()
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupBackgroundView()
        viewModel.loadViewInitialData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        navigationController?.setNavigationBarHidden(true, animated: false)
        super.viewWillAppear(animated)
    }
    
    private func setupBackgroundView() {
        view.backgroundColor = .white
    }
    
    private lazy var homeTabView: UIView = {
        let homeTabView = UIView.init(frame: view.frame)
        homeTabView.translatesAutoresizingMaskIntoConstraints = false
        homeTabView.backgroundColor = .clear
        view.addSubview(homeTabView)
        homeTabView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor).isActive = true
        homeTabView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        homeTabView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        homeTabView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor).isActive = true
        return homeTabView
    }()
    
    private lazy var tableView: UITableView = {
        let tableView = UITableView(frame: self.view.frame, style: .plain)
        tableView.translatesAutoresizingMaskIntoConstraints = false
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = .clear
        tableView.separatorStyle = .none
        tableView.register(MovieTableViewCell.self, forCellReuseIdentifier: cellReuseIdentifier)
        self.homeTabView.addSubview(tableView)
        tableView.topAnchor.constraint(equalTo: self.homeTabView.topAnchor).isActive = true
        tableView.leadingAnchor.constraint(equalTo: self.homeTabView.leadingAnchor).isActive = true
        tableView.bottomAnchor.constraint(equalTo: self.homeTabView.bottomAnchor).isActive = true
        tableView.trailingAnchor.constraint(equalTo: self.homeTabView.trailingAnchor).isActive = true
        return tableView
    }()
}

extension MovieListViewController: UITableViewDelegate, UITableViewDataSource, MovieListViewControllerProtocol {
    func updateView() {
        tableView.reloadData()
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.moviesCount()
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: cellReuseIdentifier) as? MovieTableViewCell else {
            return UITableViewCell()
        }
        
        viewModel.checkAndHandleIfPaginationRequired(at: indexPath.row)
        
        if let movieModel = viewModel.movieInfoModel(at: indexPath.row) {
            cell.configure(with: movieModel)
            return cell
        }
        return UITableViewCell()
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        // TODO: should go to movie detail scene
    }
}
