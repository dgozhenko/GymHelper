package com.dh.gymhelper.data

import android.app.Application
import com.instabug.library.Feature
import com.instabug.library.Instabug
import com.instabug.library.invocation.InstabugInvocationEvent
import com.instabug.library.ui.onboarding.WelcomeMessage
import com.instabug.library.visualusersteps.State
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Instabug.Builder(this, "fbb9de34b2e04c2a1161fe7d2d6dd0c1")
            .setInvocationEvents(InstabugInvocationEvent.SHAKE)
            .setReproStepsState(State.ENABLED)

            .build()
        Instabug.showWelcomeMessage(WelcomeMessage.State.LIVE)
        Instabug.setSessionProfilerState(Feature.State.ENABLED)
    }
}