# LocationReminder

LocationReminder is an app to demonstrate the use of Google Maps API, Location, Geofencing. 
In LocationReminder a user can select a loction on a map and create a Reminder. This will result in a Geofence that will trigger a notification when the user gets close to the selected location.

Uses the following libraries and frameworks:
[Koin](https://github.com/InsertKoinIO/koin) Lightweight dependency injection for kotlin
[Google Maps](https://developers.google.com/maps/documentation) For maps and places

Testing
[Mockito](https://github.com/mockito/mockito) Mocking framework
[Espresso](https://github.com/mockito/mockito) Android UI testing framework
[Hamcrest](https://http://hamcrest.org/) Library for testing
[Robolectric](https://github.com/robolectric/robolectric) Unit testing framework for Android

It leverages the following components from the Jetpack library:

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) with binding adapters
* [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) with the SafeArgs plugin for parameter passing between fragments
