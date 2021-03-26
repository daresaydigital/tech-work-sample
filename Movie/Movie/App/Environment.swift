//
//  Environment.swift
//  Movie
//
//  Created by Adrian Sergheev on 2021-03-25.
//

import Foundation

/*
 inspired from pointfree.co
 https://vimeo.com/291588126
 */

// swiftlint:disable all
var Current = Environment()

struct Environment {
    let token = """
0e3d23cbe1ef8e612bd89882bbf65290
"""
    let locale = "en-US"
    let networkEnvironment: NetworkEnvironment = .staging
}
// swiftlint:enable all
