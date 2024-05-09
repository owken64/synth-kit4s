
def time2theta(samplingFreq:Double)(freq: Double)(time: Double): Double = time * (freq / samplingFreq) * 2.0 * Math.PI
def Sine(theta: Double) = Math.sin( theta )
def Saw(theta: Double) = (theta % (2.0 * Math.PI)) / 2.0 * Math.PI
def Triangle(theta:Double) = {
    if ( (theta % (2.0 * Math.PI)) < Math.PI ) (theta % (2.0 * Math.PI)) / Math.PI
    else ((2.0 * Math.PI) - (theta % (2.0 * Math.PI))) / Math.PI
}
