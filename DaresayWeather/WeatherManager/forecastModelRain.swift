//
//	forecastModelRain.swift
//
//	Create by Hamza Khan on 21/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class forecastModelRain : Codable {
	
    let rain3h : Float?



	enum CodingKeys: String, CodingKey {
		case rain3h = "3h"

	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		rain3h = try values.decodeIfPresent(Float.self, forKey: .rain3h) ?? Float()

	}


}
