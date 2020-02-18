package frc.robot.cals;

import frc.robot.cals.MotorCal.MotorType;

public class CannonCals extends CalSet {

    public boolean disabled = false;
    public MotorCal ccMotor = new MotorCal(MotorType.TALON_SRX, 2).invert().limit(0.2);
    public MotorCal ccMotor2 = new MotorCal(MotorType.TALON_SRX, 3).limit(0.2);
    public int hoodSolValue = 1;
    public int stopSolValue = 2;
    public int camLightsSol = 3;
    public int ShootVClimbValue = 4;
    public double layupDist;
    public double trenchDist;
    public double power = 0.2;
    public double kPDrive;
    public double tolerance = 3.0;
    public double initJogDist = 0.0;
    public double initJogAng = 0.0;
    public double distJog = 3.0;
    public double angJog = 1.0;
    public double[][] rpm = {{1000.0, 2000.0, 5400.0},
                             {1000.0, 2000.0, 5400.0},
                             {1000.0, 2000.0, 5400.0}, 
                             {1000.0, 2000.0, 5400.0}};
    public double[][] dist = {{  5.0,  40.0,  80.0},
                              { 60.0, 120.0, 180.0},
                              { 90.0, 150.0, 210.0},
                              {150.0, 250.0, 350.0}};
    public final double SOL_RESTTIME = 0.1;                                

    public CannonCals(){
        
        switch(type){
            case COMPETITION:

            break;

            case PRACTICE:

            break;
        }
    }
}