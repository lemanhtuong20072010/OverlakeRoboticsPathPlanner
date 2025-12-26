package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "Video Guide AprilTag Test")
public class AprilTagExample extends LinearOpMode {
    
    // Gọi class chúng ta vừa tạo ở trên
    AprilTagWebcam webcam = new AprilTagWebcam();

    @Override
    public void runOpMode() {
        // Khởi tạo
        webcam.init(hardwareMap);

        telemetry.addData("Trạng thái", "Sẵn sàng! Nhấn Play để xem dữ liệu.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Cập nhật dữ liệu từ webcam
            webcam.update();

            // Ví dụ: Tìm thẻ ID 20
            AprilTagDetection tag20 = webcam.getTagById(20);

            if (tag20 != null && tag20.metadata != null) {
                telemetry.addLine("--- THÔNG TIN THẺ ID 20 ---");
                // Khoảng cách (Range), Góc nhìn (Bearing), Góc xoay (Yaw) [00:16:52]
                telemetry.addData("Khoảng cách (R)", "%5.2f cm", tag20.ftcPose.range);
                telemetry.addData("Góc nhìn (B)", "%5.2f độ", tag20.ftcPose.bearing);
                telemetry.addData("Góc xoay thẻ (Yaw)", "%5.2f độ", tag20.ftcPose.yaw);
            } else {
                telemetry.addLine("Không tìm thấy thẻ ID 20");
            }

            telemetry.update();
            sleep(20);
        }
        
        webcam.stop();
    }
}
