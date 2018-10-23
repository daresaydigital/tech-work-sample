//
//	citiesModel .swift
//
//	Create by Hamza Khan on 22/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class citiesModel  : Codable {

	let cities : [citiesModelCity]?


	enum CodingKeys: String, CodingKey {
		case cities = "cities"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		cities = try values.decodeIfPresent([citiesModelCity].self, forKey: .cities) ?? [citiesModelCity]()
	}


}