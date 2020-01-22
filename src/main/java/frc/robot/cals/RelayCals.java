package frc.robot.cals;

public class RelayCals{
    public RelayType type;
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

    public enum RelayType{
        SOLENOID
    }

    public static RelayCals solenoid(int id){
        return new RelayCals(RelayType.SOLENOID, id);
    }

    RelayCals(RelayType type, int id, double kP, double kI, double kD){

    }

    RelayCals(RelayType type, int id, double powerLim){
        this.type = type;
        this.id = id;
        this.maxPower = powerLim;
        this.minPower = -powerLim;
    }

    RelayCals(RelayType type, int id){
        this(type, id, 1);
    }

    public RelayCals pid(double p, double i, double d, double f){
        pid = true;
        kP = p;
        kI = i;
        kD = d;
        kF = f;
        return this;
    }

    public RelayCals limit(double min, double max){
        minPower = min;
        maxPower = max;
        return this;
    }

    public RelayCals dFilt(double filter){
        kDFilt = filter;
        return this;
    }

    public RelayCals ramp(double rate){
        rampRate = rate;
        return this;
    }
}