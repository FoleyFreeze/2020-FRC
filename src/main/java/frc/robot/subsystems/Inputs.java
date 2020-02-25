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
    public JoystickButton enableBudClimb;
    public JoystickButton dropFoot;
    public JoystickButton manualIntake;
    public JoystickButton revolve;
    public JoystickButton manualShoot;
    public JoystickButton shift;
    public Trigger camMode;
    public Trigger trenchMode;
    public JoystickButton pitMode;
    public Trigger jogUp;
    public Trigger jogDn;
    public Trigger jogL;
    public Trigger jogR;
    public JoystickButton cwActivate;
    
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

        camMode = new Trigger(new BooleanSupplier(){
        
            @Override
            public boolean getAsBoolean() {
                return cam();
            }
        });

        trenchMode = new Trigger(new BooleanSupplier(){
        
            @Override
            public boolean getAsBoolean() {
                return trench();
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
            angleReset = new JoystickButton(joy, cals.FS_ANGRESET);
            autoTrench = new JoystickButton(joy, cals.FS_AUTOTRENCH);
        }else{
            angleReset = new JoystickButton(joy, cals.XB_ANGRESET);
            autoTrench = new JoystickButton(joy, cals.XB_AUTOTRENCH);
        }
/*
        enableBudClimb = new JoystickButton(ds, cals.DS_ENABLEBUDCLIMB);
        dropFoot = new JoystickButton(ds, cals.DS_DROPFOOT);
        manualIntake = new JoystickButton(ds, cals.DS_MINTAKE);
        revolve = new JoystickButton(ds, cals.DS_REVOLVE);
        
        shift = new JoystickButton(ds, cals.DS_SHIFT);
        cwActivate = new JoystickButton(ds, cals.DS_CWACTIVATE);
        */
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
        return ds.getRawButton(cals.DS_TWOVTHREE);
    }

    public boolean cwRotNotPos(){
        return ds.getRawButton(cals.DS_TWOVTHREE);
    }

    public boolean cwActivate(){
        return ds.getRawButton(cals.DS_CWACTIVATE);
    }

    public boolean enableBudClimb(){
        if(!cals.DS_ENABLED) return false;
        return ds.getRawButton(cals.DS_ENABLEBUDCLIMB);
    }

    public boolean climbUp(){
        if(!cals.DS_ENABLED) return false;
        return ds.getRawAxis(cals.DS_CLIMBUP) == 1;
    }

    public boolean climbDn(){
        if(!cals.DS_ENABLED) return false;
        return ds.getRawAxis(cals.DS_CLIMBDN) == -1;
    }

    public boolean dropFoot(){
        if(!cals.DS_ENABLED) return false;
        return ds.getRawButton(cals.DS_DROPFOOT);
    }

    public boolean layup(){
        return ds.getRawAxis(cals.DS_LAYUP) == 1;
    }

    public boolean trench(){
        if(!cals.DS_ENABLED) return false;
        return (!layup() && !cam());
    }

    public boolean cam(){
        if(!cals.DS_ENABLED) return false;
        return ds.getRawAxis(cals.DS_CAMMODE) == -1;
    }

    public boolean intake(){
        if(!cals.DS_ENABLED) return false;
        return ds.getRawButton(cals.DS_MINTAKE);
    }

    public boolean revolve(){
        if(!cals.DS_ENABLED) return false;
        return ds.getRawButton(cals.DS_REVOLVE);
    }

    public boolean shoot(){
        return ds.getRawButton(cals.DS_MSHOOT);
    }

    public boolean shift(){
        return ds.getRawButton(cals.DS_SHIFT);
    }

    public boolean pitMode(){
        if(!cals.DS_ENABLED) return false;
        return ds.getRawButton(cals.DS_PITMODE);
    }

    public boolean jogUp(){
        return ds.getPOV() == cals.DS_JOGUP;
    }

    public boolean jogDn(){
        return ds.getPOV() == cals.DS_JOGDN;
    }

    public boolean jogL(){
        return ds.getPOV() == cals.DS_JOGL;
    }

    public boolean jogR(){
        return ds.getPOV() == cals.DS_JOGR;
    }
}