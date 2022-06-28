//
//  HTTPRequest.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import Foundation

typealias JSONDictionary = [String: Any]
typealias JSONArray = [JSONDictionary]

struct HTTPRequest {
    
    let httpMethod: HTTPMethod
    let requestURL: RequestURL
    let auth: MovieAuth
    let timeout: HTTPTimeOut
    
    // Will be omitted if request is not GET or DELETE
    let parameters: JSONDictionary?
    let bodyMessage: ServerModel?
    let serializedBody: Data?
    let headers: [String: String]?
    let accept: HttpContentType
    let contentType: HttpContentType
    
    init(method: HTTPMethod, url: RequestURL, auth: MovieAuth, parameters: JSONDictionary? = nil, bodyMessage: ServerModel? = nil, serializedBody: Data? = nil, headers: [String: String]? = nil, timeOut: HTTPTimeOut = .normal, acceptType: HttpContentType = .json, contentType: HttpContentType = .json) {
        
        self.httpMethod = method
        self.requestURL = url
        self.auth = auth
        self.timeout = timeOut
        self.parameters = parameters
        self.headers = headers
        self.accept = acceptType
        self.contentType = contentType
        self.bodyMessage = bodyMessage
        self.serializedBody = serializedBody
    }
    
    var request: URLRequest? {
        
        guard let url = self.requestURL.url else {
            return nil
        }
        
        var result = URLRequest(url: url as URL, cachePolicy: .useProtocolCachePolicy, timeoutInterval: self.timeout.rawValue)
        
        result.prepareRequestMethod(method: self.httpMethod)
        if let headers = self.headers {
            result.prepareHeaders(headers: headers)
        }
        result.prepareAcceptHeader(accept: self.accept)
        result.prepareContentTypeHeader(contentType: self.contentType)
        result.prepareAuthorization(auth: self.auth)
        result.prepareBaseHeaders(headers: headers)
        
        if self.httpMethod == HTTPMethod.GET || self.httpMethod == HTTPMethod.DELETE {
            if let params = self.parameters {
                result.prepareURLParameters(params: params, url: url)
            }
        } else {
            if let params = self.parameters {
                if self.contentType == .urlEncodedForm {
                    result.prepareURLEncodedForm(params: params)
                } else {
                    result.prepareURLParameters(params: params, url: url)
                }
            }
            if let bodyMessage = self.bodyMessage {
                result.prepareBody(bodyMessage: bodyMessage)
            } else if let serializedBody = self.serializedBody {
                result.prepareSerializedBody(body: serializedBody)
            }
        }
        
        if let headers = result.allHTTPHeaderFields {
            print(headers)
        }
        print(result)
        if let httpBody = result.httpBody {
            print(httpBody)
        }
        
        return result
    }
}

extension URLRequest {
    
    private func percentEscapeString(_ string: String) -> String {
        var characterSet = CharacterSet.alphanumerics
        characterSet.insert(charactersIn: "-._* ")
        
        return string
            .addingPercentEncoding(withAllowedCharacters: characterSet)?
            .replacingOccurrences(of: " ", with: "+")
            .replacingOccurrences(of: " ", with: "+", options: [], range: nil) ?? string
    }
    
    mutating func prepareAuthorization(auth: MovieAuth) {
        
        func addAuthHeader(token: String, type: TokenType) {
            let tokenString = "\(type.rawValue) \(token)"
            self.setValue(tokenString, forHTTPHeaderField: HTTPHeaders.Authorization)
        }
        
        switch auth {
        case .none:
            return
        case .open:
            return
        case .otp:
            let otpToken = APIKey.readAccessToken.rawValue
            print("** access token : \(otpToken)")
            addAuthHeader(token: otpToken, type: .Bearer)
        case .password:
            return
        case .custom(let customToken):
            addAuthHeader(token: customToken, type: .Bearer)
        }
    }
    
    mutating func prepareRequestMethod(method: HTTPMethod) {
        self.httpMethod = method.rawValue
    }
    
    mutating func prepareHeaders(headers: [String: String]) {
        for key in headers.keys {
            self.setValue(headers[key], forHTTPHeaderField: key)
        }
    }
    
    mutating func prepareAcceptHeader(accept: HttpContentType) {
        self.setValue(accept.value, forHTTPHeaderField: HTTPHeaders.Accept)
    }
    
    mutating func prepareContentTypeHeader(contentType: HttpContentType) {
        self.setValue(contentType.value, forHTTPHeaderField: HTTPHeaders.ContentType)
    }
    
    mutating func prepareBaseHeaders(headers: [String: String]?) {
        let currentHeaders = self.allHTTPHeaderFields?.keys.compactMap { $0 } ?? []
        baseRequestHeaders.forEach {
            if !currentHeaders.contains($0.key) {
                self.setValue($0.value, forHTTPHeaderField: $0.key)
            }
        }
    }
    
    mutating func prepareURLEncodedForm(params: JSONDictionary) {
        let parameterArray = params.map { arg -> String in
            let (key, value) = arg
            let val = "\(value)"
            return "\(key)=\(self.percentEscapeString(val))"
        }
        self.httpBody = parameterArray.joined(separator: "&").data(using: String.Encoding.utf8)
        
    }
    
    mutating func prepareURLParameters(params: JSONDictionary, url: URL) {
        var queryString = ""
        for key in params.keys {
            
            guard let val = params[key]  else {
                print("⚠️ Skipping value for key: \(key) ⚠️")
                continue
            }
            
            queryString += queryString.isEmpty ? "" : "&"
            queryString += key + "=" + String(describing: val)
            if let encodedQuery = queryString.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed) {
                queryString = encodedQuery
            }
        }
        
        let absoluteString = url.absoluteString
        
        let newUrlStr = absoluteString + "?" + queryString
        guard let newURL = URL(string: newUrlStr) else {
            print("❌ Invalid url: \(newUrlStr) ❌")
            return
        }
        
        self.url = newURL
    }
    
    mutating func prepareBody(bodyMessage: ServerModel) {
        guard let bodyData = bodyMessage.serializedData else {
            print("⚠️ Body message is empty. Skipping body value set. ⚠️")
            return
        }
        self.httpBody = bodyData
        self.setValue("\(bodyData.count)", forHTTPHeaderField: HTTPHeaders.ContentLength)
        
    }
    
    mutating func prepareSerializedBody(body: Data) {
        
        self.httpBody = body
        self.setValue("\(body.count)", forHTTPHeaderField: HTTPHeaders.ContentLength)
    }
    
}
