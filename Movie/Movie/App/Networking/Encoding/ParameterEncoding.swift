//
//  ParameterEncoding.swift
//  
//
//  Created by Andrian Sergheev on 2019-02-25.
//  Copyright © 2019 Andrian Sergheev. All rights reserved.
//

import Foundation

public typealias Parameters = [String: Any]

public protocol ParameterEncoder {
	func encode(urlRequest: inout URLRequest, with parameters: Parameters) throws
}

public enum NetworkError: String, Error {
	case parametersNil = "Parameters were nil."
	case encodingFailed = "Parameter encoding failed."
	case missingURL = "URL is nil."
}

public enum ParameterEncoding {

	case none
	case urlEncoding
	case jsonEncoding
	case urlAndjsonEncoding
	case multiPartEncoding

	public func encode(urlRequest: inout URLRequest,
					   bodyParameters: Parameters?,
					   urlParameters: Parameters?) throws {
		do {
			switch self {
			case .none:
				break
			case .urlEncoding:
				guard let urlParameters = urlParameters else { return }
				try URLParameterEncoder().encode(urlRequest: &urlRequest, with: urlParameters)
			case .jsonEncoding:
				guard let bodyParameters = bodyParameters else { return }
				try JSONParameterEncoder().encode(urlRequest: &urlRequest, with: bodyParameters)
			case .urlAndjsonEncoding:
				guard let bodyParameters = bodyParameters,
					  let urlParameters = urlParameters else { return }
				try URLParameterEncoder().encode(urlRequest: &urlRequest, with: urlParameters)
				try JSONParameterEncoder().encode(urlRequest: &urlRequest, with: bodyParameters)
			case .multiPartEncoding:
				guard let bodyParameters = bodyParameters else { return }
				try MultiPartParameterEncoder().encode(urlRequest: &urlRequest, with: bodyParameters)
			}
		} catch let error {
			print("Encoding error: \(error)")
			throw error
		}
	}

}
