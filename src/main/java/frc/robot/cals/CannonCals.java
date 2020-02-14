package frc.robot.cals;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.cals.MotorCal.MotorType;

public class CannonCals extends CalSet {

    public boolean disabled = true;
    public MotorCal ccMotor = new MotorCal(MotorType.TALON_SRX, -1);
    public MotorCal ccMotor2 = new MotorCal(MotorType.TALON_SRX, -1);
    public Solenoid hoodSol = new Solenoid(-1);
    public Solenoid stopSol = new Solenoid(-1);
    public Solenoid shootVsClimb = new Solenoid(-1);
    public double layupDist;
    public double trenchDist;
    public double power;
    public double kPDrive;
    public double tolerance = 3.0;
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