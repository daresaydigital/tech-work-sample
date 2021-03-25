//
//  NetworkLogger.swift
//  
//
//  Created by Andrian Sergheev on 2019-02-25.
//  Copyright © 2019 Andrian Sergheev. All rights reserved.
//

import Foundation

final class NetworkLogger {
	static func log(request: URLRequest) {

		print("\n - - - - - - - - - - OUTGOING - - - - - - - - - - \n")
		defer { print("\n - - - - - - - - - -  END - - - - - - - - - - \n") }

		let urlAsString = request.url?.absoluteString ?? ""
		let urlComponents = NSURLComponents(string: urlAsString)

		let method = request.httpMethod != nil ? "\(request.httpMethod ?? "")" : ""
		let path = "\(urlComponents?.path ?? "")"
		let query = "\(urlComponents?.query ?? "")"
		let host = "\(urlComponents?.host ?? "")"

		var logOutput = """
		\(urlAsString) \n\n
		\(method) \(path)?\(query) HTTP/1.1 \n
		HOST: \(host)\n
		"""
		for (key, value) in request.allHTTPHeaderFields ?? [:] {
			logOutput += "\(key): \(value) \n"
		}
		if let body = request.httpBody {
			logOutput += "\n \(NSString(data: body, encoding: String.Encoding.utf8.rawValue) ?? "")"
		}

		print(logOutput)
	}

	static func logResponse(data: Data?, response: HTTPURLResponse?, error: Error?) {

		print("\n - - - - - - - - - - IN - - - - - - - - - - \n")
		defer { print("\n - - - - - - - - - -  END - - - - - - - - - - \n") }

		let urlString = response?.url?.absoluteString
		let components = NSURLComponents(string: urlString ?? "")

		let path = "\(components?.path ?? "")"
		let query = "\(components?.query ?? "")"

		var responseLog: String = ""
		if let urlString = urlString {
			responseLog += "\(urlString)"
			responseLog += "\n\n"
		}

		if let statusCode =  response?.statusCode {
			responseLog += "HTTP \(statusCode) \(path)?\(query)\n"
		}
		if let host = components?.host {
			responseLog += "Host: \(host)\n"
		}
		for (key, value) in response?.allHeaderFields ?? [:] {
			responseLog += "\(key): \(value)\n"
		}
		if let body = data {
			responseLog += "\n\(String(describing: NSString(data: body, encoding: String.Encoding.utf8.rawValue)))\n"
		}
		if error != nil {
			responseLog += "\nError: \(String(describing: error?.localizedDescription))\n"
		}
		print(responseLog)
	}
}
