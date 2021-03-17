//
//	weatherModelWind.swift
//
//	Create by Hamza Khan on 21/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class weatherModelWind : Codable {

	let deg : Float?
	let speed : Float?


	enum CodingKeys: String, CodingKey {
		case deg = "deg"
		case speed = "speed"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		deg = try values.decodeIfPresent(Float.self, forKey: .deg) ?? Float()
		speed = try values.decodeIfPresent(Float.self, forKey: .speed) ?? Float()
	}


}