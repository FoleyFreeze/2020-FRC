package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.cals.ElectroKendro;
import frc.robot.util.Vector;

public class Inputs{
    public boolean flySky;//there is also an f310
    
    public Joystick joy = new Joystick(0);
    public Joystick ds = new Joystick(1);
    
    public Trigger gather;
    public Trigger shoot;
    public JoystickButton angleReset;
    public JoystickButton autoTrench;

    public JoystickButton climbUp;
    public JoystickButton climbDn;
    public JoystickButton enableBudClimb;
    public JoystickButton dropFoot;
    public JoystickButton manualIntake;
    public JoystickButton revolve;
    public JoystickButton manualShoot;
    public JoystickButton shift;
    public JoystickButton camMode;
    public JoystickButton trenchMode;
    public JoystickButton pitMode;
    public JoystickButton jogUp;
    public JoystickButton jogDn;
    public JoystickButton jogL;
    public JoystickButton jogR;
    
    public double xAxis;
    public double yAxis;
    public double rotXAxis;

    public ElectroKendro cals;

    public Inputs(ElectroKendro cal){
        SmartDashboard.putString("JoystickName", joy.getName());
        flySky = joy.getName().contains("FlySky");

        cals = cal;

        gather = new Trigger(new BooleanSupplier(){
            @Override
            public boolean getAsBoolean() {
                return autoGather();
            }
        });

        shoot = new Trigger(new BooleanSupplier(){
        
            @Override
            public boolean getAsBoolean() {
                return autoShoot();
            }
        });

        if(flySky){
            angleReset = new JoystickButton(joy, cals.FS_ANGRESET);
            autoTrench = new JoystickButton(joy, cals.FS_AUTOTRENCH);
        }else{
            angleReset = new JoystickButton(joy, cals.XB_ANGRESET);
            autoTrench = new JoystickButton(joy, cals.XB_AUTOTRENCH);
        }

        climbUp = new JoystickButton(ds, cals.DS_CLIMBUP);
        climbDn = new JoystickButton(ds, cals.DS_CLIMBDN);
        enableBudClimb = new JoystickButton(ds, cals.DS_ENABLEBUDCLIMB);
        dropFoot = new JoystickButton(ds, cals.DS_DROPFOOT);
        manualIntake = new JoystickButton(ds, cals.DS_MINTAKE);
        revolve = new JoystickButton(ds, cals.DS_REVOLVE);
        manualShoot = new JoystickButton(ds, cals.DS_MSHOOT);
        shift = new JoystickButton(ds, cals.DS_SHIFT);
        camMode = new JoystickButton(ds, cals.DS_CAMMODE);
        trenchMode = new JoystickButton(ds, cals.DS_TRENCHMODE);
        pitMode = new JoystickButton(ds, cals.DS_PITMODE);
        jogUp = new JoystickButton(ds, cals.DS_JOGUP);
        jogDn = new JoystickButton(ds, cals.DS_JOGDN);
        jogR = new JoystickButton(ds, cals.DS_JOGR);
        jogL = new JoystickButton(ds, cals.DS_JOGL);
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

    //driver
    public boolean autoShoot(){
        return false;
    }

    public boolean autoGather(){
        return false;
    }

    public boolean autoTrench(){
        return false;
    }

    public boolean enableBallCam(){
        return false;
    }

    //operator
    public boolean twoVThree(){
        return false;
    }

    public boolean cwRotNotPos(){
        return false;
    }

    public boolean cwActivate(){
        return false;
    }

    public boolean enableBudClimb(){
        return ds.getRawButton(cals.DS_ENABLEBUDCLIMB);
    }

    public boolean climbUp(){
        return ds.getRawButton(cals.DS_CLIMBUP);
    }

    public boolean climbDn(){
        return ds.getRawButton(cals.DS_CLIMBDN);
    }

    public boolean dropFoot(){
        return ds.getRawButton(cals.DS_DROPFOOT);
    }

    public boolean layup(){
        return (!cam() && !trench());
    }

    public boolean trench(){
        return ds.getRawButton(cals.DS_TRENCHMODE);
    }

    public boolean cam(){
        return ds.getRawButton(cals.DS_CAMMODE);
    }

    public boolean intake(){
        return ds.getRawButton(cals.DS_MINTAKE);
    }

    public boolean revolve(){
        return ds.getRawButton(cals.DS_REVOLVE);
    }

    public boolean shoot(){
        return false;
    }

    public boolean shift(){
        return false;
    }

    public boolean pitMode(){
        return ds.getRawButton(cals.DS_PITMODE);
    }
}