//
//  WeatherViewController.swift
//  Weather
//
//  Created by Christian  Huang on 03/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit

class WeatherViewController: UIViewController {
    @IBOutlet private weak var headerView: UIView!
    @IBOutlet private weak var headerViewTopConstraint: NSLayoutConstraint!
    @IBOutlet private weak var areaLabel: UILabel!
    @IBOutlet private weak var weatherLabel: UILabel!
    @IBOutlet private weak var temperatureLabel: UILabel!
    private var todayForecastView = DailyForecastView.instanceFromNib()
    @IBOutlet private weak var todayView: UIView!
    @IBOutlet private weak var todayViewTopConstraint: NSLayoutConstraint!
    @IBOutlet private weak var todayForecastCollectionView: ForecastCollectionView!
    @IBOutlet private weak var detailScrollView: UIScrollView!
    @IBOutlet private weak var forecastDailyStackView: ForecastDailyStackView!
    @IBOutlet private weak var forecastDescriptionLabel: UILabel!
    @IBOutlet private weak var infoStackView: UIStackView!
    private var sunriseSunsetView = InfoView.instanceFromNib()
    private var pressureHumidityView = InfoView.instanceFromNib()
    @IBOutlet private weak var loadingBGView: UIView!
    @IBOutlet private weak var loadingView: UIActivityIndicatorView!
    
    private var weatherViewModel = WeatherViewModel()
    private var headerViewTopDefault: CGFloat = 0
    private var todayViewTopDefault: CGFloat = 0
    private var totalTopShift: CGFloat = 0
    private var headerViewTopRatio: CGFloat = 0
    private var todayViewTopRatio: CGFloat = 0
    private var scrollViewOffset = CGPoint.zero
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        initViewModel()
        setupUI()
    }
    
    override var preferredStatusBarStyle : UIStatusBarStyle {
        return .lightContent
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        showLoadingView()
        weatherViewModel.startWeatherForcasting()
    }


}


//MARK:- ViewModel related
extension WeatherViewController {
    func initViewModel() {
        weatherViewModel.area.listener = { self.areaLabel.text = $0 }
        weatherViewModel.weather.listener = { self.weatherLabel.text = $0 }
        weatherViewModel.temperature.listener = { self.temperatureLabel.text = "\(Int($0))°" }
        weatherViewModel.summary.listener = { summary in
            self.forecastDescriptionLabel.text = summary
        }
        weatherViewModel.sunrise.listener = { self.sunriseSunsetView.detail1 = $0 }
        weatherViewModel.sunset.listener = { self.sunriseSunsetView.detail2 = $0 }
        weatherViewModel.pressure.listener = { self.pressureHumidityView.detail1 = $0 }
        weatherViewModel.humidity.listener = { self.pressureHumidityView.detail2 = $0 }
        
        weatherViewModel.updateWeatherDataClosure = updateWeatherData
        weatherViewModel.finishedFetchingWeatherClosure = hideLoadingView
        
        todayForecastCollectionView.initViewModel()
        forecastDailyStackView.initViewModel()
        weatherViewModel.forecastListViewModel = todayForecastCollectionView.forecastListViewModel
        weatherViewModel.forecastDailyListViewModel = forecastDailyStackView.forecastDailyListViewModel
    }
    
    private func updateWeatherData() {
        todayForecastView.configureTodayView(weatherViewModel: weatherViewModel)
    }
}

//MARK:- private func
extension WeatherViewController {
    private func setupUI() {
        self.view.layoutIfNeeded()
        self.view.insertSubview(todayForecastView, belowSubview: todayView)
        sunriseSunsetView.configureInfoView(title1: "Sunrise", title2: "Sunset")
        infoStackView.addArrangedSubview(sunriseSunsetView)
        pressureHumidityView.configureInfoView(title1: "Pressure", title2: "Humidity", hasSeparator: false)
        infoStackView.addArrangedSubview(pressureHumidityView)
        
        NSLayoutConstraint.activate([
            todayForecastView.widthAnchor.constraint(equalTo: view.widthAnchor),
            todayForecastView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            todayForecastView.bottomAnchor.constraint(equalTo: todayView.topAnchor)
            ])
        
        headerViewTopDefault = headerViewTopConstraint.constant
        todayViewTopDefault = todayViewTopConstraint.constant
        totalTopShift = headerViewTopDefault + todayViewTopDefault
        headerViewTopRatio = headerViewTopDefault / totalTopShift
        todayViewTopRatio = todayViewTopDefault / totalTopShift
    }
    private func showLoadingView(){
        loadingBGView.backgroundColor = view.backgroundColor
        loadingBGView.alpha = 1
        loadingView.startAnimating()
    }
    private func hideLoadingView() {
        UIView.animate(withDuration: 0.2,
                       animations: {
                        self.loadingBGView.alpha = 0
        },
                       completion: { finished in
                        self.loadingView.stopAnimating()
        })
    }
}

//MARK:- ScrollView Delegate
extension WeatherViewController: UIScrollViewDelegate {
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        var offset = scrollView.contentOffset
        if (offset.y > 0 && todayViewTopConstraint.constant > 0) ||
            (offset.y < 0 && todayViewTopConstraint.constant < todayViewTopDefault) {
            let shift = offset.y
            headerViewTopConstraint.constant -= shift * headerViewTopRatio
            todayViewTopConstraint.constant -= shift * todayViewTopRatio
            headerViewTopConstraint.constant = max(0, min(headerViewTopDefault, headerViewTopConstraint.constant))
            todayViewTopConstraint.constant = max(0, min(todayViewTopDefault, todayViewTopConstraint.constant))
            
            self.view.layoutIfNeeded()
            
            let maxValue = (todayViewTopDefault / 2)
            var alpha = (todayViewTopConstraint.constant - maxValue) / maxValue
            alpha = max(0, alpha)
            temperatureLabel.alpha = alpha
            todayForecastView.alpha = alpha
            
            offset.y = 0
            scrollView.contentOffset = offset
        }
    }
}

