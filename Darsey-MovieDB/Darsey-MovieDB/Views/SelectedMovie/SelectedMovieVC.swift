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

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }


}
