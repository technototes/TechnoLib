package com.technototes.library.hardware;

import static com.technototes.library.hardware.HardwareDevice.hardwareMap;

import com.qualcomm.ftccommon.SoundPlayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class Speaker {
    private final Map<String, Integer> list;

    private String currentSong = null;

    public Speaker(String... songs) {
        this(1f, songs);
    }
    public Speaker(float volume, String... songs) {
        list = new HashMap<>();
        addSongs(songs);
        setVolume(volume);
    }

    public Speaker addSongs(String... name){
        for(String s : name) {
            list.put(s, hardwareMap.appContext.getResources().getIdentifier(s, "raw", hardwareMap.appContext.getPackageName()));
        }
        return this;
    }


    public Speaker start(String name){
        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, list.get(name));
        currentSong = name;
        return this;
    }

    public Speaker stop(){
        SoundPlayer.getInstance().stopPlayingAll();
        currentSong = null;
        return this;
    }

    public Speaker setVolume(float volume){
        SoundPlayer.getInstance().setMasterVolume(volume);
        return this;
    }

    public float getVolume(){
        return SoundPlayer.getInstance().getMasterVolume();
    }
    @Nullable
    public String getCurrentSong(){
        return currentSong;
    }
}
