//
//  Temperature.swift
//  WeatherForecast
//
//  Created by Iciar Novo Fernandez on 13/05/2018.
//  Copyright © 2018 Icicleta. All rights reserved.
//

import Foundation

struct Temperature {
    var kelvin: Double
    
    var fahrenheit: Double {
        return (kelvin - 273.15) * 1.8 + 32
    }
    var celsius: Double {
        return kelvin - 273.15

    }
}
