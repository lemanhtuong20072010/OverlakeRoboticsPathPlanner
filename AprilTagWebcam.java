package org.firstinspires.ftc.teamcode;

import android.util.Size;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class AprilTagWebcam {
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;
    private List<AprilTagDetection> detectedTags;

    // Hàm khởi tạo camera
    public void init(HardwareMap hwMap) {
        // 1. Xây dựng bộ xử lý AprilTag
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)         // Hiện ID lên màn hình [00:07:02]
                .setDrawTagOutline(true)    // Vẽ khung bao quanh thẻ
                .setDrawAxes(true)          // Vẽ trục tọa độ XYZ [00:07:18]
                .setDrawCubeProjection(true)// Vẽ hình lập phương 3D
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES) // Đơn vị CM và Độ [00:07:33]
                .build();

        // 2. Xây dựng Vision Portal (Kết nối với Webcam)
        visionPortal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, "Webcam 1")) // Tên đặt trong Config [00:08:44]
                .setCameraResolution(new Size(640, 480))           // Độ phân giải chuẩn C270 [00:08:52]
                .addProcessor(aprilTagProcessor)
                .build();
    }

    // Cập nhật danh sách thẻ quét được trong mỗi vòng lặp
    public void update() {
        detectedTags = aprilTagProcessor.getDetections();
    }

    // Lấy danh sách tất cả thẻ đang thấy
    public List<AprilTagDetection> getDetectedTags() {
        return detectedTags;
    }

    // Hàm lấy 1 thẻ cụ thể theo ID (ví dụ ID 20 cho mùa CenterStage) [00:10:47]
    public AprilTagDetection getTagById(int id) {
        if (detectedTags == null) return null;
        for (AprilTagDetection detection : detectedTags) {
            if (detection.id == id) {
                return detection;
            }
        }
        return null;
    }

    // Đóng camera khi không dùng để tiết kiệm tài nguyên [00:12:11]
    public void stop() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}
