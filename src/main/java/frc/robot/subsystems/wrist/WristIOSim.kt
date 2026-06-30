package frc.robot.subsystems.wrist

import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim

class WristIOSim() : WristIOHardware() {
    private val wristSim: SingleJointedArmSim =
        SingleJointedArmSim(
            DCMotor.getKrakenX60(1),
            WristConstants.WRIST_GEARING,
            WristConstants.WRIST_INERTIA,
            WristConstants.WRIST_LENGTH,
            WristConstants.MIN_ANGLE,
            WristConstants.MAX_ANGLE,
            false,
            WristConstants.MIN_ANGLE
        )

    private val wristSimState = wristMotor.simState

    override fun updateInputs(inputs: WristIO.WristIOInputs) {
        wristSimState.setSupplyVoltage(12.0)

        wristSim.setInput(wristSimState.motorVoltage)
        wristSim.update(WristConstants.LOOP_TIME)

        val wristRotorVel = Units.radiansToRotations(wristSim.velocityRadPerSec) * WristConstants.WRIST_GEARING

        wristSimState.setRotorVelocity(wristRotorVel)

        super.updateInputs(inputs)
    }
}