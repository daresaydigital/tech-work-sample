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
    private var isFirstLoad = true
    
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
        weatherViewModel.startWeatherForecasting()
    }

    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destination.
     // Pass the selected object to the new view controller.
     }
     */

}


//MARK:- ViewModel related
extension WeatherViewController {
    func initViewModel() {
        
        weatherViewModel.startFetchingWeatherClosure = {
            DispatchQueue.main.async {
                self.showLoadingView()
            }
        }
        weatherViewModel.updateWeatherDataClosure = {
            DispatchQueue.main.async {
                self.updateWeatherData()
            }
        }
        weatherViewModel.finishedFetchingWeatherClosure = {
            DispatchQueue.main.async {
                self.hideLoadingView()
            }
        }
        
        todayForecastCollectionView.initViewModel()
        forecastDailyStackView.initViewModel()
        weatherViewModel.forecastListViewModel = todayForecastCollectionView.forecastListViewModel
        weatherViewModel.forecastDailyListViewModel = forecastDailyStackView.forecastDailyListViewModel
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
    
    private func updateWeatherData() {
        areaLabel.text = weatherViewModel.area
        weatherLabel.text = weatherViewModel.weather
        temperatureLabel.text = "\(Int(weatherViewModel.temperature))°"
        forecastDescriptionLabel.text = weatherViewModel.summary
        sunriseSunsetView.detail1 = weatherViewModel.sunrise
        sunriseSunsetView.detail2 = weatherViewModel.sunset
        view.backgroundColor = DayTimeColor.colorFor(weatherViewModel.dayTime)
        pressureHumidityView.detail1 = weatherViewModel.pressure
        pressureHumidityView.detail2 = weatherViewModel.humidity
        
        todayForecastView.configureTodayView(weatherViewModel: weatherViewModel)
    }
    
    private func showLoadingView(){
        if isFirstLoad {
            isFirstLoad = false
            loadingBGView.backgroundColor = view.backgroundColor
            loadingBGView.alpha = 1
            loadingView.startAnimating()
        }
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

