//
//  NetworkManager.swift
//  NetworkLayer
//
//

import Foundation

enum NetworkResponse:String {
    case success
    case authenticationError = "network_authentication_error"
    case badRequest = "network_bad_request"
    case outdated = "network_outdated"
    case failed = "network_failed"
    case noData = "network_no_data"
    case unableToDecode = "network_unable_to_decode"
    case noResponseObject = "network_no_response_object"
}

enum Result<String>{
    case success
    case failure(String)
}

protocol WeatherNetworkManager {
    var apiKey: String { get set }
    
    init(apiKey: String, environment: NetworkEnvironment)
    /**
     Fetch weather data from Weather API server
     - Parameter latitude: user's latitude position.
     - Parameter longitude: user's longitude position.
     - Parameter completion: block to handle the fetch results
     */
    func fetchWeather(latitude: Double, longitude: Double, completion: @escaping (_ response: WeatherResponse?,_ error: String?)->())
    
    func fetchForecast(latitude: Double, longitude: Double, completion: @escaping (_ response: ForecastResponse?,_ error: String?)->())
}

class NetworkManager: WeatherNetworkManager {
    static var environment : NetworkEnvironment = .production
    var apiKey: String
    let router = Router<WeatherAPI>()
    
    required init(apiKey: String, environment: NetworkEnvironment) {
        self.apiKey = apiKey
        NetworkManager.environment = environment
    }
    
    /**
     Fetch weather data from Weather API server
     - Parameter lat: user's latitude position.
     - Parameter lon: user's longitude position.
     - Parameter completion: block to handle the fetch results
     */
    func fetchWeather(latitude: Double, longitude: Double, completion: @escaping (_ response: WeatherResponse?,_ error: String?)->()) {
        router.request(.weather(latitude: Float(latitude), longitude: Float(longitude), apiKey: apiKey)) { data, response, error in
            let result = self.handleNetworkResponse(data: data, response: response, error: error)
            switch result{
            case .success:
                do {
                    /*let jsonData = try JSONSerialization.jsonObject(with: data!, options: .mutableContainers)
                    print(jsonData)*/
                    let weatherResponse = try JSONDecoder().decode(WeatherResponse.self, from: data!)
                    completion(weatherResponse, nil)
                }catch {
                    print(error)
                    completion(nil, NetworkResponse.unableToDecode.rawValue)
                }
            case .failure(let error):
                completion(nil, error)
            }
        }
    }
    
    func fetchForecast(latitude: Double, longitude: Double, completion: @escaping (_ response: ForecastResponse?,_ error: String?)->()) {
        router.request(.forecast(latitude: Float(latitude), longitude: Float(longitude), apiKey: apiKey)) { data, response, error in
            let result = self.handleNetworkResponse(data: data, response: response, error: error)
            switch result{
            case .success:
                do {
                    /*let jsonData = try JSONSerialization.jsonObject(with: data!, options: .mutableContainers)
                     print(jsonData)*/
                    let forecastResponse = try JSONDecoder().decode(ForecastResponse.self, from: data!)
                    completion(forecastResponse, nil)
                }catch {
                    print(error)
                    completion(nil, NetworkResponse.unableToDecode.rawValue)
                }
            case .failure(let error):
                completion(nil, error)
            }
        }
    }
    
    func cancelLastRequest() {
        router.cancel()
    }
    
    /**
     Handle all basic network error responses
     */
    fileprivate func handleNetworkResponse(data: Data?, response: URLResponse?, error: Error?) -> Result<String>{
        if error != nil {
            return .failure("Please check your network connection.")
        }
        
        if let response = response as? HTTPURLResponse {
            switch response.statusCode {
            case 200...299:
                print("HTTPURLResponse: success")
            case 401...500: return .failure(NetworkResponse.authenticationError.rawValue)
            case 501...599: return .failure(NetworkResponse.badRequest.rawValue)
            case 600: return .failure(NetworkResponse.outdated.rawValue)
            default: return .failure(NetworkResponse.failed.rawValue)
            }
        }
        
        guard data != nil else {
            return .failure(NetworkResponse.noData.rawValue)
        }
        
        return .success
    }
}
