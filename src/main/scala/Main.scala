
import cats.effect.unsafe.implicits._
import cats.effect.{IO, IOApp}

//object Main extends IOApp.Simple {
//  val run = AudioOut.Bit16_44K.play(Sine ,440.0)
//}

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.text.Text
import scalafx.scene.control.Button
import scalafx.Includes._

object ScalaFXHelloWorld extends JFXApp3 {
  var osc : Double => Double = Sine

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
      title = "ScalaFX Hello World"
      scene = new Scene {
        fill = Color.rgb(38, 38, 38)
        content = new HBox {
          padding = Insets(50, 80, 50, 80)
          children = Seq(
/*
            new Text {
              text = "Synth-kit"
              style = "-fx-font: normal bold 100pt sans-serif"
              fill = new LinearGradient(
                endX = 0,
                stops = Stops(Red, DarkRed))
            },
            new Text {
              text = "4s"
              style = "-fx-font: italic bold 100pt sans-serif"
              fill = new LinearGradient(
                endX = 0,
                stops = Stops(White, DarkGray)
              )
              effect = new DropShadow {
                color = DarkGray
                radius = 15
                spread = 0.25
              }
            },
*/
            new Button("Sine") {
              onMouseClicked = handle {
                osc  = Sine
              }
            },
            new Button("Triangle") {
              onMouseClicked = handle {
                osc  = Triangle
              }
            },
            new Button("Saw") {
              onMouseClicked = handle {
                osc  = Saw
              }
            },
            new Button("Square") {
              onMouseClicked = handle {
                osc  = Square
              }
            },
            new Button("Ring tone") {
              onMouseClicked = handle {
                // println("hello")
                // 音を鳴らす
                //AudioOut.Bit16_44K.play(osc ,440.0, 1.0).unsafeRunSync()
                val wave = generateWave(16,44100, osc, 440.0, 1.0 )
                AudioOut.Bit16_44K.playWave(wave)
              }
            }
          )
        }
      }
    }
  }
}