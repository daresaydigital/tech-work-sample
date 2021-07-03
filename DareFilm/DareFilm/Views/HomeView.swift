//
//  HomeView.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import SwiftUI

struct HomeView: View {
    @State private var tabSelection = "Popular"
    @ObservedObject var movieHandler = MovieHandler()
    let popularSectionTitle = "Popular"
    let ratedSectionTitile = "Top rated"
    var body: some View {
        VStack(spacing: 0){
            Text("DareFilm")
                .font(.title)
                .padding()
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
            
            TabView(selection: $tabSelection){
                MovieListView(movies: movieHandler.movies)
                    .tag(popularSectionTitle)
                
                MovieListView(movies: movieHandler.movies.reversed())
                    .tag(ratedSectionTitile)
            }
            .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
