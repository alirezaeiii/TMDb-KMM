//
//  TMDbRow.swift
//  iosApp
//
//  Created by Ali on 1/13/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct TMDbRow: View {
    var categoryName: String
    var items: [Movie]
    var index: Int

    var body: some View {
        VStack(alignment: .leading) {
            Text(categoryName)
                .font(.headline)
                .padding(.leading, 15)
                .padding(.top, 5)
                .padding(.bottom, 10)

            ScrollView(.horizontal, showsIndicators: false) {
                HStack(alignment: .top, spacing: 0) {
                    ForEach(items, id: \.self.id) { movie in
                        TMDbItem(movie: movie, index: index)
                    }
                }
            }
            .frame(height: Constants.frameSize)
        }
    }
    
    private struct Constants {
        static let frameSize: Double = 185
    }
}


#Preview {
    let movie = Movie(id: 1, overview: "", releaseDate: nil, posterPath: nil, backdropPath: nil, name: "name", voteAverage: 1, voteCount: 1)
    return TMDbRow(
        categoryName: "Trending",
        items: [movie],
        index: 1
    )
}
