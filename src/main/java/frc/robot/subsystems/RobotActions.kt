package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.Command
import frc.robot.RobotContainer

class RobotActions (
    val robotContainer: RobotContainer
) {
    val wrist = robotContainer.wrist
    val turret = robotContainer.turret

    fun runCommandOne(angle: Double): Command =
        wrist.runCommandOne(angle)

    fun runCommandTwo(angleOne: Double, angleTwo: Double): Command =
        wrist.runCommandTwo(angleOne, angleTwo)
}