//
//  DisplayState.swift
//  DaresayChallenge
//
//  Created by Keihan Kamangar on 2022-06-27.
//

import Foundation

enum DisplayState<T> {
    case loading
    case success(T)
    case failure(String)
}
