package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Inputs;
import frc.robot.util.Vector;

public class JoystickDrive extends CommandBase{
    private Drivetrain mDrivetrain;
    private Inputs mInput;

    public JoystickDrive(Drivetrain drivetrain, Inputs input){
        mDrivetrain = drivetrain;
        mInput = input;
        addRequirements(mDrivetrain);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute(){
        Vector strafe = mInput.getXY();
        double rot = mInput.getRot();
        double pausePwr = mDrivetrain.k.pausePwrPne;
        //m_subsystem.m_pneumatics.pauseReq(strafe.r > pausePwr 
          //  || Math.abs(rot) > pausePwr);
        
        mDrivetrain.drive(strafe, 
            rot, mDrivetrain.k.DRV_XROBOTCENT, 
            mDrivetrain.k.DRV_YROBOTCENT, 
            mInput.fieldOrient());
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}