# Weather App

This is a weather forecast application which uses a remote source an api provided by [World Weather Online](#https://www.worldweatheronline.com/api/).

Features of this app:
* search for a city and add this city to a list shown in the app. City removal is also allowed.
* see a quick overview of the weather forecast for a particular day, including the next 5 days.
* select either the current day or one of the next 5 days, and have a more detailed view of the forecast for the selected day by :
* * reading the forecast in the first tab of a tab-layout
* * having a second tab with a detailed overview of the forecast per hour and see its details using a BottonSheet Dialog.
* user can see also the forecast for his current location, in case he grants the location permission to the app.
* finally, he can search for a city and view it's focecast details without saving it locally.
  *Note:To achieve this user must not click on the add button provided in the suggestion box, rather clicking in the center of the item.*

#### Why certain approaches were used?

The architecture used to build this application is MVVM and the guide provided by Android developer site, found [here](#https://developer.android.com/jetpack/guide) , was followed.
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


#### Why certain third-party libraries were used?

* [Glide](#https://github.com/bumptech/glide) is a framework for make image loading very fast and simple. Api provided url for the weather icons
* [Timber](#https://github.com/JakeWharton/timber) a logger library build on top of Android Log. Makes logging simpler and can be installed only in debug versions.
  At first i though, i would have to provide a release apk.
* [Retrofit](#https://github.com/square/retrofit) HTTP client

#### Anything extra you would have done given more time?
I would like to have some extra screens.

For example one of them could provide a graph for the temperature on a particular date.
In another one, we could see the temperatures etc for a given date in the past.

Moreover a local map engine like [VTM](#) could be used to show user cities on the map making user able to click on the
markers and have a quick view of the forecast. Additionally, the user would be able to select certain points on the map and
get the weather forecast for that location.

Additionally, a Preferences screen would be useful, so as to configure the query params being sent in each request.
The user would have the choice to select whether to fetch the next 5 days or more.

It would be also nice to have a foreground service and getting location updates in the background. Then, a notification could
be shown to show user a quick view of the weather status at that point.

Other features, that i would like to add, is a repeated task for fetching weather forecast update. This can be done
either by using ScheduledExecutorService or better, Android's WorkManager.

#### Anything else you feel we should know?

Dates contained in API responses were mixed and sometimes got confused while attempting to process the data provided in the UI.
Since i had to design the UI, and i didn't know how a weather app looks like, it took me more time to design and implement
my ideas.