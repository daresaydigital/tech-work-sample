//
//  GroupForecast.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import Foundation

struct GroupForecast {
    
    /// The weather that identifies this group
    var weather: Weather
    
    /// Weather from the same day later on
    var laterOn: [Weather]
}
