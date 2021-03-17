//
//	citiesModelStation.swift
//
//	Create by Hamza Khan on 22/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class citiesModelStation : Codable {

	let dist : Int?
	let id : Int?
	let kf : Int?


	enum CodingKeys: String, CodingKey {
		case dist = "dist"
		case id = "id"
		case kf = "kf"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		dist = try values.decodeIfPresent(Int.self, forKey: .dist) ?? Int()
		id = try values.decodeIfPresent(Int.self, forKey: .id) ?? Int()
		kf = try values.decodeIfPresent(Int.self, forKey: .kf) ?? Int()
	}


}