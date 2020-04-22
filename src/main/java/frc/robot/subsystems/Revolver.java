package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.TransporterCals;
import frc.robot.motors.Motor;

public class Revolver extends SubsystemBase{
    
    public TransporterCals k;
    private RobotState mState;
    private Motor revMot;
    public Solenoid launcher;
    public AnalogInput ballSensor;
    public AnalogInput ballShot;
    public int tgtPos = 0;
    public double currPos = 0;
    public double tgtInTicks = 0;
    double currInTicks = 0;
    public boolean unjamming = false;
    public boolean jammed = false;
    private boolean[] ballpositions = {false, false, false, false, false};
    int currCount = 0;
    private double shootTime;
    private boolean prevBallShot;
    
    public Revolver(TransporterCals cals, RobotState state){
        k = cals;
        mState = state;
        if(k.disabled) return;
        revMot = Motor.initMotor(k.rotateMotor);
        launcher = new Solenoid(k.launcherValue);
        ballSensor = new AnalogInput(k.ballSensorValue);
        ballSensor.setAverageBits(4);
        ballShot = new AnalogInput(k.ballShotValue);
    }
    
    @Override
    public void periodic() {
        currPos = revMot.getPosition();

        boolean first = true;
        if(DriverStation.getInstance().isAutonomous() && first){
            first = false;
            mState.ballnumber = 5;
            ballpositions[0] = true;
            ballpositions[1] = true;
            ballpositions[2] = true;
            ballpositions[3] = true;
            ballpositions[4] = true;
        }

        if(revMot.getCurrent() >= 30){
            currCount++;
        }else currCount--;
        jammed = currCount > 5;

        int x = (int) Math.round(currPos / k.countsPerIndex);
        if(ballpositions[(x + 4) % 5] && launcher.get()){
            mState.ballnumber--;
            ballpositions[(x + 4) % 5] = false;
        }

        Display.put("Ball Position 0", ballpositions[0]);
        Display.put("Ball Position 1", ballpositions[1]);
        Display.put("Ball Position 2", ballpositions[2]);
        Display.put("Ball Position 3", ballpositions[3]);
        Display.put("Ball Position 4", ballpositions[4]);
    }

    public void index(int count){
        if(!unjamming){
            tgtPos += count;
            tgtInTicks += k.countsPerIndex*count;
        } else {
            unjamming = false;
        }
        revMot.setPosition(tgtInTicks);
    }

    public boolean hasBall(){
        double volts = ballSensor.getAverageVoltage();
        return volts > k.hasBallMinV && volts < k.hasBallMaxV;
    }

    double waitTime = 0;
    double prevTime = 0;

    public void gatherIndex(){
        if(k.disabled) return;
        double error = tgtInTicks - currPos;
        double time = Timer.getFPGATimestamp();
        if(hasBall() && Math.abs(error) < k.countsPerIndex / 2 && mState.ballnumber < 5){ //only spin if not moving & we have an open spot
            waitTime += time - prevTime;
            if(waitTime > k.ballSenseDelay){
                waitTime = 0;
                mState.ballnumber++;
                int x = (int) Math.round(tgtPos/k.countsPerIndex);
                ballpositions[x % 5] = true;
                index(1);
            }
        }
        SmartDashboard.putNumber("waittime",waitTime);
        prevTime = time;
    }
    
    public boolean indexing(){
        return !(tgtInTicks == currPos) && !unjamming;
    }

    public int currPos(){
        return 0;
    }

    public void setPower(double pwr){
        revMot.setPower(pwr);
    }

    public void unjam(){
        unjamming = true;
        setPower(k.unjamPwr);
    }

    public boolean isJamming(){
        return jammed;
    }

    public void enablefire(boolean on){
        if(k.disabled) return;
        launcher.set(on);
    }

    //stop shooting
    public void stoprot(){
        if(k.disabled) return;
        enablefire(false);
        double x = currPos / k.countsPerIndex;
        x = Math.round(x);
        tgtPos = (int) x;
    }

    public void shootAll(){
        if(k.disabled) return;
        enablefire(mState.ballnumber > 0);
        double error = tgtInTicks - currPos;
        if(Math.abs(error) < k.countsPerIndex && mState.ballnumber > 0){
            index(1);
        }
    }

    public boolean ballShot(){
        double volts = ballShot.getAverageVoltage();
        prevBallShot = !(volts > k.ballShotMinV && volts < k.ballShotMaxV);
        return !(volts > k.ballShotMinV && volts < k.ballShotMaxV);
    }

    public boolean launchTime(){//TODO Name Me!!!
        if((ballShot() && !prevBallShot) || !ballShot()){
            shootTime = Timer.getFPGATimestamp() + k.tooLongHasPassed;   
        }

        return shootTime > Timer.getFPGATimestamp();
    }
}