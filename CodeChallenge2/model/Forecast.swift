//
//  Forecast.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import Foundation

struct Forecast: Codable {
    
    var list: [Weather]
    var cod: String
}
