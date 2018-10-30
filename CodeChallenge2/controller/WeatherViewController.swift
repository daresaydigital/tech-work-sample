//
//  WeatherViewController.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import UIKit

class WeatherViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        LocationConfigurator.shared.delegate = self
        LocationConfigurator.shared.startUpdating()
    }
    
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        LocationConfigurator.shared.stopUpdating()
    }
    
    func setup(){
        
    }
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return .lightContent
    }
}


extension WeatherViewController: LocationConfiguratorDelegate{
    func locationConfigurator(update location: (lat: Double, lon: Double)) {
        print("updating location in tvc: \(location)")
    }
}
