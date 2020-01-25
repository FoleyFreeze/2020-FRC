package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.DriverCals;
import frc.robot.motors.Motor;
import frc.robot.util.Vector;
import com.kauailabs.navx.frc.*;

public class Drivetrain extends SubsystemBase{
    public class Wheel{
        int idx;
        Motor driveMotor;
        Motor turnMotor;
        AnalogInput enc;
        Vector location;
        Vector rotVec;
        Vector wheelVec;
        double angleOffset;
        
        //create a wheel object to make assigning motor values easier
        public Wheel(DriverCals cals, int idx){
            driveMotor = Motor.initMotor(cals.driveMotors[idx]);
            turnMotor = Motor.initMotor(cals.turnMotors[idx]);
            enc = new AnalogInput(cals.turnEncoderIds[idx]);
            location = Vector.fromXY(cals.xPos[idx], cals.yPos[idx]);
            this.idx = idx;
            this.angleOffset = cals.angleOffset[idx];
        }

        public double calcRotVec(double centX, double centY){
            Vector v = Vector.fromXY(centX, centY);
            v.inverse().add(location);
            v.theta -= Math.PI/2;
            rotVec = v;
            SmartDashboard.putString("Location" + idx, location.toString());
            SmartDashboard.putString("InitRotate" + idx, v.toString());
            return rotVec.r;
        }

        public void drive(boolean parkMode, boolean drivingStraight){
            double encVoltage = enc.getVoltage();
            SmartDashboard.putNumber("RawEnc" + idx, encVoltage);
            double currentAngle = ((encVoltage - angleOffset) *2*Math.PI/5);
            double angleDiff = wheelVec.theta - currentAngle;
            SmartDashboard.putNumber("AngleRaw" + idx, Math.toDegrees(angleDiff));
            angleDiff = ((angleDiff+Math.PI*2) % (2*Math.PI)) - Math.PI;
            //angleDiff = Math.IEEEremainder(angleDiff, 2*Math.PI) - Math.PI;
            SmartDashboard.putNumber("AngleDiff" + idx, Math.toDegrees(angleDiff));
            if(Math.abs(angleDiff) > Math.PI/2){
                wheelVec.r *= -1;
                if(angleDiff < 0) {
                    angleDiff += Math.PI;
                    wheelVec.theta += Math.PI;
                } else if(angleDiff > 0) {
                    angleDiff -= Math.PI;
                    wheelVec.theta -= Math.PI;
                }
            }
            SmartDashboard.putNumber("CurrAngle" + idx, Math.toDegrees(currentAngle));
            SmartDashboard.putNumber("AngleCorr" + idx, Math.toDegrees(angleDiff));
            SmartDashboard.putString("WheelCmd" + idx, wheelVec.toString());
            

            double deltaTicks = angleDiff / (2*Math.PI) / k.turnGearRatio * 4096;
            double targetTicks = turnMotor.getPosition();
            if(wheelVec.r != 0 || parkMode || !drivingStraight){ //don't turn unless we actually want to move
                targetTicks += deltaTicks;
            } 
            SmartDashboard.putNumber("TargetTicks" + idx, targetTicks);
            driveMotor.setPower(wheelVec.r);
            turnMotor.setPosition(targetTicks);
        }
    }

    Wheel[] wheels;
    public DriverCals k;
    public AHRS navX;

    public Drivetrain(DriverCals cals){
        if(cals.disabled) return;
        k = cals;
        int size = k.driveMotors.length;
        wheels = new Wheel[size];
        for(int i=0; i<size; i++){
            wheels[i] = new Wheel(k, i);
        }

        navX = new AHRS(Port.kMXP);
    }

    public double parkTime = 0.0;
    public boolean parkMode = false;
    double currentTime = 0.0;
    public double prevRot;
    public boolean driveStraight = false;
    public double prevAng = 0.0;
    public double goalAng = 0.0;
    int i = 0;

        //joystick x, joystick y, joystick rot, center of rotation x and y, field oriented
    public void drive(Vector strafe, double rot, double centX, double centY, boolean fieldOrient){
        if(k.disabled) return;
        currentTime = Timer.getFPGATimestamp();
        SmartDashboard.putString("Strafe", String.format("%.2f, %.0f", strafe.r, Math.toDegrees(strafe.theta)));
        SmartDashboard.putNumber("prevAngle", Math.toDegrees(prevAng));
        SmartDashboard.putNumber("RobotAngle", navX.getAngle());
        if(fieldOrient){
            strafe.theta -= Math.toRadians(-navX.getAngle()) - Math.PI/2;
        }

        if(rot == 0 && !parkMode) driveStraight = true;
        else driveStraight = false;
        
        SmartDashboard.putBoolean("Driving Straigth", driveStraight);
        
        if(0 == rot && 0 == strafe.r){
            if(parkTime <= currentTime){
                parkMode = true;
            }
            else parkMode = false;
        }else{
            parkTime = currentTime + k.parkOffset;
            parkMode = false;
        }

        if(!driveStraight) goalAng = prevAng;
        SmartDashboard.putNumber("Goal Angle", Math.toDegrees(goalAng));

        SmartDashboard.putNumber("Rot", rot);
        SmartDashboard.putNumber("parkTime", parkTime);
        SmartDashboard.putBoolean("parkMode", parkMode);
        
        double maxMag = 0;
        for(Wheel w : wheels){//calculate rotation
            double mag = w.calcRotVec(centX, centY);
            maxMag = Math.max(maxMag, Math.abs(mag));
        }
        SmartDashboard.putNumber("MaxMag",maxMag);


        Wheel maxOut = wheels[0];
        for(Wheel w : wheels){//normalize the rotation
            w.rotVec.r /= maxMag;
            w.rotVec.r *= rot;
            w.wheelVec = Vector.add(w.rotVec, strafe); 
            if(Math.abs(w.wheelVec.r) > Math.abs(maxOut.wheelVec.r)){
                maxOut = w;
            }
            SmartDashboard.putString("NormRotate" + w.idx, w.rotVec.toString());
            SmartDashboard.putString("RawWheelCmd" + w.idx, w.wheelVec.toString());

            if(parkMode){
                w.wheelVec.theta = w.location.theta;
            }

            if(driveStraight){
                w.wheelVec.theta -= outRot(k.pCorrection, goalAng);
            }

        }
        
        if(Math.abs(maxOut.wheelVec.r) > 1){
            double reducRatio = 1/maxOut.wheelVec.r;

            strafe.r *= Math.sqrt(reducRatio);

            for(Wheel w: wheels){
                w.rotVec.r *= reducRatio;
                w.wheelVec= Vector.add(strafe, w.rotVec);
            }
        }

        for(Wheel w : wheels){
            SmartDashboard.putString("FinalWheel" + w.idx, w.wheelVec.toString());
            w.drive(parkMode, driveStraight);
        }

        if(i == 0){
            if(Math.toDegrees(prevAng) != navX.getAngle()) prevAng = Math.toRadians(navX.getAngle());
            i =1;
        }else{
            i = 0;
        }
        
    }

    public double outRot(double pCorrection, double targetAng){
        double error = targetAng - navX.getAngle();
        double kP = pCorrection;
        SmartDashboard.putNumber("Straight Error", Math.toDegrees(error));
        double output = kP * error;
        return output;
    }
}