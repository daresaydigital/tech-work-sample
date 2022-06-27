//
//  ServerModels.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import Foundation

extension Array where Element: Encodable {
    var serializedArrayData: Data? {
        do {
            return try JSONEncoder().encode(self)
        } catch {
            print("❌ Could not serialize server model.\n\(error) ❌")
            return nil
        }
    }
}

extension Encodable {
    var serializedData: Data? {
        do {
            return try JSONEncoder().encode(self)
        } catch {
            print("❌ Could not serialize server model.\n\(error) ❌")
            return nil
        }
    }
}

protocol ServerModel: Codable { }

struct ServerData<T: Codable> {
    let httpStatus: Int?
    let model: T
    let httpHeaders: [AnyHashable: Any]
}

enum ServerModels {
    // This is a placeholder enumeration to help manage different type of models
}

extension Encodable {
    
    func prettyJSON() -> String {
        
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        
        do {
            guard let prettyJSONString = String(data: try encoder.encode(self), encoding: .utf8) else {
                print("❌ Could not transfer to String ❌")
                return ""
            }
            return prettyJSONString
        } catch {
            print("❌ Could not serialize model ❌")
            return ""
        }
        
    }
    
    func jsonString() -> String {
        
        let encoder = JSONEncoder()
        
        do {
            guard let prettyJSONString = String(data: try encoder.encode(self), encoding: .utf8) else {
                print("❌ Could not convert to String ❌")
                return ""
            }
            return prettyJSONString
        } catch {
            print("❌ Could not serialize model ❌")
            return ""
        }
    }
    
    func jsonStringData() -> Data {
        
        let encoder = JSONEncoder()
        
        do {
            guard let prettyJSONString = String(data: try encoder.encode(self), encoding: .utf8) else {
                print("❌ Could not transfer to String ❌")
                return Data()
            }
            return prettyJSONString.data(using: .utf8) ?? Data()
        } catch {
            print("❌ Could not serialize model ❌")
            return Data()
        }
    }
    
}
