//
//  DayTimeColor.swift
//  Weather
//
//  Created by Christian  Huang on 11/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit


enum DayTime: Int {
    case morning
    case day
    case evening
    case night
}

class DayTimeColor {
    static func colorFor(_ dayTime: DayTime) -> UIColor {
        switch dayTime {
        case .morning:
            return UIColor(hexString: "D9781F")
        case .day:
            return UIColor(hexString: "499BC4")
        case .evening:
            return UIColor(hexString: "C43900")
        default:
            return UIColor.black
        }
    }
}
