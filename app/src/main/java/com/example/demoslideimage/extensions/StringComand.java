package com.example.demoslideimage.extensions;

import android.content.Context;

import java.util.ArrayList;

public class StringComand {
    public static String resizeImage(String uri, Context context) {
        String path = PathVideo.getPathTempImg(context);
        String nameFile = uri.replaceAll("/", "");
        String cmd = "-y -i " + uri + " -filter_complex [0:v]scale=1280:720,boxblur=luma_radius=min(h\\,w)/20:luma_power=1:chroma_radius=min(cw\\,ch)/20:chroma_power=1[bg];[0:v]scale=-2:720:force_original_aspect_ratio=decrease[fg];[bg][fg]overlay=(W-w)/2:(H-h)/2[outv] -map [outv] -map 0:a? " + path + "/" + nameFile;
        return cmd;
    }

    public static String resizeImageKeepName(String uri, Context context) {
        String path = PathVideo.getPathTempImg(context);
        String nameFile = uri.substring(uri.lastIndexOf("/") + 1);
        String cmd = "-y -i " + uri + " -filter_complex [0:v]scale=1280:720,boxblur=luma_radius=min(h\\,w)/20:luma_power=1:chroma_radius=min(cw\\,ch)/20:chroma_power=1[bg];[0:v]scale=-2:720:force_original_aspect_ratio=decrease[fg];[bg][fg]overlay=(W-w)/2:(H-h)/2[outv] -map [outv] -map 0:a? " + path + "/" + nameFile;
        return cmd;
    }

    public static String createVideo(Context context, ArrayList<String> listPath, float timeLoad, ArrayList<String> effect) {
        String path = PathVideo.getPathTempVideo(context);
        String nameFile = "/temp.mp4";
        String cmd = "";
        for (String item : listPath) {
            cmd += "-loop 1 -t " + timeLoad + " -i "
                    + item + " ";
        }
        cmd += "-y"
                + " -s " + SizeVideo.W_720 + "x" + SizeVideo.H_720
                + " -filter_complex [0:v]trim=duration=3,fade=t=out:st=2.5:d=0.5[v0];[1:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v1];[2:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v2];[3:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v3];[v0][v1][v2][v3]concat=n=4:v=1:a=0,format=yuv420p[v] -map [v] -preset ultrafast "
                + path + nameFile;
        return cmd;
    }

    public static String addPictureFrameToVideo(Context context, String uriPicture) {
        String path = PathVideo.getPathTempVideo(context);
        String nameFile = "/temp1.mp4";
        String cmd = "-y -i " + path + "/temp.mp4 -i " + uriPicture + " -filter_complex [1]scale=1280:720,geq=r='r(X,Y)':a='1*alpha(X,Y)'[s1];[0][s1]overlay=0:0 " + path + nameFile;
        return cmd;
    }

    /**
     * Đây chính là chèn nhạc vào video và hủy sound của video nếu có sẵn
     */
    public static String addMusicToVideoCancelOldSound(Context context, String pathVideo, String uriSound) {
        String path = PathVideo.getPathTempVideo(context);
        String nameFile = "/temp1.mp4";
        String cmd = "-y -i " + pathVideo + " -i " + uriSound
                + " -c:v copy -c:a aac -strict experimental "
                + "-map 0:v:0 -map 1:a:0 -shortest " + path + nameFile;
        return cmd;
    }

    public static String addMusicToVideo(Context context, String pathVideo, String uriSound) {
        String path = PathVideo.getPathTempVideo(context);
        String nameFile = "/temp1.mp4";
        String cmd = "-y -i " + pathVideo + " -i " + uriSound
                + " -c copy "
                + " -shortest " + path + nameFile;
        return cmd;
    }

    public static String effectPIP(Context context, String pathVideo) {
        String path = PathVideo.getPathTempVideo(context);
        String nameFile = "/temp2.mp4";
        String cmd = "-i testbw.wmv -i test.wmv -filter_complex \"[0]scale=iw/5:ih/5 [pip]; [1][pip] overlay=main_w-overlay_w-10:main_h-overlay_h-10\" -profile:v main -level 3.1 -b:v 440k -ar 44100 -ab 128k -s 720x400 -vcodec h264 -acodec libvo_aacenc " + path + nameFile;
        return cmd;
    }
}
