package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CannonClimber;
import frc.robot.subsystems.Revolver;
import frc.robot.subsystems.Vision;

public class AutoShoot extends CommandBase{

    private CannonClimber mCannon;
    private Revolver mRevolver;
    private Vision mVision;

    public AutoShoot(CannonClimber cannon, Revolver revolver, Vision vision){
        mCannon = cannon;
        mRevolver = revolver;
        mVision = vision;
        
        new ParallelRaceGroup(
            new InstantCommand(() -> mCannon.prime(mVision.targetData.getFirst().dist)),
            new SequentialCommandGroup(
                new InstantCommand(() -> mRevolver.launcher.set(true)),
                new TransIndex(revolver, 1).withInterrupt(() -> mRevolver.unjamming),
                new WaitCommand(0.5),
                new InstantCommand(() -> mRevolver.unjam()),
                new WaitCommand(0.5)
            )
        ).withInterrupt(() -> mRevolver.launchTime());
    }
}