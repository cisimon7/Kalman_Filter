package view

import Data
import Data.mean
import Data.points
import Data.sigma_data
import KalmanAlgorithm
import io.data2viz.geom.Point
import io.data2viz.random.RandomDistribution
import io.data2viz.scale.Scales
import io.data2viz.viz.Margins
import kotlin.math.cos
import kotlin.math.sin

val margins = Margins(40.5, 30.5, 50.5, 70.5)

const val width = 1_000.0
const val height = 500.0
val chartWidth = width - margins.hMargins
val chartHeight = height - margins.vMargins

val xScale = Scales.Continuous.linear {
    domain = listOf(Data.minX, Data.maxX)
    range = listOf(.0, chartWidth)
}

val yScale = Scales.Continuous.linear {
    domain = listOf(Data.minY, Data.maxY)
    range = listOf(chartHeight, .0) // <- y is mapped in the reverse order (in SVG, javafx (0,0) is top left.
}

val sensor_points = points

val rnd_gauss = RandomDistribution.normal(0.0, 5.0)

val model_equation = { prev:Double -> prev + rnd_gauss() }
val model_points = generateSequence(mean) { prev -> prev + rnd_gauss() }
    .zip(points.asSequence())
    .map { (y, pt) -> Point(pt.x, y) }
    .toList()

val kalman_points = KalmanAlgorithm(3.0, 5.0, model_points, sensor_points).run().toList()

val gauss_points = (1..1_000).map { x -> Point((x/2500).toDouble(), rnd_gauss()) }