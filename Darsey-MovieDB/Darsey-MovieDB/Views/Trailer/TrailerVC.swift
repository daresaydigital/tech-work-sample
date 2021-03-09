//
//  TrailerVC.swift
//  Darsey-MovieDB
//
//  Created by Emil Vaklinov on 09/03/2021.
//

import UIKit
import WebKit

class TrailerVC: UIViewController {

    //MARK:- Outlets
    @IBOutlet weak var webView: WKWebView!
    @IBOutlet weak var closeView: UIView!
    
    //MARK:- Properties
    static let reuseId = "TrailerVC"
    var trailerId: String
    
    init(trailerId: String) {
        self.trailerId = trailerId
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func viewDidAppear(_ animated: Bool) {
        setupVC()
        loadTrailer()
    }
}

extension TrailerVC {
    
    private func setupVC(){
        closeView.isHidden = false
        ViewProperties.configureCircularViewWithShadow(backgroundView: closeView)
        ViewProperties.animateBounceEffect(backgroundView: closeView)
    }
    
    func loadTrailer() {
        guard let trailerURL = URL(string: ApiURL.youtubeURL + "\(trailerId)") else { return }
        let request = URLRequest(url: trailerURL)
        webView.load(request)
    }
    
    @IBAction func closeButtonTapped(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
}
