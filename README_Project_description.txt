This app (Weatheri) was made for the recruitment process for Daresay. This app was made in 10:30 hours by Rogelio Robledo Ruiz

How it is made:

- Weatheri has a MVP architecture. All business logic is in the presenter, the communication between View and
Presenter is made by a contract which defines the methods to use from both sides.
- Retrofit + RxJava is used for Network purposes.
- Weatheri shows the current weather and the forecast for the next days in an Horizontal RecyclerView.
- LocationManager with GPSProvider is used to retrieve the current location of the user, the only accurate parameter 
we should get is the city. To test different cities you can mock locations. Emulators may not work.
- Weatheri includes some custom animations when loading the recyclerview, tapping some views and showing some results
- Weatheri is written in Java, Kotlin was not used since I am faster and more comfortable using Java for now.

Things that could get improved but not made due to the time limit:

- Use dependency injection (Dagger 2)
- Persist models
- Location changes should be handle by Presenter
- Handle errors in different ways according to different types



