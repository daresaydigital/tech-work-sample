//
//  WeatherViewController.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import UIKit
import Alamofire

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
        print("updating location in vc: \(location)")
        WeatherConfigurator.shared.current(based: location) { (weather) in
            print("Printing weather in vc: \(weather)")
        }
//        Alamofire.request("http://worksample-api.herokuapp.com/weather?lat=18.49311984926528&lon=-69.82712812034117&key=62fc4256-8f8c-11e5-8994-feff819cdc9f", method: .get, parameters: nil, encoding: JSONEncoding.default, headers: nil).responseData { (data) in
//            let weather: Weather? = try? JSONDecoder().decode(Weather.self, from: data.result.value!)
//            print(weather)
//        }
    }
}
