package com.bfs.papertoss.platform;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import com.iscle.papertoss.R;

import java.io.IOException;
import java.util.HashMap;

public class SoundMgr {
    BackgroundSound backgroundSound;
    PlaySound playSound;
    SetSound setSound;
    public static boolean m_sound = true;
    MediaPlayer m_player = null;
    private String m_current_background_sound = "";
    SoundPool soundPool = new SoundPool(5, 3, 0);
    HashMap<String, Integer> map = new HashMap<>();

    public SoundMgr() {
        this.playSound = new PlaySound();
        this.backgroundSound = new BackgroundSound();
        this.setSound = new SetSound();
        preloadAllSounds();
        Evt.getInstance().subscribe("paperTossPlaySound", this.playSound);
        Evt.getInstance().subscribe("setBackgroundSound", this.backgroundSound);
        Evt.getInstance().subscribe("setSound", this.setSound);
    }

    private void preloadAllSounds() {
        try {
            String[] filenames = Globals.m_context.getAssets().list("sounds");
            for (String filename : filenames) {
                try {
                    if (!filename.contains(".raw")) {
                        String filename2 = "sounds/" + filename;
                        int id = this.soundPool.load(Globals.m_context.getAssets().openFd(filename2), 1);
                        this.map.put(filename2, id);
                    }
                } catch (Exception e) {
                    Log.d("BFS", "Could not pre-load sound: " + filename, e);
                }
            }
        } catch (IOException e2) {
            Log.d("BFS", "Could not locate sounds directory", e2);
        }
    }

    private class PlaySound implements EvtListener {
        @Override
        public void run(Object object) {
            if (SoundMgr.m_sound) {
                String filename = ("sounds/" + object).replace(".wav", ".OGG");
                if (!SoundMgr.this.map.containsKey(filename)) {
                    try {
                        int id = SoundMgr.this.soundPool.load(Globals.m_context.getAssets().openFd(filename), 1);
                        SoundMgr.this.map.put(filename, id);
                    } catch (IOException e) {
                        Log.d("BFS", "Could not lazily-load sound: " + filename, e);
                    }
                }
                int id2 = SoundMgr.this.map.get(filename);
                SoundMgr.this.soundPool.play(id2, 1.0f, 1.0f, 1, 0, 1.0f);
            }
        }
    }

    private class BackgroundSound implements EvtListener {
        @Override
        public void run(Object object) {
            String filename = ("sounds/" + object).replace(".mp3", ".OGG");
            int sound_id = 0;
            if (filename.contains("OfficeNoise")) {
                sound_id = R.raw.officenoise;
            } else if (filename.contains("AirportNoise")) {
                sound_id = R.raw.airportnoise;
            } else if (filename.contains("BasementAmbient")) {
                sound_id = R.raw.basementambient;
            } else if (filename.contains("Bathroom Background")) {
                sound_id = R.raw.bathroombackground;
            }
            if (!filename.equals(SoundMgr.this.m_current_background_sound)) {
                SoundMgr.this.m_current_background_sound = filename;
                try {
                    if (SoundMgr.this.m_player != null && SoundMgr.this.m_player.isPlaying()) {
                        SoundMgr.this.m_player.stop();
                        SoundMgr.this.m_player.release();
                        SoundMgr.this.m_player = null;
                    }
                    if (SoundMgr.m_sound && sound_id != 0) {
                        SoundMgr.this.m_player = MediaPlayer.create(Globals.m_context, sound_id);
                        SoundMgr.this.m_player.setLooping(true);
                        SoundMgr.this.m_player.start();
                    }
                } catch (Exception e) {
                    Log.d("BFS", "Could not play background sound: " + filename, e);
                }
            }
        }
    }

    private class SetSound implements EvtListener {
        @Override
        public void run(Object object) {
            SoundMgr.m_sound = (Boolean) object;
            if (SoundMgr.m_sound) {
                SoundMgr.this.startBackgroundSound();
            } else {
                SoundMgr.this.stopBackgroundSound();
            }
        }
    }

    public void stopBackgroundSound() {
        if (this.m_player != null && this.m_player.isPlaying()) {
            this.m_player.stop();
            this.m_player.release();
            this.m_player = null;
        }
    }

    public void startBackgroundSound() {
        this.backgroundSound.run(this.m_current_background_sound);
    }
}
