//
//	weatherModelWeather.swift
//
//	Create by Hamza Khan on 21/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class weatherModelWeather : Codable {

	let descriptionField : String?
	let icon : String?
	let id : Int?
	let main : String?


	enum CodingKeys: String, CodingKey {
		case descriptionField = "description"
		case icon = "icon"
		case id = "id"
		case main = "main"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		descriptionField = try values.decodeIfPresent(String.self, forKey: .descriptionField) ?? String()
		icon = try values.decodeIfPresent(String.self, forKey: .icon) ?? String()
		id = try values.decodeIfPresent(Int.self, forKey: .id) ?? Int()
		main = try values.decodeIfPresent(String.self, forKey: .main) ?? String()
	}


}