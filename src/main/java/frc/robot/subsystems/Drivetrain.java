package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.DriverCals;
import frc.robot.motors.Motor;

public class Drivetrain extends SubsystemBase{
    public class Wheel{
        Motor driveMotor;
        Motor turnMotor;
        AnalogInput enc;
        double x;
        double y;
        public Wheel(DriverCals cals, int idx){
            driveMotor = Motor.initMotor(cals.driveMotors[idx]);
            turnMotor = Motor.initMotor(cals.driveMotors[idx]);
            enc = new AnalogInput(cals.turnEncoderIds[idx]);
            x = cals.xPos[idx];
            y = cals.yPos[idx];
        }
    }

    Wheel[] wheels;
    
    public Drivetrain(DriverCals cals){
        int size = cals.driveMotors.length;
        wheels = new Wheel[size];
        for(int i=0; i<size; i++){
            wheels[i] = new Wheel(cals, i);
        }
    }

    public void drive(double x, double y, double rot, Wheel wheel){
        double axisPower = x + y;//TODO: Fix this in general
        double rotPower = rot;

        if(axisPower > rotPower){
            if(axisPower > 1){
                rotPower /= axisPower;
                axisPower = 1;
            }
        } 
        else if(axisPower < -1){
            rotPower /= axisPower;
            axisPower = -1;
        } 
        
        if(rotPower > 1){
            axisPower /= rotPower;
            rotPower = 1;
        } 
        else if(rotPower < -1){
            axisPower /= rotPower;
            rotPower = -1;
        } 

        wheel.driveMotor.setPower(axisPower);
        wheel.turnMotor.setPower(rotPower);
    }

}