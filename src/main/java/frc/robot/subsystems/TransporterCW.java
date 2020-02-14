package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.cals.CWheelCals;
import frc.robot.cals.TransporterCals;
import frc.robot.motors.Motor;

public class TransporterCW extends SubsystemBase{

    public Motor rotatemotor;
    public Motor loadMotor;
    public TransporterCals tCals;
    public CWheelCals cCals;
    public DigitalInput ballsensor;
    public Solenoid launcher;
    public Solenoid CWNotTransport;
    private double targetpos = 0;
    private boolean[] ballpositions = {false, false, false, false, false};
    private int ballnumber = 0;
    private RobotContainer mSubsystem;

    public TransporterCW(TransporterCals tCals, CWheelCals cCals, RobotContainer subsystem){
        this.tCals = tCals;
        this.cCals = cCals;
        mSubsystem = subsystem;

        rotatemotor = Motor.initMotor(tCals.rotateMotor);
        loadMotor = Motor.initMotor(tCals.loadMotor);
        ballsensor = new DigitalInput(tCals.sensorValue);
        launcher = new Solenoid(tCals.launcherValue);
        CWNotTransport = new Solenoid(tCals.CWNotTransport);
    }

    public void periodic(){
        if(!mSubsystem.m_input.cwActivate()){
            CWNotTransport.set(false);
        }
        rotatemotor.setPosition(targetpos);
        int x = (int) Math.round(rotatemotor.getPosition() / tCals.countsPerIndex);
        if(ballpositions[(x + 3) % 5]){
            ballnumber--;
            ballpositions[x % 5] = false;
        }
        if(mSubsystem.m_input.autoGather() && !ballpositions[x%5]){
            loadMotor.setPower(tCals.TN_LOADSPEED);
        }else if(mSubsystem.m_input.autoGather()) {
            loadMotor.setPower(tCals.TN_STOPSPEED);
        }else if(CWNotTransport.get()){
            if(mSubsystem.m_input.cwRotNotPos()){
                loadMotor.setPower(cCals.rotSpeed);
            } else{
                loadMotor.setPower(cCals.colSpeed);
            }
        } else loadMotor.setPower(0.0);
    }

    //increment the ball storage
    public void index(double positions){
        targetpos += positions * tCals.countsPerIndex;
    }

    //used for gathering
    public void gatherIndex(){
        double error = targetpos - rotatemotor.getPosition();
        if(ballsensor.get() && error < tCals.countsPerIndex / 2 && ballnumber < 5){ //only spin if not moving & we have an open spot
            ballnumber++;
            int x = (int) Math.round(targetpos/tCals.countsPerIndex);
            ballpositions[x % 5] = true;
            index(1);
        }
    }

    public void shootAll(){
        enablefire(ballnumber > 0);
        double error = targetpos - rotatemotor.getPosition();
        if(error < tCals.countsPerIndex/2 && ballnumber > 0){
            index(1);
        }
    }

    //stop shooting
    public void stoprot(){
        enablefire(false);
        double x = rotatemotor.getPosition() / tCals.countsPerIndex;
        x = Math.round(x);
        targetpos = x * tCals.countsPerIndex;
    }

    public void enablefire(boolean on){
        launcher.set(on);
    }

    public void deploy(boolean activated){
        CWNotTransport.set(activated);
    }
}