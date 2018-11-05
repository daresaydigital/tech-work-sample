//
//  Info.swift
//  Weather
//
//  Created by Christian  Huang on 05/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation


enum DayTime: Int {
    case morning
    case day
    case evening
    case night
}

struct Coordinate: Codable {
    let longitude: Double
    let latitude: Double
    
    enum CodingKeys: String, CodingKey {
        case longitude = "lon"
        case latitude = "lat"
    }
}

struct Temperature: Codable {
    let min: Double
    let max: Double
    let morning: Double
    let day: Double
    let evening: Double
    let night: Double
    
    enum CodingKeys: String, CodingKey {
        case min
        case max
        case morning = "morn"
        case day
        case evening = "eve"
        case night
    }
}

struct Wind: Codable {
    let speed: Double
    let degree: Double?
    
    enum CodingKeys: String, CodingKey {
        case speed
        case degree = "deg"
    }
}

struct Clouds: Codable {
    let cloudiness: Double
    
    enum CodingKeys: String, CodingKey {
        case cloudiness = "all"
    }
}

struct Rain: Codable {
    let rain3h: Double
    
    enum CodingKeys: String, CodingKey {
        case rain3h = "3h"
    }
}

struct Snow: Codable {
    let snow3h: Double
    
    enum CodingKeys: String, CodingKey {
        case snow3h = "3h"
    }
}

struct SystemData: Codable {
    let type: Int
    let id: Int
    let message: Double
    let country: String
    let sunrise: TimeInterval
    let sunset: TimeInterval
    
}

struct City: Codable {
    let id: Int
    let name: String
    let coordinate: Coordinate
    let country: String
    
    enum CodingKeys: String, CodingKey {
        case id
        case name
        case coordinate = "coord"
        case country
    }
}


