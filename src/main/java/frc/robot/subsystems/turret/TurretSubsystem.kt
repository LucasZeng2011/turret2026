package frc.robot.subsystems.turret

import com.ctre.phoenix6.hardware.TalonFX
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.units.Units
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.AutoLogOutput
import edu.wpi.first.wpilibj.Alert
import kotlin.math.abs

class TurretSubsystem (
    private val io: TurretIO
) : SubsystemBase() {
    private val inputs: TurretIO.TurretIOInputs = TurretIO.TurretIOInputs()
    private val turretMotor = TalonFX(TurretConstants.TURRET_MOTOR_ID)

    @AutoLogOutput(key = "Turret/TurretAngle")
    var turretAngle: Double = 0.0
        private set
        get() = turretMotor.position.value.`in`(Units.Radians)

    @AutoLogOutput(key = "Turret/RadiansPerSec")
    var turretAngularVelocity: Double = 0.0
        private set
        get() = turretMotor.velocity.value.`in`(Units.RadiansPerSecond)

    @AutoLogOutput(key = "Turret/TargetAngle")
    var turretTargetAngle: Double = 0.0

    private val turretDisconnectedAlert =
        Alert("Turret Disconnected (ID ${TurretConstants.TURRET_MOTOR_ID}).", Alert.AlertType.kError)

    override fun periodic() {
        io.updateInputs(inputs)

        turretDisconnectedAlert.set(!inputs.turretConnected)
    }

    @AutoLogOutput(key = "Turret/TurretAtTolerance")
    fun isTurretAngleAtTolerance(): Boolean =
        abs(inputs.turretAngle - turretTargetAngle) < TurretConstants.TURRET_ANGLE_ERROR_TOLERANCE

    val pid: PIDController = PIDController(
        TurretConstants.TURRET_KP,
        TurretConstants.TURRET_KI,
        TurretConstants.TURRET_KD
    )
}
