package uz.gita.dima2048.ui.game

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.LinearLayoutCompat
import uz.gita.dima2048.R
import uz.gita.dima2048.databinding.ActivityGameBinding
import uz.gita.dima2048.data.model.SideEnum
import uz.gita.dima2048.repository.AppRepository
import uz.gita.dima2048.utils.*

class GameActivity : AppCompatActivity(), GameContract.View {
    private val items: MutableList<TextView> = ArrayList(16)
    private var _binding: ActivityGameBinding? = null
    private val binding get() = _binding!!
    private val util = BackgroundUtil()
    private lateinit var myTouchListener: MyTouchListener
    private var setScores: Boolean = false
    private lateinit var dialog: Dialog

    private val presenter: GameContract.Presenter = GamePresenter(this)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        dialog = Dialog(this)

        loadViews()

        presenter.describeMatrixToViews()

        myTouchListener = MyTouchListener(this)

        myTouchListener.setMyMoveListener {
            when (it) {
                SideEnum.DOWN -> {
                    presenter.moveToDown()
                    presenter.describeMatrixToViews()
                }
                SideEnum.UP -> {
                    presenter.moveToUp()
                    presenter.describeMatrixToViews()
                }
                SideEnum.RIGHT -> {
                    presenter.moveToRight()
                    presenter.describeMatrixToViews()
                }
                SideEnum.LEFT -> {
                    presenter.moveToLeft()
                    presenter.describeMatrixToViews()
                }
            }
        }

        binding.apply {
            mainView.setOnClickListener {
                it.setOnTouchListener(myTouchListener)
            }

            icRefresh.setOnClickListener {
                setScores = true
                presenter.clearMatrix()
                presenter.describeMatrixToViews()
            }
        }

        presenter.getFulledListener()
    }

    private fun loadViews() {
        binding.apply {
            for (i in 0 until mainView.childCount) {
                val linear = mainView.getChildAt(i) as LinearLayoutCompat
                for (j in 0 until linear.childCount) {
                    items.add(linear.getChildAt(j) as TextView)
                }
            }
            presenter.getHigScore()
        }
    }

    override fun describeMatrixToViews(matrix: Array<Array<Int>>) {
        if (setScores) {
            binding.apply {
                presenter.getScore()
                presenter.getHigScore()
            }
        } else {
            presenter.getScore()
        }

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                items[i * 4 + j].apply {
                    text = if (matrix[i][j] == 0) ""
                    else matrix[i][j].toString()
                    setBackgroundResource(util.colorByAmount(matrix[i][j]))
                }
            }
        }
    }

    override fun describeHighScore(highScore: Int) {
        binding.txtHighScore.text = highScore.toString()
    }

    override fun describeScore(score: Int) {
        binding.txtScore.text = score.toString()
    }

    override fun getFulledListener(repository: AppRepository) {
        repository.getFulledListener {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.dialog_item)
            dialog.show()

            binding.apply {
                ivShine.visible()
                ivShine.startAnimation(
                    AnimationUtils.loadAnimation(this@GameActivity, R.anim.rotate_anim)
                )
            }

            dialog.setOnDismissListener {
                binding.apply {
                    ivShine.gone()
                    ivShine.clearAnimation()
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}