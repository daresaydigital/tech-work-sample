//
//  MovieDetailView.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import SwiftUI

struct MovieDetailView: View {
    var movie: Movie
    var body: some View {
        VStack{
            Text(movie.title)
            Text(movie.description)
            Text(movie.rating)
        }
    }
}

//struct MovieDetailView_Previews: PreviewProvider {
//    static var previews: some View {
//        MovieDetailView()
//    }
//}
