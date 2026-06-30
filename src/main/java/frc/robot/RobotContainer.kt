package frc.robot

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.subsystems.wrist.WristSubsystem
import frc.robot.Constants.Mode
import frc.robot.subsystems.wrist.WristIOHardware
import frc.robot.subsystems.wrist.WristIOSim
import frc.robot.subsystems.wrist.WristIO
import frc.robot.subsystems.RobotActions
import frc.robot.subsystems.turret.TurretIO
import frc.robot.subsystems.turret.TurretIOHardware
import frc.robot.subsystems.turret.TurretIOSim
import frc.robot.subsystems.turret.TurretSubsystem

object RobotContainer
{
    val driverController: CommandXboxController = CommandXboxController(0)
    val opController: CommandXboxController = CommandXboxController(1)

    var autonomousCommand: Command = PrintCommand("Hello World!")

    val wrist: WristSubsystem = WristSubsystem(
        when (Constants.CURRENT_MODE) {
            Mode.REAL -> WristIOHardware()
            Mode.SIM -> WristIOSim()
            Mode.REPLAY -> object : WristIO {}
        }
    )

    val turret: TurretSubsystem = TurretSubsystem(
        when (Constants.CURRENT_MODE) {
            Mode.REAL -> TurretIOHardware()
            Mode.SIM -> TurretIOSim()
            Mode.REPLAY -> object : TurretIO {}
        }
    )

    val actions = RobotActions(this)

    val bindings = Bindings(this)
}