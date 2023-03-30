package uz.gita.dima2048.ui.game

import uz.gita.dima2048.repository.AppRepository

class GameModel: GameContract.Model {

    private val repository = AppRepository.getInstance()

    override fun getMatrix(): Array<Array<Int>> {
        return repository.matrix
    }

    override fun getHighScore(): Int {
        return repository.getHighScore()
    }

    override fun getScore(): Int {
        return repository.getScore()
    }

    override fun getRepository(): AppRepository {
        return repository
    }

    override fun moveToDown() {
        repository.moveToDown()
    }

    override fun moveToUp() {
        repository.moveToUp()
    }

    override fun moveToRight() {
        repository.moveToRight()
    }

    override fun moveToLeft() {
        repository.moveToLeft()
    }

    override fun clearMatrix() {
        repository.clearMatrix()
    }
}