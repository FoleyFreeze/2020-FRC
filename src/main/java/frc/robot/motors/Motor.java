package frc.robot.motors;

public abstract class Motor {

    public enum MotorType{
        PWM_TALON, SPARK_MAX, TALON_SRX
    }

    public class MotorCal{

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

        MotorType type;
        int id;
        double maxPower;
        double minPower;
        double kP;
        double kI;
        double kD;
        double kF;
    }

    public static Motor initMotor(MotorCal cal){
        switch(cal.type){
            case PWM_TALON:
                return new MotorTalonPWM(cal);

            case SPARK_MAX:
                return new MotorSparkMax(cal);

            case TALON_SRX:
                return new MotorTalonSRX(cal);

            default:
                return null;
        }
    }

    public abstract void setPower(double power);
    public abstract void setPosition(double position);
    //public abstract void setSpeed(double speed);

}