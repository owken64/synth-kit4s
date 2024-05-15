def time2theta(samplingFreq:Double)(freq: Double)(time: Double): Double = time * (freq / samplingFreq) * 2.0 * Math.PI

def generateWave(bitDepth:Int, sampleRate:Int, wave_func: Double => Double, freq: Double, time: Double) = {
    import java.nio.ByteBuffer
    (0 until (sampleRate * time).toInt)
      .map(time => wave_func(time2theta(sampleRate)(freq)(time.toDouble))) //振幅を計算し
      .map(_ * Short.MaxValue)  //16Bitに変換し
      .map(_.toShort)  //Short型にする
      .toArray
      .flatMap(ByteBuffer.allocate(2).putShort(_).array())
}
