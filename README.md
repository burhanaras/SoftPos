💳 💵 🏦 ₿
# SoftPos [Android]
A soft pos application.


Developed by [Burhan ARAS] with all the love on planet.

SoftPos : Transform your smartphone into a professional financial POS terminal.
SoftPos enables acceptance of contactless payments made with plastic card and any of its virtual equivalents added to Google Pay, Apple Pay, wearables or proprietary HCE wallets and crypto currencies such as Bitcoin and Ethereum.



## Architecture

We have developed this application using **AndroCoda Architecture** architecture which is an improved version of Clean Architecture + MVVM. We haved used the **Kotlin** programming language using industry-proven tools and libraries.

  - Reference 0: [Guide To App Architecture](https://developer.android.com/jetpack/docs/guide)
  - Reference 1:  [Developing Android Apps With Kotlin](https://www.udacity.com/course/developing-android-apps-with-kotlin--ud9012)



The main players in the MVVM pattern are:
  - **The View** — that informs the ViewModel about the user’s actions
  - **The ViewModel** — exposes streams of data relevant to the View
  - **The DataModel** — abstracts the data source. The ViewModel works with the DataModel to get and save the data.


In this MVVM architecture Activities and Fragments depend only on a view model. The repository is the only class that depends on multiple other classes; in this project, the repository depends on a persistent data model and a remote backend data source.

Repository is the single source of truth for all the app data and has a clean API for UI to communicate with.

Repository fetches data from network then it saves into local database and also notifies UI View classes.

Also we have implemented a background worker using **WorkManager** to run periodically in the background and keep local db up to date. We have configured worker to run once a day.

This design creates a consistent and pleasant user experience. Regardless of whether the user comes back to the app several minutes after they've last closed it or several days later, they instantly see a user's information that the app persists locally. If this data is stale, the app's repository module starts updating the data in the background.

Architecture of SoftPos App: 
![alt text](https://github.com/burhanaras/ListenHub/blob/master/screenshots/androcoda.png "SoftPos App architecture")

AndroCoda Architecture Overview:
![SoftPos Architecture Diagram](https://github.com/burhanaras/ListenHub/blob/master/screenshots/architecture.jpg?raw=true)


## Used technologies and libraries

We have used popular, industry-proven tools and libraries :

* **Architecture Components - ViewModel** We keep UI related logic here.
* **Architecture Components - LiveData** We keep data that UI needs. Fragments observe this LiveData
* **DataBinding** To bind XML UI with data
* **Room** To save data to local db
* **Android Material** To benefit new Android Material design library
* **Android KTX**  provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
* **Coroutines** To fetch data in background threads. (We no longer need RxJava)
* **WorkManager** To fetch data in the background periodically.
* **Retrofit** To connect a web service
* **Stetho** To trace the network requests and see local db content
* **GSon** To parse Json
* **LeakCanary** To detect memory leaks
* **MonkeyRunner** To test UI crashes crazily :)
* **Lint** To see warnings in our code
* **JUnit - Espresso** To write automated tests.
* **Android Profiler - APK Analyzer** To analyze our apk
* **Proguard** To obfuscate our Apk code


## Package Structure

* **UI** Contains UI related classes which are Activities, Fragments, ViewModels  and custom views.
* **Database** Contains DAO, entity classes, Room DB implementation and everything else related to database
* **Domain** Keeps domain objects, anything related to business logic and usecases
* **Network** Contains Retrofit implementation, service api interface and data transfer objects
* **Repository** Single source of truth for all the app data
* **Work** Contains a WorkManager worker to run daily and keep local db up to date


## TO-DO List

* SSL Pinning (https://developer.android.com/training/articles/security-config#CertificatePinning)
* Instant App support (https://developer.android.com/topic/google-play-instant/getting-started/instant-enabled-app-bundle)
* Shortcut Support (https://developer.android.com/guide/topics/ui/shortcuts/creating-shortcuts)


Developed By Burhan ARAS with all the love on planet
------------

www.burhanaras.net

   [Burhan ARAS]: <http://www.burhanaras.net>


License
-------

    Copyright 2020 Burhan ARAS

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
