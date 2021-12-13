//
//  HomeViewController.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/13/21.
//

import UIKit

class HomeViewController: UIViewController, Storyboarded {

    weak var coordinator: HomeCoordinator?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        self.title = LocalizedStrings.test.value
    }
}
