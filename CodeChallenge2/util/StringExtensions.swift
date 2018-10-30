//
//  StringExtensions.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import Foundation


extension String{
    
    /// Assuming that the given string is a key in a Localizable file it will give back the value for that key
    var localized: String{
        return NSLocalizedString(self, comment: "")
    }
}
