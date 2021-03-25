//
//  MultiPartParameterEncoder.swift
//  
//
//  Created by Andrian Sergheev on 2019-08-09.
//  Copyright © 2019 Andrian Sergheev. All rights reserved.
//

import Foundation

public struct MultiPartParameterEncoder: ParameterEncoder {

	public func encode(urlRequest: inout URLRequest, with parameters: Parameters) throws {

		let boundary = "\(UUID().uuidString)"
		urlRequest.setValue("multipart/form-data; boundary=\(boundary)", forHTTPHeaderField: "Content-Type")

		// find the multipart data
		let multipartData = parameters.filter { $1 is Data }
		// make a copy of the params
		var parametersCopy = parameters
		// remove the multipart data from the copy
		multipartData.forEach {parametersCopy.removeValue(forKey: $0.key)}
		// get the first value from the multipart
		let first = multipartData.first { $0.value is Data }

		guard let params = parametersCopy as? [String: String],
			let data = first?.value as? Data else { throw NetworkError.encodingFailed }

		urlRequest.httpBody = createBody(parameters: params,
										 boundary: boundary,
										 data: data,
										 mimeType: "image/png",
										 filename: "image.png")
	}

	private func createBody(parameters: [String: String],
							boundary: String,
							data: Data,
							mimeType: String,
							filename: String) -> Data {
		let body = NSMutableData()

		let boundaryPrefix = "--\(boundary)\r\n"

		for (key, value) in parameters {
			body.appendString(boundaryPrefix)
			body.appendString("Content-Disposition: form-data; name=\"\(key)\"\r\n\r\n")
			body.appendString("\(value)\r\n")
		}

		body.appendString(boundaryPrefix)
		body.appendString("Content-Disposition: form-data; name=\"file\"; filename=\"\(filename)\"\r\n")
		body.appendString("Content-Type: \(mimeType)\r\n\r\n")
		body.append(data)
		body.appendString("\r\n")
		body.appendString("--".appending(boundary.appending("--")))

		return body as Data
	}
}

extension NSMutableData {
	func appendString(_ string: String) {
		guard
			let data = string.data(using: String.Encoding.utf8, allowLossyConversion: false)
			else { fatalError() }
		append(data)
	}
}
