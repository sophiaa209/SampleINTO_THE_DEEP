package org.firstinspires.ftc.teamcode.drive.writtenCode.controllers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.writtenCode.RobotMap;
@Config

public class ClawRotateController {
//this controls the rotation of the claw
    public enum clawRotateStatus
    {
        INIT,
        ROTATE;
    }

    public clawRotateStatus currentStatus=clawRotateStatus.INIT;
    public clawRotateStatus previousStatus=null;

    public Servo clawRotate = null;
    public static double init_position=0.25;
    public static double rotate_position=0.7;


    public ClawRotateController(RobotMap robot) {
        this.clawRotate=robot.clawRotate;
    }

    public void update()
    {
        if(currentStatus!=previousStatus)
        {
            previousStatus=currentStatus;


            switch(currentStatus)
            {

                case INIT:
                {
                    this.clawRotate.setPosition(init_position);
                    break;
                }

                case ROTATE:
                {
                    this.clawRotate.setPosition(rotate_position);
                    break;
                }

            }
        }

    }

}