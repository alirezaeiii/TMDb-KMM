//
//  TMDbItem.swift
//  iosApp
//
//  Created by Ali on 1/13/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import Shared
import SDWebImageSwiftUI


struct TMDbItem: View {
    var movie: Movie
    
    var body: some View {
        let shape = RoundedRectangle(cornerRadius: Constants.cornerRadius)
        let placeHolder = shape.foregroundColor(.secondary)
            .frame(width: Constants.frameWidth, height: Constants.frameHeight)
        VStack(alignment: .leading) {
            if let posterPath = movie.posterPath {
                WebImage(url: URL(string: String(format: posterPath))) { image in
                    image
                        .resizable()
                        .frame(width: Constants.frameWidth, height: Constants.frameHeight)
                        .clipShape(shape)
                } placeholder: {
                    placeHolder
                }
                Text(movie.name)
                    .font(.caption)
            } else {
                placeHolder
            }
        }
        .padding(.leading, 15)
    }
    
    private struct Constants {
        static let cornerRadius: Double = 20
        static let frameWidth: Double = 125
        static let frameHeight: Double = 175
    }
}


#Preview {
    let movie = Movie(id: 1, overview: "", releaseDate: nil, posterPath: nil, backdropPath: nil, name: "name", voteAverage: 1, voteCount: 1)
    return TMDbItem(movie: movie)
}
