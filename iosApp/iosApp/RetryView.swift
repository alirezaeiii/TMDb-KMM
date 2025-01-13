//
//  RetryView.swift
//  iosApp
//
//  Created by Ali on 1/13/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct RetryView: View {
    let message: String
    let retry: () -> Void
    
    var body: some View {
        VStack(spacing: 20) {
            Text(message)
            Button(action: {
                retry()
            }, label: {
                Text(getRetryText())
            })
        }
    }
}
