//
//  MovieDetailView.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import SwiftUI
/*
 The detailed movie description
 */
struct MovieDetailView: View {
    @ObservedObject var imageHandler = ImageHandler()
    @Binding var isShowing: Bool
    var movie: Movie
    var body: some View {
        VStack{
            ZStack{
                //MARK: Background and placeholder
                Rectangle()
                    .fill(Color.yellow)
                    .frame(minHeight: 100)
                Text("\(movie.title)")
                    .italic()
                    .padding()
                
                //MARK: Fetched horizontal image
                Image(uiImage: imageHandler.image ?? UIImage())
                    .resizable()
                    .onAppear(){
                        imageHandler.loadImage(withPath: movie.backdropPath)
                    }
            }
            .scaledToFit()
            
            //MARK: Detailed information
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
            
            //MARK: Button for closing details sheet
            Button("Close"){
                isShowing = false
            }
            .padding(.bottom, 50)
        }
        .background(Color("BackgroundColor"))
        .ignoresSafeArea()
    }
}
