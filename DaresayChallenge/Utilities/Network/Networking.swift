//
//  Networking.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import Foundation
import PromiseKit

protocol ServerProtocol {
    var session: URLSession! { get set }
    
    var dispatchQueue: DispatchQueue! { get set }
    
    var validResponseCodes: [Int]! { get set }
    
    func perform<T: Codable>(request: HTTPRequest) -> Promise<ServerData<T>>
}

final class MovieServer: NSObject, ServerProtocol {
    
    var session: URLSession!
    
    var dispatchQueue: DispatchQueue!
    
    var validResponseCodes: [Int]!
    
    static let shared = MovieServer()
    
    private override init() {
        super.init()
        self.validResponseCodes = [200, 201]
        self.dispatchQueue = .main
        self.session = URLSession.shared
    }
    
    public init(session: URLSession, dispatchQueue: DispatchQueue, validResponseCodes: [Int]) {
        self.session = session
        self.dispatchQueue = dispatchQueue
        self.validResponseCodes = validResponseCodes
    }
    
    func perform<T: Codable>(request: HTTPRequest) -> Promise<ServerData<T>> {
        return Promise { seal in
            
            func reject(_ error: APIError) {
                print(request)
                print("⚠️ at: \(error) ⚠️")
                dispatchQueue.async {
                    seal.reject(error)
                }
                
            }
            
            // Check to see if we have a valid request
            guard let urlRequest = request.request as URLRequest? else {
                return reject(APIError.invalidRequest)
            }
            
            // Generate the task
            let dataTask = session.dataTask(with: urlRequest) { [weak self] (responseData, urlResponse, error) -> Void in
                
                guard let self = self else { return }
                
                // If error is not nil, it means there was an error in network connection.
                if let netError = error {
                    return reject(APIError.networkError(netError))
                }
                
                // Convert the response to usable response.
                guard let httpResponse = urlResponse as? HTTPURLResponse else {
                    return reject(APIError.invalidResponse)
                }
                
                let httpHeader = httpResponse.allHeaderFields
                
                if var serverTime = httpHeader["ServerTime"] as? String {
                    serverTime.removeFirst()
                    serverTime.removeLast()
                    let serverTimeWithoutTimeZone = serverTime.components(separatedBy: "+")
                    if let date = DateFormatter.iso8601Full.date(from: serverTimeWithoutTimeZone[0]) {
                        ServerTime.shared.now = date
                    }
                }
                
                // Check to see if the response code is what we were waiting for.
                guard self.validResponseCodes.contains(httpResponse.statusCode) else {
                    let errorModel = self.generateServerError(from: urlResponse, responseData: responseData, status: httpResponse.statusCode)
                    return reject(APIError.invalidResponseCode(errorModel))
                }
                
                // Get the data
                guard var responseData = responseData else {
                    return reject(APIError.noResponseData)
                }
                
                // Parse Response
                do {
                    if responseData.isEmpty || T.self == ServerModels.EmptyServerModel.self {
                        responseData = "{}".data(using: .utf8) ?? Data()
                    }
                    
                    let decoder = JSONDecoder()
                    decoder.dateDecodingStrategy = .formatted(DateFormatter.iso8601Full)
                    decoder.keyDecodingStrategy = .convertFromSnakeCase
                    let responseObject = try decoder.decode(T.self, from: responseData)
                    
                    let serverData = ServerData(httpStatus: httpResponse.statusCode, model: responseObject, httpHeaders: httpHeader)
                    
                    self.dispatchQueue.async {
                        seal.fulfill(serverData)
                    }
                    
                } catch {
                    return reject(APIError.parserFailed(error))
                }
            }
            dataTask.resume()
        }
    }
    
    private func generateServerError(from urlResponse: URLResponse?, responseData: Data?, status: Int) -> ServerErrorModel {
        
        let url = (urlResponse as? HTTPURLResponse)?.url?.absoluteString
        
        func fallbackErrorModel() -> ServerErrorModel {
            
            return ServerErrorModel(statusCode: status, url: url, messages: nil)
        }
        
        guard let responseData = responseData else {
            return fallbackErrorModel()
        }
        
        do {
            let errorMessages = try JSONDecoder().decode([ServerErrorModel.ServerErrorMessage].self, from: responseData)
            
            let errorModel = ServerErrorModel(statusCode: status, url: url, messages: errorMessages)
            return errorModel
        } catch {
            print("❌ Could not parse server error message!\n\(error) ❌")
            let errorString = String(data: responseData, encoding: .utf8)
            print("❌ ++++++++ [\(url ?? "")] ERROR: \(errorString ?? "") ❌")
            return fallbackErrorModel()
        }
    }
}

class ServerTime {
    var now: Date?
    static let shared = ServerTime()
}
