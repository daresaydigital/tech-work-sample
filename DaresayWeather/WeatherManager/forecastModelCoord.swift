//
//	forecastModelCoord.swift
//
//	Create by Hamza Khan on 21/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class forecastModelCoord : Codable {

	let lat : Float?
	let lon : Float?


	enum CodingKeys: String, CodingKey {
		case lat = "lat"
		case lon = "lon"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		lat = try values.decodeIfPresent(Float.self, forKey: .lat) ?? Float()
		lon = try values.decodeIfPresent(Float.self, forKey: .lon) ?? Float()
	}


}