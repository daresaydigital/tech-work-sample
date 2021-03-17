//
//	citiesModelStat.swift
//
//	Create by Hamza Khan on 22/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class citiesModelStat : Codable {

	let level : Float?
	let population : Int?


	enum CodingKeys: String, CodingKey {
		case level = "level"
		case population = "population"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		level = try values.decodeIfPresent(Float.self, forKey: .level) ?? Float()
		population = try values.decodeIfPresent(Int.self, forKey: .population) ?? Int()
	}


}