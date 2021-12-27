//
//  ViewController.swift
//  MovieShow
//
//  Created by Ramy Atalla on 2021-12-27.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .green
        NetworkManager.shared.fetchMovies(from: .popular) { result in
            switch result {
            case .success(let movieResponse):
                print("DEBUG: \(movieResponse.results)")
            case .failure(let err): print("DEBUG: found error \(err.localizedDescription)")
            }
        }
    }


}

