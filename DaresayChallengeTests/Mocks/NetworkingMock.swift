//
//  NetworkingMock.swift
//  DaresayChallengeTests
//
//  Created by Keihan Kamangar on 2022-06-29.
//

import Foundation
@testable import DaresayChallenge
import PromiseKit

class NetworkingMock: ServerProtocol {
    var session: URLSession!
    
    var dispatchQueue: DispatchQueue!
    
    var validResponseCodes: [Int]!
    
    public init(session: URLSession, validResponseCodes: [Int], dispatchQueue: DispatchQueue) {
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
            
            // Generate the task
            let dataTask = session.dataTask(with: request.requestURL.url!) { [weak self] (responseData, _, error) -> Void in
                
                usleep(400)
                
                guard let self = self else { return }
                
                // If error is not nil, it means there was an error in network connection.
                if let netError = error {
                    return reject(APIError.networkError(netError))
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
                    
                    let serverData = ServerData(httpStatus: 200, model: responseObject, httpHeaders: [:])
                    
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
}
