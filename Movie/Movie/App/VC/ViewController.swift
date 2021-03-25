//
//  ViewController.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-24.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = .red
        // Do any additional setup after loading the view.

        //        let api = ReviewAPI().rx
        //
        //        api.fetchReviews(for: 791373, page: 1)
        //            .debug("🙃")
        //            .subscribe()
        //            .disposed(by: disposeBag)
        //
        //        let movieApi = MovieAPI().rx

        //
        //        movieApi.fetchGenres(locale: Current.locale)
        //            .debug("!")
        //            .flatMap { _ in
        //                return api.fetchReviews(for: 791373, page: 1)
        //            }
        //            .debug("@#")
        //            .subscribe()
        //            .disposed(by: disposeBag)

        //        movieApi.fetchGenres(locale: Current.locale) { result in
        //            print(result)
        //        }

        //        api.fetchReviews(for: 791373, page: 1) { result in
        //            switch result {
        //            case .success(let payload):
        //                print("Payload: \(payload) 😇")
        //            case .failure(let error):
        //                print("Error: \(error) 😩")
        //            }
        //        }
    }

}
