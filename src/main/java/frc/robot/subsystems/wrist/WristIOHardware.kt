package frc.robot.subsystems.wrist

import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.configs.TalonFXConfiguration
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.controls.VelocityVoltage
import com.ctre.phoenix6.controls.VoltageOut
import com.ctre.phoenix6.hardware.TalonFX
import edu.wpi.first.units.Units
import edu.wpi.first.units.measure.Angle
import edu.wpi.first.units.measure.AngularVelocity
import frc.robot.util.PhoenixUtil.tryUntilOk

open class WristIOHardware : WristIO {

    val wristMotor = TalonFX(WristConstants.WRIST_MOTOR_ID)

    private val wristVoltageRequest = VoltageOut(0.0)
    private val wristVelocityRequest = VelocityVoltage(0.0)
    private val wristAngleRequest = PositionVoltage(0.0)

    private val wristMotorVoltage = wristMotor.motorVoltage
    private val wristMotorVelocity = wristMotor.velocity
    private val wristMotorPosition = wristMotor.position
    private val wristMotorSupplyCurrent = wristMotor.supplyCurrent
    private val wristMotorStatorCurrent = wristMotor.statorCurrent
    private val wristMotorTemp = wristMotor.deviceTemp

    private val isWristMotorConnected: Boolean
        get() = BaseStatusSignal.isAllGood(
            wristMotorVoltage,
            wristMotorVelocity,
            wristMotorStatorCurrent
        )

    init {
        tryUntilOk(5) { wristMotor.configurator.apply(wristConfig) }
    }

    val wristConfig =
        TalonFXConfiguration().apply {
            CurrentLimits.apply {
                SupplyCurrentLimit = WristConstants.WRIST_SUPPLY_LIM
                StatorCurrentLimit = WristConstants.WRIST_STATOR_LIM
            }

            MotorOutput.apply {
                NeutralMode = WristConstants.WRIST_NEUTRAL_MODE
                Inverted = WristConstants.WRIST_INVERSION
            }

            Feedback.SensorToMechanismRatio = WristConstants.WRIST_GEARING

            Slot0.apply {
                kP = WristConstants.WRIST_KP
                kI = WristConstants.WRIST_KI
                kD = WristConstants.WRIST_KD
            }
        }

    override fun updateInputs(inputs: WristIO.WristIOInputs) {
        inputs.wristConnected = isWristMotorConnected
        inputs.wristAppliedVolts = wristMotorVoltage.value.`in`(Units.Volts)
        inputs.pitchVelocityRadsPerSec = wristMotorVelocity.value.`in`(Units.RadiansPerSecond)
        inputs.wristPitch = wristMotorPosition.value.`in`(Units.Radians)
        inputs.wristSupplyCurrentAmps = wristMotorSupplyCurrent.value.`in`(Units.Amps)
        inputs.wristStatorCurrentAmps = wristMotorStatorCurrent.value.`in`(Units.Amps)
        inputs.wristTempCelsius = wristMotorTemp.value.`in`(Units.Celsius)
    }

    override fun setWristVoltage(voltage: Double) {
        wristMotor.setControl(wristVoltageRequest.withOutput(voltage))
    }

    override fun setWristVelocity(velocity: Double) {
        wristMotor.setControl(wristVelocityRequest.withVelocity(velocity))
    }

    override fun setWristAngle(angle: Double) {
        wristMotor.setControl(wristAngleRequest.withPosition(angle))
    }

    override fun stopWrist() {
        wristMotor.setControl(wristVoltageRequest.withOutput(0.0))
    }
}
