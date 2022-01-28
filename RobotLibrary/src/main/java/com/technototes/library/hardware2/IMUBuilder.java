package com.technototes.library.hardware2;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;

import java.util.function.Consumer;

public class IMUBuilder extends HardwareBuilder<BNO055IMU> {

    public enum AxesSigns {
        PPP(0b000),
        PPN(0b001),
        PNP(0b010),
        PNN(0b011),
        NPP(0b100),
        NPN(0b101),
        NNP(0b110),
        NNN(0b111);

        public final int bVal;

        AxesSigns(int bVal) {
            this.bVal = bVal;
        }
    }

    private BNO055IMU.Parameters parameters;


    public IMUBuilder(String name) {
        super(name);
        parameters = new BNO055IMU.Parameters();
    }

    public IMUBuilder(BNO055IMUImpl device) {
        super(device);
        parameters = new BNO055IMU.Parameters();
    }

    public IMUBuilder parameter(Consumer<BNO055IMU.Parameters> consumer){
        consumer.accept(parameters);
        return this;
    }

    public IMUBuilder degrees(){
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        return this;
    }

    public IMUBuilder radians(){
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        return this;
    }

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


    @Override
    public <U extends BNO055IMU> U build() {
        product.initialize(parameters);
        return (U) product;
    }
}
