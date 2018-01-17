package com.muzammilpeer.quadcopter.sensor.camera;

public class RaspiVideo {
    // Define the path to the raspistill executable.
    private final String _raspividPath = "/opt/vc/bin/raspivid";
    private final String _mp4boxPath = "/usr/bin/MP4Box";

    // Specify a default Capture 30 seconds
    private int _captureTime = 10000;

    // Specify a default bitrate   150kB/s bit rate
    private int _bitrate = 4*1024*1024*8;//1200000;
    // Specify a default fps
    private int _fps = 25;
    // Specify a default image width.
    private int _vidWidth = 1920;
    // Specify a default image height.
    private int _vidHeight = 1080;
    // Specify a default image name.
    private String _tempVidName = "pivideo.h264";
    private String _vidName = "pivideo.mp4";
    // Specify a default image encoding.
    private String _vidType = "mp4";

    // Default class constructor.
    public void RaspiVideo() {
        // Do anything else here. For example, you could create another
        // constructor which accepts an alternate path to raspistill,
        // or defines global parameters like the image quality.
    }

    // Default method to take a photo using the private values for name/width/height.
    // Note: See the overloaded methods to override the private values.
    public void recordVideo() {
        try {
            // Determine the image type based on the file extension (or use the default).
//            if (_picName.indexOf('.') != -1) _picType = _picName.substring(_picName.lastIndexOf('.') + 1);

            // Create a new string builder with the path to raspistill.
            StringBuilder sb = new StringBuilder(_raspividPath);

            // Add parameters for no preview and burst mode.
            // Configure the camera timeout.
            sb.append(" -t " + _captureTime);
            // Configure the picture width.
            sb.append(" -w " + _vidWidth);
            // Configure the picture height.
            sb.append(" -h " + _vidHeight);
            // Configure the fps
            sb.append(" -fps " + _fps);
            // Specify the bitrate.
            sb.append(" -b  " + _bitrate);
            // Specify the reso
            sb.append(" -p 0,0," + _vidWidth + "," + _vidHeight);
            // Specify the bitrate.
            sb.append(" -o  " + _tempVidName);

            // Invoke raspistill to take the photo.
            Runtime.getRuntime().exec(sb.toString());
            // Pause to allow the camera time to take the photo.
            Thread.sleep(_captureTime);


            //Convert to MP4
            // Create a new string builder with the path to raspistill.
            StringBuilder sbMP4 = new StringBuilder(_mp4boxPath);

            // Add parameters for no preview and burst mode.
            // Configure the camera timeout.
            sbMP4.append(" -add " + _tempVidName + " " + _vidName);

            // Invoke raspistill to take the photo.
            Runtime.getRuntime().exec(sbMP4.toString());
            // Pause to allow the camera time to take the photo.
            Thread.sleep(1000);

//            //Convert to MP4
//            // Create a new string builder with the path to raspistill.
//            StringBuilder sbClear = new StringBuilder("rm ");
//
//            // Add parameters for no preview and burst mode.
//            // Configure the camera timeout.
//            sbClear.append(_tempVidName);
//
//            // Invoke raspistill to take the photo.
//            Runtime.getRuntime().exec(sbClear.toString());


        } catch (Exception e) {
            // Exit the application with the exception's hash code.
            System.exit(e.hashCode());
        }
    }
//
//    // Overloaded method to take a photo using specific values for the name/width/height.
//    public void TakePicture(String name, int width, int height) {
//        _picName = name;
//        _picWidth = width;
//        _picHeight = height;
//        TakePicture();
//    }
//
//    // Overloaded method to take a photo using a specific value for the image name.
//    public void TakePicture(String name) {
//        TakePicture(name, _picWidth, _picHeight);
//    }
//
//    // Overloaded method to take a photo using specific values for width/height.
//    public void TakePicture(int width, int height) {
//        TakePicture(_picName, width, height);
//    }
}
