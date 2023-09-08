package com.example.nydoehighschooldirectory

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom [Application] class for the School Application.
 *
 * This class is annotated with [@HiltAndroidApp](https://dagger.dev/hilt/gradle.html#application) to enable
 * Hilt's dependency injection features for the entire application.
 */
@HiltAndroidApp
class SchoolApplication : Application(){
}