package uz.gita.dima2048.ui.main

import android.content.Context

interface MainContract {
    interface Model {
        fun saveLang(context: Context,lang: String)
        fun onAttach(base: Context): Context?
    }

    interface View {
        fun giveLang(lang: String)
        fun showDialog()
        fun share()
    }

    interface Presenter {
        fun saveLang(context: Context,lang: String)
        fun showDialog()
        fun shareApp()
        fun onAttach(base: Context): Context?
    }
}