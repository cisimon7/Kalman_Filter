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

                        /*path {
                            fill=null
                            stroke= Colors.Web.blueviolet
                            strokeWidth=2.0

                            moveTo(xScale(model_points[0].x), yScale(model_points[0].y))
                            model_points.drop(0).forEach { (x,y) ->
                                lineTo(xScale(x), yScale(y))
                            }
                        }*/

                        path {
                            fill=null
                            stroke= Colors.Web.tomato
                            strokeWidth=2.0

                            moveTo(xScale(kalman_points[0].x), yScale(kalman_points[0].y))
                            kalman_points.drop(0).forEach { (x,y) ->
                                lineTo(xScale(x), yScale(y))
                            }
                        }

                        /*path {
                            fill=null
                            stroke= Colors.Web.blueviolet
                            strokeWidth=2.0

                            moveTo(xScale(gauss_points[0].x), yScale(gauss_points[0].y))
                            gauss_points.drop(0).forEach { (x,y) ->
                                lineTo(xScale(x), yScale(y))
                            }
                        }*/
                    }
                }
            }
        }
    }

    init { title="Kalman_Filter" }
}
