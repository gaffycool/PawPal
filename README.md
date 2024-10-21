
## **PawPal** using Clean Android Architecture

https://github.com/user-attachments/assets/872f3c80-4050-4289-a560-17a086d399b9

Android App:
- PawPal lists out the Top Dog breeds available, the user can add and remove their favourite breed of dog to their own watch list, as well as compare each dog breed side by side with images and filter through the list to search for particular breeds
- The app also has offline caching via a local db that allows the user to use the device offline to see their favourite Dogs.


This repository contains an implementation of a clean architecture for Android applications using Compose, MVVM, Hilt, Coroutines, Kotlin Flow, Repository, Room, Retrofit, Mockk and JUnit.

The project is divided into several modules, including:

- `app`: The main application module, responsible for defining the UI using Compose and coordinating with the presentation layer.
- `commonDomain`: The domain module, responsible for defining the business logic of the application and exposing it through interfaces.
  - `interactor`: The Interactors are responsible for call repository functions and do the buisness logics and return required models to viewmodel.
- `commonData`: The data module, responsible for defining the data repository, api services and DTOs.
  - `data-repository`: The repository module, responsible for implementing the interfaces defined in the domain module and providing data from both the local and remote data sources.
  - `data-local`: The local data source module, responsible for implementing the logic to access data stored locally, using Room as the database.
  - `data-remote`: The remote data source module, responsible for implementing the logic to access data from a remote API, using Retrofit as the network client.
 
Diagram of PawPal App Archetecture:

![pawpal app diagram](https://github.com/user-attachments/assets/f5182271-c5bc-4400-9a6f-d116adb2b590)



The project follows a layered architecture approach, with each layer (presentation, domain, repository and data) having its own set of responsibilities and being completely decoupled from the other layers. The communication between the layers is done through well-defined interfaces, allowing for easy testing and future modifications.

### Dependencies
Public API: https://dog.ceo/dog-api/

Unit test coverage 100% for VM and repository

