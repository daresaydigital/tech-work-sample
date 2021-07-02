//
//  MovieView.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import SwiftUI

struct MovieView: View {
    @State private var showingDetails = false
    var movie: Movie
    var body: some View {
        HStack{
            RoundedRectangle(cornerRadius: 25, style: .continuous)
                .fill(Color.yellow)
                .frame(width: 150, height: 230)
            VStack(alignment: .leading, spacing: 10){
                Text(movie.title)
                    .font(.title2)
                Text(movie.description)
                    .italic()
                    .lineLimit(3)
                Text(movie.rating)
            }
            Spacer()
        }
        .padding()
        .onTapGesture {
            showingDetails.toggle()
        }
        .sheet(isPresented: $showingDetails) {
            MovieDetailView(movie: movie)
        }
    }
}

struct MovieView_Previews: PreviewProvider {
    static var previews: some View {
        MovieView(movie: Movie(title: "The best movie ever", description: "Johannes searches for greatness when he tries to build the best app ever seen", rating: "5/5"))
    }
}
