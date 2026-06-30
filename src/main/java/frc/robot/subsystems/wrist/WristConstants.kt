package frc.robot.subsystems.wrist

import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue

object WristConstants {
    const val WRIST_MOTOR_ID = 0

    const val WRIST_ANGLE_ERROR_TOLERANCE = 1.0

    const val WRIST_STATOR_LIM = 0.0
    const val WRIST_SUPPLY_LIM = 0.0

    val WRIST_NEUTRAL_MODE = NeutralModeValue.Brake
    val WRIST_INVERSION = InvertedValue.CounterClockwise_Positive

    const val WRIST_KP = 0.0
    const val WRIST_KI = 0.0
    const val WRIST_KD = 0.0

    const val LOOP_TIME = 0.1

    // Physical
    const val WRIST_GEARING = 1.0 / 1.0
    const val WRIST_INERTIA = 0.240
    const val WRIST_LENGTH = 0.335

    const val MIN_ANGLE = 1.0
    const val MAX_ANGLE = 2.0

}