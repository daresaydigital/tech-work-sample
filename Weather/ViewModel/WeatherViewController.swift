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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }


}

