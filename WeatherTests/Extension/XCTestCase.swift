//
//  XCTestCase.swift
//  UnitTests
//
//  Created by Christian  Huang on 01/09/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import XCTest

extension XCTestCase {
    
    // MARK: - Helper Methods
    
    func loadDataFromBundle(withName name: String, extension: String) ->  Data {
        let bundle = Bundle(for: classForCoder)
        let url = bundle.url(forResource: name, withExtension: `extension`)
        
        let data = try! Data(contentsOf: url!)
        return data
    }
    
    func loadJSONDictionaryFromBundle(withName name: String, extension: String) ->  Dictionary<String,AnyObject> {
        let bundle = Bundle(for: classForCoder)
        let url = bundle.url(forResource: name, withExtension: `extension`)
        
        let data = try! Data(contentsOf: url!)
        let jsonResult = try! JSONSerialization.jsonObject(with: data, options: [])
        let jsonDic = jsonResult as! Dictionary<String,AnyObject>
        return jsonDic
    }
    
    func loadJSONArrayFromBundle(withName name: String, extension: String) ->  [Dictionary<String,AnyObject>] {
        let bundle = Bundle(for: classForCoder)
        let url = bundle.url(forResource: name, withExtension: `extension`)
        
        let data = try! Data(contentsOf: url!)
        let jsonResult = try! JSONSerialization.jsonObject(with: data, options: [])
        let jsonDicArray = jsonResult as! [Dictionary<String,AnyObject>]
        return jsonDicArray
    }
    
    
}
