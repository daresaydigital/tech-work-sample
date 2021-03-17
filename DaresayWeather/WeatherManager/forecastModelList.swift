//
//	forecastModelList.swift
//
//	Create by Hamza Khan on 21/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class forecastModelList : Codable {

	let clouds : forecastModelCloud?
	let dt : Int?
	let dtTxt : String?
	let main : forecastModelMain?
	let rain : forecastModelRain?
	let sys : forecastModelSy?
	let weather : [forecastModelWeather]?
	let wind : forecastModelWind?


	enum CodingKeys: String, CodingKey {
		case clouds
		case dt = "dt"
		case dtTxt = "dt_txt"
		case main
		case rain
		case sys
		case weather = "weather"
		case wind
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		clouds = try values.decodeIfPresent(forecastModelCloud.self, forKey: .clouds)  //?? forecastModelCloud()
		dt = try values.decodeIfPresent(Int.self, forKey: .dt) ?? Int()
		dtTxt = try values.decodeIfPresent(String.self, forKey: .dtTxt) ?? String()
		main = try values.decodeIfPresent(forecastModelMain.self, forKey: .main)  //?? forecastModelMain()
		rain = try values.decodeIfPresent(forecastModelRain.self, forKey: .rain)  //?? forecastModelRain()
		sys = try values.decodeIfPresent(forecastModelSy.self, forKey: .sys)  //?? forecastModelSy()
		weather = try values.decodeIfPresent([forecastModelWeather].self, forKey: .weather) ?? [forecastModelWeather]()
		wind = try values.decodeIfPresent(forecastModelWind.self, forKey: .wind)  //?? forecastModelWind()
	}


}