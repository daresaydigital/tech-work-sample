//
//  ChooseCountryViewController.swift
//  WeatherForecast
//
//  Created by Iciar Novo Fernandez on 07/05/2018.
//  Copyright © 2018 Icicleta. All rights reserved.
//

import UIKit

class ChooseCountryViewController: UIViewController {

    @IBOutlet weak var countryTextField: UITextField!
    @IBOutlet weak var errorText: UILabel!
    
    var country: String?
    var weatherForecastInfo: WeatherForecast?

    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "displayCountryWeather"{
            if let weatherForecast = segue.destination as? WeatherForecastViewController {
                weatherForecast.weatherForecastData = self.weatherForecastInfo
            }
        }
    }
    
    @IBAction func sendCountryWeatherData(_ sender: Any) {
        guard let country = countryTextField.text else { return }
        self.country = country
        getWeatherForecast {
            guard self.weatherForecastInfo != nil else {
                self.errorText.text = "Data for this country couldn't be retrieved, try again please"
                return
            }
            self.performSegue(withIdentifier: "displayCountryWeather", sender: self)
        }
    }

    func getWeatherForecast(completion: @escaping () -> Void) {
        OpenWeatherMapAPI.getWeatherForecastJSON(country: self.country!) { (json) in
            guard let feed = json else { print("JSON data hasn't been found"); return }
            print(feed)
            if let error = feed["message"] {
                print(error)
                return
            } else {
                self.weatherForecastInfo = WeatherForecast(dictionary: feed)
                
            }
            OperationQueue.main.addOperation {
                completion()
            }
        }
    }
    
    @IBAction func unwindToChooseCountryTableView(segue: UIStoryboardSegue)
    {}
    
}
