package uz.gita.dima2048.repository

import android.content.Context
import uz.gita.dima2048.data.sharedPref.SharedPref
import kotlin.random.Random
import uz.gita.dima2048.utils.Constants.NEW_ELEMENT

class AppRepository private constructor() {
    private val sharedPref = SharedPref.getInstance()

    private var isFulledListener: (() -> Unit)? = null

    private var state: String = ""

    private var score = sharedPref.score

    private val numbersText = sharedPref.state

    private val numbersArray = numbersText!!.split("#").toTypedArray()

    private val numbersList: MutableList<String> = ArrayList()

    companion object {
        private lateinit var instance: AppRepository
        fun getInstance(): AppRepository {
            if (!(::instance.isInitialized))
                instance = AppRepository()
            return instance
        }
    }

    var matrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    init {
        for (i in numbersArray.indices) {
            numbersList.add(numbersArray[i])
        }
        addNewElement()
        addNewElement()

        if (numbersList[0] == "") {

        } else {
            for (i in numbersList.indices) {
                if (numbersList[i] != "") {
                    matrix[i / 4][i % 4] = numbersList[i].toInt()
                }
            }
        }
    }


    private fun addNewElement() {
        val emptyList = ArrayList<Pair<Int, Int>>()

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val a = matrix[i][j]
                if (matrix[i][j] == 0) emptyList.add(Pair(i, j))
            }
        }

        if (emptyList.isEmpty()) {
            var count = 0
            for (i in 0 until 4) {
                for (j in 0 until 3) {
                    if (matrix[i][j] == matrix[i][j + 1]) {
                        count++
                        break
                    }
                }
            }

            for (i in 0 until 4) {
                for (j in 0 until 3) {
                    if (matrix[j][i] == matrix[j + 1][i]) {
                        count++
                        break
                    }
                }
            }

            if (count == 0) {
                isFulledListener?.invoke()
            }

        } else {
            val randomPos = Random.nextInt(0, emptyList.size)
            matrix[emptyList[randomPos].first][emptyList[randomPos].second] = NEW_ELEMENT
            state = ""
            for (i in matrix.indices) {
                for (j in matrix[i].indices) {
                    state += "${matrix[i][j]}#"
                }
            }
            sharedPref.state = state
        }
    }

    fun moveToLeft() {
        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)
            var bool = true
            for (j in matrix[i].indices) {
                val a = matrix[i][j]
                if (matrix[i][j] == 0) continue
                if (amounts.isEmpty()) {
                    amounts.add(matrix[i][j])
                } else {
                    if (amounts.last() == matrix[i][j] && bool) {
                        score += amounts.last() * 2
//                        sharedPref.score = myScore
                        amounts[amounts.size - 1] = amounts.last() * 2
                        bool = false
                    } else {
                        val left = matrix[i][j]
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }

            for (k in amounts.indices) {
                val left = amounts[k]
                matrix[i][k] = amounts[k]
            }
        }

        // bosh joy yoq bolsa oyin tugashi kk
        val a = matrix


        sharedPref.score = score
        addNewElement()
//        setHighScore()
    }

    fun moveToRight() {
        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)
            var bool = true
            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (amounts.isEmpty()) amounts.add(matrix[i][j])
                else {
                    if (amounts.last() == matrix[i][j] && bool) {
                        score += amounts.last() * 2
//                        sharedPref.score = myScore
                        amounts[amounts.size - 1] = amounts.last() * 2
                        bool = false
                    } else {
                        val right = matrix[i][j]
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }




            for (k in amounts.indices) {
                val right = amounts[k]
                matrix[i][matrix[i].size - k - 1] = amounts[k]
            }
        }
        // bosh joy yoq bolsa oyin tugashi kk
        sharedPref.score = score
        addNewElement()
//        setHighScore()
    }

    fun moveToUp() {
        for (i in matrix.indices) {
            val amounts = ArrayList<Int>()
            var bool = true
            for (j in matrix[i].indices) {
                if (matrix[j][i] == 0) continue
                if (amounts.isEmpty()) amounts.add(matrix[j][i])
                else {
                    if (amounts.last() == matrix[j][i] && bool) {
//                        score += matrix[i][j]
                        score += amounts.last() * 2
//                        sharedPref.score = myScore
                        amounts[amounts.size - 1] = amounts.last() * 2
                        bool = false
                    } else {
                        bool = true
                        val up = matrix[i][j]
                        amounts.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }

            for (k in amounts.indices) {
                val up = amounts[k]
                matrix[k][i] = amounts[k]
            }
        }
        val a = matrix
        sharedPref.score = score
        addNewElement()
//        setHighScore()
    }

    fun moveToDown() {
        for (i in matrix.indices) {
            val amounts = ArrayList<Int>()
            var bool = true

            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[j][i] == 0) continue
                if (amounts.isEmpty()) amounts.add(matrix[j][i])
                else {
                    if (amounts.last() == matrix[j][i] && bool) {
                        score += amounts.last() * 2
                        amounts[amounts.size - 1] = amounts.last() * 2
                        bool = false
                    } else {
                        bool = true
                        val down = matrix[i][j]
                        amounts.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }
            for (k in amounts.size - 1 downTo 0) {
                val down = amounts[k]
                matrix[3 - k][i] = amounts[k]
            }
        }
        sharedPref.score = score
        addNewElement()
//        setHighScore()
    }

    fun clearMatrix() {
        if (score > sharedPref.highScore) {
            sharedPref.highScore = score
        }
        score = 0
        sharedPref.score = 0

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                matrix[i][j] = 0
            }
        }

        addNewElement()
        addNewElement()
    }

    fun getHighScore(): Int {
        return if (score > sharedPref.highScore) {
            sharedPref.highScore = score
            sharedPref.highScore
        } else {
            sharedPref.highScore
        }
    }

    fun getScore(): Int = score

    fun getFulledListener(block: (() -> Unit)) {
        this.isFulledListener = block
    }

    fun saveLang(context: Context,lang: String) {
//        sharedPref.lang = lang
        sharedPref.persist(context,lang)
    }

    fun onAttach(base: Context): Context? {
        return sharedPref.onAttach(base)
    }
}
