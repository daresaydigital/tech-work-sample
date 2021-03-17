//
//	forecastModelviaLocation.swift
//
//	Create by Hamza Khan on 21/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class forecastModelviaLocation : Codable {

	let city : forecastModelCity?
	let cnt : Int?
	let cod : String?
	let list : [forecastModelList]?
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
		city = try values.decodeIfPresent(forecastModelCity.self, forKey: .city)  //?? forecastModelCity()
		cnt = try values.decodeIfPresent(Int.self, forKey: .cnt) ?? Int()
		cod = try values.decodeIfPresent(String.self, forKey: .cod) ?? String()
		list = try values.decodeIfPresent([forecastModelList].self, forKey: .list) ?? [forecastModelList]()
		message = try values.decodeIfPresent(Float.self, forKey: .message) ?? Float()
	}


}