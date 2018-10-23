//
//	weatherModelviaLocation.swift
//
//	Create by Hamza Khan on 21/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class weatherModelviaLocation : Codable {

	let base : String?
	let clouds : weatherModelCloud?
	let cod : Int?
	let coord : weatherModelCoord?
	let dt : Int?
	let id : Int?
	let main : weatherModelMain?
	let name : String?
	let sys : weatherModelSy?
	let visibility : Int?
	let weather : [weatherModelWeather]?
	let wind : weatherModelWind?


	enum CodingKeys: String, CodingKey {
		case base = "base"
		case clouds
		case cod = "cod"
		case coord
		case dt = "dt"
		case id = "id"
		case main
		case name = "name"
		case sys
		case visibility = "visibility"
		case weather = "weather"
		case wind
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		base = try values.decodeIfPresent(String.self, forKey: .base) ?? String()
		clouds = try values.decodeIfPresent(weatherModelCloud.self, forKey: .clouds)  //?? weatherModelCloud()
		cod = try values.decodeIfPresent(Int.self, forKey: .cod) ?? Int()
		coord = try values.decodeIfPresent(weatherModelCoord.self, forKey: .coord)  //?? weatherModelCoord()
		dt = try values.decodeIfPresent(Int.self, forKey: .dt) ?? Int()
		id = try values.decodeIfPresent(Int.self, forKey: .id) ?? Int()
		main = try values.decodeIfPresent(weatherModelMain.self, forKey: .main)  //?? weatherModelMain()
		name = try values.decodeIfPresent(String.self, forKey: .name) ?? String()
		sys = try values.decodeIfPresent(weatherModelSy.self, forKey: .sys)  //?? weatherModelSy()
		visibility = try values.decodeIfPresent(Int.self, forKey: .visibility) ?? Int()
		weather = try values.decodeIfPresent([weatherModelWeather].self, forKey: .weather) ?? [weatherModelWeather]()
		wind = try values.decodeIfPresent(weatherModelWind.self, forKey: .wind)  //?? weatherModelWind()
	}


}