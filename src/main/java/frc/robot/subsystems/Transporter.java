package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.TransporterCals;
import frc.robot.motors.Motor;

public class Transporter extends SubsystemBase{

    public Motor rotatemotor;
    public TransporterCals mCals;
    public DigitalInput ballsensor;
    public Solenoid launcher;
    private double targetpos = 0;
    private boolean[] ballpositions = {false, false, false, false, false};
    private int ballnumber = 0;

    public Transporter(TransporterCals cals){
        mCals = cals;

        rotatemotor = Motor.initMotor(mCals.rotateMotor);
        ballsensor = new DigitalInput(mCals.sensorValue);
        launcher = new Solenoid(mCals.launcherValue);
    }

    public void periodic(){
        rotatemotor.setPosition(targetpos);
        int x = (int) Math.round(rotatemotor.getPosition() / mCals.countsPerIndex);
        if(ballpositions[(x + 3) % 5]){
            ballnumber--;
            ballpositions[x % 5] = false;
        }
    }

    //increment the ball storage
    public void index(double positions){
        targetpos += positions * mCals.countsPerIndex;
    }

    //used for gathering
    public void gatherIndex(){
        double error = targetpos - rotatemotor.getPosition();
        if(ballsensor.get() && error < mCals.countsPerIndex / 2 && ballnumber < 5){ //only spin if not moving & we have an open spot
            ballnumber++;
            int x = (int) Math.round(targetpos/mCals.countsPerIndex);
            ballpositions[x % 5] = true;
            index(1);
        }
    }

    public void shootAll(){
        enablefire(ballnumber > 0);
        double error = targetpos - rotatemotor.getPosition();
        if(error < mCals.countsPerIndex/2 && ballnumber > 0){
            index(1);
        }
    }

    //stop shooting
    public void stoprot(){
        double x = rotatemotor.getPosition() / mCals.countsPerIndex;
        x = Math.round(x);
        targetpos = x * mCals.countsPerIndex;
    }

    public void enablefire(boolean on){
        launcher.set(on);
    }
}