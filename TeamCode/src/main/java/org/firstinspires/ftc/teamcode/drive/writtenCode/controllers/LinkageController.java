package org.firstinspires.ftc.teamcode.drive.writtenCode.controllers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.writtenCode.RobotMap;

@Config
public class LinkageController {
//this controls the position of the linkage; it can be in init or extend position

    public enum linkageStatus
    {
        INIT,
        EXTEND;
    }

    public linkageStatus currentStatus= linkageStatus.INIT;
    public linkageStatus previousStatus=null;

    public Servo linkage1 = null;
    public Servo linkage2 = null;

    public static double init_position=0;
    public static double extend_position=1;


    public LinkageController(RobotMap robot) {
        this.linkage1=robot.linkage1;
        this.linkage2=robot.linkage2;
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
                    this.linkage1.setPosition(init_position);
                    this.linkage2.setPosition(init_position);
                    break;
                }

                case EXTEND:
                {
                    this.linkage1.setPosition(extend_position);
                    this.linkage2.setPosition(extend_position);
                    break;
                }

            }
        }

    }
}