//
//	forecastDailyList.swift
//
//	Create by Hamza Khan on 22/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class forecastDailyList : Codable {

	let dt : Int?
	let humidity : Int?
	let pressure : Float?
	let temp : forecastDailyTemp?
	let weather : [forecastDailyWeather]?


	enum CodingKeys: String, CodingKey {
		case dt = "dt"
		case humidity = "humidity"
		case pressure = "pressure"
		case temp
		case weather = "weather"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		dt = try values.decodeIfPresent(Int.self, forKey: .dt) ?? Int()
		humidity = try values.decodeIfPresent(Int.self, forKey: .humidity) ?? Int()
		pressure = try values.decodeIfPresent(Float.self, forKey: .pressure) ?? Float()
		temp = try values.decodeIfPresent(forecastDailyTemp.self, forKey: .temp)  //?? forecastDailyTemp()
		weather = try values.decodeIfPresent([forecastDailyWeather].self, forKey: .weather) ?? [forecastDailyWeather]()
	}


}