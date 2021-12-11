//
//  MovieListVC.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import UIKit

class MovieListVC: UIViewController {

    @IBOutlet weak var moviesTableView: UITableView!
    @IBOutlet weak var filterSegment: UISegmentedControl!

    var viewModel: MoviesListViewModel!

    override func viewDidLoad() {
        
        super.viewDidLoad()
        
        // tableview
        moviesTableView.dataSource = self

        viewModel = MoviesListViewModel(movieListServices: MovieListAPIServiceImpl(httpRequest: HTTPRequestImpl()))
        registerMovieCell()
        bind()
        viewModel.viewDidLoad()
    }
    
    func registerMovieCell() {
        let movieCell = UINib(nibName: "MovieTableViewCell", bundle: nil)
        self.moviesTableView.register(movieCell, forCellReuseIdentifier: MovieTableViewCell.identifier)
    }
    
    func bind() {
        viewModel.onShouldReloadTableView = { [weak self] in
            self?.moviesTableView.reloadData()
        }
    }
    
    @IBAction func filterSegmentChanged(_ sender: Any) {
        
        viewModel.movieSegmentDidChange(selectedSegmentIndex: self.filterSegment.selectedSegmentIndex)
    }
    
}

//MARK: - Tableview DataSource
extension MovieListVC : UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.numberOfRows
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        guard let cell:MovieTableViewCell = tableView.dequeueReusableCell(withIdentifier: MovieTableViewCell.identifier,
                                                       for: indexPath) as? MovieTableViewCell else {
            let cell: UITableViewCell = UITableViewCell()
            return cell
        }

        guard let cellData = viewModel.getCellData(for: indexPath) else {
            return cell
        }
        cell.configure(movieData: cellData)
        
        cell.selectionStyle = .none
        return cell
    }
}

//MARK: - UIScrollViewDelegate
extension MovieListVC : UIScrollViewDelegate {
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        let currentOffset = scrollView.contentOffset.y
        let maximumOffset = scrollView.contentSize.height - scrollView.frame.size.height

        if maximumOffset - currentOffset <= 20.0 {
            viewModel.listReachedBottom()
        }
    }
}
