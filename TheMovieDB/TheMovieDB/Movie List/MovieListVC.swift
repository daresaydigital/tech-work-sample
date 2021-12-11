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
        bind()
        viewModel.viewDidLoad()
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
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell: UITableViewCell = UITableViewCell()

        return cell
    }
}

