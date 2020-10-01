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
    private val kPoints = mutableListOf<Point>()

    fun run(): Array<Pair<Point, Point>> {
        val first = input.first()
        val kalmanFilter = mutableListOf(Triple(first.first, first.third, sigma_model))   // take the first sensor reading as accurate
        val remain = input.drop(0).listIterator()

        while (remain.hasNext()) {
            val (id, m, s) = remain.next()
            val (_, prevPoint, prevError) = kalmanFilter.last()

            val nextErrorSqr = (pow(sigma_sensor, 2)*(prevError + sigma_model)) / (pow(sigma_model, 2) + pow(sigma_sensor, 2) + prevError)
            val nextK = nextErrorSqr / pow(sigma_sensor, 2)
            val nextPoint = nextK*s  + (1-nextK)*(prevPoint)
            kalmanFilter.add(Triple(id, nextPoint, nextErrorSqr))
            kPoints.add(Point(id, nextK))
            println(nextK)
        }


        return kalmanFilter.map { (x, y, _) -> Point(x, y) }.zip(kPoints).toTypedArray()
    }
}