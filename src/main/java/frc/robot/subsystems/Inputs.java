package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Inputs{
    public boolean flySky;
    
    public Joystick joy = new Joystick(0);
    
    public JoystickButton autoGather;
    public JoystickButton camGather;
    public JoystickButton camShoot;
    public JoystickButton layup;
    public JoystickButton climb;
    public JoystickButton extend;
    public double xAxis;
    public double yAxis;
    public double rotXAxis;
    public double rotYAxis;

    public Inputs(boolean flySky){
        this.flySky = flySky;

        xAxis = 0;
        yAxis = 1;
        rotXAxis = 4;//Double check me!!!
        rotYAxis = 5;//Double check me!!!

        if(flySky){
            autoGather = new JoystickButton(joy, -1);
            camGather= new JoystickButton(joy, -1);
            camShoot= new JoystickButton(joy, -1);
            layup = new JoystickButton(joy, -1);
            climb = new JoystickButton(joy, -1);
            extend = new JoystickButton(joy, -1);
        }else{
            autoGather = new JoystickButton(joy, -1);
            camGather = new JoystickButton(joy, -1);
            camShoot = new JoystickButton(joy, -1);
            layup = new JoystickButton(joy, -1);
            climb = new JoystickButton(joy, -1);
            extend = new JoystickButton(joy, -1);
        }
    }
}