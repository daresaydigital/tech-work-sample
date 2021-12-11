# The Movie DB

<div style="width:830px; background-color:white; height:120px; overflow:auto;">
        <div style="width: 2000px; height: 90px;">
            <img src="https://github.com/AliSani/tech-work-sample/blob/master/AppScreens/TheMovieAppMockup.png" width=500/>
    </div>

# Features
- [x] Home view: Shows list of movies could be filtered by popular or top rated by a segment
- [x] Movie details view: Shows details of a movie including the overview, genres and posters.
- [x] Pagination for movie lists
- [x] Memory caching for filtering: No extra requests are sent to backend when filter is changed if data already exists in memory.
- [x] Architecture: MVVM, protocol oriented, covering SOLID principles, inversion of control (dependency injection via protocols instead of concerete types), error handlings. 
- [x] Unit Test demonstration for movie details view model
- [x] UI Test demonstration


# Tech discussions

* Due to my UI/UX design experience we have discussed earlier, I used my budget for both tech and design parts to demonstrate both.  
  * Due to simplicity of the requirements no complex or advanced networking is been implemented and the shared `URLSession` has been used. Usually I go with using Almofire which was an overkill for this project.
  * For the binding between view and viewmodel I have used clousures.
  * There is no coordinator implemeted since there is very few navigations in the project. 
  * Dependency Injection: Please check the view models where there API services are injected as protocols instead of concerete types. So we can easily create fakes for our unit testing purposes.
  * I have put a few unit tests to just demonstrate the testability of the the view models but in a real project full unit test coverage for view models is preffered.
  * I have put a few UI tests to just demonstrate it but due to shortage of time I did not use mocked data and am testing against real data which should definitely be changed, thus I have put a TODO in code instead of putting more time than the budget. 
  * I have used storyboard and XIB for creating the most of UI parts but have also created the `LoadingView` programmatically to just demonstrate the preffered way of creating UIs for biggers teams to avoid conflicts.    

## How to run:
Clone the repo and open TheMovieDB.xcodeproj.
