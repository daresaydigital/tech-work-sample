//
//  HomeView.swift
//  DareFilm
//
//  Created by Johannes Loor on 2021-07-02.
//

import SwiftUI

struct HomeView: View {
    @State private var tabSelection = "Popular"
    let popularSectionTitle = "Popular"
    let ratedSectionTitile = "Top rated"
    let movies = [Movie(title: "The best movie ever", description: "Johannes searches for greatness when he tries to build the best app ever seen", rating: "5/5"), Movie(title: "The worst movie ever", description: "Johannes searches for failure when he tries to build the best app ever seen", rating: "1/5")]
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
                MovieListView(movies: movies.reversed())
                    .tag(popularSectionTitle)
                
                MovieListView(movies: movies)
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
