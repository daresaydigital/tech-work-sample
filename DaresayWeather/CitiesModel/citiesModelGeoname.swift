//
//	citiesModelGeoname.swift
//
//	Create by Hamza Khan on 22/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class citiesModelGeoname : Codable {

	let cl : String?
	let code : String?
	let parent : Int?


	enum CodingKeys: String, CodingKey {
		case cl = "cl"
		case code = "code"
		case parent = "parent"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		cl = try values.decodeIfPresent(String.self, forKey: .cl) ?? String()
		code = try values.decodeIfPresent(String.self, forKey: .code) ?? String()
		parent = try values.decodeIfPresent(Int.self, forKey: .parent) ?? Int()
	}


}