package frc.robot.subsystems.wrist

import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.AutoLogOutput
import edu.wpi.first.wpilibj.Alert
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.WaitCommand
import kotlin.math.abs

class WristSubsystem (
    private val io: WristIO
) : SubsystemBase() {
    private val inputs: WristIO.WristIOInputs = WristIO.WristIOInputs()

    @AutoLogOutput(key = "Wrist/WristAngle")
    var wristPitch: Double = 0.0
        private set

    @AutoLogOutput(key = "Wrist/RadiansPerSec")
    var wristAngularVelocity: Double = 0.0
        private set

    @AutoLogOutput(key = "Wrist/TargetAngle")
    var wristTargetAngle: Double = 0.0

    private val wristDisconnectedAlert =
        Alert("Wrist Disconnected (ID ${WristConstants.WRIST_MOTOR_ID}).", Alert.AlertType.kError)

    override fun periodic() {
        io.updateInputs(inputs)

        wristDisconnectedAlert.set(!inputs.wristConnected)
    }

    @AutoLogOutput(key = "Wrist/WristAtTolerance")
    fun isWristAngleAtTolerance(): Boolean =
        abs(inputs.wristPitch - wristTargetAngle) < WristConstants.WRIST_ANGLE_ERROR_TOLERANCE

    fun runCommandOne(angle: Double): Command =
        runOnce { io.setWristAngle(angle) }

    fun runCommandTwo(angleOne: Double, angleTwo: Double): Command =
        Commands.sequence(
            runOnce { io.setWristAngle(angleOne) },
            WaitCommand(1.0),
            runOnce { io.setWristAngle(angleTwo) }
        )
}
