//
//  LocationManager.swift
//  Weather
//
//  Created by Christian  Huang on 03/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit
import CoreLocation

class LocationManager: NSObject {
    static private var shared: LocationManager!
    private var locationManager = CLLocationManager()
    var lastLocation: CLLocation?
    var lastPlacemark: CLPlacemark?
    var isPlacemarkNeeded = false
    var locationUpdateClosure: ((CLLocation?, CLPlacemark?)->())?
    
    static func sharedInstance() -> LocationManager {
        if shared == nil {
            shared = LocationManager()
        }
        return shared
    }
    
    func startUpdatingLocation(placemarkNeeded: Bool = false) {
        isPlacemarkNeeded = placemarkNeeded
        if self.requestAuthorization() {
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.delegate = self
            locationManager.startUpdatingLocation()
        } else {
            print("PLease turn on location services or GPS")
        }
    }
    
    func stopUpdatingLocation() {
        locationManager.stopUpdatingLocation()
    }
    
    func requestLocation(placemarkNeeded: Bool = true) {
        isPlacemarkNeeded = placemarkNeeded
        if self.requestAuthorization() {
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.delegate = self
            locationManager.requestLocation()
        } else {
            print("PLease turn on location services or GPS")
        }
    }
    
    func lookUCurrentLocation(_ location: CLLocation, completionHandler: @escaping (CLPlacemark?) -> Void ) {
        
        let geocoder = CLGeocoder()
            
        // Look up the location and pass it to the completion handler
        geocoder.reverseGeocodeLocation(location, completionHandler: { (placemarks, error) in
            if error == nil {
                let firstLocation = placemarks?[0]
                completionHandler(firstLocation)
            }
            else {
                // An error occurred during geocoding.
                completionHandler(nil)
            }
        })
    }
}

//MARK:- private func
extension LocationManager {
    private func requestAuthorization() -> Bool {
        if CLLocationManager.locationServicesEnabled() == true {
            if CLLocationManager.authorizationStatus() == .restricted || CLLocationManager.authorizationStatus() == .denied ||  CLLocationManager.authorizationStatus() == .notDetermined {
                locationManager.requestWhenInUseAuthorization()
            }
            return true
        } else {
            print("PLease turn on location services or GPS")
            return false
        }
    }
}

//MARK:- CLLocationManager Delegates
extension LocationManager: CLLocationManagerDelegate {
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        lastLocation = locations[0]
        print("user latitude = \(lastLocation!.coordinate.latitude)")
        print("user longitude = \(lastLocation!.coordinate.longitude)")
        
        if isPlacemarkNeeded {
            lookUCurrentLocation(lastLocation!) { placemark in
                self.lastPlacemark = placemark
                print("Placemark: \(placemark?.name ?? "") - \(placemark?.subLocality ?? "") - \(placemark?.country ?? "") - \(placemark?.locality ?? "") - \(placemark?.country ?? "")")
                
                self.locationUpdateClosure?(locations[0], placemark)
            }
        } else {
            locationUpdateClosure?(lastLocation, nil)
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("Unable to access your current location: \(error)")
    }
}
