package frc.robot.subsystems.turret

import org.littletonrobotics.junction.AutoLog

interface TurretIO {
    @AutoLog
    open class TurretIOInputs {
        @JvmField var turretConnected: Boolean = false

        @JvmField var turretAppliedVolts: Double = 0.0

        @JvmField var velocityRadsPerSec: Double = 0.0

        @JvmField var turretAngle: Double = 0.0

        @JvmField var turretSupplyCurrentAmps: Double = 0.0

        @JvmField var turretStatorCurrentAmps: Double = 0.0

        @JvmField var turretTempCelsius: Double = 0.0
    }

    fun updateInputs(inputs: TurretIOInputs) {}

    fun setTurretVoltage(voltage: Double) {}

    fun setTurretVelocity(velocity: Double) {}

    fun setTurretAngle(angle: Double) {}

    fun stopTurret() {} //cut all voltage
}