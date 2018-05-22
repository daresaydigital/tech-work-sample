//
//  WeatherForecastViewController.swift
//  WeatherForecast
//
//  Created by Iciar Novo Fernandez on 07/05/2018.
//  Copyright © 2018 Icicleta. All rights reserved.
//

import UIKit


class WeatherForecastViewController: UITableViewController {
//    var country: String?
    var weatherForecastData: WeatherForecast?
    
    @IBOutlet weak var weatherImage: UIImageView!
    @IBOutlet weak var mainWeather: UILabel!
    @IBOutlet weak var temperatureText: UILabel!
    @IBOutlet weak var descriptionText: UILabel!
    @IBOutlet weak var minTempText: UILabel!
    @IBOutlet weak var maxTempText: UILabel!
    @IBOutlet weak var humidityText: UILabel!
    @IBOutlet weak var windText: UILabel!
    @IBOutlet weak var pressureText: UILabel!
    @IBOutlet weak var cloudsText: UILabel!
    @IBOutlet weak var sunriseText: UILabel!
    @IBOutlet weak var sunsetText: UILabel!
    
    @IBOutlet weak var weatherScaleSegment: UISegmentedControl!
    
    @IBAction func weatherScaleSelected(_ sender: UISegmentedControl) {
        guard let data = weatherForecastData else { return }
        switch sender.selectedSegmentIndex
        {
        case 0:
            temperatureText.text = "\(Int(round(data.temperature.celsius)))°"
            minTempText.text =  "\(Int(round(data.minTemp.celsius)))°"
            maxTempText.text =  "\(Int(round(data.maxTemp.celsius)))°"
        case 1:
            temperatureText.text = "\(Int(round(data.temperature.fahrenheit)))°"
            minTempText.text =  "\(Int(round(data.minTemp.fahrenheit)))°"
            maxTempText.text =  "\(Int(round(data.maxTemp.fahrenheit)))°"
        default:
            break;
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        updateWeatherForecast()
    }
    
    func updateWeatherForecast() {
        guard let data = weatherForecastData else { return }
        weatherImage.image = data.iconWeather
        temperatureText.text = "\(Int(round(data.temperature.celsius)))°"
//        descriptionText.text = data.description
        mainWeather.text = "\(data.mainWeather)"
        minTempText.text = "\(Int(round(data.minTemp.celsius)))°"
        maxTempText.text = "\(Int(round(data.maxTemp.celsius)))°"
        humidityText.text = "\(data.humidity) %"
        windText.text = "Speed: \(data.windSpeed) m/s  Degree: \(data.windDeg) %"
        pressureText.text = "\(data.pressure) hPa"
        cloudsText.text = "\(data.clouds) %"
        sunriseText.text = data.sunrise
        sunsetText.text = data.sunset
    }
}

