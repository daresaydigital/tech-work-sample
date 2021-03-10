Weather
======

This repository contains a sample Weather app for requesting Weather's API.


---
# Installation

To install the dependencies
* Open a terminal and cd to the directory containing the Podfile
* Run the `pod install` command

(For further details regarding cocoapod installation see https://cocoapods.org/)

Notes:
Don't forget to change the apiKey for the Weather's API in "Constants.swift"

---
# Existing Functionalities

The app is currently able to load Weather data from Weather's API, and show it in 2 UI designs (list form and dashboard)

* When the app loads, it will load the Weather data from Weather's API, and show them in list form ( like iOS default "Weather" app)
* Upon selecting "Other Design", it will open a view with Dashboard like presentation of Weather data


---
# Development Steps

1. Create new project based on single view app
2. Create folders for MVVM pattern
3. Add WeatherViewController and Design the UI layout to Weather data
4. Add LocationManager to detect user current location
5. Add Networking Layer to handle the Weather's API
6. Add ViewModel and Model, that will show the Weather data at WeatherViewController
7. Add Forecast and ForecastDaily features
8. Add scroll effect feature
9. Add pods: Kingfisher
10. Add MyWeatherViewController and Design the Dashboard UI layout
11. Implement existing ViewModels to MyWeatherViewController
10. Add Unit Test to test the process




==================================================================


# Work sample - Application developer

## Assignment

- Build an awesome movie app that shows popular and high rated movies.
- Code it for the platform (Android, iOS, web) you applied for or the one you prefer.

## Requirements

- Use an open https://developers.themoviedb.org/open source API. Please read the API [Authentication section](https://developers.themoviedb.org/3/getting-started/authentication) to get started.
- Discover most popular and highly rated movies.
- Display the movies with creative look and feel of an app to meet design guidelines for your platform (Material Design etc.).
- Launch a detail screen whenever a particular movie is selected.

## Examples of bonus features

- Allow user to save a favorite movie for offline access.
- Allow user to read movie reviews.

## We expect you to

- Write clean code.
- Create a responsive design.
- Handle error cases.
- Use the latest libraries and technologies.
- Tested code is a big plus.

### User experience

The features of the app might be few, but we expect you to deliver a solution with a high user experience. Imagine this application to be used by real users, with real needs. Make it interesting, fun and intuitive to use. And of course you are allowed to extend your applications functionality.

### Code

We expect that the code is of high quality and under source control. Expect the solution to be continuously worked on by other developers and should therefore be easy to understand, adjust and extend. True beauty starts on the inside!

## Delivery

Fork the repository, code in your fork and make a pull request when done. A nice commit history describing your work is preferred over squashing it into one commit.
Also send us an e-mail to let us know!

### Good luck!

---

