package com.example.demoslideimage.extensions;

import android.content.Context;

import java.util.ArrayList;

public class StringComand {
    public static final String INDEX_EFFECT = "effect_index";
    public static final String EFFECT_ZOOM = "[" + INDEX_EFFECT + ":v]zoompan=z='if(lte(zoom,1.0),1.5,max(1.001,zoom-0.0015))':d=125,fade=t=out:st=4:d=1[v" + INDEX_EFFECT + "]; ";
    public static final String Curtains_Vertical = "ffmpeg -i in.mp4 -filter_complex \\\n" +
            "         \"[0]format=yuv444p,split=2[bg][v];[bg]drawbox=t=fill[bg]; \\\n" +
            "          [v][bg]blend=all_expr='if(lte(2*abs(Y-H/2)/H,(T-2)/3),A,B)',\\\n" +
            "             format=yuv420p\" \\\n" +
            " out.mp4";
    public static final String CircleWipe = "ffmpeg -i in.mp4 -filter_complex \\\n" +
            "         \"[0]format=yuv444p,split=2[bg][v];[bg]drawbox=t=fill[bg]; \\\n" +
            "          [v][bg]blend=all_expr='if(lte(sqrt(pow(X-W/2,2)+pow(Y-H/2,2))/sqrt(pow(W,2)+pow(H,2)),(T-2)/3)/2,A,B)',\\\n" +
            "             format=yuv420p\" \\\n" +
            " out.mp4";
    public static final String DiagonalWipe = "ffmpeg -i old.mp4 -i new.mp4 -filter_complex\n" +
            "       \"[1]format=yuva444p,\n" +
            "           geq=lum='p(X,Y)':\n" +
            "           a='st(1,(1+W/H/TN)*H/D);if(lt(W-X,((ld(1)*T-Y)/(ld(1)*T))*ld(1)*T*TN),p(X,Y),0)':\n" +
            "           enable='lte(t,D)',setpts=PTS+D/TB[new];\n" +
            "        [0:v][new]overlay\" wipe.mp4";


    /**
     * Cú pháp:
     * -i source: là lệnh đưa file nguồn vào
     * -y: ghi đè file nếu đã tồn tại
     * -shortest : nếu đoạn audio dài hơn video thì đầu ra sẽ chỉ đến đoạn video dừng
     */

    public static String resizeImage(String uri, Context context) {
        String path = PathVideo.getPathTempImg(context);
        String nameFile = uri.replaceAll("/", "");
        String cmd = "-y -i " + uri + " -vf scale=" + SizeVideo.W_720 + ":" + SizeVideo.H_720 + " " + path + "/" + nameFile;
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
//        cmd += "-y"
//                + " -s " + SizeVideo.W_720 + "x" + SizeVideo.H_720
//                + " -filter_complex [0:v]trim=duration=3,fade=t=out:st=2.5:d=0.5[v0];[1:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v1];[2:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v2];[3:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v3];[v0][v1][v2][v3]concat=n=4:v=1:a=0,format=yuv420p[v] -map [v] -preset ultrafast "
//                + path + nameFile;


        cmd = "-y -i " + path + nameFile + " -filter_complex " +
                "         [0]format=yuv444p,split=2[bg][v];[bg]drawbox=t=fill[bg]; " +
                "          [v][bg]blend=all_expr=\"if(lte(2*abs(Y-H/2)/H,(T-2)/3),A,B)\"," +
                "             format=yuv420p " +
                path + nameFile;
        return cmd;
    }

    public static String addPictureFrameToVideo(Context context, String pathVideo, String uriPicture) {
        String path = PathVideo.getPathTempVideo(context);
        String nameFile = "/temp3.mp4";
//        String cmd = "-y -i " + pathVideo + " -i " + uriPicture + "  -filter_complex [0][1]overlay=(W-w)/2:(H-h)/2 " + path + nameFile;
        String cmd = "-y -i " + pathVideo + " -i " + uriPicture + " -filter_complex overlay=x=0:y=0 " + path + nameFile;
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
