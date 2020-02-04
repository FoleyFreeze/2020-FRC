package frc.robot.cals;

public class DriverCals extends CalSet {

    public boolean disabled = false;

    public MotorCal[] driveMotors = {   
        MotorCal.spark( 1).ramp(0.3).limit(0.2, 0.2).invert(), 
        MotorCal.spark(14).ramp(0.3).limit(0.2, 0.2).invert(), 
        MotorCal.spark(15).ramp(0.3).limit(0.2, 0.2).invert(), 
        MotorCal.spark(20).ramp(0.3).limit(0.2, 0.2).invert()};
    public MotorCal[] turnMotors = {   
        MotorCal.spark(11).pid(0.00005, 0, 0.0000, 0).dFilt(0.3).limit(0.3, 0.3).brake().ramp(0.3), 
        MotorCal.spark( 4).pid(0.00005, 0, 0.0000, 0).dFilt(0.3).limit(0.3, 0.3).brake().ramp(0.3), 
        MotorCal.spark( 5).pid(0.00005, 0, 0.0000, 0).dFilt(0.3).limit(0.3, 0.3).brake().ramp(0.3), 
        MotorCal.spark(10).pid(0.00005, 0, 0.0000, 0).dFilt(0.3).limit(0.3, 0.3).brake().ramp(0.3)};
    public int[] turnEncoderIds = {2, 1, 3, 0};
    public double[] xPos = {-12.375, 12.375, -12.375, 12.375};
    public double[] yPos = {10.625, 10.625, -10.625, -10.625};
    public double[] angleOffset;

    //this is for 2019 prac bot
    //public double turnGearRatio = 20.0/60.0 * 20.0/60.0 * 40.0/64.0;
    public double turnGearRatio = 1.0/60.0;

    public double pausePwrPne = 0.9;//driving over this power will pause compressor

    public double parkOffset = 99999;//0.5;

    public double driveStraightKp = 0;//0.05;//100% after abt 100deg of error
    
    public double trenchRunAngKp = 0.01;
    public double trenchRunDistKp = 0.01;

    public final double DRV_XROBOTCENT = 0.0;
    public final double DRV_YROBOTCENT = 0.0;

    public double gathererDist = 6.75;
    public double climberDist = 8.5;

    public DriverCals(){

        switch(type){
            case COMPETITION:
                angleOffset = new double[]{0.555,3.044,2.7,3.054};
            break;

            case PRACTICE:
                angleOffset = new double[]{0, 0, 0, 0};
            break;
        }

    }

}