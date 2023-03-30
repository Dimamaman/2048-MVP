package uz.gita.dima2048.ui.game

class GamePresenter(private val view: GameContract.View): GameContract.Presenter {

    private val model = GameModel()

    override fun describeMatrixToViews() {
        val matrix = model.getMatrix()
        view.describeMatrixToViews(matrix = matrix)
    }

    override fun getHigScore(){
        val highScore = model.getHighScore()
        view.describeHighScore(highScore)
    }

    override fun getScore() {
        val score = model.getScore()
        view.describeScore(score)
    }

    override fun getFulledListener() {
        view.getFulledListener(model.getRepository())
    }

    override fun moveToDown() {
        model.moveToDown()
    }

    override fun moveToUp() {
        model.moveToUp()
    }

    override fun moveToRight() {
        model.moveToRight()
    }

    override fun moveToLeft() {
        model.moveToLeft()
    }

    override fun clearMatrix() {
        model.clearMatrix()
    }


}