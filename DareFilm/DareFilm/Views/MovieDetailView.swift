//
//  MovieDetailView.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import SwiftUI

struct MovieDetailView: View {
    @ObservedObject var imageHandler = ImageHandler()
    var movie: Movie
    var body: some View {
        VStack{
            ZStack{
                Rectangle()
                    .fill(Color.yellow)
                    .frame(minHeight: 100)
                Text("\(movie.title)")
                    .italic()
                    .padding()
                Image(uiImage: imageHandler.image ?? UIImage())
                    .resizable()
                    .onAppear(){
                        imageHandler.loadImage(withPath: movie.backdropPath)
                    }
            }
            .scaledToFit()
            Text(movie.title)
                .font(.title)
            VStack(alignment: .leading){
                Text("Released: \(movie.releaseDate ?? "")")
                Text("⭐️: \(String(movie.voteAverage))")
                        
                Text("Description:")
                    .italic()
                    .padding(.top)
                Text(movie.overview)
                
            }
            .padding()
            Spacer()
        }
        .background(Color("BackgroundColor"))
        .ignoresSafeArea()
    }
}

//struct MovieDetailView_Previews: PreviewProvider {
//    static var previews: some View {
//        MovieDetailView()
//    }
//}
