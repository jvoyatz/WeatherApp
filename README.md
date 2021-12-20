# Weather App

It is an Android weather forecast application which provides the ability to have a 
quick overview of the current weather conditions for a selected city or region as well as user is able to select and see the weather for one of the next 5 days.
User is a able to search for worldwide cities and store them as favorites.

Remote datasources, used to collect weather condition details and search for cities, are the APIs provide by [World Weather Online](#https://www.worldweatheronline.com/api/).

To summarize, this app gives the user the ability to:

* search for cities and store them locally using Android Room
* see a quick overview of the weather forecast for a particular day, including the next 5 days.
* after selecting either the current day or one of the next 5 days, user can have an in detail look over this day's weather forecast. This happens in two ways:
   * a quick summary of the current weather forecast or
   * a detailed overview of the forecast per hour and the details for the selected using a BottomSheet dialog.
* user can see also the forecast for his current location, in case he grants the location permission to the app.
* It is not mandatory when user is searching for a city to store this city locally
  *Note:To achieve this user must not click on the add button provided in the suggestion box, rather clicking in the center of the item.*

#### Archicteture

This app is build using a MVVM architecture. The guide

The architecture used to build this application is MVVM and the guide provided by Android developer site, found [here](https://developer.android.com/jetpack/guide) , was followed.
This guide follows some common architectural patterns giving the developers the ability to write cleaner apps.

Certain classes like ViewModel and Livedata (which are lifecycle aware) used to store and manage the UI related Data,
giving the ability to the developer to avoid dealing with storing the View object in a presenter like MVP architecture.

My design includes the Repository class as suggested in the guide, where you can select whether to fetch from the local db or the remote server.
For remote requests, Retrofit was used along with classes provided in Android Samples to make Retrofit able
to return LiveData<T> instances instead of the default Call classes.
As for the storege, Android Room ORM was used. So, when fetching responses from the API, the response was transformed
to Android Room entities, stored in the db.

In addition, ViewBinding & Databinding were used. The DataBinding library provides us the ability to write
the code used to fill UI elements in the xml layout files making Android Activities and Fragments clear with less code written.

Finally, Dependency Injection is supported through Dagger Hilt library. This way, the creation of certain classes needed by the app to be singleton
for exampled was not made by hand making code more cleaner and maintainable.

Had a look into the samples provided [here](#https://github.com/android/architecture-components-samples).

#### Why certain third-party libraries were used?

* [Glide](#https://github.com/bumptech/glide) is a framework for make image loading very fast and simple. Api provided url for the weather icons
* [Timber](#https://github.com/JakeWharton/timber) a logger library build on top of Android Log. Makes logging simpler and can be installed only in debug versions.
  At first i though, i would have to provide a release apk.
* [Retrofit](#https://github.com/square/retrofit) HTTP client
