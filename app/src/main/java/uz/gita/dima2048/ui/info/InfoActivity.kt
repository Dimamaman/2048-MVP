package uz.gita.dima2048.ui.info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import uz.gita.dima2048.R
import uz.gita.dima2048.databinding.ActivityInfoBinding
import uz.gita.dima2048.ui.main.MainActivity

class InfoActivity : AppCompatActivity() {
    private var _binding: ActivityInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.icBack.setOnClickListener {
            startActivity(Intent(this@InfoActivity,MainActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}