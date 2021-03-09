//
//  SelectedMovieVC.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 09/03/2021.
//

import UIKit

class SelectedMovieVC: UIViewController {
    
    //MARK:- Outlets
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var posterImageView: UIImageView!
    @IBOutlet weak var yearLabel: UILabel!
    @IBOutlet weak var runtimeLabel: UILabel!
    @IBOutlet weak var scoreLabel: UILabel!
    @IBOutlet weak var genresLabel: UILabel!
    @IBOutlet weak var summaryView: UIView!
    @IBOutlet weak var summaryTextView: UITextView!
    @IBOutlet weak var closeButtonView: UIView!
    @IBOutlet weak var playTrailerView: UIView!
    @IBOutlet weak var playTrailerLabel: UILabel!
    @IBOutlet weak var posterLeadingConstraint: NSLayoutConstraint!
    @IBOutlet weak var posterTrailingConstraint: NSLayoutConstraint!
    @IBOutlet weak var trailerViewConstraint: NSLayoutConstraint!
    
    //MARK: - Properties
    static let reuseId = "SelectedMovieVC"
    var movieId: Int?
    var viewModel: SelectedMovieViewModel?
    var backdropUrl: String?
    var trailerId: String?

    //MARK:- LifeCycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        posterImageView.layer.cornerRadius = 7
        setupVC()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        loadMovieDetails()
    }
}

//MARK: VC Extension, Methods and Actions
extension SelectedMovieVC {
    
    private func setupVC() {
        ViewProperties.configureCircularViewWithShadow(backgroundView: closeButtonView)
        ViewProperties.configureViewWithRoundBorderAndShadow(backgroundView: playTrailerView)
        if UIDevice.current.userInterfaceIdiom == .pad {
            summaryTextView.font = .systemFont(ofSize: 20)
        }
        posterImageView.contentMode = .scaleAspectFill
    }
    
    private func loadMovieDetails(){
        guard let movieId = movieId else { return }
        guard let currentLanguage = UserDefaults.standard.string(forKey: Keys.APP_LANGUAGE)  else { return }
        showActivityIndicator()
        
        MovieService.shared.getSelectedMovie(movieId: movieId, language: currentLanguage) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .success(let movieDetails):
                DispatchQueue.main.async {
                    self.viewModel = SelectedMovieViewModel(movieDetails)
                    
                    self.titleLabel.text = self.viewModel?.title
                    self.runtimeLabel.text = self.viewModel?.runtimeInMinutes
                    self.yearLabel.text = self.viewModel?.yearOfRelease
                    self.scoreLabel.text = self.viewModel?.score
                    self.summaryTextView.text = self.viewModel?.overview
                    self.genresLabel.text = self.viewModel?.allGenres
                }
            case .failure:
                self.hideActivityIndicator()
            }
        }
        
        guard let posterURL = backdropUrl else { return }
        MovieService.shared.getImage(from: posterURL, completed: { image in
            DispatchQueue.main.async {
                self.hideActivityIndicator()
                self.posterImageView.image = image != nil ? image : Images.emptyImage
            }
        })
        
        MovieService.shared.getTrailers(movieId: movieId, language: currentLanguage) { [weak self] response in
            guard let self = self else { return }
            switch response {
            case .success(let movieTrailers):
                DispatchQueue.main.async {
                    guard ((movieTrailers.first?.key) != nil) else { return }
                    self.trailerId = movieTrailers.first?.key
                    self.playTrailerView.isHidden = false
                    ViewProperties.animateBounceEffect(backgroundView: self.playTrailerView)
                }
            case .failure:
                self.hideActivityIndicator()
            }
        }
    }
    
    @IBAction func closeButtonTapped(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func trailerButtonTapped(_ sender: Any) {
        guard let trailerId = trailerId else { return }
        let trailerVC = TrailerVC(trailerId: trailerId)
        
        self.present(trailerVC, animated: true, completion: nil)
    }
}
