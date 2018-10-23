//
//	forecastModelSy.swift
//
//	Create by Hamza Khan on 21/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class forecastModelSy : Codable {

	let pod : String?


	enum CodingKeys: String, CodingKey {
		case pod = "pod"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		pod = try values.decodeIfPresent(String.self, forKey: .pod) ?? String()
	}


}