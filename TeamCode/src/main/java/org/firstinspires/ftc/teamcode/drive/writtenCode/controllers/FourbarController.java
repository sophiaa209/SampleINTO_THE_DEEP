package org.firstinspires.ftc.teamcode.drive.writtenCode.controllers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.writtenCode.RobotMap;

@Config
public class FourbarController {
    public enum fourbarStatus
    {
        INIT,
        HIGH;
    }
    public fourbarStatus currentStatus= fourbarStatus.INIT;
    public fourbarStatus previousStatus=null;

    public Servo fourbar = null;
    public static double init_position=0;
    public static double high_position=0.7;


    public FourbarController(RobotMap robot) {
        this.fourbar=robot.fourbar;
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
                    this.fourbar.setPosition(init_position);
                    break;
                }

                case HIGH:
                {
                    this.fourbar.setPosition(high_position);
                    break;
                }

            }
        }

    }
}