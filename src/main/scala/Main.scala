object Synth{
  type Time = Double //時間単位
  type Amplitude = Double //波の振幅
}

// 「波形」を表す
trait Waveform {
  import Synth.{Time, Amplitude}

  def apply(time:Time):Amplitude

  def +(amp:Amplitude):Waveform = (time:Time) => this.apply(time) + amp
  def -(amp:Amplitude):Waveform = (time:Time) => this.apply(time) - amp
  def *(amp:Amplitude):Waveform = (time:Time) => this.apply(time) * amp
  def /(amp:Amplitude):Waveform = (time:Time) => this.apply(time) / amp

  def +(waveform:Waveform):Waveform = (time:Time) =>
    this.apply(time) + waveform(time)
  def -(waveform:Waveform):Waveform = (time:Time) =>
    this.apply(time) - waveform(time)
  def *(waveform:Waveform):Waveform = (time:Time) =>
    this.apply(time) * waveform(time)
  def /(waveform:Waveform):Waveform = (time:Time) =>
    this.apply(time) / waveform(time)

  def play(time:Time)(implicit context:PCMContext) =
    context.play(this,time)
}

// 「信号処理」を表す
trait Processor {
 def apply(waveForm:Waveform) : Waveform
}

object Waveform{
  import Synth.{Time, Amplitude}
  val SineWave:Waveform = (time:Time) => Math.sin(Math.PI * time * 2.0)
  val Freq:Amplitude => Waveform = (amp:Amplitude) => (time:Time) => amp
  val Gain:Amplitude => Waveform = (gain:Amplitude) => (time:Time) => gain
}

object Processor {
  import Synth.{Time, Amplitude}
  //波形を受け取りオシレーターを返す関数
  val Oscillator:Waveform => Processor =
    (waveform:Waveform) => (pitch: Waveform) => (time: Time) => waveform(time * pitch(time))

  // サイン波オシレータ
  val SineOsc:Processor = Oscillator(Waveform.SineWave)
}

class PCMContext(val bitDepth:Int,val sampleRate:Int) {
  def waveformToArray(waveform:Waveform,time:Double):Array[Short] =
    (0 until (sampleRate * time).toInt)
      .map(time => waveform(time.toDouble)) //振幅を計算し
      .map(_ * Short.MaxValue)  //16Bitに変換し
      .map(_.toShort)  //Short型にする
      .toArray

  def play(waveform:Waveform,time:Double):Unit = {
    import javax.sound.sampled._
    import java.nio.ByteBuffer
    //サンプルレートとビット数、チャンネル数などを指定し
    val format = new AudioFormat(sampleRate.toFloat ,bitDepth,1,true,true)
    val line = AudioSystem.getSourceDataLine(format)

    //ラインにバッファを書き込む
    line.open(format)
    line.start()
    val buff = this
      .waveformToArray(waveform,time)
      .flatMap(ByteBuffer.allocate(2).putShort(_).array())
    line.write(buff,0,buff.length)
  }
}

object PCMContext {
  val Bit16_44K = new PCMContext(16,44100)
}



object Main extends App {
  import Waveform._
  import Processor._
  val lfo = SineOsc(Freq(2.0)) //低周波オシレータ
  val osc = SineOsc(Freq(440.0) + (lfo * 2.0)) //ラの音（440Hz)ピッチをLFOで揺らす
  val pure_osc= SineOsc(Freq(440.0))

  // ちょっとうるさいので、波に0.1をかけて音量を下げておく。
  //(osc * Gain(0.1)).play(1.0) (PCMContext.Bit16_44K)
  osc.play(100.0)(PCMContext.Bit16_44K)
}