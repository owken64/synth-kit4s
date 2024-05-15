
def Sine(theta: Double) = Math.sin( theta )
def Saw(theta: Double) = (theta % (2.0 * Math.PI)) / (2.0 * Math.PI)
def Triangle(theta:Double) = {
    if ( (theta % (2.0 * Math.PI)) < Math.PI ) (theta % (2.0 * Math.PI)) / Math.PI
    else ((2.0 * Math.PI) - (theta % (2.0 * Math.PI))) / Math.PI
}
def Square(theta: Double) = {
    if ( (theta % (2.0 * Math.PI)) < Math.PI) 1.0
    else -1.0
}

def Clipping(wave: Double) : Double = {
    if (wave > 1.0) 1.0
    else if (wave < -1.0) -1.0
    else wave
}
