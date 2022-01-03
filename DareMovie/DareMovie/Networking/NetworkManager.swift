//
//  NetworkManager.swift
//  DareMovie
//
//  Created by Emran on 1/2/22.
//

import Foundation

public class NetworkManager {
    
    private let session: URLSession
    
    init() {
        let config = URLSessionConfiguration.default
        config.requestCachePolicy = .reloadIgnoringLocalCacheData
        config.urlCache = nil
        session = URLSession.init(configuration: config)
    }
    
    func fetchPopular(urlRouter: URLRouter, completionHandler: @escaping (PopularResponseModel?) -> Void) {
        let task = session.dataTask(with: urlRouter.url) { (data, response, error) in
                if let err = error {
                    print("An Error Occured \(err.localizedDescription)")
                    completionHandler(nil)
                    return
                }
                guard let mime = response?.mimeType, mime == "application/json" else {
                    print("Wrong MIME type!")
                    completionHandler(nil)
                    return
                }
                if let jsonData = data {
                    do {
                        let decoder = JSONDecoder()
                        decoder.keyDecodingStrategy = .convertFromSnakeCase
                        let decodedPopularModel = try decoder.decode(PopularResponseModel.self, from: jsonData)
                        completionHandler(decodedPopularModel)
                    } catch {
                        print("JSON error: \(error.localizedDescription)")
                    }
                }
            }
            task.resume()
    }
}
