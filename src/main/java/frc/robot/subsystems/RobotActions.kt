package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.RobotContainer
import kotlin.math.PI

class RobotActions(
    val robotContainer: RobotContainer
) {
    val wrist = robotContainer.wrist
    val turret = robotContainer.turret

    fun runCommandOne(angle: Double): Command = wrist.runCommandOne(angle)

    fun runCommandTwo(
        angleOne: Double,
        angleTwo: Double
    ): Command = wrist.runCommandTwo(angleOne, angleTwo)

    fun turretForward(): Command = turret.moveForward()

    fun turretBackward(): Command = turret.moveBackward()

    fun targetUp(): Command = turret.setTarget(PI / 2)

    fun targetDown(): Command = turret.setTarget(3 * PI / 2)
}
