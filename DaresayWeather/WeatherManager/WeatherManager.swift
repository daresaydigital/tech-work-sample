//
//  WeatherManager.swift
//  DaresayWeather
//
//  Created by Hamza Khan on 21/10/2018.
//  Copyright © 2018 HMBP. All rights reserved.
//

import Foundation
import Alamofire

class WeatherManager : AFManagerProtocol {
    
    var isSuccess = false
    private var weatherData: weatherModelviaLocation?
    private var forecastData : forecastModelviaLocation?

    //custom function for ease of use.
    func api(_ param: AFParam, completion: @escaping () -> Void) {
        
        self.isSuccess = false
        
        //Request
        AFNetwork.shared.apiRequest(param, isSpinnerNeeded: true, success: { [unowned self] (response) in
            
            
            guard let data = response else { return }
            let decoder = JSONDecoder()
            //check if the end point is weather or forecast
            if param.endpoint == apiEndPoints.weather.rawValue {
            //if weather
            do {
                //decode response into weatherModelViaLocation
                let model = try decoder.decode(weatherModelviaLocation.self, from: data)
                    self.weatherData = model
                    self.isSuccess = true
                
            } catch let err {
                print("Err", err)
            }
            
            completion()
                
            }
            else {
                //if forecast
                do {
                    //decode response into forecastModelViaLocation
                    let model = try decoder.decode(forecastModelviaLocation.self, from: data)
                    self.forecastData = model
                    self.isSuccess = true
                    
                    
                } catch let err {
                    print("Err", err)
                }
                
                completion()
                
            }
            
        
    }) { (error) in
            
            completion()
        }
    }
}
// MARK: - Parameters

extension WeatherManager {
    //params for weather via location
    func paramsForWeatherViaLocation(latitude : String , longitude : String, apiEndPoint : apiEndPoints)-> AFParam {
        
        let headers: [String : String]? = [:]
        let parameters = [
            "key": Constants.apiKey,
            "lat":latitude,
            "lon":longitude,
            
            
            ] as [String : AnyObject]
        
        let param = AFParam(endpoint:apiEndPoint.rawValue, params: parameters, headers: headers!, method: .get, parameterEncoding: URLEncoding.default, images: [])
        
        return param
    }
    
    //params for weather via City ID

    func paramsForWeatherViaCityID(cityID : String, apiEndPoint : apiEndPoints)-> AFParam {
        
        let headers: [String : String]? = [:]
        let parameters = [
            "key": Constants.apiKey,
            "id":cityID,
            
            
            ] as [String : AnyObject]
        
        let param = AFParam(endpoint: apiEndPoint.rawValue, params: parameters, headers: headers!, method: .get, parameterEncoding: URLEncoding(destination: .methodDependent), images: [])
        
        return param
    }
    //params for weather via zipcode

    func paramsForWeatherViaZipCode(zipCode: String , countryCode: String)-> AFParam{
        let headers: [String : String]? = [:]
        let parameters = [
            "key": Constants.apiKey,
            "zip": "\(zipCode),\(countryCode)",

            ] as [String : AnyObject]
        
        let param = AFParam(endpoint: apiEndPoints.weather.rawValue, params: parameters, headers: headers!, method: .get, parameterEncoding: URLEncoding(destination: .methodDependent), images: [])
        
        return param
    }
    
    //params for weather via city name

    func paramsForWeatherViaCityName(cityName: String , countryCode: String)-> AFParam{
        let headers: [String : String]? = [:]
        let parameters = [
            "key": Constants.apiKey,
            "q": "\(cityName),\(countryCode)",
            
            ] as [String : AnyObject]
        
        let param = AFParam(endpoint:Constants.baseURL + apiEndPoints.weather.rawValue, params: parameters, headers: headers!, method: .get, parameterEncoding: URLEncoding(destination: .methodDependent), images: [])
        
        return param
    }
   
    
}
// MARK: - Data manipulation.
extension WeatherManager {
    
    
    func getWeatherViaLocationData() -> weatherModelviaLocation?{
        return weatherData
    }
    func getForecastViaLocation()-> forecastModelviaLocation? {
        return forecastData
    }
    func getForcecastDays()-> [forecastModelList]? {
        
        //since the data is coming in hours.
        //total count is 40
        //Number of timestamps of day reported = 8
        // Since we need to display 5 days future forecast
        //we calculated it using %
        //get every 8th item (7 because of array starting from 0)
        
        var arrTruncated : [forecastModelList]?
        
        guard let data = forecastData , let arrList = data.list else {
            return arrTruncated
        }
        arrTruncated = [forecastModelList]()

        for (index, _) in (arrList.enumerated()) {
            
            if index % 7 == 0 {
                arrTruncated?.append(arrList[index])
            }
        }

        return arrTruncated
        
    }

}
