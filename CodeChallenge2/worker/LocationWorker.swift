//
//  LocationWorker.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import Foundation
import CoreLocation

class LocationWorker: NSObject {
    
    
    let locationManager: CLLocationManager = CLLocationManager()
    var lastLocation : CLLocation?
    var delegate: LocationWorkerDelegate?
    
    /// Starts updating the location, and ask for the when in use authorization
    func startUpdating(){
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestWhenInUseAuthorization()
        locationManager.delegate = self
        if CLLocationManager.locationServicesEnabled() {
            locationManager.startUpdatingLocation()
        }
    }
    
    
    /// Stop updating location
    func stopUpdating(){
        locationManager.stopUpdatingLocation()
    }
}


// MARK: - The CLLocation manager delegate, used to receive updates from manager
extension LocationWorker: CLLocationManagerDelegate{
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        delegate?.locationWorker(update: (lat: locations[0].coordinate.latitude, lon: locations[0].coordinate.longitude), with: lastLocation == nil ? 0 : locations[0].distance(from: lastLocation!), first: lastLocation == nil)
        lastLocation = locations[0]
    }
}




/// Delegate to receive updates from location worker
protocol LocationWorkerDelegate {
    
    
    /// Worker method to update location info
    ///
    /// - Parameters:
    ///   - location: a touple with lat and lon values
    ///   - distance: a double saying in meters the distance from last location, if nil then there's no distance as this is the first location
    ///   - first: a boolean value saying whether this is the first provided location
    func locationWorker(update location: (lat: Double, lon: Double), with distance: Double, first: Bool)
}
