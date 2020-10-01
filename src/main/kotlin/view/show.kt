package view

import io.data2viz.axis.Orient
import io.data2viz.axis.axis
import io.data2viz.color.Colors
import io.data2viz.color.col
import io.data2viz.geom.size
import javafx.scene.Parent
import round
import tornadofx.*
import visualize

fun main(args: Array<String>) = launch<KalmanApp>(args)

class KalmanApp : App(MyView::class)

class MyView : View() {
    override val root: Parent = group {
        canvas(width, height) {
            visualize {
                size = size(chartWidth, chartHeight)
                group {
                    transform { translate(x= margins.left, y= margins.top) }

                    group {
                        transform { translate(x= -10.0) }
                        axis(Orient.LEFT, yScale){ tickFormat={it.round().toString()} }
                    }

                    group {
                        transform { translate(y= chartHeight+10.0) }
                        axis(Orient.BOTTOM, xScale) { tickFormat={it.round().toString()} }
                    }

                    group {
                        path {
                            fill=null
                            stroke="#FECE3E".col
                            strokeWidth=3.0

                            moveTo(xScale(sensor_points[0].x), yScale(sensor_points[0].y))
                            sensor_points.drop(0).forEach { (x,y) ->
                                lineTo(xScale(x), yScale(y))
                            }
                        }

                        path {
                            fill=null
                            stroke= Colors.Web.tomato
                            strokeWidth=2.0

                            moveTo(xScale(kalmanFilter[0].x), yScale(kalmanFilter[0].y))
                            kalmanFilter.drop(0).forEach { (x,y) ->
                                lineTo(xScale(x), yScale(y))
                            }
                        }

                        // uncomment to see how the kalman's constant is changing, also change y-axis domain range
                        /*path {
                            fill=null
                            stroke= Colors.Web.green
                            strokeWidth=2.0

                            moveTo(xScale(kalmanConstant[0].x), yScale(kStab))
                            kalmanConstant.drop(0).forEach { (x, _) ->
                                lineTo(xScale(x), yScale(kStab))
                            }
                        }

                        kalmanConstant.forEach { (x_, y_) ->
                            circle {
                                x = xScale(x_)
                                y = yScale(y_)
                                radius = 3.0
                                fill = Colors.Web.crimson
                            }
                        }*/
                    }
                }
            }
        }
    }

    init { title="Kalman_Filter" }
}
