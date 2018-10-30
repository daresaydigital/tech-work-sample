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
        WeatherConfigurator.shared.current(based: location) { (weather) in
            guard let weather = weather else{
                return
            }
            
            self.locationLabel.text = weather.name
            self.weatherLabel.text = "\(Int(weather.main.temp))"
            self.weatherMessageLabel.text = String.localizedStringWithFormat("principal.weather.message".localized, "\(weather.wind.speed)", "\(weather.main.humidity)")
        }
        WeatherConfigurator.shared.forecast(based: location) { (forecast) in
            guard let forecast = forecast else{
                return
            }
            
        }
    }
}
