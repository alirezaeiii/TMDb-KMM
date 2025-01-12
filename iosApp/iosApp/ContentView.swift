import SwiftUI
import Shared

struct ContentView: View {
    @State private var viewModel: FeedViewModel = KoinKt.getFeedViewModel()
    
    
    var body: some View {
        VStack {
            Observing(viewModel.stateFlow) { uiState in
                switch uiState {
                case is MovieListUiState.Loading:
                    ProgressView()
                case let error as MovieListUiState.Error:
                    VStack(spacing: 20) {
                        Text(error.message)
                        Button(action: {
                            viewModel.refresh()
                        }, label: {
                            Text("Retry")
                        })
                    }
                case let success as MovieListUiState.Success:
                    if let firstFeed = success.result.first?.feeds.first {
                        Text(firstFeed.name)
                    } else {
                        Text("No data")
                    }
                default:
                    Text("Unknown state")
                }
            }
            
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
