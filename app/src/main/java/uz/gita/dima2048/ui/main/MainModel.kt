package uz.gita.dima2048.ui.main

import android.content.Context
import uz.gita.dima2048.repository.AppRepository

class MainModel: MainContract.Model {
    private val repository = AppRepository.getInstance()

    override fun saveLang(context: Context,lang: String) {
        repository.saveLang(context,lang)
    }

    override fun onAttach(base: Context): Context? {
        return repository.onAttach(base)
    }
}