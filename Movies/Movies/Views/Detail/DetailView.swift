//
//  DetailView.swift
//  Movies
//
//  Created by Richard Segerblom on 2022-12-21.
//

import SwiftUI

struct DetailView: View {
    let viewModel: ViewModel
    
    var body: some View {
        ScrollView {
            VStack {
                Text(viewModel.title)
                    .font(.title)
                Text(viewModel.releseYear)
                
                AsyncImage(url: viewModel.movePosterURL)
                    .frame(width: 185, height: 278)
                
                Text(viewModel.description)
                    .padding(30)
            }
        }
    }
}

struct DetailView_Previews: PreviewProvider {
    static var previews: some View {
        DetailView(viewModel: viewModel)
    }
    
    static var viewModel: DetailView.ViewModel {
        .init(movie: .init(id: 0,
                           title: "The Shawshank Redemtion",
                           posterURL: "",
                           releaseDate: "1994-06-12",
                           description: "Two impresioned men bond over a number of years, finding solace and eventual redemtion trough acts of common decency."))
    }
}
