//
//	forecastModelCity.swift
//
//	Create by Hamza Khan on 21/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class forecastModelCity : Codable {

	let coord : forecastModelCoord?
	let country : String?
	let id : Int?
	let name : String?


	enum CodingKeys: String, CodingKey {
		case coord
		case country = "country"
		case id = "id"
		case name = "name"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		coord = try values.decodeIfPresent(forecastModelCoord.self, forKey: .coord)  //?? forecastModelCoord()
		country = try values.decodeIfPresent(String.self, forKey: .country) ?? String()
		id = try values.decodeIfPresent(Int.self, forKey: .id) ?? Int()
		name = try values.decodeIfPresent(String.self, forKey: .name) ?? String()
	}


}