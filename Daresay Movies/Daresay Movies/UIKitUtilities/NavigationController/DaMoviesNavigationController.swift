//
//  DaMoviesNavigationController.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import UIKit

class DaMoviesNavigationController: UINavigationController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    private func setupView() {
        navigationBar.barTintColor = .systemGreen
        
        if #available(iOS 15, *) {
            let appearance = UINavigationBarAppearance()
            appearance.configureWithOpaqueBackground()
            appearance.backgroundColor = UIColor.systemGreen
            appearance.shadowImage = UIImage()
            appearance.shadowColor = .clear
            navigationController?.navigationBar.scrollEdgeAppearance = appearance
            
            self.navigationBar.standardAppearance = appearance
            UINavigationBar.appearance().scrollEdgeAppearance = appearance
            UINavigationBar.appearance().compactScrollEdgeAppearance = appearance
        }
    }
}
