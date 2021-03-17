//
//  Constants.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 20/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import Foundation


enum Constants {
    
    static let apiKey  = "62fc4256-8f8c-11e5-8994-feff819cdc9f"
    static let baseURL = "http://worksample-api.herokuapp.com/"
    static let iconURL = "http://openweathermap.org/img/w/"
    static let cityJsonFile = "cityList"
}
enum apiEndPoints : String {
    case weather = "weather?"
    case forecast = "forecast?"
    case daily = "forecast/daily?"
}


enum apptheme : Int {
        case darkMode = 0
        case lightMode
    
}

enum cityViewDescription : String {
    case locationAccessDenied = "We respect your privacy. Its completely fine that you don't want to share your current location. Kindly enter the city below to know its temperature."
    case changeCity = "Kindly select city name below"
}

enum homeViewControllerCells : String {
    case HomeOtherInformationTableViewCell = "HomeOtherInformationTableViewCell"
    case HomeDescriptionTableViewCell = "HomeDescriptionTableViewCell"
    case HomeForecastDaysTableViewCell = "HomeForecastDaysTableViewCell"
    case HomeForecastTableViewCell = "HomeForecastTableViewCell"
    case HourlyForecastCollection = "HourlyForecastCollection"
}

enum Storyboards : String {
        case Main = "Initial"
}
enum AppVCs : String{
    case SelectCityViewController = "SelectCityViewController"
    case HomeViewController = "HomeViewController"

}
