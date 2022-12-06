package com.technototes.library.hardware2;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import java.util.function.Consumer;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;

/**
 * TODO: Remove this. I don't believe this adds much value. Yeah, HardwareMap.get is weird, but
 * it's in all the documentation, so just read it, see it in the examples, and you're done.
 */
public class IMUBuilder extends HardwareBuilder<BNO055IMU> {

    /**
     * This is duplicated in the IMU class
     */
    public enum AxesSigns {
        /**
         * deprecated
         */
        PPP(0b000),
        /**
         * deprecated
         */
        PPN(0b001),
        /**
         * deprecated
         */
        PNP(0b010),
        /**
         * deprecated
         */
        PNN(0b011),
        /**
         * deprecated
         */
        NPP(0b100),
        /**
         * deprecated
         */
        NPN(0b101),
        /**
         * deprecated
         */
        NNP(0b110),
        /**
         * deprecated
         */
        NNN(0b111);

        /**
         * deprecated
         */
        public final int bVal;

        /**
         * deprecated
         */
        AxesSigns(int bVal) {
            this.bVal = bVal;
        }
    }

    private BNO055IMU.Parameters parameters;

    /**
     * deprecated
     *
     * @param name Deprecated
     */
    public IMUBuilder(String name) {
        super(name);
        parameters = new BNO055IMU.Parameters();
    }

    /**
     * Deprecated
     *
     * @param device Deprecated
     */
    public IMUBuilder(BNO055IMUImpl device) {
        super(device);
        parameters = new BNO055IMU.Parameters();
    }

    /**
     * Deprecated
     *
     * @param consumer Deprecated
     * @return Deprecated
     */
    public IMUBuilder parameter(Consumer<BNO055IMU.Parameters> consumer) {
        consumer.accept(parameters);
        return this;
    }

    /**
     * Deprecated
     *
     * @return Deprecated
     */
    public IMUBuilder degrees() {
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        return this;
    }

    /**
     * Deprecated
     *
     * @return Deprecated
     */
    public IMUBuilder radians() {
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        return this;
    }

    /**
     * Deprecated
     *
     * @param order Deprecated
     * @param signs Deprecated
     * @return Deprecated
     */
    public IMUBuilder remap(AxesOrder order, AxesSigns signs) {
        try {
            // the indices correspond with the 2-bit encodings specified in the datasheet
            int[] indices = order.indices();
            int axisMapConfig = 0;
            axisMapConfig |= (indices[0] << 4);
            axisMapConfig |= (indices[1] << 2);
            axisMapConfig |= (indices[2]);

            // the BNO055 driver flips the first orientation vector so we also flip here
            int axisMapSign = signs.bVal ^ (0b100 >> indices[0]);

            // Enter CONFIG mode
            product.write8(BNO055IMU.Register.OPR_MODE, BNO055IMU.SensorMode.CONFIG.bVal & 0x0F);

            Thread.sleep(100);

            // Write the AXIS_MAP_CONFIG register
            product.write8(BNO055IMU.Register.AXIS_MAP_CONFIG, axisMapConfig & 0x3F);

            // Write the AXIS_MAP_SIGN register
            product.write8(BNO055IMU.Register.AXIS_MAP_SIGN, axisMapSign & 0x07);

            // Switch back to the previous mode
            product.write8(BNO055IMU.Register.OPR_MODE, product.getParameters().mode.bVal & 0x0F);

            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        parameters = product.getParameters();
        return this;
    }

    /**
     * Deprecated
     *
     * @param <U> Deprecated
     * @return Deprecated
     */
    @Override
    public <U extends BNO055IMU> U build() {
        product.initialize(parameters);
        return (U) product;
    }
}
