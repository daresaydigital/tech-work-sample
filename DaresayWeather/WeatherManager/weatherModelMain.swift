//
//	weatherModelMain.swift
//
//	Create by Hamza Khan on 21/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class weatherModelMain : Codable {

	let humidity : Float?
	let pressure : Float?
	let temp : Float?
	let tempMax : Float?
	let tempMin : Float?


	enum CodingKeys: String, CodingKey {
		case humidity = "humidity"
		case pressure = "pressure"
		case temp = "temp"
		case tempMax = "temp_max"
		case tempMin = "temp_min"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		humidity = try values.decodeIfPresent(Float.self, forKey: .humidity) ?? Float()
		pressure = try values.decodeIfPresent(Float.self, forKey: .pressure) ?? Float()
		temp = try values.decodeIfPresent(Float.self, forKey: .temp) ?? Float()
		tempMax = try values.decodeIfPresent(Float.self, forKey: .tempMax) ?? Float()
		tempMin = try values.decodeIfPresent(Float.self, forKey: .tempMin) ?? Float()
	}


}
