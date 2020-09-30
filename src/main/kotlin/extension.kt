import io.data2viz.viz.JFxVizRenderer
import io.data2viz.viz.Viz
import javafx.scene.canvas.Canvas
import koma.extensions.map
import koma.matrix.Matrix

fun Canvas.visualize(init: Viz.() -> Unit) {
    val viz = io.data2viz.viz.viz(init)
    JFxVizRenderer(this, viz)
    viz.startAnimations()
    viz.render()
}

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

fun Matrix<Double>.short() = this.map { it.round() }