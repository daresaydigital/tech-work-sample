//
//	citiesModelCity.swift
//
//	Create by Hamza Khan on 22/10/2018
//	Copyright © 2018. All rights reserved.
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import Foundation

class citiesModelCity : Codable {

	let coord : citiesModelCoord?
	let country : String?
	let geoname : citiesModelGeoname?
	let id : Int?
	let langs : [citiesModelLang]?
	let name : String?
	let stat : citiesModelStat?
	let stations : [citiesModelStation]?
	let zoom : Int?


	enum CodingKeys: String, CodingKey {
		case coord
		case country = "country"
		case geoname
		case id = "id"
		case langs = "langs"
		case name = "name"
		case stat
		case stations = "stations"
		case zoom = "zoom"
	}
	required init(from decoder: Decoder) throws {
		let values = try decoder.container(keyedBy: CodingKeys.self)
		coord = try values.decodeIfPresent(citiesModelCoord.self, forKey: .coord)  //?? citiesModelCoord()
		country = try values.decodeIfPresent(String.self, forKey: .country) ?? String()
		geoname = try values.decodeIfPresent(citiesModelGeoname.self, forKey: .geoname)  //?? citiesModelGeoname()
		id = try values.decodeIfPresent(Int.self, forKey: .id) ?? Int()
		langs = try values.decodeIfPresent([citiesModelLang].self, forKey: .langs) ?? [citiesModelLang]()
		name = try values.decodeIfPresent(String.self, forKey: .name) ?? String()
		stat = try values.decodeIfPresent(citiesModelStat.self, forKey: .stat)  //?? citiesModelStat()
		stations = try values.decodeIfPresent([citiesModelStation].self, forKey: .stations) ?? [citiesModelStation]()
		zoom = try values.decodeIfPresent(Int.self, forKey: .zoom) ?? Int()
	}


}