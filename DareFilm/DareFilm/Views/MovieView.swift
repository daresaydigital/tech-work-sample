//
//  MovieView.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import SwiftUI
/*
 The view represtning each movie object
 */
struct MovieView: View {
    @ObservedObject var imageHandler = ImageHandler()
    @State private var showingDetails = false
    var movie: Movie
    var body: some View {
        HStack{
            ZStack{
                //MARK: Background and placeholder
                Rectangle()
                    .fill(Color.yellow)
                    .frame(width: 155, height: 235)

                    .cornerRadius(20)

                Text("\(movie.title)")
                    .italic()
                    .padding()
                
                //MARK: Fetched poster
                Image(uiImage: imageHandler.image ?? UIImage())
                    .resizable()
                    .frame(width: 150, height: 230)
                    .cornerRadius(20)
                    .onAppear(){
                        imageHandler.loadImage(withPath: movie.posterPath)
                    }
            }
            .frame(width: 155, height: 235)
            
            //MARK: General movie information
            VStack(alignment: .leading, spacing: 10){
                Text(movie.title)
                    .font(.title2)
                Text(movie.overview)
                    .italic()
                    .lineLimit(3)
                Text("⭐️\(String(movie.voteAverage))")
            }
            Spacer()
        }
        .padding(.bottom)
        .onTapGesture {
            let hapticGenerator = UIImpactFeedbackGenerator(style: .light)
            hapticGenerator.impactOccurred()
            showingDetails.toggle()
        }
        //MARK: Detailed movie sheet shown on tap
        .sheet(isPresented: $showingDetails) {
            MovieDetailView(isShowing: $showingDetails, movie: movie)
        }
    }
}
