//
//  Bundle+info.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import Foundation

public struct InfoPlistKey {
    static let baseAPIURL = "BaseAPIURL"
    static let APIAccessToken = "APIAccessToken"
}

extension Bundle {
    public func load<T>(_ nibName: String = String(describing: T.self), owner: Any? = nil) -> T {
        return loadNibNamed(nibName, owner: owner, options: nil)?.first as! T
    }
    
    public func info(for key: String) -> String! {
        guard let value = infoDictionary?[key] else {
            return nil
        }
        return (value as! String).replacingOccurrences(of: "\\", with: "")
    }
    
    public func read(fileName: String, type: String) -> String? {
        autoreleasepool {
            if let path = self.path(forResource: fileName, ofType: type) {
                do {
                    let data = try Data(contentsOf: URL(fileURLWithPath: path), options: .mappedIfSafe)
                    return String(data: data, encoding: .utf8)
                } catch {
                    return nil
                }
            } else {
                return nil
            }
        }
    }
}
