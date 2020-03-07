package frc.robot.cals;

import frc.robot.cals.MotorCal.MotorType;

public class CannonClimbCals extends CalSet {

    //Cannon
    public boolean shootDisabled = true;
    public MotorCal ccMotor = new MotorCal(MotorType.TALON_SRX, 2).invert().limit(0.5);
    public MotorCal ccMotor2 = new MotorCal(MotorType.TALON_SRX, 3).limit(0.5);
    public int hoodSolValue = 2;
    public int stopSolValue = 0;
    public int camLightsSol;
    public int ShootVClimbValue;
    public double layupDist = 24.0;
    public double trenchDist = 208.75;
    public double power = 0.5;
    public double kPDrive = 0.05;
    public double kDDrive = 0.05;
    public double maxRot = 0.2;
    public double tolerance = 3.0;
    public double initJogDist = 0.0;
    public double initJogAng = 0.0;
    public double distJog = 3.0;
    public double angJog = 1.0;
    public double shootTime = .25;
    public double[][] rpm = {{1000.0, 2000.0, 5400.0},
                             {1000.0, 2000.0, 5400.0},
                             {1000.0, 2000.0, 5400.0}, 
                             {1000.0, 2000.0, 5400.0}};
    public double[][] dist = {{  5.0,  40.0,  80.0},
                              { 60.0, 120.0, 180.0},
                              { 90.0, 150.0, 210.0},
                              {150.0, 250.0, 350.0}};
    public final double SOL_RESTTIME = 0.1; 
    
    //Climber
    public boolean climbDisabled = true;
    public int dropFootValue = -1;
    public static double upPower = 0.3;
    public static double dnPower = -0.7;

    public CannonClimbCals(){
        
        switch(type){
            case COMPETITION:

            break;

            case PRACTICE:
                ccMotor = new MotorCal(MotorType.TALON_SRX, 2).limit(-0.55, 0.55);
                ccMotor2 = new MotorCal(MotorType.TALON_SRX, 3).invert().limit(-0.55, 0.55);

                hoodSolValue = 2;
                stopSolValue = 0;
                camLightsSol = -1;
                ShootVClimbValue = -1;
            break;

            case LASTYEAR:

            break;
        }
    }
}