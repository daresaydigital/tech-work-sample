//
//  MovieDetailView.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import SwiftUI

struct MovieDetailView: View {
    @ObservedObject var imageHandler = ImageHandler()
    @Binding var isShowing: Bool
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
            
            ScrollView{
                Text(movie.title)
                    .font(.title)
                VStack(alignment: .leading){
                    
                    Text("⭐️: \(String(movie.voteAverage))")
                        .font(.title2)
                    Text("Number of votes: \(movie.voteCount)")
                    Text("Released: \(movie.releaseDate ?? "")")
                        .padding(.top)
                    Text("Description:")
                        .italic()
                        .padding(.top)
                    Text(movie.overview)
                    
                }
                .padding()
                Spacer()
            }
            
            Button("Close"){
                isShowing = false
            }
            .padding(.bottom, 50)
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
