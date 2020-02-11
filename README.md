# Weather Application - Android

## About
- Simple android to show weather for your current location.
- App is developed in kotlin, demonstrating the use of RxJava2, Retrofit, Dagger2 and implementing MVVM with LiveData.

## General flow of data
- Check if the current location is set for the app, if not set then retrieve lat-long of the user location.
- If the location is set, show the weather for the current location.
- Show weather forecast for the current day and forecast the entire week.
- Show weather details for the selected day of the week.

## Base url
http://worksample-api.herokuapp.com
- It is a simple wrapper of some of the endpoints provided on http://openweathermap.org/.

#### API key (mandatory)
The API-key is required for all API calls.

##An example request:
[http://worksample-api.herokuapp.com/weather?q=Stockholm,SE&key={API_KEY}](http://worksample-api.herokuapp.com/weather?q=Stockholm,SE&key={API_KEY})
