package frc.robot.subsystems.turret

import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.configs.TalonFXConfiguration
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.controls.VelocityVoltage
import com.ctre.phoenix6.controls.VoltageOut
import com.ctre.phoenix6.hardware.TalonFX
import edu.wpi.first.units.Units
import frc.robot.util.PhoenixUtil.tryUntilOk

open class TurretIOHardware : TurretIO {

    val turretMotor = TalonFX(TurretConstants.TURRET_MOTOR_ID)

    private val turretVoltageRequest = VoltageOut(0.0)
    private val turretVelocityRequest = VelocityVoltage(0.0)
    private val turretAngleRequest = PositionVoltage(0.0)

    private val turretMotorVoltage = turretMotor.motorVoltage
    private val turretMotorVelocity = turretMotor.velocity
    private val turretMotorPosition = turretMotor.position
    private val turretMotorSupplyCurrent = turretMotor.supplyCurrent
    private val turretMotorStatorCurrent = turretMotor.statorCurrent
    private val turretMotorTemp = turretMotor.deviceTemp

    private val isTurretMotorConnected: Boolean
        get() = BaseStatusSignal.isAllGood(
            turretMotorVoltage,
            turretMotorVelocity,
            turretMotorStatorCurrent
        )

    init {
        tryUntilOk(5) { turretMotor.configurator.apply(turretConfig) }
    }

    val turretConfig =
        TalonFXConfiguration().apply {
            CurrentLimits.apply {
                SupplyCurrentLimit = TurretConstants.TURRET_SUPPLY_LIM
                StatorCurrentLimit = TurretConstants.TURRET_STATOR_LIM
            }

            MotorOutput.apply {
                NeutralMode = TurretConstants.TURRET_NEUTRAL_MODE
                Inverted = TurretConstants.TURRET_INVERSION
            }

            Feedback.SensorToMechanismRatio = TurretConstants.TURRET_GEARING

            Slot0.apply {
                kP = TurretConstants.TURRET_KP
                kI = TurretConstants.TURRET_KI
                kD = TurretConstants.TURRET_KD
            }
        }

    override fun updateInputs(inputs: TurretIO.TurretIOInputs) {
        inputs.turretConnected = isTurretMotorConnected
        inputs.turretAppliedVolts = turretMotorVoltage.value.`in`(Units.Volts)
        inputs.velocityRadsPerSec = turretMotorVelocity.value.`in`(Units.RadiansPerSecond)
        inputs.turretAngle = turretMotorPosition.value.`in`(Units.Radians)
        inputs.turretSupplyCurrentAmps = turretMotorSupplyCurrent.value.`in`(Units.Amps)
        inputs.turretStatorCurrentAmps = turretMotorStatorCurrent.value.`in`(Units.Amps)
        inputs.turretTempCelsius = turretMotorTemp.value.`in`(Units.Celsius)
    }

    override fun setTurretVoltage(voltage: Double) {
        turretMotor.setControl(turretVoltageRequest.withOutput(voltage))
    }

    override fun setTurretVelocity(velocity: Double) {
        turretMotor.setControl(turretVelocityRequest.withVelocity(velocity))
    }

    override fun setTurretAngle(angle: Double) {
        turretMotor.setControl(turretAngleRequest.withPosition(angle))
    }

    override fun stopTurret() {
        turretMotor.setControl(turretVoltageRequest.withOutput(0.0))
    }
}
