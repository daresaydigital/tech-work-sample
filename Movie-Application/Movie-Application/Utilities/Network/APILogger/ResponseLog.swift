//
//  ResponseLog.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/26/22.
//

import Foundation

struct MovieResponseLog: URLRequestLoggableProtocol {
    
    let ENABLELOG = true
    
    func logResponse(_ response: HTTPURLResponse?, data: Data?, error: Error?, HTTPMethod: String?) {
        guard ENABLELOG else { return }
        print("\n💛 ========== Start logResponse ========== 💛")
        defer {
            print("🟡 ========== End logResponse ========== 🟡\n")
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

extension Data {
    var prettyPrintedJSONString: String? {
        guard let object = try? JSONSerialization.jsonObject(with: self, options: []),
              let data = try? JSONSerialization.data(withJSONObject: object, options: [.prettyPrinted]),
              let prettyPrintedString = NSString(data: data, encoding: String.Encoding.utf8.rawValue) as String? else { return nil }
            
        return prettyPrintedString
    }
}
