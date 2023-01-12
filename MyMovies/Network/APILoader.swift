//
//  APILoader.swift
//  MyMovies
//
//  Created by Caio dos Santos Ambrosio on 1/10/23.
//

import Foundation

struct APILoader<T: APIHandler> {
    let apiHandler: T
    let urlSession: URLSession

    init(apiHandler: T, urlSession: URLSession = .shared) {
        self.apiHandler = apiHandler
        self.urlSession = urlSession
    }

    func loadAPIRequest(requestData: T.RequestDataType, completion: @escaping (T.ResponseDataType?, ServiceError?) -> ()) {
        guard let urlRequest = apiHandler.makeRequest(from: requestData) else {
            completion(nil, ServiceError(message: "Error when performing request"))
            return
        }

        urlSession.dataTask(with: urlRequest) { data, response, error in
            guard let httpResponse = response as? HTTPURLResponse else {
                completion(nil, ServiceError(message: "Error when performing request"))
                return
            }

            guard error == nil,
                  let responseData = data else {
                    completion(
                        nil,
                        ServiceError(message: "Service Error: \(error?.localizedDescription ?? "Unknow Error")")
                    )
                    return
                }

            do {
                let parsedResponse = try self.apiHandler.parseResponse(data: responseData, response: httpResponse)
                completion(parsedResponse, nil)
            } catch {
                if error is ServiceError {
                    completion(nil, error as? ServiceError)
                } else {
                    completion(
                        nil,
                        ServiceError(message: "Error when decoding json: \(error.localizedDescription)")
                    )
                }
            }
        }.resume()
    }
}
