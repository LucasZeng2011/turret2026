package frc.robot

class Bindings(
    val robotContainer: RobotContainer
) {
    val driver = robotContainer.driverController
    val operator = robotContainer.opController
    val actions = robotContainer.actions

    fun bindControls() {
        driver
            .a()
            .onTrue(
                actions.turretForward(),
            )
        driver
            .b()
            .onTrue(
                actions.turretBackward(),
            )
        driver
            .x()
            .onTrue(
                actions.targetUp(),
            )
        driver
            .y()
            .onTrue(
                actions.targetDown(),
            )
    }
}
