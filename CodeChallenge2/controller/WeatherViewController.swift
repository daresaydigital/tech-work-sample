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
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var backgroundImageView: UIImageView!
    
    var data = [GroupForecast]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        LocationConfigurator.shared.delegate = self
        LocationConfigurator.shared.startUpdating()
        setup()
    }
    
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        LocationConfigurator.shared.stopUpdating()
    }
    
    
    /// Setting up content
    func setup(){
        tableView.dataSource = self
        tableView.delegate = self
        tableView.rowHeight = UITableView.automaticDimension
        tableView.estimatedRowHeight = 50
//        tableView.selectedBackgroundView = UIView()
    }
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return .lightContent
    }
}



// MARK: - Location configurator delegate
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
            self.data = WeatherConfigurator.shared.group(forecast: forecast)
            Dispatch.main{
                self.tableView.reloadData()
            }
        }
    }
}


// MARK: - Table view data source
extension WeatherViewController: UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: ForecastDayTableViewCell.identifier) as? ForecastDayTableViewCell else{
            return UITableViewCell()
        }
        let data = self.data[indexPath.row]
        cell.setup(at: indexPath, with: data)
        return cell
    }
    
}

extension WeatherViewController: UITableViewDelegate{
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        data[indexPath.row].weather.showingDetail = !data[indexPath.row].weather.showingDetail
        tableView.reloadRows(at: [indexPath], with: .automatic)
        
    }
}
