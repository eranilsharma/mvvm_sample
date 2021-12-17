object Deps {


    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //ANDROID
    const val multiDex = "androidx.multidex:multidex:${Versions.multiDex}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
    const val annotation = "androidx.annotation:annotation:${Versions.androidAnnotation}"


    //MATERIAL DESIGN
    const val material = "com.google.android.material:material:${Versions.materialDesign}"

    //KTX
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveDataKtx}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx}"
    const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navFragmentKtx}"
    const val navUIKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navUiKtx}"

    //SQUARE
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
    const val httpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.httpLoggingInterceptor}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    const val chuck = "com.readystatesoftware.chuck:library:${Versions.CHUCK}"
    const val chuckNoOp = "com.readystatesoftware.chuck:library-no-op:${Versions.CHUCK}"

    //FIREBASE
    const val bom = "com.google.firebase:firebase-bom:${Versions.firebaseBOM}"
    const val analyticKtx = "com.google.firebase:firebase-analytics-ktx"
    const val messaging = "com.google.firebase:firebase-messaging"


    //JETPACK
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineAndroid}"
    const val coroutineCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineCore}"
    const val dataStore = "androidx.datastore:datastore:${Versions.dataStore}"
    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    //CUSTOM VIEWS
    const val circleImageView = "de.hdodenhof:circleimageview:${Versions.circleImageView}"

    //MISC
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val lottie = "com.airbnb.android:lottie:${Versions.LOTTIE}"
    const val cognito =
        "com.amazonaws:aws-android-sdk-cognitoidentityprovider:${Versions.AWS_COGNITO}"
}