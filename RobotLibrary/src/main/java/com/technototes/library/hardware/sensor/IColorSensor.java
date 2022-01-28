
package com.technototes.library.hardware.sensor;

import android.graphics.Color;

import androidx.annotation.ColorInt;

public interface IColorSensor {
    /** Get the RGB red of the sensor
     *
     * @return Red
     */
    default int red(){
        return (argb() >> 16) & 0xff;
    }
    /** Get the RGB green of the sensor
     *
     * @return Green
     */
    default int green(){
        return (argb() >> 8) & 0xff;
    }
    /** Get the RGB blue of the sensor
     *
     * @return Blue
     */
    default int blue(){
        return (argb()) & 0xff;

    }

    /** Get the alpha (transparency) of the color
     *
     * @return Alpha
     */
    default int alpha(){
        return (argb() >> 24) & 0xff;

    }
    /** Get HSV as an int
     *
     * @return HSV
     */
    default int hsv(){
        float[] f = this.hsvArray();
        return ((int)f[0] << 16) | ((int)f[1] << 8) | (int)f[2];
    }

    default int rgb(){
        return (argb()) & 0x00ffffff;
    }

    @ColorInt
    int argb();

    default float[] hsvArray(){
        float[] f = new float[3];

        Color.RGBToHSV(red(),green(),blue(),f);
        return f;
    }


    /** Get HSV hue
     *
     * @return Hue
     */
    default int hue(){
        return (int) this.hsvArray()[0];
    }

    /** Get HSV saturation
     *
     * @return Saturation
     */
    default int saturation(){
        return (int) this.hsvArray()[1];
    }

    /** Get HSV value (not entire HSV, just 'V')
     *
     * @return Value
     */
    default int value(){
        return (int) this.hsvArray()[2];
    }
}
