package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Inputs{
    public boolean flySky;//there is also an f310
    
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

    public Inputs(){
        flySky = joy.getName().contains("FlySky");

        xAxis = 0;
        yAxis = 1;
        rotXAxis = 4;//Double check me!!!

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