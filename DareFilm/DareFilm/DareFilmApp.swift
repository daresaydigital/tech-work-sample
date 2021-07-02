//
//  DareFilmApp.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-01.
//

import SwiftUI

@main
struct DareFilmApp: App {
    var body: some Scene {
        WindowGroup {
            ZStack{
                Color("BackgroundColor")
                    .ignoresSafeArea()
                HomeView()
                    .edgesIgnoringSafeArea(.bottom)
            }

        }
    }
}
