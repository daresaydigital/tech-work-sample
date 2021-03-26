# Movie App


# Features
- [x] Main View. Shows the list of the movies. Can be filtered by popular, trending, top rated.
- [x] Detail View, shows detailed info about a specific movie.
- [x] Clean Architecture (Subjectively) 
- [x] Error Handling
- [ ] Unfortunately, no tests. I'd happily have done TDD if i'd have more time though ; )
## Bonus Features
- [x] Pagination (infinite scrolling)
- [x] Pull to Refresh


# Technical Overview

  * Generally, the architecture is inspired from pointfree.co creators, specifically the style used here: https://github.com/kickstarter/ios-oss. 
  * Swift\RxSwift. While Combine+SwiftUI could have been used and I am comfortable with those  technologies as well, I have written more code in RxSwift thus given the limited time I chose RxSwift. Plain Swift (no reactive stuff) works as well.
  * Plain navigation. No Coordinators given there are very few views. 
  * MVVM + Redux. Redux is used for more complex state management, in our case pagination.
  * Expandable and highly customizable networking layer. New endpoints (supporting search, via GET /search/movie for example) can be added in less than 100 lines of code. Please check MovieApiProvider Enum as an example. No dependencies were used for this. Some of the code was reused from my older projects. The endpoint for fetching reviews is as well ready to use.
  * Dependency Injection. Usually I am using a service-locator for this (such as Resolver: https://github.com/hmlongco/Resolver) however for our app it is an overkill.
  * RxDataSources for effective collectionView diffing.
  * UI is a mix of storyboards&code. Generally code is prioritized(As in, text is not hardcoded so it can be further localized etc.). For larger scale projects, only code.

## How to run:
cd into Movie folder, run pod install, open Movie.xcworkspace.