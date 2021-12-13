//
//  ResponseValidatableProtocol.swift
//  Daresay Movies
//
//  Created by Emad Bayramy on 12/14/21.
//

import Foundation

protocol ResponseValidatableProtocol {
    func validation<T: Codable>(response: HTTPURLResponse?, data: Data?) -> Result<T, RequestError>
}
