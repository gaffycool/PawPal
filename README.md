
## **PawPal** using Clean Android Architecture
- PawPal allows the user to search for their favourite breed of dog using a public dog api. THe user is able to save thier favourite breed of dog into thier favourite list, and also able to search for different breed of dogs as well as compare different dog breed side by side
- The app also has offline caching via a local db that allows the user to use the device offline to see their favourite Dogs.



https://github.com/user-attachments/assets/2fafb303-1398-43e5-9ac2-8b7413a474d5



**App Archetecture:**
This repository contains an implementation of a clean architecture for Android applications using Compose, MVVM, Hilt, Coroutines, Kotlin Flow, Repository, Room, Retrofit, Mockk and JUnit.

The project is divided into several modules, including:
- `app`: The main application module, responsible for defining the UI using Compose and coordinating with the presentation layer.
- `commonDomain`: The domain module, responsible for defining the business logic of the application and exposing it through interfaces.
  - `interactor`: The Interactors are responsible for call repository functions and do the buisness logics and return required models to viewmodel.
- `commonData`: The data module, responsible for defining the data repository, api services and DTOs.
  - `data-repository`: The repository module, responsible for implementing the interfaces defined in the domain module and providing data from both the local and remote data sources.
  - `data-local`: The local data source module, responsible for implementing the logic to access data stored locally, using Room as the database.
  - `data-remote`: The remote data source module, responsible for implementing the logic to access data from a remote API, using Retrofit as the network client.
 
**Diagram of PawPal App Archetecture:**
![pawpal app diagram](https://github.com/user-attachments/assets/f5182271-c5bc-4400-9a6f-d116adb2b590)

The project follows a layered architecture approach, with each layer (presentation, domain, repository and data) having its own set of responsibilities and being completely decoupled from the other layers. The communication between the layers is done through well-defined interfaces, allowing for easy testing and future modifications.

**Dependencies**
Public API: https://dog.ceo/dog-api/

**Unit testing:**
- Unit testing with JUnit 100% coverage for all Viewmodels, Repositories and Interactors
- Unit testing with Screenshot testing using Paparazzi https://betterprogramming.pub/sanely-test-your-android-ui-libraries-with-paparazzi-b6d46c55f6b0

Proprietary License: This project is copyrighted and all rights are reserved. No use, redistribution, or modification is permitted without explicit permission.

Copyright (c) 2024-2025 [@GaffyCool]. All rights reserved. This software is proprietary and cannot be used, modified, or redistributed without explicit permission from the author. No warranties are provided, and use of this software is entirely at your own risk.
