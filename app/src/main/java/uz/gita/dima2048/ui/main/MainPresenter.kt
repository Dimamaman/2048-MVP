package uz.gita.dima2048.ui.main

import android.content.Context

class MainPresenter(private val view: MainContract.View): MainContract.Presenter {

    private val model = MainModel()

    override fun saveLang(context: Context,lang: String) {
        model.saveLang(context,lang)
    }

    override fun showDialog() {
        view.showDialog()
    }

    override fun shareApp() {
        view.share()
    }

    override fun onAttach(base: Context): Context? {
        return model.onAttach(base)
    }
}