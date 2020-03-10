package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.cals.CwTnCals;
import frc.robot.motors.Motor;

public class GateCW extends SubsystemBase{

    public Motor rotateMotor;
    public Motor loadMotor;
    public CwTnCals k;
    public AnalogInput ballSensor;
    public ColorSensorV3 colorSensor;
    public Solenoid launcher;
    public Solenoid CWNotTransport;
    private double targetpos = 0;
    private boolean[] ballpositions = {false, false, false, false, false};
    public boolean isIndexing;
    private RobotContainer mSubsystem;
    private RobotState mState;
    public ColorMatch colorMatch;
    public String colorString;
    public Color detectedColor;
    public Color lastColor;
    public String gameData;

    public GateCW(CwTnCals k, RobotContainer subsystem, RobotState state){
        this.k = k;
        mSubsystem = subsystem;
        mState = state;
        if(k.tnDisabled && k.cwDisabled) return;

        rotateMotor = Motor.initMotor(k.rotateMotor);
        loadMotor = Motor.initMotor(k.loadMotor);
        ballSensor = new AnalogInput(k.sensorValue);
        ballSensor.setAverageBits(4);
        colorSensor = new ColorSensorV3(Port.kOnboard);
        launcher = new Solenoid(k.launcherValue);
        CWNotTransport = new Solenoid(k.CWNotTransport);
        colorMatch = new ColorMatch();

        colorMatch.addColorMatch(k.Blue);
        colorMatch.addColorMatch(k.Green);
        colorMatch.addColorMatch(k.Red);
        colorMatch.addColorMatch(k.Yellow);
    }

    boolean first = true;
    double jamTime;
    boolean prevJammed;
    public void periodic(){
        if(DriverStation.getInstance().isAutonomous() && first){
            first = false;
            mState.ballnumber = 5;
            ballpositions[0] = true;
            ballpositions[1] = true;
            ballpositions[2] = true;
            ballpositions[3] = true;
            ballpositions[4] = true;
        }

        if(k.tnDisabled && k.cwDisabled) return;
        
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        if(!mSubsystem.m_input.cwActivate()){
            CWNotTransport.set(false);
        }

        if(Timer.getFPGATimestamp() > jamTime){
            if(prevJammed) {
                index(1);
                prevJammed = false;
            }
        } 
        if(rotateMotor.isJammed() && !prevJammed){
            jamTime = Timer.getFPGATimestamp() + k.jamRestTime;
            index(-2);
            prevJammed = true;
        }

        rotateMotor.setPosition(targetpos);
        

        int x = (int) Math.round(rotateMotor.getPosition() / k.countsPerIndex);
        if(ballpositions[(x + 4) % 5] && launcher.get()){
            mState.ballnumber--;
            ballpositions[(x + 4) % 5] = false;
        }

        /*if(mSubsystem.m_intake.isOut() && !ballpositions[x%5]){
            gatePower(k.TN_LOADSPEED);
        }else if(mSubsystem.m_intake.isOut()) {
            gatePower(k.TN_STOPSPEED);
        }else*/ if(CWNotTransport.get()){
            if(mSubsystem.m_input.cwRotNotPos()){
                gatePower(k.rotSpeed);
            } else{
                gatePower(k.colSpeed);
            }
        } else gatePower(0.0);

        detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatch.matchClosestColor(detectedColor);
        if(match.color == k.Blue){
            colorString = "Blue";
        } else if(match.color == k.Green){
            colorString = "Green";
        } else if(match.color == k.Red){
            colorString = "Red";
        } else if(match.color == k.Yellow){
            colorString = "Yellow";
        } else colorString = "N/A";

        Display.put("Current Pos", rotateMotor.getPosition() / k.countsPerIndex);
        Display.put("Ball Number", mState.ballnumber);
        Display.put("Ball Position 0", ballpositions[0]);
        Display.put("Ball Position 1", ballpositions[1]);
        Display.put("Ball Position 2", ballpositions[2]);
        Display.put("Ball Position 3", ballpositions[3]);
        Display.put("Ball Position 4", ballpositions[4]);
        Display.put("TCMotorCurrent0", rotateMotor.getCurrent());
        Display.put("TCMotorCurrent1", loadMotor.getCurrent());
        Display.put("TC Motor Temp 0", rotateMotor.getTemp());
        Display.put("TC Motor Temp 1", loadMotor.getTemp());
        Display.put("Color Info", String.format("R: %d G: %d B: %d IR: %d Prox: %d", 
            colorSensor.getRed(), colorSensor.getGreen(), colorSensor.getBlue(), 
            colorSensor.getIR(), colorSensor.getProximity()));
        Display.put("Detected Color", String.format("Color Guess: " + colorString + 
            " Confidence: %f", match.confidence));
        lastColor = detectedColor;
    }

    //increment the ball storage
    public void index(double positions){
        targetpos += positions * k.countsPerIndex;
    }

    public void spinGate(double pwr){
        loadMotor.setPower(pwr);
    }

    public boolean isIndexing(){
        if(k.tnDisabled) return false;
        double error = targetpos - rotateMotor.getPosition();
        return Math.abs(error) > k.allowedIndexError;
    }

    double waitTime = 0;
    double prevTime = 0;
    //used for gathering
    public void gatherIndex(){
        if(k.tnDisabled) return;
        double error = targetpos - rotateMotor.getPosition();
        double time = Timer.getFPGATimestamp();
        if(hasBall() && Math.abs(error) < k.countsPerIndex / 2 && mState.ballnumber < 5){ //only spin if not moving & we have an open spot
            waitTime += time - prevTime;
            if(waitTime > k.ballSenseDelay){
                waitTime = 0;
                mState.ballnumber++;
                int x = (int) Math.round(targetpos/k.countsPerIndex);
                ballpositions[x % 5] = true;
                index(1);
            }
        }
        SmartDashboard.putNumber("waittime",waitTime);
        prevTime = time;
    }

    double restTime = 0;
    public void gatePower(double power){
        if(restTime > Timer.getFPGATimestamp()){
            loadMotor.setPower(k.TN_STOPSPEED);
        } else {
            loadMotor.setPower(power);
        }

        if(loadMotor.getCurrent() > k.maxGateCurr){
            restTime = Timer.getFPGATimestamp() + k.gateRestTime;
        }
    }

    public void shootAll(){
        if(k.tnDisabled) return;
        enablefire(mState.ballnumber > 0);
        double error = targetpos - rotateMotor.getPosition();
        if(Math.abs(error) < k.countsPerIndex && mState.ballnumber > 0){
            index(1);
        }
    }

    //stop shooting
    public void stoprot(){
        if(k.tnDisabled) return;
        enablefire(false);
        double x = rotateMotor.getPosition() / k.countsPerIndex;
        x = Math.round(x);
        targetpos = x * k.countsPerIndex;
    }

    public void enablefire(boolean on){
        if(k.tnDisabled) return;
        launcher.set(on);
    }

    public void deployCW(boolean activated){
        if(k.cwDisabled) return;
        CWNotTransport.set(activated);
    }

    public boolean hasBall(){
        double volts = ballSensor.getAverageVoltage();
        return volts > k.hasBallMinV && volts < k.hasBallMaxV;
    }
}