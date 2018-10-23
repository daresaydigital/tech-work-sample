//
//	citiesModelLang.swift
//
//	Create by Hamza Khan on 22/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class citiesModelLang : Codable {

	let en : String?
	let ru : String?


	enum CodingKeys: String, CodingKey {
		case en = "en"
		case ru = "ru"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		en = try values.decodeIfPresent(String.self, forKey: .en) ?? String()
		ru = try values.decodeIfPresent(String.self, forKey: .ru) ?? String()
	}


}