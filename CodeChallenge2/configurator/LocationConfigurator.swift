//
//  LocationConfigurator.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import Foundation
//import CoreLocation

struct LocationConfigurator {
    
    
    /// shared instance of Location Configurator
    static var shared = LocationConfigurator()
    
    /// worker for the location management
    private let worker = LocationWorker()
    
    /// The minimum move distance for the delegate to react
    var minMoveDistance: Double = 200
    
    
    /// Delegate to receive updates from
    var delegate: LocationConfiguratorDelegate?
    
    /// Starts updating the location, and ask for the when in use authorization
    func startUpdating(){
        worker.startUpdating()
        worker.delegate = self
    }
    
    /// Stops updating location
    func stopUpdating(){
        worker.stopUpdating()
    }
}


// MARK: - LocationWorker Delegate in order to receive updates from worker
extension LocationConfigurator: LocationWorkerDelegate{
    func locationWorker(update location: (lat: Double, lon: Double), with distance: Double, first: Bool) {
        if first || distance >= minMoveDistance{
            delegate?.locationConfigurator(update: location)
        }
    }
    
    
}

protocol LocationConfiguratorDelegate {
    /// Worker method to update location info
    ///
    /// - Parameters:
    ///   - location: a touple with lat and lon values
    func locationConfigurator(update location: (lat: Double, lon: Double))
}
