package frc.robot.subsystems.wrist

import org.littletonrobotics.junction.AutoLog

interface WristIO {
    @AutoLog
    open class WristIOInputs {
        @JvmField var wristConnected: Boolean = false

        @JvmField var wristAppliedVolts: Double = 0.0

        @JvmField var pitchVelocityRadsPerSec: Double = 0.0

        @JvmField var wristPitch: Double = 0.0

        @JvmField var wristSupplyCurrentAmps: Double = 0.0

        @JvmField var wristStatorCurrentAmps: Double = 0.0

        @JvmField var wristTempCelsius: Double = 0.0
    }

    fun updateInputs(inputs: WristIOInputs) {}

    fun setWristVoltage(voltage: Double) {}

    fun setWristVelocity(velocity: Double) {}

    fun setWristAngle(angle: Double) {}

    fun stopWrist() {} //cut all voltage
}