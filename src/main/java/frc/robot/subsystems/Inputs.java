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

    public Trigger climbUp;
    public Trigger climbDn;
    public JoystickButton shift;
    public JoystickButton dropFoot;
    public JoystickButton manualIntake;
    public JoystickButton revolve;
    public JoystickButton manualShoot;
    public Trigger jogUp;
    public Trigger jogDn;
    public Trigger jogL;
    public Trigger jogR;
    public JoystickButton cwActivate;
    public Trigger layupTrigger;
    
    public double xAxis;
    public double yAxis;
    public double rotXAxis;

    public ElectroKendro k;

    public Inputs(ElectroKendro k){
        SmartDashboard.putString("JoystickName", joy.getName());
        flySky = joy.getName().contains("FlySky");

        this.k = k;

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

        climbUp = new Trigger(new BooleanSupplier(){                
            @Override
            public boolean getAsBoolean(){
                return climbUp();
            }
        });

        climbDn = new Trigger(new BooleanSupplier(){
        
            @Override
            public boolean getAsBoolean() {
                return climbDn();
            }
        });

        layupTrigger = new Trigger(new BooleanSupplier(){
        
            @Override
            public boolean getAsBoolean() {
                return layup();
            }
        });

        jogUp = new Trigger(new BooleanSupplier(){
        
            @Override
            public boolean getAsBoolean() {
                return jogUp();
            }
        });

        jogDn = new Trigger(new BooleanSupplier(){
        
            @Override
            public boolean getAsBoolean() {
                return jogDn();
            }
        });

        jogR = new Trigger(new BooleanSupplier(){
        
            @Override
            public boolean getAsBoolean() {
                return jogR();
            }
        });

        jogL = new Trigger(new BooleanSupplier(){
        
            @Override
            public boolean getAsBoolean() {
                return jogL();
            }
        });

        if(flySky){
            angleReset = new JoystickButton(joy, k.FS_ANGRESET);
            autoTrench = new JoystickButton(joy, k.FS_AUTOTRENCH);
        }else{
            angleReset = new JoystickButton(joy, k.XB_ANGRESET);
            autoTrench = new JoystickButton(joy, k.XB_AUTOTRENCH);
        }

        dropFoot = new JoystickButton(ds, k.DS_DROPFOOT);
        manualIntake = new JoystickButton(ds, k.DS_MINTAKE);
        manualShoot = new JoystickButton(ds, k.DS_MSHOOT);
        revolve = new JoystickButton(ds, k.DS_REVOLVE);        
        cwActivate = new JoystickButton(ds, k.DS_CWACTIVATE);
        shift = new JoystickButton(ds, k.DS_SHIFT);
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
        return joy.getRawAxis(k.FS_AUTOSHOOT) > 0.5;
    }

    public boolean autoGather(){
        return joy.getRawAxis(k.FS_AUTOGATHER) > 0.5;
    }

    public boolean autoTrench(){
        return joy.getRawButton(k.FS_AUTOTRENCH);
    }

    public boolean enableBallCam(){
        return joy.getRawButton(k.FS_EnblBallCam);
    }

    public boolean fieldOrient(){
        if(flySky){
            return joy.getRawButton(k.FS_FIELDORIENT);
        }else{
            return joy.getRawButton(k.XB_FIELDORIENT);
        }
        
    }

    public double getX(){
        double val;
        if(flySky) val = expo(threshDeadband(joy.getRawAxis(
            k.FS_XAXIS), k.FS_LOWDEADBND, k.FS_HIGHDEADBND), 
            k.FS_EXPONENT);
        else val = expo(scaleDeadband(joy.getRawAxis(k.XB_XAXIS), 
            k.XB_LOWDEADBND, k.XB_HIGHDEADBND), k.XB_EXPONENT);

        SmartDashboard.putNumber("X", val);
        return val;
    }

    public double getY(){
        double val;
        if(flySky) val = -expo(threshDeadband(joy.getRawAxis
            (k.FS_YAXIS), k.FS_LOWDEADBND, k.FS_HIGHDEADBND), 
            k.FS_EXPONENT);
        else val = -expo(scaleDeadband(joy.getRawAxis(k.XB_YAXIS), 
            k.XB_LOWDEADBND, k.XB_HIGHDEADBND), k.XB_EXPONENT);

        SmartDashboard.putNumber("Y", val);
        return val;
    }

    public double getRot(){
        double val;
        if(flySky) val = expo(threshDeadband(
            joy.getRawAxis(k.FS_ROTAXIS), k.FS_LOWDEADBND, 
            k.FS_HIGHDEADBND), k.FS_EXPONENT);
        else val = expo(scaleDeadband(joy.getRawAxis(k.XB_ROTAXIS),
             k.XB_LOWDEADBND, k.XB_HIGHDEADBND), k.XB_EXPONENT);

        SmartDashboard.putNumber("R", val);
        return val;
    }

    public Vector getXY(){
        Vector v = Vector.fromXY(getX(), getY());
        if(flySky) v.scaleNorm();
        else v.threshNorm();
        return v;
    }

    //operator
    public boolean twoVThree(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawButton(k.DS_TWOVTHREE);
    }

    public boolean cwRotNotPos(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawButton(k.DS_CWROTPOS);
    }

    public boolean cwActivate(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawButton(k.DS_CWACTIVATE);
    }

    public boolean enableBudClimb(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawButton(k.DS_ENABLEBUDCLIMB);
    }

    public boolean climbUp(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawAxis(k.DS_CLIMBUP) > 0.5;
    }

    public boolean climbDn(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawAxis(k.DS_CLIMBDN) < -0.5;
    }

    public boolean dropFoot(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawButton(k.DS_DROPFOOT);
    }

    public boolean layup(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawAxis(k.DS_LAYUP) > 0.5;
    }

    public boolean trench(){
        if(!k.DS_ENABLED) return false;
        return (!layup() && !cam());
    }

    public boolean cam(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawAxis(k.DS_CAMMODE) < -0.5;
    }

    public boolean intake(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawButton(k.DS_MINTAKE);
    }

    public boolean revolve(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawButton(k.DS_REVOLVE);
    }

    public boolean shoot(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawButton(k.DS_MSHOOT);
    }

    public boolean shift(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawButton(k.DS_SHIFT);
    }

    public boolean pitMode(){
        if(!k.DS_ENABLED) return false;
        return ds.getRawButton(k.DS_PITMODE);
    }

    public boolean jogUp(){
        if(!k.DS_ENABLED) return false;
        return ds.getPOV() == k.DS_JOGUP;
    }

    public boolean jogDn(){
        if(!k.DS_ENABLED) return false;
        return ds.getPOV() == k.DS_JOGDN;
    }

    public boolean jogL(){
        if(!k.DS_ENABLED) return false;
        return ds.getPOV() == k.DS_JOGL;
    }

    public boolean jogR(){
        if(!k.DS_ENABLED) return false;
        return ds.getPOV() == k.DS_JOGR;
    }
}