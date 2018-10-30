# Code Challenge 2
Based on a given Code Challenge, a clean MVC weather app holding a UITableView and scalable / testable code.

### Libraries
Using cocoapods with the following libraries:

 - 'Alamofire' ~v4.7'

### Design Patterns
The app architecture is made and using the following patterns:
 - SRP (Single Responsability Principle)
 - Adapter
 - Factory
 - Delegation
 - Singleton

### Unit Testing

This app is wired up with Unit testing. Right bellow is described which test are available and what they do.

- ##### Unit Testing
    - **testWeatherLoad**: This test runs the weather load and fails if nil is found
    - **testForecastLoad**: This test runs the forcast load and fails if nil is found

### How to run

In order to run the project make sure you have your cocoapods up to date. After that just run a `pod install` and then open the file `CodeChallenge2.xcworkspace`, finally run it in the device of your choice.

PD: You will need a developer account wired up in project setings and also due to a requeriment in order to see the app working you need to set up a value for the constant **API_KEY** in the file WeatherWorker.swift, nothing is going to work if you don't do so (Sorry).
