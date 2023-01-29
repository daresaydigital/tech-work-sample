//
//  LoggerHelper.swift
//  DarsayTechTest
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import OSLog
import Foundation

protocol LogNetworkProtocol {
    func log(_ response: HTTPURLResponse?, data: Data?, error: Error?, HTTPMethod: String?)
}

final class LoggerHelper: LogNetworkProtocol {
    
    static let shared = LoggerHelper()
    
    let ENABLELOG = true
    
    func log(_ response: HTTPURLResponse? = nil, data: Data? = nil, error: Error? = nil, HTTPMethod: String? = nil) {
        guard ENABLELOG else { return }
        
        print("\n🔵 ========== Start logResponse ========== 🔵")
        defer {
            print("🟦 ========== End logResponse ========== 🟦\n")
        }
        guard let response = response else {
            print("==", "❌ NULL Response ERROR: ❌")
            return
        }
        if let url = response.url?.absoluteString {
            print("==", "Request URL: `\(url)`")
            print("==", "Response CallBack Status Code: `\(response.statusCode)`")
        } else {
            print("==", "❌ LOG ERROR: ❌")
            print("==", "Empty URL")
        }
        if let method = HTTPMethod {
            print("==", "Request HTTPMethod: `\(method)`")
        }
        if let error = error {
            print("==", "❌ GOT URL REQUEST ERROR: ❌")
            print(error)
        }
        guard let data = data else {
            print("==", "❌ Empty Response ERROR: ❌")
            return
        }
        print("==", "✅ Response CallBack Data: ✅")
        if let json = data.prettyPrintedJSONString {
            print(json)
        } else {
            let responseDataString: String = String(data: data, encoding: .utf8) ?? "BAD ENCODING"
            print(responseDataString)
        }
    }
}
