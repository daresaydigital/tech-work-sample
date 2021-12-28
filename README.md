# Work sample - Application developer

# Daresay Assignment

## About App
This is an assignment for Daresay.

## App Architecture

I chose the clean architecture for this app. because architecture should be
scalable i separated layers so i developed multi-module clean app.
there is modules in application :
* App Module : application entry point which has Application class and has
core and presentation module on dependencies.
* Presentation Module :  represent presentation layer include of android ui
component (fragments, views, etc) and ViewModels. it has domain and core modules
on dependencies.
* Domain Module : represent domain layer of clean architecture and center of
application logic. it is not an android library module and it only has core module
on dependencies. this module contains all data layer abstractions and usecases
that presentation layer use.
* Data Module : as clean architecture data layer it has all domain abstractions
implementation. this module contains repository pattern and data sources. also
it depends on domain and core module.
* Core module :  shares some util classes and libraries with other modules.

## App Patterns
In this application i used MVVM as architectural pattern. also repository pattern
in data layer. communication between views and viewModels are observe pattern.

## App Implementation
Application has a single main activity with 2 fragments for popular movies' items and top rated movies, and another activity for movie details.
application will cache the user favourite movies and also movie details to be offline first, i have used
room to handle it. i spared the reviews section because the lack of time.

## Dependencies and libraries
This dependencies could be found in versions.gradle file.

* Kotlin programming language, core and ktx
* Google lifecycle extensions as
* Kotlin coroutines for Threading
* Hilt for DI
* Navigation component
* Retrofit, OkHttp, okHttpLoggingInterceptorVerion, gsonConverterVersion and Gson for networking.
* Room as local database ORM
* Junit, mockitoKotlin, mockitoInline, coreTesting for unit testing.

## Unit Testing
Application has unit test for viewModels, repositories and data sources.

## To Reviewer
I ran out of time and i know i can do it more better specially in functionality.
you will see kind of mappers in project. i know that i could rid of it but its important
to have model for each layer.
also i only wrote test for some classes because of lack of time. i usually write unit test
for all usecases, repositories, dataSources and some times converters.
i hope you accept these few tests.
i wanted to write gitlab.yml file to have simple pipeline and CI. but i did not have enough time.
at the end.. i know that documentation is for public Api's not any methods.
Thanks.

