//
//  MyWeatherViewController.swift
//  Weather
//
//  Created by Christian  Huang on 10/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit

class MyWeatherViewController: UIViewController {
    
    @IBOutlet private weak var weatherView: UIView!
    @IBOutlet private weak var locationLabel: UILabel!
    @IBOutlet private weak var closeButton: UIButton!
    @IBOutlet private weak var weatherBaseView: UIView!
    @IBOutlet private weak var weatherCenterView: UIView!
    @IBOutlet private weak var compassBaseView: UIView!
    @IBOutlet private weak var weatherImageView: UIImageView!
    @IBOutlet private weak var loadingView: UIActivityIndicatorView!
    @IBOutlet private weak var temperatureLabel: UILabel!
    @IBOutlet private weak var maxTemperatureLabel: UILabel!
    @IBOutlet private weak var minTemperatureLabel: UILabel!
    @IBOutlet private weak var humidityLabel: UILabel!
    @IBOutlet private weak var pressureLabel: UILabel!
    @IBOutlet private weak var weatherLabel: UILabel!
    @IBOutlet private weak var windLabel: UILabel!
    @IBOutlet private weak var timeLabel: UILabel!
    @IBOutlet private weak var dayLabel: UILabel!
    
    @IBOutlet private weak var shortTermForecastCollectionView: ForecastCollectionView!
    @IBOutlet private weak var longTermForecastCollectionView: ForecastDailyCollectionView!
    
    
    private var weatherViewModel = WeatherViewModel()
    

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        initViewModel()
        setupUI()
    }
    
    override var preferredStatusBarStyle : UIStatusBarStyle {
        return .lightContent
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        weatherViewModel.startWeatherForecasting()
    }
    
    @IBAction func closeButtonTouchUpInside(sender: UIButton){
        self.dismiss(animated: true, completion: nil)
    }

}

//MARK:- ViewModel related
extension MyWeatherViewController {
    func initViewModel() {
        
        
        weatherViewModel.startFetchingWeatherClosure = showLoadingView
        weatherViewModel.updateWeatherDataClosure = {
            DispatchQueue.main.async {
                self.updateWeatherData()
            }
        }
        weatherViewModel.finishedFetchingWeatherClosure = hideLoadingView
        
        shortTermForecastCollectionView.initViewModel()
        longTermForecastCollectionView.initViewModel()
        weatherViewModel.forecastListViewModel = shortTermForecastCollectionView.forecastListViewModel
        weatherViewModel.forecastDailyListViewModel = longTermForecastCollectionView.forecastDailyListViewModel
    }
}

//MARK:- private func
extension MyWeatherViewController {
    private func setupUI() {
        closeButton.setFullRoundedCorner()
        weatherBaseView.setFullRoundedCorner()
        compassBaseView.transform = CGAffineTransform(rotationAngle: CGFloat.pi / 4)
        weatherCenterView.setFullRoundedCorner(borderWidth: 1, borderColor: UIColor.lightGray.cgColor)
    }
    
    private func updateWeatherData() {
        locationLabel.text = "\(weatherViewModel.area), \(weatherViewModel.country)"
        weatherLabel.text = weatherViewModel.weather
        temperatureLabel.text = "\(Int(weatherViewModel.temperature))"
        timeLabel.text = weatherViewModel.timeString
        dayLabel.text = weatherViewModel.dayShortString
        self.view.backgroundColor = DayTimeColor.colorFor(weatherViewModel.dayTime)
        minTemperatureLabel.text = "Min \(Int(weatherViewModel.temperatureMin))°"
        maxTemperatureLabel.text = "Max \(Int(weatherViewModel.temperatureMax))°"
        windLabel.text = "Wind \(weatherViewModel.windSpeed)"
        pressureLabel.text = weatherViewModel.pressure
        humidityLabel.text = weatherViewModel.humidity
        weatherImageView.kf.setImage(with: URL(string: weatherViewModel.icon))
    }
    
    private func showLoadingView(){
        DispatchQueue.main.async {
            self.loadingView.startAnimating()
        }
    }
    private func hideLoadingView() {
        DispatchQueue.main.async {
            self.loadingView.stopAnimating()
        }
    }
}
