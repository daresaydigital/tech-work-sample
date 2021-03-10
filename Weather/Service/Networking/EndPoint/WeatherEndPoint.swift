//
//  WeatherEndPoint.swift
//  NetworkLayer
//
//

import Foundation

public enum WeatherAPI {
    case weather(latitude: Float, longitude: Float, apiKey: String)
    case forecast(latitude: Float, longitude: Float, apiKey: String)
    case forecastDaily(latitude: Float, longitude: Float, apiKey: String)
}

extension WeatherAPI: EndPointType {
    /** API base urls. */
    var environmentBaseURL : String {
        switch NetworkManager.environment {
        case .production: return "http://worksample-api.herokuapp.com/"
        default: return "http://worksample-api.herokuapp.com/"
        }
    }
    
    var baseURL: URL {
        guard let url = URL(string: environmentBaseURL) else { fatalError("baseURL could not be configured.")}
        return url
    }
    /** API path for specific request. */
    var path: String {
        switch self {
        case .weather:
            return "weather"
        case .forecast:
            return "forecast"
        case .forecastDaily:
            return "forecast/daily"
        }
        
    }
    
    var httpMethod: HTTPMethod {
        switch self {
        default:
            return .get
        }
    }
    
    /** generate task based on requested Weather's API. */
    var task: HTTPTask {
        switch self {
        case .weather(let latitude, let longitude, let apiKey):
            return .requestParametersAndHeaders(bodyParameters: nil,
                                                bodyEncoding: .urlEncoding,
                                                urlParameters: ["lat" : latitude,
                                                                "lon" : longitude,
                                                                "key" : apiKey],
                                                additionHeaders: nil)
        case .forecast(let latitude, let longitude, let apiKey):
            return .requestParametersAndHeaders(bodyParameters: nil,
                                                bodyEncoding: .urlEncoding,
                                                urlParameters: ["lat" : latitude,
                                                                "lon" : longitude,
                                                                "key" : apiKey],
                                                additionHeaders: nil)
        case .forecastDaily(let latitude, let longitude, let apiKey):
            return .requestParametersAndHeaders(bodyParameters: nil,
                                                bodyEncoding: .urlEncoding,
                                                urlParameters: ["lat" : latitude,
                                                                "lon" : longitude,
                                                                "key" : apiKey],
                                                additionHeaders: nil)
        }
    }
    
    var headers: HTTPHeaders? {
        return nil
    }
}



