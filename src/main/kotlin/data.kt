import io.data2viz.geom.point
import koma.pow
import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.sqrt

object Data {
    val points = Files.newBufferedReader(Path.of(Data::class.java.getResource("case4.csv").toURI()))
        .lineSequence()
        .map { it.split(",") }
        .map { point(it.first().toDouble(), it.last().toDouble()) }.toList()

    private val count = points.count()

    // calculation of mean of data
    val mean = points.map { it.y }.sum().div(count)

    // calculation of standard deviation
    val sigma_data = points.map { pow((it.y - mean), 2) }.sum().div(count-3).also(::sqrt)

    val maxY = points.maxByOrNull { it.y }!!.y
    val minY = points.minByOrNull { it.y }!!.y
    val maxX = points.maxByOrNull { it.x }!!.x
    val minX = points.minByOrNull { it.x }!!.x
}
