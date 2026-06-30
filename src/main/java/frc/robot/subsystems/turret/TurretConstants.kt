package frc.robot.subsystems.turret

import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue
import kotlin.math.PI

object TurretConstants {
    const val TURRET_MOTOR_ID = 0

    const val TURRET_ANGLE_ERROR_TOLERANCE = 1.0

    const val TURRET_STATOR_LIM = 0.0
    const val TURRET_SUPPLY_LIM = 0.0

    val TURRET_NEUTRAL_MODE = NeutralModeValue.Brake
    val TURRET_INVERSION = InvertedValue.CounterClockwise_Positive

    const val TURRET_KP = 0.0
    const val TURRET_KI = 0.0
    const val TURRET_KD = 0.0

    const val LOOP_TIME = 0.1

    // Physical
    const val TURRET_GEARING = 1.0 / 1.0
    const val TURRET_INERTIA = 0.240
    const val TURRET_LENGTH = 0.335

    const val MIN_ANGLE = 0.0
    const val MAX_ANGLE = 2.0 * PI

}