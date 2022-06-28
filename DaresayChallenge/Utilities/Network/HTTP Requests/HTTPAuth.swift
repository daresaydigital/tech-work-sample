//
//  HTTPAuth.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import Foundation

enum MovieAuth {
  case none
  case open
  case otp
  case password
  case custom(String)
}

enum TokenType: String {
  case basic, Bearer
}
