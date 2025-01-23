package org.firstinspires.ftc.teamcode.drive.writtenCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.writtenCode.controllers.ClawController;
import org.firstinspires.ftc.teamcode.drive.writtenCode.controllers.ClawPositionController;
import org.firstinspires.ftc.teamcode.drive.writtenCode.controllers.ClawRotateController;
import org.firstinspires.ftc.teamcode.drive.writtenCode.controllers.FourbarController;
import org.firstinspires.ftc.teamcode.drive.writtenCode.controllers.LiftController;
import org.firstinspires.ftc.teamcode.drive.writtenCode.controllers.LinkageController;
import org.firstinspires.ftc.teamcode.drive.writtenCode.controllers.PivotController;

@TeleOp(name="TeleOpCode", group="Linear OpMode")

public class TeleOpCode extends LinearOpMode {

    public void setMotorRunningMode(DcMotor leftFront, DcMotor leftBack, DcMotor rightFront,
                                    DcMotor rightBack, DcMotor.RunMode runningMode) {
        leftFront.setMode(runningMode);
        rightFront.setMode(runningMode);
        leftBack.setMode(runningMode);
        rightBack.setMode(runningMode);
    }

    public void setMotorZeroPowerBehaviour(DcMotor leftFront, DcMotor leftBack, DcMotor rightFront,
                                           DcMotor rightBack, DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        leftFront.setZeroPowerBehavior(zeroPowerBehavior);
        rightFront.setZeroPowerBehavior(zeroPowerBehavior);
        leftBack.setZeroPowerBehavior(zeroPowerBehavior);
        rightBack.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public void robotCentricDrive(DcMotor leftFront, DcMotor leftBack,
                                  DcMotor rightFront, DcMotor rightBack,
                                  double leftTrigger, double rightTrigger) {

        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = (-gamepad1.left_trigger + gamepad1.right_trigger) * 1.05; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double leftFrontPower = (y + x + rx) / denominator;
        double leftBackPower = (y - x + rx) / denominator;
        double rightFrontPower = (y - x - rx) / denominator;
        double rightBackPower = (y + x - rx) / denominator;


        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
    }

    ElapsedTime GlobalTimer = new ElapsedTime();


    int liftTargetPosition=0;

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        RobotMap robot= new RobotMap(hardwareMap);

        ClawRotateController clawRotateController = new ClawRotateController(robot);
        ClawPositionController clawPositionController = new ClawPositionController(robot);
        FourbarController fourbarController = new FourbarController(robot);
        LinkageController linkageController = new LinkageController(robot);
        LiftController liftController = new LiftController(robot);
        PivotController pivotController = new PivotController(robot);
        ClawController clawController = new ClawController(robot);


        clawRotateController.update();
        clawPositionController.update();
        fourbarController.update();
        liftController.update(liftTargetPosition);
        clawController.update();
        pivotController.update();
        linkageController.update();

        //initializing chassis motors
        DcMotor rightFront = hardwareMap.get(DcMotor.class,"rightFront");
        DcMotor leftFront = hardwareMap.get(DcMotor.class,"leftFront");
        DcMotor rightBack = hardwareMap.get(DcMotor.class,"rightBack");
        DcMotor leftBack = hardwareMap.get(DcMotor.class,"leftBack");


        //overclocking chassis motors
        MotorConfigurationType mct1, mct2, mct3, mct4;
        mct1 = rightBack.getMotorType().clone();
        mct1.setAchieveableMaxRPMFraction(1.0);
        rightBack.setMotorType(mct1);

        mct2 = rightFront.getMotorType().clone();
        mct2.setAchieveableMaxRPMFraction(1.0);
        rightFront.setMotorType(mct2);

        mct3 = leftFront.getMotorType().clone();
        mct3.setAchieveableMaxRPMFraction(1.0);
        leftFront.setMotorType(mct3);

        mct4 = leftBack.getMotorType().clone();
        mct4.setAchieveableMaxRPMFraction(1.0);
        leftBack.setMotorType(mct4);


        setMotorRunningMode(leftFront,leftBack,rightFront,rightBack,
                DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        setMotorZeroPowerBehaviour(leftFront,leftBack,rightFront,rightBack,
                DcMotor.ZeroPowerBehavior.BRAKE);

        // see why you need previousGamepad1 & 2 on gm0
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        waitForStart();

        GlobalTimer.reset();

        while(opModeIsActive())
        {
            if(isStopRequested()) return;

            int liftCurrentPosition = robot.lift1.getCurrentPosition();

            robotCentricDrive(leftFront,leftBack,rightFront,rightBack,gamepad1.left_trigger,gamepad1.right_trigger);

            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);

            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);



            if(currentGamepad1.x && !previousGamepad1.x)
            {
                if(clawRotateController.currentStatus== ClawRotateController.clawRotateStatus.INIT) {
                    clawRotateController.currentStatus= ClawRotateController.clawRotateStatus.ROTATE;

                } else {
                    clawRotateController.currentStatus= ClawRotateController.clawRotateStatus.INIT;
                }
            }

            if(currentGamepad2.y && !previousGamepad2.y)
            {
                if(clawController.currentStatus== ClawController.clawStatus.CLOSED){
                    clawController.currentStatus= ClawController.clawStatus.OPEN;
                } else {
                    clawController.currentStatus= ClawController.clawStatus.CLOSED;
                }
            }

            if(currentGamepad2.a && !previousGamepad2.a)
            {
                if(clawPositionController.currentStatus== ClawPositionController.clawPositionStatus.INIT){
                    clawPositionController.currentStatus= ClawPositionController.clawPositionStatus.COLLECT;
                } else {
                    clawPositionController.currentStatus= ClawPositionController.clawPositionStatus.INIT;
                }

            }

            if(currentGamepad2.b && !previousGamepad2.b)
            {
                if(clawPositionController.currentStatus==ClawPositionController.clawPositionStatus.PLACESPECIMEN){
                    clawPositionController.currentStatus= ClawPositionController.clawPositionStatus.INIT;
                } else {
                    clawPositionController.currentStatus= ClawPositionController.clawPositionStatus.PLACESPECIMEN;
                }

            }

            if(currentGamepad2.left_bumper && !previousGamepad2.left_bumper)
            {
                if(fourbarController.currentStatus== FourbarController.fourbarStatus.INIT){
                    fourbarController.currentStatus= FourbarController.fourbarStatus.HIGH;
                } else {
                    fourbarController.currentStatus= FourbarController.fourbarStatus.INIT;
                }
            }

            if(currentGamepad2.dpad_up && !previousGamepad2.dpad_up)
            {
                if(liftController.currentStatus== LiftController.liftStatus.INIT){
                    liftController.currentStatus= LiftController.liftStatus.SCORE;
                } else {
                    liftController.currentStatus= LiftController.liftStatus.INIT;
                }
            }

            if(currentGamepad2.dpad_left && !previousGamepad2.dpad_left)
            {
                if(linkageController.currentStatus== LinkageController.linkageStatus.INIT){
                    linkageController.currentStatus= LinkageController.linkageStatus.EXTEND;
                } else {
                    linkageController.currentStatus= LinkageController.linkageStatus.INIT;
                }
            }

            if(currentGamepad2.dpad_right && !previousGamepad2.dpad_right)
            {
                if(pivotController.currentStatus== PivotController.pivotStatus.INIT){
                    pivotController.currentStatus= PivotController.pivotStatus.SCORE;
                } else {
                    pivotController.currentStatus= PivotController.pivotStatus.INIT;
                }
            }

            clawRotateController.update();
            clawPositionController.update();
            fourbarController.update();
            liftController.update(liftTargetPosition);
            clawController.update();
            pivotController.update();
            linkageController.update();


        }
    }
}
