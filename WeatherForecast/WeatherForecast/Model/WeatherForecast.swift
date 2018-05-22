//
//  WeatherForecast.swift
//  WeatherForecast
//
//  Created by Iciar Novo Fernandez on 10/05/2018.
//  Copyright © 2018 Icica. All rights reserved.
//

import Foundation
import UIKit

struct WeatherForecast {
    let iconWeather: UIImage?
    let mainWeather: String
//    let description: String
    let temperature: Temperature
    let minTemp: Temperature
    let maxTemp: Temperature
    let humidity: Int
    let windSpeed: Double
    let windDeg: Int
    let pressure: Int
    let clouds: Int
    let sunrise: String
    let sunset: String
    
    static func dateStringFromUnixTime(unixTime: Double) -> String  {
        let weatherDate = NSDate(timeIntervalSince1970:unixTime)
        // Returns date formatted as 12 hour time.
        let dateFormatter = DateFormatter()
        dateFormatter.timeZone = TimeZone(abbreviation: "GMT")
        dateFormatter.dateFormat = "hh:mm a"
        return dateFormatter.string(from: weatherDate as Date)
    }
    
    init(dictionary: WeatherForecastJSON) {

        let sys = dictionary["sys"] as! NSDictionary
        let weatherArray = dictionary["weather"] as! NSArray
        let main = dictionary["main"] as! NSDictionary
        let clouds = dictionary["clouds"] as! NSDictionary
        let wind = dictionary["wind"] as! NSDictionary
        
        let weather = weatherArray[0] as! NSDictionary
        
        let imageName = Description(rawValue: weather["description"] as! String)?.imageIdentifier
        print("imageName es el siguiente \(String(describing: imageName!))")
        print("esta es la imagen \(String(describing: UIImage(named: imageName!)))")
        self.iconWeather = UIImage(named: imageName!)!

        self.mainWeather = weather["main"] as! String
//        self.description = weather["description"] as! String
        self.temperature = Temperature(kelvin: main["temp"] as! Double)
        self.minTemp = Temperature(kelvin: main["temp_min"] as! Double)
        self.maxTemp = Temperature(kelvin: main["temp_max"] as! Double)
        self.humidity = main["humidity"] as! Int
        self.windSpeed = wind["speed"] as! Double
        self.windDeg = wind["deg"] as! Int
        self.pressure = main["pressure"] as! Int
        self.clouds = clouds["all"] as! Int
        self.sunrise = WeatherForecast.dateStringFromUnixTime(unixTime: sys["sunrise"] as! Double)
        self.sunset = WeatherForecast.dateStringFromUnixTime(unixTime: sys["sunset"] as! Double)
    }
}

enum Description: String {
    case thundLightRain = "thunderstorm with light rain"
    case thundRain = "thunderstorm with rain"
    case thundHeavyRain = "thunderstorm with heavy rain"
    case thundLight = "light thunderstorm"
    case thunderstorm = "thunderstorm"
    case thundHeavy = "heavy thunderstorm"
    case thundRagged = "ragged thunderstorm"
    case thundLightDrizzle = "thunderstorm with light drizzle"
    case thundDrizzle = "thunderstorm with drizzle"
    case thundHeavyDrizzle = "thunderstorm with heavy drizzle"
    case drizzleLI = "light intensity drizzle"
    case drizzle = "drizzle"
    case drizzleHI = "heavy intensity drizzle"
    case drizzleRainLI = "light intensity drizzle rain"
    case drizzleRain = "drizzle rain"
    case drizzleRainHI = "heavy intensity drizzle rain"
    case showerRainDrizzle = "shower rain and drizzle"
    case showerRainDrizzleHeavy = "heavy shower rain and drizzle"
    case showerDrizzle = "shower drizzle"
    case rainLight = "light rain"
    case rainModerate = "moderate rain"
    case rainHI = "heavy intensity rain"
    case rainVeryHeavy = "very heavy rain"
    case rainExtreme = "extreme rain"
    case rainFreezing = "freezing rain"
    case showerRainLI = "light intensity shower rain"
    case sowherRain = "shower rain"
    case showerRainHI = "heavy intensity shower rain"
    case showerRainRagged = "ragged shower rain"
    case snowLight = "light snow"
    case snow = "snow"
    case snowHeavy = "heavy snow"
    case sleet = "sleet"
    case showerSleet = "shower sleet"
    case rainSnowLight = "light rain and snow"
    case rainSnow = "rain and snow"
    case showerSnowLight = "light shower snow"
    case showerSnow = "shower snow"
    case showerSnowHeavy = "heavy shower snow"
    case clearSky = "clear sky"
    case cloudsFew = "few clouds"
    case cloudsScattered = "scattered clouds"
    case cloudsBroken = "broken clouds"
    case cloudsOvercast = "overcast clouds"
    
    var imageIdentifier: String {
        switch self {
        case .thundLightRain:
            return "Cloud-Lightning"
        case .thundRain:
            return "Cloud-Lightning"
        case .thundHeavyRain:
            return "Cloud-Lightning"
        case .thundLight:
            return "Cloud-Lightning"
        case .thunderstorm:
            return "Cloud-Lightning"
        case .thundHeavy:
            return "Cloud-Lightning"
        case .thundRagged:
            return "Cloud-Lightning"
        case .thundLightDrizzle:
            return "Cloud-Lightning"
        case .thundDrizzle:
            return "Cloud-Lightning"
        case .thundHeavyDrizzle:
            return "Cloud-Lightning"
        case .drizzleLI:
            return "Cloud-Drizzle-Alt"
        case .drizzle:
            return "Cloud-Drizzle-Alt"
        case .drizzleHI:
            return "Cloud-Drizzle-Alt"
        case .drizzleRainLI:
            return "Cloud-Drizzle-Alt"
        case .drizzleRain:
            return "Cloud-Rain-Sun-Alt"
        case .drizzleRainHI:
            return "Cloud-Rain-Sun-Alt"
        case .showerRainDrizzle:
            return "Cloud-Rain-Sun-Alt"
        case .showerRainDrizzleHeavy:
            return "Cloud-Rain-Sun-Alt"
        case .showerDrizzle:
            return "Cloud-Rain-Sun-Alt"
        case .rainLight:
            return "Cloud-Rain-Sun-Alt"
        case .rainModerate:
            return "Cloud-Rain-Sun-Alt"
        case .rainHI:
            return "Cloud-Rain-Sun"
        case .rainVeryHeavy:
            return "Cloud-Rain"
        case .rainExtreme:
            return "Cloud-Rain"
        case .rainFreezing:
            return "Cloud-Rain"
        case .showerRainLI:
            return "Cloud-Rain"
        case .sowherRain:
            return "Cloud-Rain"
        case .showerRainHI:
            return "Cloud-Rain"
        case .showerRainRagged:
            return "Cloud-Rain"
        case .snowLight:
            return "Cloud-Snow"
        case .snow:
            return "Cloud-Snow"
        case .snowHeavy:
            return "Cloud-Snow"
        case .sleet:
            return "Cloud-Snow"
        case .showerSleet:
            return "Cloud-Snow"
        case .rainSnowLight:
            return "Cloud-Snow"
        case .rainSnow:
            return "Cloud-Snow"
        case .showerSnowLight:
            return "Cloud-Snow"
        case .showerSnow:
            return "Cloud-Snow"
        case .showerSnowHeavy:
            return "Cloud-Snow"
        case .clearSky:
            return "Sun"
        case .cloudsFew:
            return "Cloud-Sun"
        case .cloudsScattered:
            return "Cloud"
        case .cloudsBroken:
            return "Cloud"
        case .cloudsOvercast:
            return "Cloud"
        }
    }
}
