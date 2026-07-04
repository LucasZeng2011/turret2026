package frc.robot.subsystems.turret

import com.ctre.phoenix6.hardware.TalonFX
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.util.Color
import edu.wpi.first.wpilibj.util.Color8Bit
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger
import kotlin.math.PI

class TurretSubsystem(
    private val io: TurretIO
) : SubsystemBase() {
    private val inputs: TurretIOInputsAutoLogged = TurretIOInputsAutoLogged()
    private val turretMotor = TalonFX(TurretConstants.TURRET_MOTOR_ID)

    val turretPID: PIDController =
        PIDController(
            TurretConstants.TURRET_KP,
            TurretConstants.TURRET_KI,
            TurretConstants.TURRET_KD,
        )

    var viz: Mechanism2d = Mechanism2d(5.0, 5.0)
    var root: MechanismRoot2d = viz.getRoot("core", 2.5, 2.5)
    var turretController: MechanismLigament2d =
        root.append<MechanismLigament2d>(
            MechanismLigament2d(
                "turret",
                1.0,
                0.0,
                6.0,
                Color8Bit(Color.kPurple),
            ),
        )
    var targetController: MechanismLigament2d =
        root.append<MechanismLigament2d>(
            MechanismLigament2d(
                "target",
                0.7,
                0.0,
                3.0,
                Color8Bit(Color.kRed),
            ),
        )

    init {
        turretPID.setpoint = 0.0
    }

    override fun periodic() {
        io.updateInputs(inputs)

        targetController.setAngle(turretPID.setpoint * 180 / PI)
        turretController.setAngle(inputs.turretAngle * 180 / PI)

        SmartDashboard.putData("turret", viz)

        Logger.processInputs("Turret", inputs)

        io.setTurretVoltage(turretPID.calculate(io.getTurretAngle()))
    }

    fun moveForward(): Command = runOnce { io.setTurretVoltage(5.0) }

    fun moveBackward(): Command = runOnce { io.setTurretVoltage(-5.0) }

    fun stopTurret(): Command = runOnce { io.setTurretVoltage(0.0) }

    fun setTarget(angle: Double): Command = runOnce { turretPID.setpoint = angle }

    /*@AutoLogOutput(key = "Turret/TurretAngle")
    var turretAngle: Double = 0.0
        private set
        get() = turretMotor.position.value.`in`(Units.Radians)

    @AutoLogOutput(key = "Turret/RadiansPerSec")
    var turretAngularVelocity: Double = 0.0
        private set
        get() = turretMotor.velocity.value.`in`(Units.RadiansPerSecond)

    @AutoLogOutput(key = "Turret/TargetAngle")
    var turretTargetAngle: Double = 0.0*/
}
