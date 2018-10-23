//
//	forecastDailyTemp.swift
//
//	Create by Hamza Khan on 22/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class forecastDailyTemp : Codable {

	let day : Float?
	let eve : Float?
	let max : Float?
	let min : Float?
	let morn : Float?
	let night : Float?


	enum CodingKeys: String, CodingKey {
		case day = "day"
		case eve = "eve"
		case max = "max"
		case min = "min"
		case morn = "morn"
		case night = "night"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		day = try values.decodeIfPresent(Float.self, forKey: .day) ?? Float()
		eve = try values.decodeIfPresent(Float.self, forKey: .eve) ?? Float()
		max = try values.decodeIfPresent(Float.self, forKey: .max) ?? Float()
		min = try values.decodeIfPresent(Float.self, forKey: .min) ?? Float()
		morn = try values.decodeIfPresent(Float.self, forKey: .morn) ?? Float()
		night = try values.decodeIfPresent(Float.self, forKey: .night) ?? Float()
	}


}