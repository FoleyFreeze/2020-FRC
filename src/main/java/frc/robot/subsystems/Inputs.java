package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.cals.InputCals;
import frc.robot.util.Vector;

public class Inputs{
    public boolean flySky;//there is also an f310
    
    public Joystick joy = new Joystick(0);
    
    public JoystickButton intakeF;
    public JoystickButton intakeR;
    public JoystickButton angleReset;
    
    public double xAxis;
    public double yAxis;
    public double rotXAxis;

    public InputCals cals;

    public Inputs(InputCals cal){
        SmartDashboard.putString("JoystickName", joy.getName());
        flySky = joy.getName().contains("FlySky");

        cals = cal;

        if(flySky){
            intakeF = new JoystickButton(joy, cals.FS_FINTAKE);
            intakeR = new JoystickButton(joy, cals.FS_RINTAKE);
            angleReset = new JoystickButton(joy, cals.FS_ANGRESET);
        }else{
            intakeF = new JoystickButton(joy, cals.XB_FINTAKE);
            intakeR = new JoystickButton(joy, cals.XB_RINTAKE);
            angleReset = new JoystickButton(joy, cals.XB_ANGRESET);
        }
    }

    public boolean fieldOrient(){
        if(flySky){
            return joy.getRawButton(cals.FS_FIELDORIENT);
        }else{
            return joy.getRawButton(cals.XB_FIELDORIENT);
        }
        
    }

    public double getX(){
        if(flySky) return expo(threshDeadband(joy.getRawAxis(
            cals.FS_XAXIS), cals.FS_LOWDEADBND, cals.FS_HIGHDEADBND), 
            cals.FS_EXPONENT);
        else return expo(scaleDeadband(joy.getRawAxis(cals.XB_XAXIS), 
            cals.XB_LOWDEADBND, cals.XB_HIGHDEADBND), cals.XB_EXPONENT);
    }

    public double getY(){
        if(flySky) return -expo(threshDeadband(joy.getRawAxis
            (cals.FS_YAXIS), cals.FS_LOWDEADBND, cals.FS_HIGHDEADBND), 
            cals.FS_EXPONENT);
        else return -expo(scaleDeadband(joy.getRawAxis(cals.XB_YAXIS), 
            cals.XB_LOWDEADBND, cals.XB_HIGHDEADBND), cals.XB_EXPONENT);
    }

    public double getRot(){
        if(flySky) return expo(threshDeadband(
            joy.getRawAxis(cals.FS_ROTAXIS), cals.FS_LOWDEADBND, 
            cals.FS_HIGHDEADBND), cals.FS_EXPONENT);
        else return expo(scaleDeadband(joy.getRawAxis(cals.XB_ROTAXIS),
             cals.XB_LOWDEADBND, cals.XB_HIGHDEADBND), cals.XB_EXPONENT);
    }

    public Vector getXY(){
        Vector v = Vector.fromXY(getX(), getY());
        if(flySky) v.scaleNorm();
        else v.threshNorm();
        return v;
    }

    private double threshDeadband(double raw, double limitLow, double limitHigh){
        if(Math.abs(raw) < limitLow){
            return 0.0;
        }else if(Math.abs(raw) > limitHigh){
            return 1.0 * Math.signum(raw);
        }
        else{
            return raw;
        }
    }

    private double scaleDeadband(double raw, double limitLow, double limitHigh){
        double temp = 1/(1-(limitLow+(1-limitHigh)));
        if(Math.abs(raw) > limitHigh) return 1.0 * Math.signum(raw);
        else if(Math.abs(raw) < limitLow) return 0.0;
        else return temp * (Math.abs(raw)-limitLow) * Math.signum(raw);
    }

    private double expo(double raw, double expo){
        return Math.pow(Math.abs(raw), expo) * Math.signum(raw);
    }
}