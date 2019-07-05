package com.example.demoslideimage.extensions;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class StringDemo {
    private ArrayList<String> list = new ArrayList<>();
    private String cmd = "-y ";
    private String cmd1 = "";
    private String cmd2 = "";
    private String cmd3 = "";
    private int WIDTH = 1280;
    private int HEIGHT = 720;
    private static final int FPS = 30;
    private int direction = TOPTOBOTTOM;
    private static final int TOPTOBOTTOM = 1;
    private static final int BOTTOMTOBOT = 2;
    private static final String BACKGROUND_COLOR = "black";
    private static final int TRANSITION_DURATION = 1;
    private static final int TRANSITION_FRAME_COUNT = TRANSITION_DURATION * FPS;
    private static final int IMAGE_FRAME_COUNT = 2 * FPS;
    private String pathFile;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public StringDemo(ArrayList<String> list, String pathFile) {
        this.list = list;
        this.pathFile = pathFile;
        for (String item : list) {
            cmd += "-loop 1 -i " + item + " ";
        }
        cmd += "-filter_complex ";

        //prepare input
        for (int i = 0; i < list.size(); i++) {
//            cmd += "[" + i + ":v]setpts=PTS-STARTPTS,scale=w='if(gte(iw/ih," + WIDTH + "/" + HEIGHT + "),-1," + WIDTH + ")':h='if(gte(iw/ih," + WIDTH + "/" + HEIGHT + ")," + HEIGHT + ",-1)',crop=" + WIDTH + ":" + HEIGHT + ",setsar=sar=1/1,fps=" + FPS + ",format=rgba,split=2[stream" + (i + 1) + "out1][stream" + (i + 1) + "out2];";
            cmd += "[" + i + ":v]scale=" + WIDTH + "x" + HEIGHT + ",setsar=sar=1/1,fps=" + FPS + ",format=rgba,boxblur=100,setsar=sar=1/1[stream" + i + "blurred];";
            cmd += "[" + i + ":v]scale=w='if(gte(iw/ih," + WIDTH + "/" + HEIGHT + "),min(iw," + WIDTH + "),-1)':h='if(gte(iw/ih," + WIDTH + "/" + HEIGHT + "),-1,min(ih," + HEIGHT + "))',scale=trunc(iw/2)*2:trunc(ih/2)*2,setsar=sar=1/1,fps=" + FPS + ",format=rgba[stream" + i + "raw];";
            cmd += "[stream" + i + "blurred][stream" + i + "raw]overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2:format=rgb,setpts=PTS-STARTPTS,split=2[stream" + (i + 1) + "out1][stream" + (i + 1) + "out2];";
        }

        //apply padding
        for (int i = 1; i <= list.size(); i++) {
            cmd1 += "[stream" + i + "out1]pad=width=" + WIDTH + ":height=" + HEIGHT + ":x=(" + WIDTH + "-iw)/2:y=(" + HEIGHT + "-ih)/2:color=" + BACKGROUND_COLOR + ",trim=duration=" + direction + ",select=lte(n\\," + IMAGE_FRAME_COUNT + ")[stream" + i + "overlaid];";
            if (i == 1) {
                if (list.size() > 1) {
                    cmd1 += "[stream" + i + "out2]pad=width=" + WIDTH + ":height=" + HEIGHT + ":x=(" + WIDTH + "-iw)/2:y=(" + HEIGHT + "-ih)/2:color=" + BACKGROUND_COLOR + ",trim=duration=" + TRANSITION_DURATION + ",select=lte(n\\," + TRANSITION_FRAME_COUNT + ")[stream" + i + "ending];";
                }
            } else if (i == list.size()) {
                cmd1 += "[stream" + i + "out2]pad=width=" + WIDTH + ":height=" + HEIGHT + ":x=(" + WIDTH + "-iw)/2:y=(" + HEIGHT + "-ih)/2:color=" + BACKGROUND_COLOR + ",trim=duration=" + TRANSITION_DURATION + ",select=lte(n\\," + TRANSITION_FRAME_COUNT + ")[stream" + i + "starting];";
            } else {
                cmd1 += "[stream" + i + "out2]pad=width=" + WIDTH + ":height=" + HEIGHT + ":x=(" + WIDTH + "-iw)/2:y=(" + HEIGHT + "-ih)/2:color=" + BACKGROUND_COLOR + ",trim=duration=" + TRANSITION_DURATION + ",select=lte(n\\," + TRANSITION_FRAME_COUNT + "),split=2[stream" + i + "starting][stream" + i + "ending];";
            }
        }

        //CREATE TRANSITION FRAMES
        for (int i = 1; i < list.size(); i++) {
            switch (direction) {
                case TOPTOBOTTOM:
                    cmd2 += "[stream" + (i + 1) + "starting][stream" + i + "ending]overlay=y='t/" + TRANSITION_DURATION + "*" + HEIGHT + "':x=0,select=lte(n\\," + TRANSITION_FRAME_COUNT + ")[stream" + (i + 1) + "blended];";
                    break;
                case BOTTOMTOBOT:
                    cmd2 += "[stream" + (i + 1) + "starting][stream" + i + "ending]overlay=y='-t/" + TRANSITION_DURATION + "*" + HEIGHT + "':x=0,select=lte(n\\," + TRANSITION_FRAME_COUNT + ")[stream" + (i + 1) + "blended];";
                    break;
            }
        }

        //Begin Concat
        for (int i = 1; i < list.size(); i++) {
            cmd3 += "[stream" + i + "overlaid][stream" + (i + 1) + "blended]";
        }

        //end concat
        cmd3 += "[stream" + list.size() + "overlaid]concat=n=" + (list.size() * 2 - 1) + ":v=1:a=0,format=yuv420p[video]";

        //end
        cmd3 += " -map [video] -vsync 2 -async 1 -rc-lookahead 0 -g 0 -profile:v main -level 42 -c:v libx264 -r " + FPS + " " + pathFile;
    }

    @NonNull
    @Override
    public String toString() {
        Log.e("TAG cmd", cmd);
        Log.e("TAG cmd1", cmd1);
        Log.e("TAG cmd2", cmd2);
        Log.e("TAG cmd3", cmd3);
        return cmd + cmd1 + cmd2 + cmd3;
    }
}
