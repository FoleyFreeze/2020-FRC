package frc.robot.cals;

public class MotorCal {
    public enum MotorType{
        PWM_TALON, SPARK_MAX, TALON_SRX
    }

    MotorCal(MotorType type, int id, double kP, double kI, double kD){

    }
    
    MotorCal(MotorType type, int id, double powerLim){
        this.type = type;
        this.id = id;
        this.maxPower = powerLim;
        this.minPower = -powerLim;
    }

    MotorCal(MotorType type, int id){
        this(type, id, 1);  
    }   

    public MotorType type;
    public int id;
    double maxPower;
    double minPower;
    double kP;
    double kI;
    double kD;
    double kF;
}