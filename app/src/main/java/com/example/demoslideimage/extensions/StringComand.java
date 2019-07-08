package com.example.demoslideimage.extensions;

import android.content.Context;

import com.example.demoslideimage.util.SettingVideo;

import java.util.ArrayList;

public class StringComand {
    public static String resizeImage(String uri, Context context) {
        String path = PathVideo.getPathTempImg(context);
        String nameFile = uri.replaceAll("/", "");
        return "-y -i " + uri + " -filter_complex [0:v]scale=1280:720,boxblur=luma_radius=min(h\\,w)/20:luma_power=1:chroma_radius=min(cw\\,ch)/20:chroma_power=1[bg];[0:v]scale=-2:720:force_original_aspect_ratio=decrease[fg];[bg][fg]overlay=(W-w)/2:(H-h)/2[outv] -map [outv] -map 0:a? "
                + path + "/" + nameFile;
    }

    public static String resizeImageKeepName(String uri, Context context) {
        String path = PathVideo.getPathTempImg(context);
        String nameFile = uri.substring(uri.lastIndexOf("/") + 1);
        return "-y -i " + uri + " -filter_complex [0:v]scale=1280:720,boxblur=luma_radius=min(h\\,w)/20:luma_power=1:chroma_radius=min(cw\\,ch)/20:chroma_power=1[bg];[0:v]scale=-2:720:force_original_aspect_ratio=decrease[fg];[bg][fg]overlay=(W-w)/2:(H-h)/2[outv] -map [outv] -map 0:a? "
                + path + "/" + nameFile;
    }

    public static String addPictureFrameToVideo(Context context, String uriPicture) {
        String path = PathVideo.getPathTempVideo(context);
        String nameFile = "/temp1.mp4";
        return "-y -i " + path + "/temp.mp4 -i " + uriPicture
                + " -filter_complex [1]scale=" + SizeVideo.W_480 + ":" + SizeVideo.H_480
                + ",geq=r='r(X,Y)':a='1*alpha(X,Y)'[s1];[0][s1]overlay=0:0 " + path + nameFile;
    }

    public static String addMusicToVideoCancelOldSound(Context context, String uriSound) {
        String path = PathVideo.getPathTempVideo(context);
        String nameFile = "/temp1.mp4";
        return "-y -i " + path + nameFile + " -i " + uriSound
                + " -c:v copy -c:a aac -strict experimental "
                + "-map 0:v:0 -map 1:a:0 -shortest " + path + "/temp2.mp4";
    }

    public static String getCmdCreateVideo(ArrayList<String> list, SettingVideo settingVideo) {
        return startCmdCreateVideo(list, settingVideo)
                + mainCmdCreateVideo(list, settingVideo)
                + endCmdCreateVideo(list, settingVideo);
    }

    private static String startCmdCreateVideo(ArrayList<String> listImage, SettingVideo settingVideo) {
        StringBuilder cmd = new StringBuilder("-y ");

        //add path list item
        for (String item : listImage) {
            cmd.append("-loop 1 -i ").append(item).append(" ");
        }

        //begin filter complex
        cmd.append("-filter_complex ");

        //prepare input
        for (int i = 0; i < listImage.size(); i++) {
            cmd.append("[").append(i).append(":v]scale=").append(settingVideo.getWidth()).append("x").append(settingVideo.getHeight()).append(",setsar=sar=1/1,fps=").append(settingVideo.getFps()).append(",format=rgba,boxblur=100,setsar=sar=1/1[stream").append(i).append("blurred];");
            cmd.append("[").append(i).append(":v]scale=w='if(gte(iw/ih,").append(settingVideo.getWidth()).append("/").append(settingVideo.getHeight()).append("),min(iw,").append(settingVideo.getWidth()).append("),-1)':h='if(gte(iw/ih,").append(settingVideo.getWidth()).append("/").append(settingVideo.getHeight()).append("),-1,min(ih,").append(settingVideo.getHeight()).append("))',scale=trunc(iw/2)*2:trunc(ih/2)*2,setsar=sar=1/1,fps=").append(settingVideo.getFps()).append(",format=rgba[stream").append(i).append("raw];");
            cmd.append("[stream").append(i).append("blurred][stream").append(i).append("raw]overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2:format=rgb,setpts=PTS-STARTPTS,split=2[stream").append(1 + i).append("out1][stream").append(1 + i).append("out2];");

            switch (settingVideo.getEffect()) {
                case BOX_IN_HORIZONTAL:
                    cmd.append("[stream").append(i + 1).append("out1]trim=duration=").append(settingVideo.getImageDuration()).append(",select=lte(n\\,").append(settingVideo.getImageFrameCount()).append(")[stream").append(i + 1).append("overlaid];");
                    cmd.append("[stream").append(i + 1).append("out2]scale=w=").append(settingVideo.getWidth()).append("/2:-1,pad=width=").append(settingVideo.getWidth()).append(":height=").append(settingVideo.getHeight()).append(":x=(ow-iw)/2:y=(oh-ih)/2:color=").append(settingVideo.getBackGroundColor()).append(",trim=duration=").append(settingVideo.getTransitionDuration()).append(",select=lte(n\\,").append(settingVideo.getTransitionFrameCount()).append("),split=4[stream").append(i + 1).append("prephasein][stream").append(i + 1).append("prezoomin][stream").append(i + 1).append("prezoomout][stream").append(i + 1).append("prephaseout];");
                    break;
                case PUSH_BOX_VERTICAL:
                case PUSH_BOX_HORIZONTAL:
                    cmd.append("[stream").append(i + 1).append("out1]trim=duration=").append(settingVideo.getImageDuration()).append(",select=lte(n\\,").append(settingVideo.getImageFrameCount()).append(")[stream").append(i + 1).append("overlaid];");
                    cmd.append("[stream$((c+1))out2]scale=w=${WIDTH}/2:-1,pad=width=${WIDTH}:height=${HEIGHT}:x=(ow-iw)/2:y=(oh-ih)/2:color=${BACKGROUND_COLOR},trim=duration=${TRANSITION_DURATION},select=lte(n\\,${TRANSITION_FRAME_COUNT}),split=5[stream$((c+1))prephasein][stream$((c+1))checkpoint][stream$((c+1))prezoomin][stream$((c+1))prezoomout][stream$((c+1))prephaseout];");
                    break;
                case SPIN_BLUR_ROTATION:
                    cmd.append(((i + 1) == 1 || (i + 1) == listImage.size())
                            ? "[${c}:v]setpts=PTS-STARTPTS,scale=w='if(gte(iw/ih,${WIDTH}/${HEIGHT}),-1,${WIDTH})':h='if(gte(iw/ih,${WIDTH}/${HEIGHT}),${HEIGHT},-1)',scale=trunc(iw/2)*2:trunc(ih/2)*2,setsar=sar=1/1,fps=${FPS},format=rgba,trim=duration=${IMAGE_DURATION},crop=${WIDTH}:${HEIGHT},split=2[stream$((c+1))][stream$((c+1))sample];"
                            : "[${c}:v]setpts=PTS-STARTPTS,scale=w='if(gte(iw/ih,${WIDTH}/${HEIGHT}),-1,${WIDTH})':h='if(gte(iw/ih,${WIDTH}/${HEIGHT}),${HEIGHT},-1)',scale=trunc(iw/2)*2:trunc(ih/2)*2,setsar=sar=1/1,fps=${FPS},format=rgba,trim=duration=${IMAGE_DURATION},crop=${WIDTH}:${HEIGHT},split=3[stream$((c+1))][stream$((c+1))sample][stream$((c+1))sample2];");
                    break;
            }
        }
        int splitCount;
        //apply padding
        switch (settingVideo.getEffect()) {
            case PUSH_BOX_HORIZONTAL:
                for (int i = 1; i < listImage.size(); i++) {
                    cmd.append("[${IMAGE_COUNT}:v][stream${c}prephaseout]overlay=x='t/(${TRANSITION_DURATION}/2)*${WIDTH}':y=0,trim=duration=${TRANSITION_PHASE_DURATION},select=lte(n\\,(${TRANSITION_FRAME_COUNT}/2))[stream${c}phaseout];");
                    if (i == 1) {
                        cmd.append("[${IMAGE_COUNT}:v]");
                    } else {
                        cmd.append("[stream$((c-1))phaseout]");
                    }
                    cmd.append("${FIRST_STREAM}[stream${c}prephasein]overlay=x='-${WIDTH}+${WIDTH}*t/(${TRANSITION_DURATION}/2)':y=0,trim=duration=${TRANSITION_PHASE_DURATION},select=lte(n\\,(${TRANSITION_FRAME_COUNT}/2))[stream${c}phasein];");
                    cmd.append("[stream${c}checkpoint]trim=duration=${CHECKPOINT_DURATION},split=2[stream${c}checkin][stream${c}checkout];");
                }
                return cmd.toString();
            case PUSH_BOX_VERTICAL:
                for (int i = 1; i < listImage.size(); i++) {
                    cmd.append("[${IMAGE_COUNT}:v][stream${c}prephaseout]overlay=x=0:y='t/(${TRANSITION_DURATION}/2)*${HEIGHT}',trim=duration=${TRANSITION_PHASE_DURATION},select=lte(n\\,(${TRANSITION_FRAME_COUNT}/2))[stream${c}phaseout];");
                    if (i == 1) {
                        cmd.append("[${IMAGE_COUNT}:v]");
                    } else {
                        cmd.append("[stream$((c-1))phaseout]");
                    }
                    cmd.append("${FIRST_STREAM}[stream${c}prephasein]overlay=x=0:y='-h+${HEIGHT}*t/(${TRANSITION_DURATION}/2)',trim=duration=${TRANSITION_PHASE_DURATION},select=lte(n\\,(${TRANSITION_FRAME_COUNT}/2))[stream${c}phasein];");
                    cmd.append("[stream${c}checkpoint]trim=duration=${CHECKPOINT_DURATION},split=2[stream${c}checkin][stream${c}checkout];");
                }
                return cmd.toString();
            case ROTATE:
                for (int i = 1; i < listImage.size(); i++) {
                    if (i == listImage.size()) {
                        splitCount = 2;
                    } else {
                        splitCount = 3;
                    }
                    cmd.append("[stream${c}pre1]pad=width=${WIDTH}:height=${HEIGHT}:x=(${WIDTH}-iw)/2:y=(${HEIGHT}-ih)/2:color=#00000000,trim=duration=${TRANSITION_DURATION},select=lte(n\\,${TRANSITION_FRAME_COUNT})[stream${c}out1];");
                    cmd.append("[stream${c}pre2]pad=width=${WIDTH}:height=${HEIGHT}:x=(${WIDTH}-iw)/2:y=(${HEIGHT}-ih)/2:color=${BACKGROUND_COLOR},trim=duration=${TRANSITION_DURATION},select=lte(n\\,${TRANSITION_FRAME_COUNT}),split=${SPLIT_COUNT}[stream${c}out2][stream${c}out3]");
                    if (i == listImage.size()) {
                        cmd.append(";");
                    } else {
                        cmd.append("[stream${c}out4];");
                    }
                }
                return cmd.toString();
            case SPIN_BLUR_ROTATION:
                for (int i = 2; i <= listImage.size(); i++) {
                    cmd.append("[stream${c}sample]rotate=PI,split=2[stream${c}rotate_in_background][stream${c}pre_rotate_in];");
                    cmd.append("[stream${c}pre_rotate_in]boxblur=luma_radius=10:luma_power=3,rotate=2*PI*t/0.6:c=none[stream${c}rotate_in];");
                }
                return cmd.toString();
        }

        for (int i = 1; i <= listImage.size(); i++) {
            cmd.append("[stream").append(i).append("out1]pad=width=").append(settingVideo.getWidth()).append(":height=").append(settingVideo.getHeight()).append(":x=(").append(settingVideo.getWidth()).append("-iw)/2:y=(").append(settingVideo.getHeight()).append("-ih)/2:color=").append(settingVideo.getBackGroundColor()).append(",trim=duration=").append(settingVideo.getImageDuration()).append(",select=lte(n\\,").append(settingVideo.getImageFrameCount()).append(")[stream").append(i).append("overlaid];");
            if (i == 1) {
                if (listImage.size() > 1) {
                    cmd.append("[stream").append(i).append("out2]pad=width=").append(settingVideo.getWidth()).append(":height=").append(settingVideo.getHeight()).append(":x=(").append(settingVideo.getWidth()).append("-iw)/2:y=(").append(settingVideo.getHeight()).append("-ih)/2:color=").append(settingVideo.getBackGroundColor()).append(",trim=duration=").append(settingVideo.getTransitionDuration()).append(",select=lte(n\\,").append(settingVideo.getTransitionFrameCount()).append(")[stream").append(i).append("ending]");
                }
            } else if (i == listImage.size()) {
                cmd.append("[stream").append(i).append("out2]pad=width=").append(settingVideo.getWidth()).append(":height=").append(settingVideo.getHeight()).append(":x=(").append(settingVideo.getWidth()).append("-iw)/2:y=(").append(settingVideo.getHeight()).append("-ih)/2:color=").append(settingVideo.getBackGroundColor()).append(",trim=duration=").append(settingVideo.getTransitionDuration()).append(",select=lte(n\\,").append(settingVideo.getTransitionFrameCount()).append(")[stream").append(i).append("starting];");
            } else {
                cmd.append("[stream").append(i).append("out2]pad=width=").append(settingVideo.getWidth()).append(":height=").append(settingVideo.getHeight()).append(":x=(").append(settingVideo.getWidth()).append("-iw)/2:y=(").append(settingVideo.getHeight()).append("-ih)/2:color=").append(settingVideo.getBackGroundColor()).append(",trim=duration=").append(settingVideo.getTransitionDuration()).append(",select=lte(n\\,").append(settingVideo.getTransitionFrameCount()).append("),split=2[stream").append(i).append("starting][stream").append(i).append("ending];");
            }
        }
        return cmd.toString();
    }

    private static String endCmdCreateVideo(ArrayList<String> list, SettingVideo settingVideo) {
        StringBuilder cmd = new StringBuilder();

        //begin concat
        for (int i = 1; i < list.size(); i++) {
            cmd.append("[stream").append(i).append("overlaid][stream").append(i + 1).append("blended]");
        }

        //end concat
        cmd.append("[stream").append(list.size()).append("overlaid]concat=n=").append(2 * list.size() - 1).append(":v=1:a=0,format=yuv420p[video] ");

        //end
        cmd.append(" -map [video] -vsync 2 -async 1 -rc-lookahead 0 -g 0 -profile:v main -level 42 -c:v libx264 -r ").append(settingVideo.getFps()).append(" ");
        return cmd.toString();
    }

    private static String mainCmdCreateVideo(ArrayList<String> list, SettingVideo settingVideo) {
        StringBuilder cmd = new StringBuilder();
        String overlay = "";

        //create transition frame
        switch (settingVideo.getEffect()) {
            case BAR_HORIZONTAL:
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='if((lte(mod(Y,(${HEIGHT}/${BAR_COUNT})),(${HEIGHT}/${BAR_COUNT})*T/${TRANSITION_DURATION})),A,B)':shortest=1[stream$((c+1))blended];");
                }
                break;
            case BAR_VERTICAL:
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='if((lte(mod(X,(${WIDTH}/${BAR_COUNT})),(${WIDTH}/${BAR_COUNT})*T/${TRANSITION_DURATION})),A,B)':shortest=1[stream$((c+1))blended];");
                }
                break;
            case BOX_IN_HORIZONTAL:
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[stream${c}prezoomin]scale=${WIDTH}*5:-1,zoompan=z='min(pzoom+0.04,2)':d=${TRANSITION_DURATION}:fps=${FPS}:x='iw/2-(iw/zoom/2)':y='ih/2-(ih/zoom/2)':s=${WIDTH}x${HEIGHT},setpts=0.5*PTS[stream${c}zoomin];");
                }
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[stream${c}prezoomout]scale=${WIDTH}*5:-1,zoompan=z='2-in*0.04':d=${TRANSITION_DURATION}:x='iw/2-(iw/zoom/2)':y='ih/2-(ih/zoom/2)':s=${WIDTH}x${HEIGHT},setpts=0.5*PTS[stream${c}zoomout];");
                }
                break;
            case BOX_IN_VERTICAL:
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[stream${c}prezoomin]scale=${WIDTH}*5:-1,zoompan=z='min(pzoom+0.04,2)':d=${TRANSITION_DURATION}:fps=${FPS}:x='iw/2-(iw/zoom/2)':y='ih/2-(ih/zoom/2)':s=${WIDTH}x${HEIGHT},setpts=0.5*PTS[stream${c}zoomin];");
                }
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[stream${c}prezoomout]scale=${WIDTH}*5:-1,zoompan=z='2-in*0.04':d=${TRANSITION_DURATION}:x='iw/2-(iw/zoom/2)':y='ih/2-(ih/zoom/2)':s=${WIDTH}x${HEIGHT},setpts=0.5*PTS[stream${c}zoomout];");
                }
                break;
            case CHECKER_BOARD:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='if((lte(mod(X,${CELL_SIZE}),${CELL_SIZE}/2-(${CELL_SIZE}/2)*T/${TRANSITION_DURATION})+lte(mod(Y,${CELL_SIZE}),${CELL_SIZE}/2-(${CELL_SIZE}/2)*T/${TRANSITION_DURATION}))+(gte(mod(X,${CELL_SIZE}),(${CELL_SIZE}/2)+(${CELL_SIZE}/2)*T/${TRANSITION_DURATION})+gte(mod(Y,${CELL_SIZE}),(${CELL_SIZE}/2)+(${CELL_SIZE}/2)*T/${TRANSITION_DURATION})),B,A)':shortest=1[stream$((c+1))blended];");
                break;
            case CLOCK:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream${c}starting][stream$((c-1))ending]blend=all_expr='if(lte(T,0.125),if(gt(X,W/2)*lte(Y,H/2)*lte(X-W/2+Y-H/2,0),A,B),if(lte(T,0.25),if(gt(X,W/2)*lte(Y,H/2),A,B),if(lte(T,0.375),if((gt(X,W/2)*gt(Y,H/2)*gt(X-W/2-Y+H/2,0))+(gt(X,W/2)*lte(Y,H/2)),A,B),if(lte(T,0.5),if(gt(X,W/2),A,B),if(lte(T,0.625),if((lte(X,W/2)*gt(Y,H/2)*gt(X-W/2+Y-H/2,0))+gt(X,W/2),A,B),if(lte(T,0.75),if((lte(X,W/2)*gt(Y,H/2))+gt(X,W/2),A,B),if(lte(T,0.875),if((lte(X,W/2)*lte(Y,H/2)*lte(Y-H/2-X+W/2,0)),B,A),A)))))))':shortest=1[stream${c}blended];");
                break;
            case COLLAPSE_BOTH:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='if((gte(X,(W/2)*T/${TRANSITION_DURATION})*gte(Y,(H/2)*T/${TRANSITION_DURATION}))*(lte(X,W-(W/2)*T/${TRANSITION_DURATION})*lte(Y,H-(H/2)*T/${TRANSITION_DURATION})),B,A)':shortest=1[stream$((c+1))blended];");
                break;
            case COLLAPSE_CIRCULAR:
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[stream$((c+1))starting]geq=lum='p(X,Y)':a='if(lte(pow(sqrt(pow(W/2,2)+pow(H/2,2))-sqrt(pow(T/${TRANSITION_DURATION}*W/2,2)+pow(T/${TRANSITION_DURATION}*H/2,2)),2),pow(X-(W/2),2)+pow(Y-(H/2),2)),255,0)'[stream$((c+1))circularstarting];");
                    cmd.append("[stream${c}ending][stream$((c+1))circularstarting]overlay=0:0:shortest=1[stream$((c+1))blended];");
                }
                break;
            case COLLAPSE_HORIZONTAL:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='if(gte(X,(W/2)*T/${TRANSITION_DURATION})*lte(X,W-(W/2)*T/${TRANSITION_DURATION}),B,A)':shortest=1[stream$((c+1))blended];");
                break;
            case COLLAPSE_VERTICAL:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='if(gte(Y,(H/2)*T/${TRANSITION_DURATION})*lte(Y,H-(H/2)*T/${TRANSITION_DURATION}),B,A)':shortest=1[stream$((c+1))blended];");
                break;
            case COVER_HORIZONTAL:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='if(gte(X,W*T/${TRANSITION_DURATION}),B,A)':shortest=1[stream$((c+1))blended];");
                break;
            case COVER_VERTICAL:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='if(gte(Y,H*T/${TRANSITION_DURATION}),B,A)':shortest=1[stream$((c+1))blended];");
                break;
            case EXPAND_BOTH:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='if((lte(X,(W/2)-(W/2)*T/${TRANSITION_DURATION})+lte(Y,(H/2)-(H/2)*T/${TRANSITION_DURATION}))+(gte(X,(W/2)+(W/2)*T/${TRANSITION_DURATION})+gte(Y,(H/2)+(H/2)*T/${TRANSITION_DURATION})),B,A)':shortest=1[stream$((c+1))blended];");
                break;
            case EXPAND_CIRCULAR:
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[stream$((c+1))starting]geq=lum='p(X,Y)':a='if(lte(pow(sqrt(pow(T/${TRANSITION_DURATION}*W/2,2)+pow(T/${TRANSITION_DURATION}*H/2,2)),2),pow(X-(W/2),2)+pow(Y-(H/2),2)),0,255)'[stream$((c+1))circularstarting];");
                    cmd.append("[stream${c}ending][stream$((c+1))circularstarting]overlay=0:0:shortest=1[stream$((c+1))blended];");
                }
                break;
            case EXPAND_HORIZONTAL:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='if(lte(X,(W/2)-(W/2)*T/${TRANSITION_DURATION})+gte(X,(W/2)+(W/2)*T/${TRANSITION_DURATION}),B,A)':shortest=1[stream$((c+1))blended];");
                break;
            case EXPAND_VERTICAL:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='if(lte(Y,(H/2)-(H/2)*T/${TRANSITION_DURATION})+gte(Y,(H/2)+(H/2)*T/${TRANSITION_DURATION}),B,A)':shortest=1[stream$((c+1))blended];");
                break;
            case FADE_IN:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream$((c+1))starting][stream${c}ending]blend=all_expr='A*(if(gte(T,${TRANSITION_DURATION}),1,T/${TRANSITION_DURATION}))+B*(1-(if(gte(T,${TRANSITION_DURATION}),1,T/${TRANSITION_DURATION})))',select=lte(n\\,${TRANSITION_FRAME_COUNT})[stream$((c+1))blended];");
                break;
            case PUSH_BOX_HORIZONTAL:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream${c}prezoomin]scale=iw*5:ih*5,zoompan=z='min(pzoom+0.04,2)':d=${TRANSITION_DURATION}:fps=${FPS}:x='iw/2-(iw/zoom/2)':y='ih/2-(ih/zoom/2)':s=${WIDTH}x${HEIGHT},setpts=0.5*PTS[stream${c}zoomin];");
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream${c}prezoomout]scale=iw*5:ih*5,zoompan=z='2-in*0.04':d=${TRANSITION_DURATION}:x='iw/2-(iw/zoom/2)':y='ih/2-(ih/zoom/2)':s=${WIDTH}x${HEIGHT},setpts=0.5*PTS[stream${c}zoomout];");
                break;
            case PUSH_BOX_VERTICAL:
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream${c}prezoomin]scale=iw*5:ih*5,zoompan=z='min(pzoom+0.04,2)':d=${TRANSITION_DURATION}:fps=${FPS}:x='iw/2-(iw/zoom/2)':y='ih/2-(ih/zoom/2)':s=${WIDTH}x${HEIGHT},setpts=0.5*PTS[stream${c}zoomin];");
                for (int i = 1; i < list.size(); i++)
                    cmd.append("[stream${c}prezoomout]scale=iw*5:ih*5,zoompan=z='2-in*0.04':d=${TRANSITION_DURATION}:x='iw/2-(iw/zoom/2)':y='ih/2-(ih/zoom/2)':s=${WIDTH}x${HEIGHT},setpts=0.5*PTS[stream${c}zoomout];");
                break;
            case PUSH_HORIZONTAL:
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[$((IMAGE_COUNT+1)):v][stream${c}ending]overlay=x='t/${TRANSITION_DURATION}*${WIDTH}':y=0,trim=duration=${TRANSITION_DURATION},select=lte(n\\,${TRANSITION_FRAME_COUNT})[stream${c}moving];");
                    cmd.append("[stream${c}moving][stream$((c+1))starting]overlay=x='-w+t/${TRANSITION_DURATION}*${WIDTH}':y=0:shortest=1,trim=duration=${TRANSITION_DURATION},select=lte(n\\,${TRANSITION_FRAME_COUNT})[stream$((c+1))blended];");
                }
                break;
            case PUSH_VERTICAL:
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[$((IMAGE_COUNT+1)):v][stream${c}ending]overlay=y='t/${TRANSITION_DURATION}*${HEIGHT}':x=0:threads=1,trim=duration=${TRANSITION_DURATION},select=lte(n\\,${TRANSITION_FRAME_COUNT})[stream${c}moving];");
                    cmd.append("[stream${c}moving][stream$((c+1))starting]overlay=y='-h+t/${TRANSITION_DURATION}*${HEIGHT}':x=0:threads=1:shortest=1,trim=duration=${TRANSITION_DURATION},select=lte(n\\,${TRANSITION_FRAME_COUNT})[stream$((c+1))blended];");
                }
                break;
            case ROTATE:
                for (int i = 1; i < list.size(); i++) {
                    if (i == list.size()) {
                        overlay += "${IMAGE_COUNT}:v";
                    } else {
                        overlay += "stream$((c-1))out2";
                    }
                    cmd.append("[stream${c}out1]rotate=2*PI*t:ow=4*${WIDTH}:c=#00000000,[" + overlay + "]overlay=x='${WIDTH}*3/2-w+t/${TRANSITION_DURATION}*${WIDTH}':y=0,trim=duration=${TRANSITION_DURATION},select=lte(n\\,${TRANSITION_FRAME_COUNT})[stream${c}rotating];");
                }
                break;
            case SLIDING_BAR_HORIZONTAL:
                for (int i = 2; i <= list.size(); i++) {
                    cmd.append("[stream${c}starting]split=${BAR_COUNT}");
                    for (int d = 1; d <= 8; d++) {
                        cmd.append("[stream${c}starting" + d + "]");
                    }
                    cmd.append(";");
                    for (int d = 1; d <= 8; d++) {
                        cmd.append("[stream${c}starting" + d + "]crop=out_w=iw:out_h=ih/${BAR_COUNT}:x=0:y=ih/${BAR_COUNT}*" + (d - 1) + ",pad=w=${WIDTH}:h=${HEIGHT}:x=0:y=0:color=#00000000[stream${c}starting" + d + "cropped];");
                    }
                    for (int d = 1; d <= list.size(); d++) {
                        cmd.append(d == 1 ? "[stream$((c-1))ending]" : "[stream${c}starting$((d-1))added]");
                        cmd.append("[stream${c}starting" + (d) + "cropped]overlay=x='if(between(t,(${TRANSITION_DURATION}/${BAR_COUNT})*" + (d - 1) + ",(${TRANSITION_DURATION}/${BAR_COUNT})*" + d + "),-w+${WIDTH}*(t-(${TRANSITION_DURATION}/${BAR_COUNT})*" + (d - 1) + ")/(${TRANSITION_DURATION}/${BAR_COUNT}),if(gte(t,(${TRANSITION_DURATION}/${BAR_COUNT})*" + (d) + "),0,-w))':y=h/${BAR_COUNT}*" + (d - 1) + ",select=lte(n\\,${TRANSITION_FRAME_COUNT})");
                        cmd.append(d == list.size() ? "[stream${c}blended];" : "[stream${c}starting" + d + "added];");
                    }
                }
                break;
            case SLIDING_BAR_VERTICAL:
                for (int i = 2; i <= list.size(); i++) {
                    cmd.append("[stream${c}starting]split=${BAR_COUNT}");
                    for (int d = 1; d <= 8; d++) {
                        cmd.append("[stream${c}starting" + d + "]");
                    }
                    cmd.append(";");
                    for (int d = 1; d <= 8; d++) {
                        cmd.append("[stream${c}starting" + d + "]crop=out_w=iw/${BAR_COUNT}:out_h=ih:x=iw/${BAR_COUNT}*" + (d - 1) + ":y=0,pad=w=${WIDTH}:h=${HEIGHT}:x=0:y=0:color=#00000000[stream${c}starting" + d + "cropped];");
                    }
                    for (int d = 1; d <= 8; d++) {
                        cmd.append(d == 1 ? "[stream$((c-1))ending]" : "[stream${c}starting" + (d - 1) + "added]");
                        cmd.append("[stream${c}starting" + (d) + "cropped]overlay=y='if(between(t,(${TRANSITION_DURATION}/${BAR_COUNT})*" + (d - 1) + ",(${TRANSITION_DURATION}/${BAR_COUNT})*" + (d) + "),-h+${HEIGHT}*(t-(${TRANSITION_DURATION}/${BAR_COUNT})*" + (d - 1) + ")/(${TRANSITION_DURATION}/${BAR_COUNT}),if(gte(t,(${TRANSITION_DURATION}/${BAR_COUNT})*" + (d) + "),0,-h))':x=w/${BAR_COUNT}*" + (d - 1) + ":threads=1,select=lte(n\\,${TRANSITION_FRAME_COUNT})");
                        cmd.append(d == list.size() ? "[stream${c}blended];" : "[stream${c}starting${d}added];");
                    }
                }
                break;
            case SPIN_BLUR_ROTATION:
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[stream${c}pre_rotate_out]boxblur=luma_radius=10:luma_power=3,rotate=2*PI*t/0.4:c=none[stream${c}rotate_out];");
                    cmd.append(i == 1 ?
                            "[stream${c}sample]split=2[stream${c}rotate_out_background][stream${c}pre_rotate_out];"
                            : "[stream${c}sample2]split=2[stream${c}rotate_out_background][stream${c}pre_rotate_out];");
                    cmd.append("[stream${c}rotate_out_background][stream${c}rotate_out]overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2:format=rgb,crop=${WIDTH}:${HEIGHT},trim=duration=0.2[transition${c}part1];");
                    cmd.append("[stream$((c+1))rotate_in_background][stream$((c+1))rotate_in]overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2:format=rgb,crop=${WIDTH}:${HEIGHT},trim=duration=0.3[transition${c}part2];");
                }
                break;
            case WIPE_HORIZONTAL:
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[stream$((c+1))starting][stream${c}ending]overlay=x='t/${TRANSITION_DURATION}*${WIDTH}':y=0,select=lte(n\\,${TRANSITION_FRAME_COUNT})[stream$((c+1))blended];");
                }
                break;
            case WIPE_VERTICAL:
                for (int i = 1; i < list.size(); i++) {
                    cmd.append("[stream$((c+1))starting][stream${c}ending]overlay=y='t/${TRANSITION_DURATION}*${HEIGHT}':x=0,select=lte(n\\,${TRANSITION_FRAME_COUNT})[stream$((c+1))blended];");
                }
                break;
        }
        return cmd.toString();
    }
}







































