package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.util.Vector;

public class Inputs{
    public boolean flySky;//there is also an f310
    
    public Joystick joy = new Joystick(0);
    
    public JoystickButton intakeF;
    public JoystickButton intakeR;
    
    public double xAxis;
    public double yAxis;
    public double rotXAxis;

    public Inputs(){
        SmartDashboard.putString("JoystickName", joy.getName());
        flySky = joy.getName().contains("FlySky");

        if(flySky){
            intakeF = new JoystickButton(joy, 1);
            intakeR = new JoystickButton(joy, 2);
        }else{
            intakeF = new JoystickButton(joy, 1);
            intakeR = new JoystickButton(joy, 2);
        }
    }

    public boolean fieldOrient(){
        return joy.getRawButton(3);
    }

    public boolean angleReset(){
        return joy.getRawButton(4);
    }

    public double getX(){
        if(flySky) return expo(threshDeadband(joy.getRawAxis(0), 0.05, 1.0), 1);
        else return expo(scaleDeadband(joy.getRawAxis(0), 0.1, 0.9), 1);
    }

    public double getY(){
        if(flySky) return -expo(threshDeadband(joy.getRawAxis(1), 0.05, 1.0), 1);
        else return -expo(scaleDeadband(joy.getRawAxis(1), 0.1, 0.9), 1);
    }

    public double getRot(){
        if(flySky) return expo(threshDeadband(joy.getRawAxis(4), 0.05, 1.0), 1);
        else return expo(scaleDeadband(joy.getRawAxis(4), 0.10, 0.9), 1);
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