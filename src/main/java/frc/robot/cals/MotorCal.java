package frc.robot.cals;

public class MotorCal {
    public MotorType type;
    public int id;
    public double minPower;
    public double maxPower;
    public boolean pid = false;
    public double kP;
    public double kI;
    public double kD;
    public double kDFilt;
    public double kF;
    public double rampRate;
    public boolean brake;
    public boolean invert = false;

    public enum MotorType{
        PWM_TALON, SPARK_MAX, TALON_SRX
    }

    public static MotorCal spark(int id){
        return new MotorCal(MotorType.SPARK_MAX, id);
    }

    public static MotorCal pwm(int id){
        return new MotorCal(MotorType.PWM_TALON, id);
    }

    public static MotorCal srx(int id){
        return new MotorCal(MotorType.TALON_SRX, id);
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

    public MotorCal pid(double p, double i, double d, double f){
        pid = true;
        kP = p;
        kI = i;
        kD = d;
        kF = f;
        return this;
    }

    public MotorCal brake(){
        this.brake = true;
        return this;
    }

    public MotorCal invert(){
        this.invert = true;
        return this;
    }

    public MotorCal coast(){
        brake = false;
        return this;
    }

    public MotorCal limit(double min, double max){
        minPower = -min;
        maxPower = max;
        return this;
    }

    public MotorCal limit(double lim){
        return limit(lim,lim);
    }

    public MotorCal dFilt(double filter){
        kDFilt = filter;
        return this;
    }

    public MotorCal ramp(double rate){
        rampRate = rate;
        return this;
    }
}