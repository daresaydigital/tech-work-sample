//
//  HomeView.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import SwiftUI

/*
 First landing page of the app, building the overall structure
 */
struct HomeView: View {
    @State private var tabSelection = "Popular"
    @ObservedObject var movieHandler = MovieHandler()
    let popularSectionTitle = "Popular"
    let ratedSectionTitile = "Top rated"
    
    var body: some View {
        VStack(spacing: 0){
            // MARK: Title
            Text("DareFilm")
                .font(.title)
                .padding()
            
            //MARK: Top selection bar
            HStack{
                Spacer()
                Button(tabSelection == popularSectionTitle ? "\(popularSectionTitle)🍿" : popularSectionTitle){
                    tabSelection = popularSectionTitle
                }
                .foregroundColor(tabSelection == popularSectionTitle ? .primary : .gray)
                
                Spacer()
                
                Button(tabSelection == ratedSectionTitile ? "🍿\(ratedSectionTitile)" : ratedSectionTitile){
                    tabSelection = ratedSectionTitile
                }
                .foregroundColor(tabSelection == ratedSectionTitile ? .primary : .gray)
                Spacer()
            }
            .font(.headline)
            .padding(.bottom)
            Divider()
            
            //MARK: Tabview content
            TabView(selection: $tabSelection){
                MovieListView(movies: movieHandler.popularMovies)
                    .tag(popularSectionTitle)
                    .onAppear(){
                        if movieHandler.popularMovies.isEmpty{
                            movieHandler.fetchMovieData(withFilter: .popular)
                        }
                    }
                
                MovieListView(movies: movieHandler.topRatedMovies)
                    .tag(ratedSectionTitile)
                    .onAppear(){
                        if movieHandler.topRatedMovies.isEmpty{
                            movieHandler.fetchMovieData(withFilter: .topRated)
                        }
                    }
            }
            .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
        }
    }
}
