package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ZeroReset extends CommandBase{

    private Drivetrain mDrivetrain;

    public ZeroReset(Drivetrain drivetrain){
        mDrivetrain = drivetrain;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        mDrivetrain.zeroAll();
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}