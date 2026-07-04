package frc.robot

import com.ctre.phoenix6.SignalLogger
import edu.wpi.first.hal.FRCNetComm
import edu.wpi.first.hal.FRCNetComm.tInstances
import edu.wpi.first.hal.FRCNetComm.tResourceType
import edu.wpi.first.hal.HAL
import edu.wpi.first.math.geometry.Pose3d
import edu.wpi.first.math.geometry.Rotation3d
import org.littletonrobotics.junction.LoggedRobot
import org.littletonrobotics.junction.Logger

object Robot : LoggedRobot() {
    private val robotContainer = RobotContainer

    init {
        println("Hello World!")

        HAL.report(FRCNetComm.tResourceType.kResourceType_Language, FRCNetComm.tInstances.kLanguage_Kotlin)

        SignalLogger.start()
        Logger.start()
    }

    override fun robotInit() {
        robotContainer.bindings.bindControls()
    }

    override fun robotPeriodic() {
        logComponentPoses()
    }

    private fun logComponentPoses() {
        val wristAngle = robotContainer.wrist.wristPitch

        Logger.recordOutput(
            "FinalComponentPoses",
            Pose3d(0.3, 0.0, 0.2, Rotation3d(0.0, 0.0, 0.0)),
            Pose3d(0.0, 0.0, 0.0, Rotation3d()),
            Pose3d(-0.1, 0.0, 0.4, Rotation3d(0.0, 0.0 + 0.2591940418, 0.0)),
        )
    }
}
