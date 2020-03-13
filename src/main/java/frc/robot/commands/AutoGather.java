package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Revolver;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.GateCW;

public class AutoGather extends CommandBase{
    
    public Intake mIntake;
    public Revolver mRevolve;
    public GateCW mTransCW;
    public RobotState mState;
    
    public AutoGather(Intake intake, Revolver revolver, GateCW transCW, RobotState state){
        mIntake = intake;
        mRevolve = revolver;
        mTransCW = transCW;
        mState = state;
        addRequirements(mIntake, mRevolve, mTransCW);
            
        new ParallelRaceGroup(
            new StartEndCommand(() -> mIntake.dropIntake(true), 
                                () -> mIntake.dropIntake(false)), 
            new LoopCommand(
                new InstantCommand(() -> {transCW.spinGate(transCW.k.TN_LOADSPEED);
                                          new IntakeSpin(mIntake);}),
                new WaitCommand(0.5),
                new InstantCommand(() -> {mIntake.setPower(mIntake.k.idxPower);
                                          transCW.spinGate(transCW.k.TN_STOPSPEED);}),
                new LoopCommand(
                    new TransIndex(mRevolve, 1),
                    new WaitCommand(0.5).withInterrupt(() -> mRevolve.isJamming()),
                    new InstantCommand(() -> mRevolve.unjam()),
                    new WaitCommand(0.5)
                ).withInterrupt(() -> !mRevolve.indexing())
            )).withInterrupt(() -> mState.ballnumber >= 5);
    }

}