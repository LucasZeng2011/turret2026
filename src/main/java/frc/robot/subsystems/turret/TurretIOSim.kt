package frc.robot.subsystems.turret

import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.math.system.plant.LinearSystemId
import edu.wpi.first.wpilibj.simulation.DCMotorSim

class TurretIOSim : TurretIO {
    val turretSim: DCMotorSim =
        DCMotorSim(
            LinearSystemId.createDCMotorSystem(DCMotor.getNEO(1), 0.005, 3.0),
            DCMotor.getNEO(1),
        )

    init {
        turretSim.setAngle(0.0)
    }

    override fun updateInputs(inputs: TurretIO.TurretIOInputs) {
        inputs.turretAngle = turretSim.angularPositionRad
    }

    override fun setTurretVoltage(voltage: Double) {
        turretSim.setInput(voltage)
    }

    override fun getTurretAngle(): Double = turretSim.angularPositionRad
}
