package frc.robot.subsystems.turret

import edu.wpi.first.math.system.LinearSystem
import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj.simulation.LinearSystemSim
import edu.wpi.first.math.Matrix
import edu.wpi.first.math.Nat
import edu.wpi.first.math.Num

class TurretIOSim() : TurretIOHardware() {
    private val turretSim: LinearSystemSim<Num(1), Num(2), Num(3)> =
        LinearSystemSim(
            LinearSystem(
                Matrix(Nat.N1(), Nat.N1()),
                Matrix(Nat.N1(), Nat.N1()),
                Matrix(Nat.N1(), Nat.N1()),
                Matrix(Nat.N1(), Nat.N1()),
            ),
            1.0,
            1.0
        )

    private val turretSimState = turretMotor.simState

    override fun updateInputs(inputs: TurretIO.TurretIOInputs) {
        turretSimState.setSupplyVoltage(12.0)

        turretSim.setInput(turretSimState.motorVoltage)
        turretSim.update(TurretConstants.LOOP_TIME)

        val turretRotorVel = Units.radiansToRotations(turretSim.velocityRadPerSec) * TurretConstants.TURRET_GEARING

        turretSimState.setRotorVelocity(turretRotorVel)

        super.updateInputs(inputs)
    }
}