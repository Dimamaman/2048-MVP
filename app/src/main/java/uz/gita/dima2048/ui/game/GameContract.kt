package uz.gita.dima2048.ui.game

import uz.gita.dima2048.repository.AppRepository

interface GameContract {

    interface Model {
        fun getMatrix(): Array<Array<Int>>
        fun getHighScore(): Int
        fun getScore(): Int
        fun getRepository(): AppRepository
        fun moveToDown()
        fun moveToUp()
        fun moveToRight()
        fun moveToLeft()
        fun clearMatrix()
    }

    interface Presenter {
        fun describeMatrixToViews()
        fun getHigScore()
        fun getScore()
        fun getFulledListener()
        fun moveToDown()
        fun moveToUp()
        fun moveToRight()
        fun moveToLeft()
        fun clearMatrix()
    }

    interface View {
        fun describeMatrixToViews(matrix: Array<Array<Int>>)
        fun describeHighScore(highScore: Int)
        fun describeScore(score: Int)
        fun getFulledListener(repository: AppRepository)
    }
}