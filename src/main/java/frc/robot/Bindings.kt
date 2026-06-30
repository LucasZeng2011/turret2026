package frc.robot

class Bindings (
    val robotContainer: RobotContainer
) {
    val driver = robotContainer.driverController
    val operator = robotContainer.opController
    val actions = robotContainer.actions

    fun bindControls() {
        driver
            .a()
            .onTrue(
                actions.runCommandOne(1.0)
            )
        driver
            .b()
            .onTrue(
                actions.runCommandOne(0.0)
            )
        driver
            .x()
            .onTrue(
                actions.runCommandTwo(1.0, 0.0)
            )
        driver
            .y()
            .onTrue(
                actions.runCommandTwo(1.0, 2.0)
            )
    }
}