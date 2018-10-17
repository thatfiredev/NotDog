[![Build Status](https://travis-ci.org/rosariopfernandes/NotDog.svg?branch=master)](https://travis-ci.org/rosariopfernandes/NotDog)

# NotDog

[NotDog](https://not-dog-io.firebaseapp.com) is the Shazam of animals. It tells you whether you have a dog or not.
It is based on the [Not HotDog app](https://www.seefoodtechnologies.com/nothotdog/) developed by See Food Technologies.


<p align="center">
  <img src="screenshots/negative_notdog.jpg" height="480" width="270" alt="Not Dog"/>
  <img src="screenshots/positive_notdog.jpg" height="480" width="270" alt="Not Dog"/>
</p>

# Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

## Prerequisites
Before building the project, you will need:
- A [Firebase Project](https://console.firebase.google.com/).
- A recent version of Android Studio (v3.0+).
- A Minimum SDK level of 19.

## Installing 
To get the app up and running, you have to:
1. Open the [Firebase Console](https://console.firebase.google.com/), create a new project (or use an existing one) and add an android app.
2. When prompted, use the application ID "io.github.rosariopfernandes.notdog" and your SHA-1 Key. You can do it like this:
    1. Open your project in Android Studio.
    2. Click on Gradle Menu on the right side and expand it.
    3. Click on android and then signing report. Your SHA-1 will show up on the Run Tab at the bottom.
3. Download the ```google-services.json``` file.
4. Copy the ```google-services.json``` file to the app folder.
4. Build the project


# Built With
- [ML Kit for Firebase](https://firebase.google.com/products/ml-kit)

# Contributing
Contributions are welcome. Please read the [contributing guide](CONTRIBUTING.md) for more information.

# License
This project is licensed under the MIT License - see the [license](LICENSE) file for details.

# Acknowledgements
The project makes uses of the following libraries:
- [Glide](https://bumptech.github.io/glide/)
- [Nahu](https://github.com/PauloEnoque/Nahu)
