package uz.gita.dima2048.data.sharedPref

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import uz.gita.dima2048.utils.Constants.HIGH_SCORE
import uz.gita.dima2048.utils.Constants.LANG
import uz.gita.dima2048.utils.Constants.SCORE
import uz.gita.dima2048.utils.Constants.SHARED_PREF
import uz.gita.dima2048.utils.Constants.STATE
import java.util.*


class SharedPref private constructor(context: Context) {
    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreference.edit()

    var score: Int
        get() = sharedPreference.getInt(SCORE,0)
        set(value) = editor.putInt(SCORE,value).apply()

    var highScore: Int
        get() = sharedPreference.getInt(HIGH_SCORE, 0)
        set(value) = editor.putInt(HIGH_SCORE, value).apply()

    var state: String?
        get() = sharedPreference.getString(STATE,"")
        set(value) = editor.putString(STATE,value).apply()

    var lang: String?
        get() = sharedPreference.getString(LANG,"")
        set(value) = editor.putString(LANG,value).apply()

    companion object {
        private lateinit var instance: SharedPref

        fun init(context: Context) {
            instance = SharedPref(context)
        }

        fun getInstance() = instance
    }

    private val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    fun onAttach(context: Context): Context? {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

    fun onAttach(context: Context, defaultLanguage: String): Context? {
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context): String? {
        return getPersistedData(context, Locale.getDefault().language)
    }

    private fun setLocale(context: Context, language: String?): Context? {
        persist(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    fun persist(context: Context, language: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    private fun updateResources(context: Context, language: String?): Context? {
        val locale = language?.let { Locale(it) }
        if (locale != null) {
            Locale.setDefault(locale)
        }
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String?): Context? {
        val locale = language?.let { Locale(it) }
        if (locale != null) {
            Locale.setDefault(locale)
        }
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}