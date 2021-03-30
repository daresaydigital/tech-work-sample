//
//  NSObject+Ext.swift
//  
//
//  Created by Andrian Sergheev on 2020-06-10.
//  Copyright © 2020 Andrian Sergheev. All rights reserved.
//

import Foundation.NSObject

extension NSObject {
	class var className: String {
		guard let name = NSStringFromClass(self).components(separatedBy: ".").last else {
			fatalError("Accessed wrong defined name of a class")
		}
		return name
	}
}
