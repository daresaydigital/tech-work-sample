//
//	forecastDailyviaLocation.swift
//
//	Create by Hamza Khan on 22/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class forecastDailyviaLocation : Codable {

	let city : forecastDailyCity?
	let cnt : Int?
	let cod : String?
	let list : [forecastDailyList]?
	let message : Float?


	enum CodingKeys: String, CodingKey {
		case city
		case cnt = "cnt"
		case cod = "cod"
		case list = "list"
		case message = "message"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		city = try values.decodeIfPresent(forecastDailyCity.self, forKey: .city)  //?? forecastDailyCity()
		cnt = try values.decodeIfPresent(Int.self, forKey: .cnt) ?? Int()
		cod = try values.decodeIfPresent(String.self, forKey: .cod) ?? String()
		list = try values.decodeIfPresent([forecastDailyList].self, forKey: .list) ?? [forecastDailyList]()
		message = try values.decodeIfPresent(Float.self, forKey: .message) ?? Float()
	}


}