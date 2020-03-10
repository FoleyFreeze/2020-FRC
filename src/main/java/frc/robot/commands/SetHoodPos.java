package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Hood;

public class SetHoodPos extends SequentialCommandGroup{

    public SetHoodPos(Hood hood){
        addCommands(new WaitUntilCommand(hood::checkPos),//nothingness til they are the same 
            new InstantCommand(hood::moveHood), //move the hood to where we want it
            new WaitCommand(hood.k.waitTime));//wait and do it again
    }
}