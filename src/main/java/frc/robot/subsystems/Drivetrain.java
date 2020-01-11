package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.DriverCals;
import frc.robot.motors.Motor;
import frc.util.Vector;

public class Drivetrain extends SubsystemBase{
    public class Wheel{
        int idx;
        Motor driveMotor;
        Motor turnMotor;
        AnalogInput enc;
        Vector location;
        Vector rotVec;
        Vector wheelVec;
        
        //create a wheel object to make assigning motor values easier
        public Wheel(DriverCals cals, int idx){
            driveMotor = Motor.initMotor(cals.driveMotors[idx]);
            turnMotor = Motor.initMotor(cals.driveMotors[idx]);
            enc = new AnalogInput(cals.turnEncoderIds[idx]);
            location = Vector.fromXY(cals.xPos[idx], cals.yPos[idx]);
            this.idx = idx;
        }

        public double calcRotVec(double centX, double centY){
            Vector v = Vector.fromXY(centX, centY);
            v.inverse().add(location);
            rotVec = v;
            SmartDashboard.putString("InitRotate" + idx, String.format("%.2f, %.0f", v.r, Math.toDegrees(v.theta)));
            return rotVec.r;
        }

        public void drive(){
            double currentAngle = enc.getVoltage() *2*Math.PI/5;
            double angleDiff = currentAngle - wheelVec.theta;
            angleDiff = angleDiff % (2*Math.PI) - Math.PI;
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
            SmartDashboard.putString("WheelCmd" + idx, String.format("%.2f, %.0f", wheelVec.r, Math.toDegrees(wheelVec.theta)));
            
            double deltaTicks = angleDiff / (2*Math.PI) / k.turnGearRatio * 4096;
            double targetTicks = deltaTicks + turnMotor.getPosition();
            driveMotor.setPower(wheelVec.r);
            turnMotor.setPosition(targetTicks);
        }
    }

    Wheel[] wheels;
    DriverCals k;
    
    public Drivetrain(DriverCals cals){
        int size = cals.driveMotors.length;
        wheels = new Wheel[size];
        for(int i=0; i<size; i++){
            wheels[i] = new Wheel(cals, i);
        }
    }

        //joystick x, joystick y, joystick rot, center of rotation x and y, arbitration ratio 0-1
    public void drive(double x, double y, double rot, double centX, double centY, double arbRatio){
        Vector strafe = Vector.fromXY(x, y);//calculate strafe
        SmartDashboard.putString("Strafe", String.format("%.2f, %.0f", strafe.r, Math.toDegrees(strafe.theta)));
        if(k.scaleNormalize){//normalize
            strafe.scaleNorm();
        } else{
            strafe.threshNorm();
        }
        SmartDashboard.putString("NormStrafe", String.format("%.2f, %.0f", strafe.r, Math.toDegrees(strafe.theta)));

        double maxMag = 0;
        for(Wheel w : wheels){//calculate rotation
            double mag = w.calcRotVec(centX, centY);
            maxMag = Math.max(maxMag, Math.abs(mag));
        }

        Wheel maxOut = wheels[0];
        for(Wheel w : wheels){//normalize the rotation
            w.rotVec.r = maxMag/rot;
            w.wheelVec = Vector.add(w.rotVec, strafe); 
            if(Math.abs(w.wheelVec.r) > Math.abs(maxOut.wheelVec.r)){
                maxOut = w;
            }
            SmartDashboard.putString("NormRotate" + w.idx, String.format("%.2f, %.0f", w.rotVec.r, Math.toDegrees(w.rotVec.theta)));
        }
        
        if(Math.abs(maxOut.wheelVec.r) > 1){
            double R = (1-arbRatio)/arbRatio;
            double r1 = strafe.r;
            double r2 = maxOut.rotVec.r;
            double a = 1 + (R*R * r2*r2 /r1/r1);
            double b = 2 * r2 * Math.cos(maxOut.rotVec.theta - strafe.theta);
            double ans = (-b + Math.sqrt(b*b + 4*a))/2/a;//c is ALWAYS -1
            double ans2 = (-b - Math.sqrt(b*b + 4*a))/2/a;//c is ALWAYS -1

            SmartDashboard.putString("QuadraticZeros", String.format("%.2f, %.2f", ans, ans2));
            if(ans2 > ans) ans = ans2;
            
            double strafeReduc = ans/r1;
            double rotReduc = R*strafeReduc;
            SmartDashboard.putString("ArbitrationFactors", String.format("%.2f, %.2f", strafeReduc, rotReduc));

            strafe.r *= strafeReduc;

            for(Wheel w: wheels){
                w.rotVec.r *= rotReduc;
                w.wheelVec= Vector.add(strafe, w.rotVec);
                SmartDashboard.putString("FinalWheel" + w.idx, String.format("%.2f, %.0f", w.wheelVec.r, Math.toDegrees(w.wheelVec.theta)));
            }
        }

        for(Wheel w : wheels){
            w.drive();
        }
    }

}