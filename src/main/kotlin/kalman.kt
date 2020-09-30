import io.data2viz.geom.Point
import koma.pow

class KalmanAlgorithm(
    private val sigma_model: Double,
    private val sigma_sensor: Double,
    model_points: List<Point>,
    sensor_points: List<Point>
) {

    init { require(model_points.count()==sensor_points.count()) }

    private val input = sensor_points.zip(model_points).map { (s, m) -> Triple(s.x, m.y, s.y) }

    fun run(): Array<Point> {
        val first = input.first()
        val kalmanPoints = mutableListOf(Triple(first.first, first.third, sigma_model))   // take the first sensor reading as accurate
        val remain = input.drop(0).listIterator()

        while (remain.hasNext()) {
            val (id, m, s) = remain.next()
            val (_, prevPoint, prevError) = kalmanPoints.last()

            val nextErrorSqr = (pow(sigma_sensor, 2)*(prevError + sigma_model)) / (pow(sigma_model, 2) + pow(sigma_sensor, 2) + prevError)
            val nextK = nextErrorSqr / pow(sigma_sensor, 2)
            val nextPoint = nextK*s  + (1-nextK)*(prevPoint)
            kalmanPoints.add(Triple(id, nextPoint, nextErrorSqr))
        }

        return kalmanPoints.map { (x, y, _) -> Point(x, y) }.toTypedArray()
    }
}