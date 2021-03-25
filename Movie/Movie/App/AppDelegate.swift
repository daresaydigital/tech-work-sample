//
//  AppDelegate.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-24.
//

import UIKit
import RxSwift

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    let disposeBag = DisposeBag()

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.

        let api = ReviewAPI().rx

        api.fetchReviews(for: 791373, page: 1)
            .debug("🙃")
            .subscribe()
            .disposed(by: disposeBag)

        //        api.fetchReviews(for: 791373, page: 1) { result in
        //            switch result {
        //            case .success(let payload):
        //                print("Payload: \(payload) 😇")
        //            case .failure(let error):
        //                print("Error: \(error) 😩")
        //            }
        //        }

        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }

}
