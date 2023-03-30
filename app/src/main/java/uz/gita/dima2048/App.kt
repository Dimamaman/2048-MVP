package uz.gita.dima2048

import android.app.Application
import uz.gita.dima2048.data.sharedPref.SharedPref

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.init(this)
    }
}