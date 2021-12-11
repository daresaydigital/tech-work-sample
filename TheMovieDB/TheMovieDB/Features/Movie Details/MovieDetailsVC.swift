//
//  MovieDetailsVC.swift
//  TheMovieDB
//
//  Created by Ali Sani on 12/11/21.
//

import UIKit

class MovieDetailsVC: UIViewController {

    @IBOutlet weak var moviesTableView: UITableView!
    @IBOutlet weak var loadingView: LoadingView!
    
    var viewModel: MovieDetailsViewModel!

    private var loading = UIActivityIndicatorView(style: .large)

    override func viewDidLoad() {
        
        super.viewDidLoad()
        // tableview
        moviesTableView.dataSource = self
        
        registerMovieCell()
        bind()
        viewModel.viewDidLoad()
    }
    
    func registerMovieCell() {
        //FIXME: use R library
        let movieDetailsCell = UINib(nibName: "MovieDetailsTableViewCell", bundle: nil)
        self.moviesTableView.register(movieDetailsCell, forCellReuseIdentifier: MovieDetailsTableViewCell.identifier)
    }

    func bind() {
        viewModel.onShouldReloadTableView = {
            self.moviesTableView.reloadData()
        }
        
        viewModel.onLoadingStateShouldChange = { [weak self] state in
            switch state {
            case .loading:
                self?.loadingView.unHide()
            case .loaded:
                self?.loadingView.hide()
            case .failed(let error):
                self?.loadingView.hide()
                self?.showErrorIfDisplayable(error: error)
            }
        }
    }
}

extension MovieDetailsVC : UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return viewModel.numberOfRows
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        guard let cell: MovieDetailsTableViewCell = tableView.dequeueReusableCell(withIdentifier: MovieDetailsTableViewCell.identifier, for: indexPath) as? MovieDetailsTableViewCell else {
            let cell: UITableViewCell = UITableViewCell()
            return cell
        }
        
        guard let cellData = viewModel.getCellData(for: indexPath) else {
            return cell
        }
        cell.configure(movieDatails: cellData)
        
        cell.selectionStyle = .none
        return cell
    }
}
