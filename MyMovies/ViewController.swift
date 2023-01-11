//
//  ViewController.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = .red

        let api = TrendingAPI()
        let apiLoader = APILoader(apiHandler: api)
        apiLoader.loadAPIRequest(requestData: (MediaType.all, TimeWindow.day)) { trending, error in
            print(trending?.results?.count)
            print(error)
        }
    }
}

