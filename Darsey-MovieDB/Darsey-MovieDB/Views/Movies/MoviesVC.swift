//
//  MoviesVC.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 09/03/2021.
//

import UIKit

class MoviesVC: UIViewController {

    //MARK:- Outlets
    @IBOutlet weak var collectionView: UICollectionView!

    //MARK: - Properties
    
    var refreshController: UIRefreshControl = UIRefreshControl()
    var movieViewModelList = [MoviesViewModel]()
    var movieType = MovieType.nowPlaying
    
    //MARK:- LifeCycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        setupVC()
        setupPullToRefresh()
    }
}

//MARK: VC Extension, Methods and Actions

extension MoviesVC {
    private func setupVC(){
        
        collectionView.register(UINib(nibName: MoviesHeader.reuseId, bundle: nil),
                                forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader,
                                withReuseIdentifier: MoviesHeader.reuseId)
        
        collectionView.register(UINib(nibName: MovieCell.resuseId , bundle: nil),
                                forCellWithReuseIdentifier: MovieCell.resuseId)
        
        collectionView.delegate = self
        collectionView.dataSource = self
        collectionView.contentInsetAdjustmentBehavior = .never
        
        MoviesHeader.languageDelegate = self
        MoviesHeader.movieSegmentDelegate = self
        
        self.navigationController?.navigationBar.isHidden = true
        
        if UIDevice.current.userInterfaceIdiom == .pad {
            let orientation = UIInterfaceOrientation.landscapeLeft.rawValue
            UIDevice.current.setValue(orientation, forKey: Keys.ORIENTATION )
        } else {
            let orientation = UIInterfaceOrientation.portrait.rawValue
            UIDevice.current.setValue(orientation, forKey: Keys.ORIENTATION )
        }
    }
    
    private func setupPullToRefresh(){
        refreshController.bounds = CGRect(x: 0, y: 50,
                                          width: refreshController.bounds.size.width,
                                          height: refreshController.bounds.size.height)
        refreshController.tintColor = UIColor(red: 1, green: 165/255, blue: 0, alpha: 1)
        refreshController.addTarget(self, action: #selector(refreshMovies),
                                    for: UIControl.Event.valueChanged)
        refreshController.attributedTitle = NSAttributedString(string: "refresh.catalog".localized,
                                                               attributes: [NSAttributedString.Key.foregroundColor: UIColor.label, NSAttributedString.Key.font: UIFont.boldSystemFont(ofSize: 15)])
//        refreshController.tintColor = .label
        collectionView.refreshControl = refreshController
    }
    
    @objc func refreshMovies(sender:AnyObject) {
        loadMovies(movieType: movieType)
        refreshController.endRefreshing()
    }
    
    private func loadMovies(movieType: MovieType){
        guard let currentLanguage = UserDefaults.standard.string(forKey: Keys.APP_LANGUAGE)  else { return }
        
        showActivityIndicator()
        MovieService.init().getMovies(movieType: movieType, language: currentLanguage) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .success (let movies):
                self.movieViewModelList = movies.map( { return MoviesViewModel($0)})
                DispatchQueue.main.async {
                    self.hideActivityIndicator()
                    self.collectionView.reloadData()
                }
            case .failure (let error):
                DispatchQueue.main.async {
                    self.hideActivityIndicator()
                    self.showError(error: error.localizedDescription)
                }
            }
        }
    }
    
    func showError(error: String){
        let alert = UIAlertController(title: "error".localized,
                                      message: error,
                                      preferredStyle: .alert)
        
        alert.addAction(UIAlertAction(title: "ok".localized,
                                      style: .default,
                                      handler: nil ))
        
        self.present(alert, animated: true, completion: nil)
    }
}


//MARK: - CollectionView Delegate & DataSource
extension MoviesVC: UICollectionViewDataSource, UICollectionViewDelegate {
    
    func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
        let header = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: MoviesHeader.reuseId, for: indexPath) as! MoviesHeader
        return header
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if !movieViewModelList.isEmpty{
            return movieViewModelList.count
        }
        return 0
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: MovieCell.resuseId, for: indexPath) as! MovieCell
        
        cell.movieViewModel = movieViewModelList[indexPath.row]
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        collectionView.deselectItem(at: indexPath, animated: false)
        
        if !movieViewModelList.isEmpty{
            let selectedMovieVC = SelectedMovieVC(nibName: SelectedMovieVC.reuseId, bundle: nil)
            selectedMovieVC.movieId = movieViewModelList[indexPath.row].id
            selectedMovieVC.backdropUrl = movieViewModelList[indexPath.row].backdropURL
            self.present(selectedMovieVC, animated: true, completion: nil)
        }
    }
}

//MARK: - CollectionView FlowLayout
extension MoviesVC : UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: 0 , left: 4 , bottom: 0, right: 4)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        var cellWidth = collectionView.bounds.width
        var cellHeight: CGFloat = 0
        
        if UIDevice.current.userInterfaceIdiom == .pad {
            cellHeight = 320
            switch UIDevice.current.orientation{
            case .portrait:
                cellWidth = cellWidth / 3 - 10
            case .landscapeLeft, .landscapeRight :
                cellWidth = cellWidth / 4 - 10
            default:
                cellWidth = cellWidth / 3 - 10
            }
        } else {
            cellHeight = 220
            switch UIDevice.current.orientation{
            case .portrait:
                cellWidth = cellWidth / 2 - 10
            case .landscapeLeft, .landscapeRight :
                cellHeight = 270
                cellWidth = cellWidth / 3 - 10
            default:
                cellWidth = cellWidth / 2 - 10
            }
        }
        
        return CGSize(width: cellWidth , height: cellHeight)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, referenceSizeForHeaderInSection section: Int) -> CGSize {
        return CGSize(width: view.frame.width, height: 90)
    }
}


//MARK:- Language Delegate

extension MoviesVC: LanguageDelegate {
    
    func chooseLanguage() {
        let alert = UIAlertController(title: "change.language".localized,
                                      message: "choose.language".localized,
                                      preferredStyle: .alert)
        
        alert.addAction(UIAlertAction(title: "english.language".localized,
                                      style: .default,
                                      handler: { (_) in
                                        UserDefaults.standard.set("en", forKey: Keys.APP_LANGUAGE)
                                        self.resetCollectionView()
                                      }))
        
        alert.addAction(UIAlertAction(title: "swedish.language".localized,
                                      style: .default,
                                      handler: { (_) in
                                        UserDefaults.standard.set("sv", forKey: Keys.APP_LANGUAGE)
                                        self.resetCollectionView()
                                      }))
        
        alert.addAction(UIAlertAction(title: "spanish.language".localized,
                                      style: .default,
                                      handler: { (_) in
                                        UserDefaults.standard.set("es", forKey: Keys.APP_LANGUAGE)
                                        self.resetCollectionView()
                                      }))
        
        alert.addAction(UIAlertAction(title: "cancel".localized,
                                      style: .destructive,
                                      handler: nil))
        
        self.present(alert, animated: true, completion: nil)
    }
    
    func resetCollectionView(){
        loadMovies(movieType: movieType)
        self.viewWillAppear(true)
        self.collectionView.setContentOffset(CGPoint(x: 0, y: 100), animated: false)
        self.collectionView.setContentOffset(CGPoint(x: 0, y: 0), animated: true)
    }
    
}

extension MoviesVC: MovieSegmentDelegate {
    func loadMovie(movieType: MovieType) {
        self.movieType = movieType
        loadMovies(movieType: movieType)
    }
}
