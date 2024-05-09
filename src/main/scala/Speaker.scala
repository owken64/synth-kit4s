
class AudioOut(val bitDepth:Int,val sampleRate:Int) {

  import cats.effect.IO
  def play(wave_func: Double => Double, freq: Double, time: Double): IO[Unit] = IO.pure{
    import javax.sound.sampled._
    import java.nio.ByteBuffer
    val format = new AudioFormat(sampleRate.toFloat, bitDepth, 1, true, true)
    val line = AudioSystem.getSourceDataLine(format)

    line.open(format)
    line.start()
    val buff = (0 until (sampleRate * time).toInt)
      .map(time => wave_func(time2theta(sampleRate)(freq)(time.toDouble))) //振幅を計算し
      .map(_ * Short.MaxValue)  //16Bitに変換し
      .map(_.toShort)  //Short型にする
      .toArray
      .flatMap(ByteBuffer.allocate(2).putShort(_).array())
    line.write(buff,0,buff.length)
  }
}

object AudioOut {
  val Bit16_44K = new AudioOut(16,44100)
}

