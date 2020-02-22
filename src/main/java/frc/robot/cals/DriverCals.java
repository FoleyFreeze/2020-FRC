package frc.robot.cals;

public class DriverCals extends CalSet {

    public boolean disabled = false;

    public MotorCal[] driveMotors = {   
        MotorCal.spark(20).ramp(0.3).limit(0.25).coast(), 
        MotorCal.spark( 1).ramp(0.3).limit(0.25).coast(), 
        MotorCal.spark(14).ramp(0.3).limit(0.25).coast(), 
        MotorCal.spark(15).ramp(0.3).limit(0.25).coast()};
    public MotorCal[] turnMotors = {   
        MotorCal.spark( 8).pid(0.08, 0, 0.16, 0).dFilt(0.01).limit(0.3).brake().ramp(0.001), 
        MotorCal.spark( 4).pid(0.08, 0, 0.16, 0).dFilt(0.01).limit(0.3).brake().ramp(0.001), 
        MotorCal.spark(10).pid(0.08, 0, 0.16, 0).dFilt(0.01).limit(0.3).brake().ramp(0.001), 
        MotorCal.spark(11).pid(0.08, 0, 0.16, 0).dFilt(0.01).limit(0.3).brake().ramp(0.001)};
    public int[] turnEncoderIds = {2, 1, 3, 0};
    public double[] xPos = {-10.75, 10.75, -10.75, 10.75};
    public double[] yPos = {12.5, 12.5, -12.5, -12.5};
    public double[] angleOffset;

    public double turnTicksPerRev = 60.0;

    public double driveTicksPerIn = 64.0/18.0 * 18.0/32.0 * 45.0/15.0 / (4.0*Math.PI);

    public double pausePwrPne = 0.9;//driving over this power will pause compressor

    public double parkOffset = 60;//0.5;

    public double driveStraightKp = 0;//0.05;//100% after abt 100deg of error
    
    public double trenchRunAngKp = 0.01;
    public double trenchRunDistKp = 0.01;

    public double autoDriveStrafeKp = 0.01;
    public double autoDriveAngKp = 0.01;
    public double autoDriveStrafeRange = 6;
    public double autoDriveAngRange = 6;
    public double autoDriveMaxPwr = 0.3;
    public double autoDriveStartPwr = 0.03;
    public double autoDriveEndPwr = 0.05;
    public double autoDriveStartDist = 42;
    public double autoDriveEndDist = 42;

    public final double DRV_XROBOTCENT = 0.0;
    public final double DRV_YROBOTCENT = 0.0;

    public double gathererDist = 6.75;
    public double climberDist = 8.5;

    public final double DRV_TURNDEADBND = 10;

    public final double DRV_GATHERKP = 0.01;

    public DriverCals(){

        switch(type){
            case COMPETITION:
                                        //these are in encoder order (not wheel order) 
                angleOffset = new double[]{0,0,0,0};
            break;

            case PRACTICE:
                angleOffset = new double[]{3.052,3.037,0.536,2.168};
            break;

            case LASTYEAR:
                angleOffset = new double[]{0.988,4.673,0.688,3.042};
                
                driveMotors[0] = MotorCal.spark( 1).ramp(0.3).limit(0.5).coast().invert();
                driveMotors[1] = MotorCal.spark(14).ramp(0.3).limit(0.5).coast().invert();
                driveMotors[2] = MotorCal.spark(20).ramp(0.3).limit(0.5).coast().invert();
                driveMotors[3] = MotorCal.spark(15).ramp(0.3).limit(0.5).coast().invert();

                turnMotors[0] = MotorCal.spark( 5).pid(0.25, 0, 0.2, 0).dFilt(0.01).limit(0.2).brake().ramp(0.001);
                turnMotors[1] = MotorCal.spark(11).pid(0.25, 0, 0.2, 0).dFilt(0.01).limit(0.2).brake().ramp(0.001); 
                turnMotors[2] = MotorCal.spark( 4).pid(0.25, 0, 0.2, 0).dFilt(0.01).limit(0.2).brake().ramp(0.001); 
                turnMotors[3] = MotorCal.spark(10).pid(0.25, 0, 0.2, 0).dFilt(0.01).limit(0.2).brake().ramp(0.001);

                turnEncoderIds[0] = 0;
                turnEncoderIds[1] = 1;
                turnEncoderIds[2] = 2;
                turnEncoderIds[3] = 3;

                driveTicksPerIn = 24.0/10.0 * 2.0/1.0 /(3*Math.PI) ;
                turnTicksPerRev = 60.0/20.0 * 60.0/20.0 * 64.0/40.0 ;
            break;

        }

    }

}