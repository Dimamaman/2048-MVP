package uz.gita.dima2048.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import uz.gita.dima2048.BuildConfig
import uz.gita.dima2048.R
import uz.gita.dima2048.databinding.ActivityMain1Binding
import uz.gita.dima2048.databinding.ActivityMainBinding
import uz.gita.dima2048.ui.game.GameActivity
import uz.gita.dima2048.ui.info.InfoActivity
import java.util.*


class MainActivity : AppCompatActivity(),MainContract.View {
    private var _binding: ActivityMain1Binding? = null
    private val binding get() = _binding!!
    private val presenter: MainContract.Presenter = MainPresenter(this)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMain1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        changeStatusBarColor(R.color.statusBarColor,false)
        changeColor(uz.gita.dima2048.R.color.statusBarColor)
        binding.apply {
            constraintPlay.setOnClickListener {
                startActivity(Intent(this@MainActivity,GameActivity::class.java))
            }

            constraintInfo.setOnClickListener {
                startActivity(Intent(this@MainActivity,InfoActivity::class.java))
            }
        }

        Log.d("TTT","onCreate -> is called")

        binding.constraintLang.setOnClickListener {
            presenter.showDialog()
        }
        

        binding.constraintShare.setOnClickListener {
            presenter.shareApp()
        }
    }

    override fun giveLang(lang: String) {
        setLocale(this@MainActivity,langCode = lang)
    }

    override fun showDialog() {
        /*val dialog = Dialog(this)
        dialog.setContentView(R.layout.item_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val qq = dialog.findViewById<LinearLayoutCompat>(R.id.ll_qq)
        val rus = dialog.findViewById<LinearLayoutCompat>(R.id.ll_rus)
        val english = dialog.findViewById<LinearLayoutCompat>(R.id.ll_english)

        qq.setOnClickListener {
            setLocale(this,"qq")
            presenter.saveLang(this,"qq")
            dialog.dismiss()
            myOnRestart()
        }
        rus.setOnClickListener {
            setLocale(this,"rus")
            presenter.saveLang(this,"rus")
            dialog.dismiss()
            myOnRestart()
        }

        english.setOnClickListener {
            setLocale(this,"en")
            presenter.saveLang(this,"en")
            dialog.dismiss()
            myOnRestart()
        }
        dialog.show()*/

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_layout)

        val qqLayout = dialog.findViewById<ConstraintLayout>(R.id.ll_qq)

        val rusLayout = dialog.findViewById<ConstraintLayout>(R.id.ll_rus)

        val englishLayout = dialog.findViewById<ConstraintLayout>(R.id.ll_english)


        qqLayout.setOnClickListener {
            setLocale(this,"kaa")
            presenter.saveLang(this,"kaa")
            dialog.dismiss()
            myOnRestart()
        }
        rusLayout.setOnClickListener {
            setLocale(this,"ru")
            presenter.saveLang(this,"ru")
            dialog.dismiss()
            myOnRestart()
        }

        englishLayout.setOnClickListener {
            setLocale(this,"en")
            presenter.saveLang(this,"en")
            dialog.dismiss()
            myOnRestart()
        }

        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }

    override fun share() {
        shareApp()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(presenter.onAttach(base!!))
    }

    private fun setLocale(context: Context, langCode: String) {
        Log.d("YYYYY", langCode)
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        onConfigurationChanged(configuration) //Add this line
    }

    private fun myOnRestart() {
        finish()
        startActivity(intent)
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
            var shareMessage = "2048 game for Android. Develop your brain".trim() + "\n"
            shareMessage = "${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}".trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Share This App"))
        } catch (e: java.lang.Exception) {
            e.toString()
        }
    }

    private fun changeColor(resourceColor: Int) {
        window.statusBarColor = ContextCompat.getColor(applicationContext, resourceColor)
        val bar: ActionBar? = supportActionBar
        bar?.setBackgroundDrawable(ColorDrawable(resources.getColor(resourceColor)))
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}