//
//  AFManagerProtocol.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 20/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import Foundation

public protocol AFManagerProtocol {
    func api(_ param: AFParam, completion: @escaping () -> Void)
}
