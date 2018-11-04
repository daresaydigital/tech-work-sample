//
//  WeatherViewController.swift
//  Weather
//
//  Created by Christian  Huang on 03/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit

class WeatherViewController: UIViewController {
    @IBOutlet weak var headerView: UIView!
    @IBOutlet weak var areaLabel: UILabel!
    @IBOutlet weak var weatherLabel: UILabel!
    @IBOutlet weak var temperatureLabel: UILabel!
    @IBOutlet weak var todayView: UIView!
    @IBOutlet weak var todayForecastCollectionView: UICollectionView!
    @IBOutlet weak var detailScrollView: UIScrollView!
    @IBOutlet weak var dailyForecastStackView: UIStackView!
    @IBOutlet weak var forecastDescriptionLabel: UILabel!
    @IBOutlet weak var infoStackView: UIStackView!
    
    @IBOutlet weak var loadingBGView: UIView!
    @IBOutlet weak var loadingView: UIActivityIndicatorView!
    
    private var locationManager = LocationManager.sharedInstance()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        locationManager.requestLocation()
    }
    
    override var preferredStatusBarStyle : UIStatusBarStyle {
        return .lightContent
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        showLoadingView()
    }


}

//MARK:- private func
extension WeatherViewController {
    private func showLoadingView(){
        loadingBGView.backgroundColor = view.backgroundColor
        loadingBGView.alpha = 1
        loadingView.startAnimating()
    }
}

