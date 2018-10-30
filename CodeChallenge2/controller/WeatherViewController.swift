//
//  WeatherViewController.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import UIKit

class WeatherViewController: UIViewController {

    @IBOutlet weak var locationLabel: UILabel!
    @IBOutlet weak var weatherLabel: UILabel!
    @IBOutlet weak var weatherMessageLabel: UILabel!
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
        print("updating location in vc: \(location)")
        WeatherConfigurator.shared.current(based: location) { (forecast) in
            guard let forecast = forecast else{
                return
            }
            
            self.locationLabel.text = forecast.name
            self.weatherLabel.text = "\(Int(forecast.main.temp))"
            self.weatherMessageLabel.text = String.localizedStringWithFormat("principal.weather.message".localized, "\(forecast.wind.speed)", "\(forecast.main.humidity)")

        }
    }
}
