# Breeze - Weather Android App

[![Build Status](https://travis-ci.com/ukhanoff/tech-work-sample.svg?branch=master)](https://travis-ci.com/ukhanoff/tech-work-sample)

![alt App Icon](/app/src/main/ic_launcher-web.png) This is an android weather app MVP. Shows current weather and weather forecast by using your device location. Hope you'll enjoy.

Developed with <3 by using Android Architecture Components, Dagger and Retrofit.

![alt App preview](/img/device_view.png)


 Release build:(/link to release apk)
 
 **how to build:** `.\gradlew clean install` will do all magic. Or just import project into android studio

_Please note:_ If you want to build app by yourself you need to add WEATHER_API_KEY into DataModule class.

---

# Work sample - Application developer

## Assignment

- Build an awesome weather app that shows the weather at your current  location.
- Code it for the platform (Android, iOS, web) you applied for or the one you prefer.

## Minimum requirements
- Show the weather for your current location
- Use data from provided API (see API DOCS)
- Good usability
- Be visually appealing and follow platform conventions
- Budget: 8-12 hours

## Assets
You can find some icon assets at [https://github.com/erikflowers/weather-icons](https://github.com/erikflowers/weather-icons) that you can use if you please. You are more than welcome to use other assets that you feel are better fitted for your solution. We do not expect you to be a designer, but since application development on Daresay most often includes visual interfaces we do expect you to to be well aware of your platforms design guidelines and conventions.

## Expectations
###### User experience
The feature of the app is simple but we expect you to deliver a solution with a high user experience. Imagine this application is to be used by real user with real needs. Maybe your application won’t be as feature-full as many other applications on the market, but at least make it interesting, fun and intuitive to use. Of course you are allowed to extend your applications functionality. We appreciate solutions that exceed our expectations.

###### Code
We expect that the code is of high quality and under source control. Expect the solution to be continuously worked on by other developers and should therefore be easy to understand, adjust and extend. True beauty starts on the inside!

## Delivery
Fork the repository, code in your fork and make a pull request when done. Also send us an e-mail to let us know when you are done!

### Good luck!

---


# API DOCS

## Base url
http://worksample-api.herokuapp.com


http://worksample-api.herokuapp.com is a simple wrapper of some of the endpoints provided on http://openweathermap.org/.

## Available Endpoints and Documentation
- http://worksample-api.herokuapp.com/weather [documentation](http://openweathermap.org/current)
- http://worksample-api.herokuapp.com/forecast [documentation](http://openweathermap.org/forecast5)
- http://worksample-api.herokuapp.com/forecast/daily [documentation](http://openweathermap.org/forecast16)

We always respond in JSON and metrics.
We also don’t support these features of the OpenWeatherMap API:

- Bulk downloading
- Search Accuracy (like/accuracy)
- Limitation of result
- Units format
- Multilingual support
- Callback functions for javascript.
​

## You can use these parameters

#### API key (mandatory)
The API-key is required for all API calls. It should have been sent to you together with the instructions asking you to do the work sample.

#### By city name:
City name and country code divided by comma, use ISO 3166 country codes.

`?q={city name},{country code}`

`?q={city name}`
​
#### By city id:
List of city ID:s can be downloaded [here](http://bulk.openweathermap.org/sample/)

`?id={id}`
​
#### By geographic coordinates:
Coordinates of the location of your interest

`?lat={lat}&lon={lon}`
​
#### By Zip Code
`?zip={zip code},{country code}`

##An example request:

[http://worksample-api.herokuapp.com/weather?q=Stockholm,SE&key={API_KEY}](http://worksample-api.herokuapp.com/weather?q=Stockholm,SE&key={API_KEY})
