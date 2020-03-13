package frc.robot.cals;

import frc.robot.cals.MotorCal.MotorType;

public class CannonClimbCals extends CalSet {

    //sol = solenoid(all values because actual motors/solenoids established in the cannon climber susystem)

    //Cannon
    public boolean shootDisabled = false;
    public MotorCal ccMotor = new MotorCal(MotorType.TALON_SRX, 2).limit(1);            //shooter motor
    public MotorCal ccMotor2 = new MotorCal(MotorType.TALON_SRX, 3).invert().follow(2); //shooter motor 2
    public double falconRpmPerPower = 5400;//max rot for shooter
    public int hoodSolValue = 1;//make the hood go up
    public int stopSolValue = 0;//the pancakes(stop the other solenoid)
    public int camLightsSol = 3; //camera lights solenoid
    public int ShootVClimbValue = 6;//solenoid switch from cannon to climber
    public int climbBrake = 4;
    public double layupDist = 0.0;
    public double trenchDist = 208.75;
    public double autonDist = 83;
    public double manualPower = 0.5;
    public double kPDrive = 0.05;
    public double kDDrive = 0.05;
    public double maxRot = 0.2;
    public double tolerance = 3.0;
    public double initJogDist = 0.0;
    public double initJogAng = 0.0;
    public double distJog = 0.5;
    public double angJog = 1.0;
    public double shootTime = 1.5;
    public double shootCentX = 0.0;
    public double shootCentY = 0.0;
    public double[][] rpm = {{2700.0, 2775.0, 3000.0},
                             {2700.0, 2900.0, 3000.0},
                             {2700.0, 3000.0, 4000.0}, 
                             {2700.0, 3350.0, 5400.0}};
    public double[][] dist = {{  -1.0,  -1.0,  -0.1},
                              { -0.5, 0.0, 35.0},
                              { 30.0, 60.0, 70.0},
                              {60.0, 83.0, 500.0}};
    public double allowedRpmError = 150;
    public double allowedRpmHyst = 5000;
    public final double SOL_RESTTIME = 0.2;

    //Climber
    public boolean climbDisabled = true;
    //public int dropFootValue = -1;
    public double upPower = 0.3;
    public double dnPower = -0.7;

    public CannonClimbCals(){
        
        switch(type){
            case COMPETITION:

            break;

            case PRACTICE:
                /*
                ccMotor = new MotorCal(MotorType.TALON_SRX, 2).limit(-0.55, 0.55);
                ccMotor2 = new MotorCal(MotorType.TALON_SRX, 3).invert().limit(-0.55, 0.55);

                hoodSolValue = 2;
                stopSolValue = 0;
                camLightsSol = -1;
                ShootVClimbValue = -1;
                */
            break;

            case LASTYEAR:

            break;
        }
    }
}