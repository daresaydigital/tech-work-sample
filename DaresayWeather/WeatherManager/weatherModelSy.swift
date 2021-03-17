//
//	weatherModelSy.swift
//
//	Create by Hamza Khan on 21/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class weatherModelSy : Codable {

	let country : String?
	let id : Int?
	let message : Float?
	let sunrise : Int?
	let sunset : Int?
	let type : Int?


	enum CodingKeys: String, CodingKey {
		case country = "country"
		case id = "id"
		case message = "message"
		case sunrise = "sunrise"
		case sunset = "sunset"
		case type = "type"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		country = try values.decodeIfPresent(String.self, forKey: .country) ?? String()
		id = try values.decodeIfPresent(Int.self, forKey: .id) ?? Int()
		message = try values.decodeIfPresent(Float.self, forKey: .message) ?? Float()
		sunrise = try values.decodeIfPresent(Int.self, forKey: .sunrise) ?? Int()
		sunset = try values.decodeIfPresent(Int.self, forKey: .sunset) ?? Int()
		type = try values.decodeIfPresent(Int.self, forKey: .type) ?? Int()
	}


}