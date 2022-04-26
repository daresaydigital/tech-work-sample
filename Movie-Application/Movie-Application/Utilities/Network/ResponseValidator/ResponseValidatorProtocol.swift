//
//  ResponseValidatorProtocol.swift
//  Movie-Application
//
//  Created by Mohanna Zakizadeh on 4/26/22.
//

import Foundation

protocol ResponseValidatorProtocol {
    func validation<T: Codable>(response: HTTPURLResponse?, data: Data?) -> Result<T, RequestError>
}
