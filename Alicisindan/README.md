# Alicisindan

![](https://github.com/CankutBoraTuncer/Alicisindan/blob/main/Alicisindan/app/src/main/res/layouts/advertisement/mipmap-hdpi/app_logo_foreground.png?raw=true)

![](https://img.shields.io/badge/dynamic/xml?color=brightgreen&label=tag&query=app_version&url=https%3A%2F%2Fraw.githubusercontent.com%2FCankutBoraTuncer%2FAlicisindan%2Fmain%2Fversion.xml) ![](https://img.shields.io/badge/dynamic/xml?color=brightgreen&label=release&query=app_version&url=https%3A%2F%2Fraw.githubusercontent.com%2FCankutBoraTuncer%2FAlicisindan%2Fmain%2Fversion.xml) ![](https://img.shields.io/github/issues/CankutBoraTuncer/Alicisindan)

Alicisindan is an e-commerce app we are developing as our CS102 Algorithms and Programming II course project.

## Team
@begumfilizoz
@BertanTurgut
@cantucer
@CankutBoraTuncer
@EmirhanYagcioglu

## How to use
This is the source code of an Android application. You can run the application on an emulator using Android Studio or you can directly run the `.apk` file using an Android phone.
The AlicisindanClient dependency is already implemented so you don't have to do anything.

## Dependencies
One of the dependencies is the library we developed, AlicisindanClient. The latest version of this library is already implemented into this source code. The implementation can be seen below:
`implementation files('libs\\lib-3.1.0.jar')`


Rounded ImageView dependency
`implementation 'com.makeramen:roundedimageview:2.3.0'`

Country code picker for flags
`implementation 'com.hbb20:ccp:2.5.1'`

Firebase dependencies
`implementation platform('com.google.firebase:firebase-bom:31.2.2')`
`implementation 'com.google.firebase:firebase-messaging:23.1.1'`
`implementation 'com.google.firebase:firebase-firestore:24.4.3'`

Multidex dependency
`implementation 'androidx.multidex:multidex:2.0.1'`

Retrofit dependency
`implementation 'com.squareup.retrofit2:retrofit:2.9.0'`
`implementation 'com.squareup.retrofit2:converter-scalars:2.9.0'`
`implementation 'androidx.work:work-runtime-ktx:2.8.0'`
`implementation 'androidx.activity:activity:1.6.1'`

Dependency for swipe animation
`implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.0.0'`

Dependency for image slider
`implementation 'com.github.denzcoskun:ImageSlideshow:0.1.2'`

## Details
This is the Android application / client side of our project Alicisindan. It has the UI classes, designs, layouts, Android related files etc.
It works together with the AlicisindanClient library.