
class AudioOut(val bitDepth:Int,val sampleRate:Int) {

  import cats.effect.IO
  import javax.sound.sampled._
  import java.nio.ByteBuffer

  def play(wave_func: Double => Double, freq: Double, time: Double): IO[Unit] = IO.pure{
    val format = new AudioFormat(sampleRate.toFloat, bitDepth, 1, true, true)
    val line = AudioSystem.getSourceDataLine(format)

    line.open(format)
    line.start()
    val buff = generateWave(bitDepth, sampleRate, wave_func, freq, time)
    line.write(buff,0,buff.length)
  }

  def playWave(wave: Array[Byte]) : IO[Unit] = {
    IO.pure{
    val format = new AudioFormat(sampleRate.toFloat, bitDepth, 1, true, true)
    val line = AudioSystem.getSourceDataLine(format)

    line.open(format)
    line.start()
    line.write(wave,0,wave.length)
    }
  }
}

object AudioOut {
  val Bit16_44K = new AudioOut(16,44100)
}

