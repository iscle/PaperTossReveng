package com.bfs.papertoss.cpp;

import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.vector.v2f;
import com.bfs.papertoss.vector.v2i;
import com.bfs.papertoss.vector.v3f;
import com.bfs.papertoss.vector.v4f;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class LevelDefs {
    public static final int AIRPORT = 3;
    public static final int BASEMENT = 4;
    public static final int BATHROOM = 5;
    public static final int EASY = 0;
    public static final int HARD = 2;
    public static final int LEFT = 0;
    public static final int MAX_ANIMS = 8;
    public static final int MAX_FOREGROUNDS = 2;
    public static final int MAX_OFF_SOUNDS = 4;
    public static final int MEDIUM = 1;
    public static final int NUM_DIRS = 2;
    private static final float OVER_BG = 449.88998f;
    public static final int RIGHT = 1;
    static MenuInfo menu_info;
    static ScoreMenuInfo score_menu_info;
    public static int NUM_LEVELS = 6;
    static LevelInfo[] level_info = new LevelInfo[NUM_LEVELS];

    public static class BasketInfo {
        float base_offset;
        float distance;
        float half_width;
        float height_offset;
        public String image;
        v3f pos = new v3f();
        v2i size = new v2i();
    }

    public static class ButtonInfo {
        public String image;
        v2i pos = new v2i();
        v2f size = new v2f();
    }

    public static class CameraInfo {
        float fov;
        float height;
    }

    public static class ForegroundInfo {
        public String image;
        v3f pos = new v3f();
    }

    public static class HudInfo {
        public String image;
        boolean show_menu;
        v3f pos = new v3f();
        v3f score_rot = new v3f();
        v3f score_pos = new v3f();
        v3f best_rot = new v3f();
        v3f best_pos = new v3f();
        v3f submit_rot = new v3f();
        v3f submit_pos = new v3f();
        v3f menu_rot = new v3f();
        v3f menu_pos = new v3f();
    }

    public static class LevelInfo {
        public String background_image;
        int toss_height;
        public String tutorial_image;
        boolean use_fireworks;
        MenuLevelInfo menu_info = new MenuLevelInfo();
        ScoreMenuLevelInfo score_menu_info = new ScoreMenuLevelInfo();
        HudInfo hud = new HudInfo();
        BasketInfo basket = new BasketInfo();
        ForegroundInfo[] foreground = new ForegroundInfo[2];
        WindAnim[][] wind = (WindAnim[][]) Array.newInstance((Class<?>) WindAnim.class, 2, 8);
        WindSpeedInfo wind_speed = new WindSpeedInfo();
        CameraInfo camera = new CameraInfo();
        SoundInfo sounds = new SoundInfo();
        SplashAnimInfo splash = new SplashAnimInfo();
    }

    public static class MenuInfo {
        public String image;
        ButtonInfo score_button = new ButtonInfo();
    }

    public static class MenuLevelInfo {
        public String image;
        public String level_name;
        v3f pos = new v3f();
        v3f score_pos = new v3f();
        int score_to_unlock_next;
    }

    public static class ScoreMenuInfo {
        ButtonInfo back_button = new ButtonInfo();
        public String image;
    }

    public static class ScoreMenuLevelInfo {
        v2i pos = new v2i();
        v2f size = new v2f();
        v2i score_pos = new v2i();
    }

    public static class SoundInfo {
        public String loop;
        public String[] offscreen_left = new String[4];
        public String[] offscreen_right = new String[4];
        public String splash;
    }

    public static class SplashAnimInfo {
        float duration;
        public String image;
        float scale;
        v2i size = new v2i();
        SplashAnimRange range = new SplashAnimRange();
    }

    public static class SplashAnimRange {
        boolean inside;
        int left;
        int right;
    }

    public static class WindAnim {
        float duration;
        int frame_count;
        public String image;
        v3f pos = new v3f();
        v2i size = new v2i();
        v2f scale = new v2f();
        v2f scroll = new v2f();
        v2f alpha_range = new v2f();
    }

    public static class WindSpeedInfo {
        float depth;
        v3f number_pos = new v3f();
        v3f arrow_pos = new v3f();
        v2f scale = new v2f();
        v4f color = new v4f();
    }

    static void initializeData() {
        if (Globals.HI_RES) {
            initializeDataFreeHiRes();
        } else {
            initializeDataFree();
        }
    }

    static void initializeDataFree() {
        menu_info = new MenuInfo();
        menu_info.image = "MMenu.jpg";
        menu_info.score_button.image = "HS_btn_green.png";
        menu_info.score_button.pos = new v2i(63, 343);
        score_menu_info = new ScoreMenuInfo();
        score_menu_info.image = "SBoard_menu.jpg";
        score_menu_info.back_button.pos = new v2i(269, 156);
        score_menu_info.back_button.size = new v2f(103.0f, 42.0f);
        for (int i = 0; i < level_info.length; i++) {
            level_info[i] = new LevelInfo();
            for (int j = 0; j < 2; j++) {
                level_info[i].foreground[j] = new ForegroundInfo();
            }
            for (int j2 = 0; j2 < 2; j2++) {
                for (int k = 0; k < 8; k++) {
                    level_info[i].wind[j2][k] = new WindAnim();
                }
            }
        }
        level_info[0].menu_info.image = "Easy_button.png";
        level_info[0].menu_info.pos = v3f.iv3f(230.0f, 155.0f, 0.0f);
        level_info[0].score_menu_info.pos = new v2i(84, 397);
        level_info[0].score_menu_info.size = new v2f(121.0f, 38.0f);
        level_info[0].score_menu_info.score_pos = new v2i(280, 397);
        level_info[0].hud.score_rot = new v3f(0.0f, 0.0f, -3.0f);
        level_info[0].hud.best_rot = new v3f(0.0f, 0.0f, -3.0f);
        level_info[0].hud.submit_rot = new v3f(0.0f, 0.0f, -3.0f);
        level_info[0].hud.menu_rot = new v3f(0.0f, 0.0f, -3.0f);
        level_info[0].hud.score_pos = new v3f(40.0f, 410.0f, OVER_BG);
        level_info[0].hud.best_pos = new v3f(40.0f, 385.0f, OVER_BG);
        level_info[0].hud.submit_pos = new v3f(40.0f, 360.0f, OVER_BG);
        level_info[0].hud.menu_pos = v3f.iv3f(48.0f, 185.0f, 0.0f);
        level_info[0].basket.image = "CanFront_L1.png";
        level_info[0].basket.pos = new v3f(160.0f, 183.0f, 6.2819996f);
        level_info[0].basket.distance = 8.282f;
        level_info[0].basket.half_width = 37.0f;
        level_info[0].basket.height_offset = 30.0f;
        level_info[0].basket.base_offset = 30.0f;
        level_info[0].foreground[0].image = "Level1_foreground.png";
        level_info[0].foreground[0].pos = new v3f(15.0f, 133.5f, 1.0f);
        level_info[0].wind[0][0].image = "fan_l1l2_left.png";
        level_info[0].wind[0][0].pos = new v3f(31.0f, 58.0f, 0.9f);
        level_info[0].wind[0][0].size = new v2i(121, 198);
        level_info[0].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[0].wind[0][0].duration = 0.1f;
        level_info[0].wind[1][0].image = "fan_l1l2_right.png";
        level_info[0].wind[1][0].pos = new v3f(292.0f, 66.0f, 0.9f);
        level_info[0].wind[1][0].size = new v2i(121, 198);
        level_info[0].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[0].wind[1][0].duration = 0.1f;
        level_info[0].wind_speed.number_pos = new v3f(0.0f, 0.0f, 4.65f);
        level_info[0].wind_speed.arrow_pos = new v3f(0.0f, 0.0f, 4.25f);
        level_info[0].wind_speed.scale = new v2f(0.6f, 0.5f);
        level_info[0].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[0].sounds.loop = "OfficeNoise.mp3";
        level_info[0].sounds.offscreen_left[0] = "OffScreenLeft0.wav";
        level_info[0].sounds.offscreen_left[1] = "OffScreenLeft1.wav";
        level_info[0].sounds.offscreen_left[2] = "OffScreenLeft2.wav";
        level_info[0].sounds.offscreen_left[3] = "OffScreenLeft3.wav";
        level_info[0].sounds.offscreen_right[0] = "OffScreenRight0.wav";
        level_info[0].sounds.offscreen_right[1] = "OffScreenRight1.wav";
        level_info[0].background_image = "Level1.jpg";
        level_info[0].camera.height = 4.661f;
        level_info[0].camera.fov = 43.357f;
        level_info[1].menu_info.image = "Med_button.png";
        level_info[1].menu_info.pos = level_info[0].menu_info.pos.minus(new v3f(0.0f, 50.0f));
        level_info[1].score_menu_info.pos = level_info[0].score_menu_info.pos.minus(new v2i(0, 42));
        level_info[1].score_menu_info.size = new v2f(155.0f, 38.0f);
        level_info[1].score_menu_info.score_pos = level_info[0].score_menu_info.score_pos.minus(new v2i(0, 42));
        level_info[1].hud.score_rot = new v3f(0.0f, 0.0f, -4.0f);
        level_info[1].hud.best_rot = new v3f(0.0f, 0.0f, -4.0f);
        level_info[1].hud.submit_rot = new v3f(0.0f, 0.0f, -4.0f);
        level_info[1].hud.menu_rot = new v3f(0.0f, 0.0f, -4.0f);
        level_info[1].hud.score_pos = new v3f(64.0f, 415.0f, OVER_BG);
        level_info[1].hud.best_pos = new v3f(64.0f, 397.0f, OVER_BG);
        level_info[1].hud.submit_pos = new v3f(64.0f, 379.0f, OVER_BG);
        level_info[1].hud.menu_pos = v3f.iv3f(162.0f, 76.0f, 0.0f);
        level_info[1].basket.image = "CanFront_L2.png";
        level_info[1].basket.pos = new v3f(162.0f, 271.0f, 10.625f);
        level_info[1].basket.distance = 12.625f;
        level_info[1].basket.half_width = 24.0f;
        level_info[1].basket.height_offset = 30.0f;
        level_info[1].basket.base_offset = 16.0f;
        level_info[1].foreground[0].image = "Level2_foreground.png";
        level_info[1].foreground[0].pos = new v3f(300.0f, 239.0f, 6.5f);
        level_info[1].foreground[1].image = "Level2_Computer.png";
        level_info[1].foreground[1].pos = new v3f(23.0f, 278.0f, 10.625f);
        level_info[1].background_image = "Level2.jpg";
        level_info[1].wind[0][0].image = "fan_l1l2_left.png";
        level_info[1].wind[0][0].pos = new v3f(7.0f, 78.0f, 0.0f);
        level_info[1].wind[0][0].size = new v2i(121, 198);
        level_info[1].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[1].wind[0][0].duration = 0.1f;
        level_info[1].wind[1][0].image = "fan_l1l2_right.png";
        level_info[1].wind[1][0].pos = new v3f(314.0f, 80.0f, 0.0f);
        level_info[1].wind[1][0].size = new v2i(121, 198);
        level_info[1].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[1].wind[1][0].duration = 0.1f;
        level_info[1].wind_speed.number_pos = new v3f(0.0f, 0.0f, 5.0f);
        level_info[1].wind_speed.arrow_pos = new v3f(0.0f, 0.0f, 4.25f);
        level_info[1].wind_speed.scale = new v2f(0.9f, 0.9f);
        level_info[1].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[1].sounds.loop = "OfficeNoise.mp3";
        level_info[1].sounds.offscreen_left[0] = "OffScreenLeft0.wav";
        level_info[1].sounds.offscreen_left[1] = "OffScreenLeft1.wav";
        level_info[1].sounds.offscreen_left[2] = "OffScreenLeft2.wav";
        level_info[1].sounds.offscreen_left[3] = "OffScreenLeft3.wav";
        level_info[1].sounds.offscreen_right[0] = "OffScreenRight0.wav";
        level_info[1].sounds.offscreen_right[1] = "OffScreenRight1.wav";
        level_info[1].camera.height = 4.276f;
        level_info[1].camera.fov = 43.357f;
        level_info[1].toss_height = 110;
        level_info[2].menu_info.image = "Hard_button.png";
        level_info[2].menu_info.pos = level_info[1].menu_info.pos.minus(new v3f(0.0f, 50.0f));
        level_info[2].score_menu_info.pos = level_info[1].score_menu_info.pos.minus(new v2i(0, 42));
        level_info[2].score_menu_info.size = new v2f(127.0f, 38.0f);
        level_info[2].score_menu_info.score_pos = level_info[1].score_menu_info.score_pos.minus(new v2i(0, 42));
        level_info[2].hud.score_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[2].hud.best_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[2].hud.submit_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[2].hud.menu_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[2].hud.score_pos = new v3f(114.0f, 414.0f, OVER_BG);
        level_info[2].hud.best_pos = new v3f(112.0f, 397.0f, OVER_BG);
        level_info[2].hud.submit_pos = new v3f(114.0f, 380.0f, OVER_BG);
        level_info[2].hud.menu_pos = v3f.iv3f(230.0f, 72.0f, 0.0f);
        level_info[2].basket.image = "CanFront_L3.png";
        level_info[2].basket.pos = new v3f(160.0f, 322.0f, 26.476f);
        level_info[2].basket.distance = 28.476f;
        level_info[2].basket.half_width = 12.0f;
        level_info[2].basket.height_offset = 16.0f;
        level_info[2].basket.base_offset = 6.0f;
        level_info[2].foreground[0].image = "Front_desks_L3.png";
        level_info[2].foreground[0].pos = new v3f(160.0f, 289.5f, 26.576f);
        level_info[2].background_image = "Level3.jpg";
        level_info[2].wind[0][0].image = "Far_fan_Left.png";
        level_info[2].wind[0][0].pos = new v3f(40.0f, 324.0f, 14.238f);
        level_info[2].wind[0][0].size = new v2i(121, 198);
        level_info[2].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[2].wind[0][0].duration = 0.1f;
        level_info[2].wind[1][0].image = "Far_fan_Right.png";
        level_info[2].wind[1][0].pos = new v3f(290.0f, 322.0f, 14.238f);
        level_info[2].wind[1][0].size = new v2i(121, 198);
        level_info[2].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[2].wind[1][0].duration = 0.1f;
        level_info[2].wind_speed.number_pos = new v3f(0.0f, 0.0f, 6.0f);
        level_info[2].wind_speed.arrow_pos = new v3f(0.0f, 0.0f, 4.0f);
        level_info[2].wind_speed.scale = new v2f(2.0f, 2.5f);
        level_info[2].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[2].sounds.loop = "OfficeNoise.mp3";
        level_info[2].sounds.offscreen_left[0] = "OffScreenLeft0.wav";
        level_info[2].sounds.offscreen_left[1] = "OffScreenLeft1.wav";
        level_info[2].sounds.offscreen_left[2] = "OffScreenLeft2.wav";
        level_info[2].sounds.offscreen_left[3] = "OffScreenLeft3.wav";
        level_info[2].sounds.offscreen_right[0] = "OffScreenRight0.wav";
        level_info[2].sounds.offscreen_right[1] = "OffScreenRight1.wav";
        level_info[2].camera.height = 4.827f;
        level_info[2].camera.fov = 43.357f;
        level_info[2].toss_height = 110;
        level_info[3].menu_info.image = "Airport_button.png";
        level_info[3].menu_info.pos = level_info[2].menu_info.pos.minus(new v3f(0.0f, 50.0f));
        level_info[3].score_menu_info.pos = level_info[2].score_menu_info.pos.minus(new v2i(0, 42));
        level_info[3].score_menu_info.size = new v2f(165.0f, 38.0f);
        level_info[3].score_menu_info.score_pos = level_info[2].score_menu_info.score_pos.minus(new v2i(0, 42));
        level_info[3].hud.score_rot = new v3f(0.0f, 0.0f, 2.0f);
        level_info[3].hud.best_rot = new v3f(0.0f, 0.0f, 2.0f);
        level_info[3].hud.submit_rot = new v3f(0.0f, 0.0f, 2.0f);
        level_info[3].hud.menu_rot = new v3f(0.0f, 0.0f, 2.0f);
        level_info[3].hud.score_pos = v3f.iv3f(268.0f, 78.0f, OVER_BG);
        level_info[3].hud.best_pos = v3f.iv3f(268.0f, 98.0f, OVER_BG);
        level_info[3].hud.submit_pos = v3f.iv3f(268.0f, 118.0f, OVER_BG);
        level_info[3].hud.menu_pos = v3f.iv3f(268.0f, 176.0f, 0.0f);
        level_info[3].basket.image = "Airport_Canfront.png";
        level_info[3].basket.pos = v3f.iv3f(160.0f, 314.0f, 2.5742502f);
        level_info[3].basket.distance = 10.099f;
        level_info[3].basket.half_width = 28.0f;
        level_info[3].basket.height_offset = 36.0f;
        level_info[3].basket.base_offset = 20.0f;
        level_info[3].background_image = "Airport.jpg";
        level_info[3].wind[0][0].image = "fan_l1l2_left.png";
        level_info[3].wind[0][0].pos = v3f.iv3f(24.0f, 445.0f, 0.9f);
        level_info[3].wind[0][0].size = new v2i(121, 198);
        level_info[3].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[3].wind[0][0].duration = 0.1f;
        level_info[3].wind[1][0].image = "fan_l1l2_right.png";
        level_info[3].wind[1][0].pos = v3f.iv3f(304.0f, 445.0f, 0.9f);
        level_info[3].wind[1][0].size = new v2i(121, 198);
        level_info[3].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[3].wind[1][0].duration = 0.1f;
        level_info[3].wind_speed.number_pos = new v3f(0.0f, 0.0f, 4.25f);
        level_info[3].wind_speed.arrow_pos = new v3f(0.0f, 0.0f, 3.7f);
        level_info[3].wind_speed.scale = new v2f(0.9f, 0.7f);
        level_info[3].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[3].sounds.loop = "AirportNoise.mp3";
        level_info[3].sounds.offscreen_left[0] = "AirportLeft0.wav";
        level_info[3].sounds.offscreen_left[1] = "AirportLeft1.wav";
        level_info[3].sounds.offscreen_right[0] = "AirportRight0.wav";
        level_info[3].sounds.offscreen_right[1] = "AirportRight1.wav";
        level_info[3].camera.height = 4.471f;
        level_info[3].camera.fov = 45.0f;
        level_info[4].menu_info.image = "Basement_button.png";
        level_info[4].menu_info.pos = level_info[3].menu_info.pos.minus(new v3f(0.0f, 50.0f));
        level_info[4].score_menu_info.pos = level_info[3].score_menu_info.pos.minus(new v2i(0, 42));
        level_info[4].score_menu_info.size = new v2f(196.0f, 38.0f);
        level_info[4].score_menu_info.score_pos = level_info[3].score_menu_info.score_pos.minus(new v2i(0, 42));
        level_info[4].hud.score_rot = new v3f(0.0f, 0.0f, -10.0f);
        level_info[4].hud.best_rot = new v3f(0.0f, 0.0f, -5.5f);
        level_info[4].hud.submit_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[4].hud.menu_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[4].hud.score_pos = new v3f(91.0f, 365.0f, OVER_BG);
        level_info[4].hud.best_pos = new v3f(89.0f, 342.0f, OVER_BG);
        level_info[4].hud.submit_pos = new v3f(91.0f, 321.0f, OVER_BG);
        level_info[4].hud.menu_pos = v3f.iv3f(284.0f, 440.0f, 0.0f);
        level_info[4].basket.image = "canfront_basement.png";
        level_info[4].basket.pos = new v3f(161.0f, 221.0f, 18.645f);
        level_info[4].basket.distance = 19.483f;
        level_info[4].basket.half_width = 18.0f;
        level_info[4].basket.height_offset = 18.0f;
        level_info[4].basket.base_offset = 13.0f;
        level_info[4].foreground[0].image = "Basement_foreground.png";
        level_info[4].foreground[0].pos = new v3f(160.0f, 284.0f, 12.0f);
        level_info[4].wind[0][0].image = "Fan_Bmt_L.png";
        level_info[4].wind[0][0].pos = new v3f(14.0f, 224.0f, 8.0f);
        level_info[4].wind[0][0].size = new v2i(28, 79);
        level_info[4].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[4].wind[0][0].duration = 0.1f;
        level_info[4].wind[1][0].image = "Fan_Bmt_R.png";
        level_info[4].wind[1][0].pos = new v3f(290.0f, 171.0f, 5.0f);
        level_info[4].wind[1][0].size = new v2i(63, 134);
        level_info[4].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[4].wind[1][0].duration = 0.1f;
        level_info[4].wind_speed.number_pos = new v3f(0.0f, 0.0f, 5.5f);
        level_info[4].wind_speed.arrow_pos = new v3f(0.0f, 0.0f, 4.5f);
        level_info[4].wind_speed.scale = new v2f(1.5f, 1.5f);
        level_info[4].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[4].sounds.loop = "BasementAmbient.mp3";
        level_info[4].sounds.offscreen_left[0] = "ryan_urinal.wav";
        level_info[4].sounds.offscreen_left[1] = "justin_worldtour.wav";
        level_info[4].sounds.offscreen_right[0] = "MetalClank.wav";
        level_info[4].sounds.offscreen_right[1] = "Rats.wav";
        level_info[4].background_image = "Basement.png";
        level_info[4].camera.height = 6.037f;
        level_info[4].camera.fov = 43.357f;
        level_info[5].menu_info.image = "Restroom_button.png";
        level_info[5].menu_info.pos = level_info[4].menu_info.pos.minus(new v3f(0.0f, 50.0f));
        level_info[5].score_menu_info.pos = level_info[4].score_menu_info.pos.minus(new v2i(0, 42));
        level_info[5].score_menu_info.size = new v2f(226.0f, 38.0f);
        level_info[5].score_menu_info.score_pos = level_info[4].score_menu_info.score_pos.minus(new v2i(0, 42));
        level_info[5].hud.score_rot = new v3f(0.0f, 0.0f, -1.0f);
        level_info[5].hud.best_rot = new v3f(0.0f, 0.0f, -1.0f);
        level_info[5].hud.submit_rot = new v3f(0.0f, 0.0f, -1.0f);
        level_info[5].hud.menu_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[5].hud.score_pos = new v3f(150.0f, 311.0f, OVER_BG);
        level_info[5].hud.best_pos = new v3f(148.0f, 293.0f, OVER_BG);
        level_info[5].hud.submit_pos = new v3f(148.0f, 274.0f, OVER_BG);
        level_info[5].hud.menu_pos = v3f.iv3f(281.0f, 228.0f, 0.0f);
        level_info[5].basket.image = "CanFront_RR.png";
        level_info[5].basket.pos = new v3f(160.0f, 215.0f, 27.023998f);
        level_info[5].basket.distance = 28.276f;
        level_info[5].basket.half_width = 11.0f;
        level_info[5].basket.height_offset = 15.5f;
        level_info[5].basket.base_offset = 7.9444447f;
        level_info[5].foreground[0].image = "RR_Fground_Rt.png";
        level_info[5].foreground[0].pos = new v3f(276.0f, 233.0f, 12.0f);
        level_info[5].wind[0][0].image = "Fan_RR_L.png";
        level_info[5].wind[0][0].pos = v3f.iv3f(23.5f, 318.5f, OVER_BG);
        level_info[5].wind[0][0].size = new v2i(47, 109);
        level_info[5].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[5].wind[0][0].duration = 0.0f;
        level_info[5].wind[1][0].image = "Fan_RR_R.png";
        level_info[5].wind[1][0].pos = v3f.iv3f(296.0f, 318.5f, 11.0f);
        level_info[5].wind[1][0].size = new v2i(48, 109);
        level_info[5].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[5].wind[1][0].duration = 0.0f;
        level_info[5].wind_speed.number_pos = new v3f(0.0f, -0.3f, 5.5f);
        level_info[5].wind_speed.arrow_pos = new v3f(0.0f, -1.1f, 4.5f);
        level_info[5].wind_speed.scale = new v2f(3.5f, 3.5f);
        level_info[5].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[5].sounds.loop = "Bathroom Background.mp3";
        level_info[5].sounds.offscreen_left[0] = "Washing Hands (LF).wav";
        level_info[5].sounds.offscreen_left[1] = "Urinal Flush (LF).wav";
        level_info[5].sounds.offscreen_left[2] = "Paper Towel Dispensor (LF).wav";
        level_info[5].sounds.offscreen_left[3] = "Cut It Out, I'm Trying to Comb My Hair (LF).wav";
        level_info[5].sounds.offscreen_right[0] = "Hey I'm Trying to Concentrate (RT).wav";
        level_info[5].sounds.offscreen_right[1] = "Why Don't You Throw (RT).wav";
        level_info[5].sounds.offscreen_right[2] = "Toilet Flushing (RT).wav";
        level_info[5].background_image = "restroom.jpg";
        level_info[5].camera.height = 5.973f;
        level_info[5].camera.fov = 45.0f;
    }

    static void initializeDataFreeHiRes() {
        float SCALE_FACTOR = Globals.SCALE_FACTOR;
        menu_info = new MenuInfo();
        menu_info.image = "MMenu.jpg";
        menu_info.score_button.image = "HS_btn_green.png";
        menu_info.score_button.pos = new v2i(68, 343);
        score_menu_info = new ScoreMenuInfo();
        score_menu_info.image = "SBoard_menu.jpg";
        score_menu_info.back_button.pos = new v2i(269, 156);
        score_menu_info.back_button.size = new v2f(103.0f, 42.0f);
        for (int i = 0; i < level_info.length; i++) {
            level_info[i] = new LevelInfo();
            for (int j = 0; j < 2; j++) {
                level_info[i].foreground[j] = new ForegroundInfo();
            }
            for (int j2 = 0; j2 < 2; j2++) {
                for (int k = 0; k < 8; k++) {
                    level_info[i].wind[j2][k] = new WindAnim();
                }
            }
        }
        level_info[0].menu_info.image = "Easy_button.png";
        level_info[0].menu_info.pos = v3f.iv3f(225.0f, 162.0f, 0.0f);
        level_info[0].score_menu_info.pos = new v2i(84, 397);
        level_info[0].score_menu_info.size = new v2f(121.0f, 38.0f);
        level_info[0].score_menu_info.score_pos = new v2i(280, 397);
        level_info[0].hud.score_rot = new v3f(0.0f, 0.0f, -3.0f);
        level_info[0].hud.best_rot = new v3f(0.0f, 0.0f, -3.0f);
        level_info[0].hud.submit_rot = new v3f(0.0f, 0.0f, -3.0f);
        level_info[0].hud.menu_rot = new v3f(0.0f, 0.0f, -3.0f);
        if (Globals.HI_RES) {
            level_info[0].hud.score_pos = new v3f(50.0f, 410.0f, OVER_BG);
            level_info[0].hud.best_pos = new v3f(50.0f, 385.0f, OVER_BG);
            level_info[0].hud.submit_pos = new v3f(50.0f, 360.0f, OVER_BG);
        } else {
            level_info[0].hud.score_pos = new v3f(40.0f, 410.0f, OVER_BG);
            level_info[0].hud.best_pos = new v3f(40.0f, 385.0f, OVER_BG);
            level_info[0].hud.submit_pos = new v3f(40.0f, 360.0f, OVER_BG);
        }
        level_info[0].hud.menu_pos = v3f.iv3f(48.0f, 185.0f, 0.0f);
        level_info[0].basket.image = "CanFront_L1.png";
        level_info[0].basket.pos = new v3f(160.0f, 183.0f, 6.2819996f);
        level_info[0].basket.distance = 8.282f;
        level_info[0].basket.half_width = 37.0f;
        level_info[0].basket.height_offset = 30.0f;
        level_info[0].basket.base_offset = 30.0f;
        level_info[0].foreground[0].image = "Level1_foreground.png";
        level_info[0].foreground[0].pos = new v3f(160.0f - (227.0f * SCALE_FACTOR), 145.0f * SCALE_FACTOR, 1.0f);
        level_info[0].wind[0][0].image = "fan_l1l2_left.png";
        level_info[0].wind[0][0].pos = new v3f(31.0f, 58.0f, 0.9f);
        level_info[0].wind[0][0].size = new v2i(196, 328);
        level_info[0].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[0].wind[0][0].duration = 0.1f;
        level_info[0].wind[1][0].image = "fan_l1l2_right.png";
        level_info[0].wind[1][0].pos = new v3f(292.0f, 66.0f, 0.9f);
        level_info[0].wind[1][0].size = new v2i(196, 328);
        level_info[0].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[0].wind[1][0].duration = 0.1f;
        level_info[0].wind_speed.number_pos = new v3f(0.0f, 0.0f, 4.65f);
        level_info[0].wind_speed.arrow_pos = new v3f(0.0f, 0.0f, 4.25f);
        level_info[0].wind_speed.scale = new v2f(0.6f / SCALE_FACTOR, 0.5f / SCALE_FACTOR);
        level_info[0].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[0].sounds.loop = "OfficeNoise.mp3";
        level_info[0].sounds.offscreen_left[0] = "OffScreenLeft0.wav";
        level_info[0].sounds.offscreen_left[1] = "OffScreenLeft1.wav";
        level_info[0].sounds.offscreen_left[2] = "OffScreenLeft2.wav";
        level_info[0].sounds.offscreen_left[3] = "OffScreenLeft3.wav";
        level_info[0].sounds.offscreen_right[0] = "OffScreenRight0.wav";
        level_info[0].sounds.offscreen_right[1] = "OffScreenRight1.wav";
        level_info[0].background_image = "Level1.jpg";
        level_info[0].camera.height = 4.661f;
        level_info[0].camera.fov = 43.357f;
        level_info[1].menu_info.image = "Med_button.png";
        level_info[1].menu_info.pos = level_info[0].menu_info.pos.minus(new v3f(0.0f, 50.0f));
        level_info[1].score_menu_info.pos = level_info[0].score_menu_info.pos.minus(new v2i(0, 42));
        level_info[1].score_menu_info.size = new v2f(155.0f, 38.0f);
        level_info[1].score_menu_info.score_pos = level_info[0].score_menu_info.score_pos.minus(new v2i(0, 42));
        level_info[1].hud.score_rot = new v3f(0.0f, 0.0f, -4.0f);
        level_info[1].hud.best_rot = new v3f(0.0f, 0.0f, -4.0f);
        level_info[1].hud.submit_rot = new v3f(0.0f, 0.0f, -4.0f);
        level_info[1].hud.menu_rot = new v3f(0.0f, 0.0f, -4.0f);
        level_info[1].hud.score_pos = new v3f(64.0f, 415.0f, OVER_BG);
        level_info[1].hud.best_pos = new v3f(64.0f, 397.0f, OVER_BG);
        level_info[1].hud.submit_pos = new v3f(64.0f, 379.0f, OVER_BG);
        level_info[1].hud.menu_pos = v3f.iv3f(162.0f, 76.0f, 0.0f);
        level_info[1].basket.image = "CanFront_L2.png";
        level_info[1].basket.pos = new v3f(162.0f, 271.0f, 10.625f);
        level_info[1].basket.distance = 12.625f;
        level_info[1].basket.half_width = 24.0f;
        level_info[1].basket.height_offset = 30.0f;
        level_info[1].basket.base_offset = 16.0f;
        level_info[1].foreground[0].image = "Level2_foreground.png";
        level_info[1].foreground[0].pos = new v3f(294.0f - SCALE_FACTOR, 214.0f, 6.0f);
        level_info[1].foreground[1].image = "Level2_Computer.png";
        level_info[1].foreground[1].pos = new v3f(30.0f, 280.0f, 10.625f);
        level_info[1].background_image = "Level2.jpg";
        level_info[1].wind[0][0].image = "fan_l1l2_left.png";
        level_info[1].wind[0][0].pos = new v3f(7.0f, 78.0f, 0.0f);
        level_info[1].wind[0][0].size = new v2i(196, 328);
        level_info[1].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[1].wind[0][0].duration = 0.1f;
        level_info[1].wind[1][0].image = "fan_l1l2_right.png";
        level_info[1].wind[1][0].pos = new v3f(314.0f, 80.0f, 0.0f);
        level_info[1].wind[1][0].size = new v2i(196, 328);
        level_info[1].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[1].wind[1][0].duration = 0.1f;
        level_info[1].wind_speed.number_pos = new v3f(0.0f, 0.0f, 5.0f);
        level_info[1].wind_speed.arrow_pos = new v3f(0.0f, 0.0f, 4.25f);
        level_info[1].wind_speed.scale = new v2f(0.9f / SCALE_FACTOR, 0.9f / SCALE_FACTOR);
        level_info[1].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[1].sounds.loop = "OfficeNoise.mp3";
        level_info[1].sounds.offscreen_left[0] = "OffScreenLeft0.wav";
        level_info[1].sounds.offscreen_left[1] = "OffScreenLeft1.wav";
        level_info[1].sounds.offscreen_left[2] = "OffScreenLeft2.wav";
        level_info[1].sounds.offscreen_left[3] = "OffScreenLeft3.wav";
        level_info[1].sounds.offscreen_right[0] = "OffScreenRight0.wav";
        level_info[1].sounds.offscreen_right[1] = "OffScreenRight1.wav";
        level_info[1].camera.height = 4.276f;
        level_info[1].camera.fov = 43.357f;
        level_info[1].toss_height = 110;
        level_info[2].menu_info.image = "Hard_button.png";
        level_info[2].menu_info.pos = level_info[1].menu_info.pos.minus(new v3f(0.0f, 50.0f));
        level_info[2].score_menu_info.pos = level_info[1].score_menu_info.pos.minus(new v2i(0, 42));
        level_info[2].score_menu_info.size = new v2f(127.0f, 38.0f);
        level_info[2].score_menu_info.score_pos = level_info[1].score_menu_info.score_pos.minus(new v2i(0, 42));
        level_info[2].hud.score_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[2].hud.best_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[2].hud.submit_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[2].hud.menu_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[2].hud.score_pos = new v3f(116.0f, 424.0f, OVER_BG);
        level_info[2].hud.best_pos = new v3f(114.0f, 407.0f, OVER_BG);
        level_info[2].hud.submit_pos = new v3f(116.0f, 390.0f, OVER_BG);
        level_info[2].hud.menu_pos = v3f.iv3f(230.0f, 72.0f, 0.0f);
        level_info[2].basket.image = "CanFront_L3.png";
        level_info[2].basket.pos = new v3f(160.0f, 322.0f, 26.476f);
        level_info[2].basket.distance = 28.476f;
        level_info[2].basket.half_width = 12.0f;
        level_info[2].basket.height_offset = 16.0f;
        level_info[2].basket.base_offset = 6.0f;
        level_info[2].foreground[0].image = "Front_desks_L3.png";
        level_info[2].foreground[0].pos = new v3f(160.0f, 299.0f, 26.576f);
        level_info[2].background_image = "Level3.jpg";
        level_info[2].wind[0][0].image = "Far_fan_Left.png";
        level_info[2].wind[0][0].pos = new v3f(160.0f - (189.0f * SCALE_FACTOR), 585.5f * SCALE_FACTOR, 14.238f);
        level_info[2].wind[0][0].size = new v2i(103, 140);
        level_info[2].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[2].wind[0][0].duration = 0.1f;
        level_info[2].wind[1][0].image = "Far_fan_Right.png";
        level_info[2].wind[1][0].pos = new v3f(293.125f, 357.65625f, 14.238f);
        level_info[2].wind[1][0].size = new v2i(152, 142);
        level_info[2].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[2].wind[1][0].duration = 0.1f;
        level_info[2].wind_speed.number_pos = new v3f(0.0f, 0.0f, 6.0f);
        level_info[2].wind_speed.arrow_pos = new v3f(0.0f, 0.0f, 4.0f);
        level_info[2].wind_speed.scale = new v2f(2.0f / SCALE_FACTOR, 2.5f / SCALE_FACTOR);
        level_info[2].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[2].sounds.loop = "OfficeNoise.mp3";
        level_info[2].sounds.offscreen_left[0] = "OffScreenLeft0.wav";
        level_info[2].sounds.offscreen_left[1] = "OffScreenLeft1.wav";
        level_info[2].sounds.offscreen_left[2] = "OffScreenLeft2.wav";
        level_info[2].sounds.offscreen_left[3] = "OffScreenLeft3.wav";
        level_info[2].sounds.offscreen_right[0] = "OffScreenRight0.wav";
        level_info[2].sounds.offscreen_right[1] = "OffScreenRight1.wav";
        level_info[2].camera.height = 4.827f;
        level_info[2].camera.fov = 43.357f;
        level_info[2].toss_height = 110;
        level_info[3].menu_info.image = "Airport_button.png";
        level_info[3].menu_info.pos = level_info[2].menu_info.pos.minus(new v3f(0.0f, 50.0f));
        level_info[3].score_menu_info.pos = level_info[2].score_menu_info.pos.minus(new v2i(0, 42));
        level_info[3].score_menu_info.size = new v2f(165.0f, 38.0f);
        level_info[3].score_menu_info.score_pos = level_info[2].score_menu_info.score_pos.minus(new v2i(0, 42));
        level_info[3].hud.score_rot = new v3f(0.0f, 0.0f, 2.0f);
        level_info[3].hud.best_rot = new v3f(0.0f, 0.0f, 2.0f);
        level_info[3].hud.submit_rot = new v3f(0.0f, 0.0f, 2.0f);
        level_info[3].hud.menu_rot = new v3f(0.0f, 0.0f, 2.0f);
        level_info[3].hud.score_pos = v3f.iv3f(268.0f, 78.0f, OVER_BG);
        level_info[3].hud.best_pos = v3f.iv3f(268.0f, 98.0f, OVER_BG);
        level_info[3].hud.submit_pos = v3f.iv3f(268.0f, 118.0f, OVER_BG);
        level_info[3].hud.menu_pos = v3f.iv3f(268.0f, 176.0f, 0.0f);
        level_info[3].basket.image = "Airport_Canfront.png";
        level_info[3].basket.pos = v3f.iv3f(160.0f, 314.0f, 2.5742502f);
        level_info[3].basket.distance = 10.099f;
        level_info[3].basket.half_width = 28.0f;
        level_info[3].basket.height_offset = 36.0f;
        level_info[3].basket.base_offset = 20.0f;
        level_info[3].background_image = "Airport.jpg";
        level_info[3].wind[0][0].image = "fan_l1l2_left.png";
        level_info[3].wind[0][0].pos = v3f.iv3f(24.0f, 445.0f, 0.9f);
        level_info[3].wind[0][0].size = new v2i(196, 328);
        level_info[3].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[3].wind[0][0].duration = 0.1f;
        level_info[3].wind[1][0].image = "fan_l1l2_right.png";
        level_info[3].wind[1][0].pos = v3f.iv3f(304.0f, 445.0f, 0.9f);
        level_info[3].wind[1][0].size = new v2i(196, 328);
        level_info[3].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[3].wind[1][0].duration = 0.1f;
        level_info[3].wind_speed.number_pos = new v3f(0.0f, 0.0f, 4.25f);
        level_info[3].wind_speed.arrow_pos = new v3f(0.0f, 0.0f, 3.7f);
        level_info[3].wind_speed.scale = new v2f(0.9f / SCALE_FACTOR, 0.7f / SCALE_FACTOR);
        level_info[3].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[3].sounds.loop = "AirportNoise.mp3";
        level_info[3].sounds.offscreen_left[0] = "AirportLeft0.wav";
        level_info[3].sounds.offscreen_left[1] = "AirportLeft1.wav";
        level_info[3].sounds.offscreen_right[0] = "AirportRight0.wav";
        level_info[3].sounds.offscreen_right[1] = "AirportRight1.wav";
        level_info[3].camera.height = 4.471f;
        level_info[3].camera.fov = 45.0f;
        level_info[4].menu_info.image = "Basement_button.png";
        level_info[4].menu_info.pos = level_info[3].menu_info.pos.minus(new v3f(0.0f, 50.0f));
        level_info[4].score_menu_info.pos = level_info[3].score_menu_info.pos.minus(new v2i(0, 42));
        level_info[4].score_menu_info.size = new v2f(196.0f, 38.0f);
        level_info[4].score_menu_info.score_pos = level_info[3].score_menu_info.score_pos.minus(new v2i(0, 42));
        level_info[4].hud.score_rot = new v3f(0.0f, 0.0f, -10.0f);
        level_info[4].hud.best_rot = new v3f(0.0f, 0.0f, -5.5f);
        level_info[4].hud.submit_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[4].hud.menu_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[4].hud.score_pos = new v3f(91.0f, 365.0f, OVER_BG);
        level_info[4].hud.best_pos = new v3f(89.0f, 342.0f, OVER_BG);
        level_info[4].hud.submit_pos = new v3f(91.0f, 321.0f, OVER_BG);
        level_info[4].hud.menu_pos = v3f.iv3f(284.0f, 440.0f, 0.0f);
        level_info[4].basket.image = "canfront_basement.png";
        level_info[4].basket.pos = new v3f(161.0f, 221.0f, 18.645f);
        level_info[4].basket.distance = 19.483f;
        level_info[4].basket.half_width = 18.0f;
        level_info[4].basket.height_offset = 18.0f;
        level_info[4].basket.base_offset = 13.0f;
        level_info[4].wind[0][0].image = "Fan_Bmt_L.png";
        level_info[4].wind[0][0].pos = new v3f(14.0f, 224.0f, 8.0f);
        level_info[4].wind[0][0].size = new v2i(81, 142);
        level_info[4].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[4].wind[0][0].duration = 0.1f;
        level_info[4].wind[1][0].image = "Fan_Bmt_R.png";
        level_info[4].wind[1][0].pos = new v3f(290.0f, 171.0f, 5.0f);
        level_info[4].wind[1][0].size = new v2i(138, 237);
        level_info[4].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[4].wind[1][0].duration = 0.1f;
        level_info[4].wind_speed.number_pos = new v3f(0.0f, 0.0f, 5.5f);
        level_info[4].wind_speed.arrow_pos = new v3f(0.0f, 0.0f, 4.5f);
        level_info[4].wind_speed.scale = new v2f(1.5f / SCALE_FACTOR, 1.5f / SCALE_FACTOR);
        level_info[4].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[4].sounds.loop = "BasementAmbient.mp3";
        level_info[4].sounds.offscreen_left[0] = "ryan_urinal.wav";
        level_info[4].sounds.offscreen_left[1] = "justin_worldtour.wav";
        level_info[4].sounds.offscreen_right[0] = "MetalClank.wav";
        level_info[4].sounds.offscreen_right[1] = "Rats.wav";
        level_info[4].background_image = "Basement.jpg";
        level_info[4].camera.height = 6.037f;
        level_info[4].camera.fov = 43.357f;
        level_info[5].menu_info.image = "Restroom_button.png";
        level_info[5].menu_info.pos = level_info[4].menu_info.pos.minus(new v3f(0.0f, 50.0f));
        level_info[5].score_menu_info.pos = level_info[4].score_menu_info.pos.minus(new v2i(0, 42));
        level_info[5].score_menu_info.size = new v2f(226.0f, 38.0f);
        level_info[5].score_menu_info.score_pos = level_info[4].score_menu_info.score_pos.minus(new v2i(0, 42));
        level_info[5].hud.score_rot = new v3f(0.0f, 0.0f, -1.0f);
        level_info[5].hud.best_rot = new v3f(0.0f, 0.0f, -1.0f);
        level_info[5].hud.submit_rot = new v3f(0.0f, 0.0f, -1.0f);
        level_info[5].hud.menu_rot = new v3f(0.0f, 0.0f, 0.0f);
        level_info[5].hud.score_pos = new v3f(150.0f, 311.0f, OVER_BG);
        level_info[5].hud.best_pos = new v3f(148.0f, 293.0f, OVER_BG);
        level_info[5].hud.submit_pos = new v3f(148.0f, 274.0f, OVER_BG);
        level_info[5].hud.menu_pos = v3f.iv3f(281.0f, 228.0f, 0.0f);
        level_info[5].basket.image = "CanFront_RR.png";
        level_info[5].basket.pos = new v3f(160.0f, 215.0f - (3.0f * SCALE_FACTOR), 27.023998f);
        level_info[5].basket.distance = 28.276f;
        level_info[5].basket.half_width = 11.0f;
        level_info[5].basket.height_offset = 15.5f;
        level_info[5].basket.base_offset = 7.9444447f;
        level_info[5].foreground[0].image = "RR_Fground_Rt.png";
        level_info[5].foreground[0].pos = new v3f(160.0f + (208.5f * SCALE_FACTOR), 381.0f * SCALE_FACTOR, 12.0f);
        level_info[5].wind[0][0].image = "Fan_RR_L.png";
        level_info[5].wind[0][0].pos = new v3f(22.0f, 147.2f, OVER_BG);
        level_info[5].wind[0][0].size = new v2i(137, 162);
        level_info[5].wind[0][0].scale = new v2f(1.0f, 1.0f);
        level_info[5].wind[0][0].duration = 0.0f;
        level_info[5].wind[1][0].image = "Fan_RR_R.png";
        level_info[5].wind[1][0].pos = v3f.iv3f(296.0f + (4.0f * SCALE_FACTOR), 319.0f + (2.5f * SCALE_FACTOR), 11.0f);
        level_info[5].wind[1][0].size = new v2i(138, 184);
        level_info[5].wind[1][0].scale = new v2f(1.0f, 1.0f);
        level_info[5].wind[1][0].duration = 0.0f;
        level_info[5].wind_speed.number_pos = new v3f(0.0f, -0.3f, 5.5f);
        level_info[5].wind_speed.arrow_pos = new v3f(0.0f, -0.8f, 4.5f);
        level_info[5].wind_speed.scale = new v2f(3.5f / Globals.SCALE_FACTOR, 3.5f / Globals.SCALE_FACTOR);
        level_info[5].wind_speed.color = new v4f(1.0f, 1.0f, 1.0f, 1.0f);
        level_info[5].sounds.loop = "Bathroom Background.mp3";
        level_info[5].sounds.offscreen_left[0] = "Washing Hands (LF).wav";
        level_info[5].sounds.offscreen_left[1] = "Urinal Flush (LF).wav";
        level_info[5].sounds.offscreen_left[2] = "Paper Towel Dispensor (LF).wav";
        level_info[5].sounds.offscreen_left[3] = "Cut It Out, I'm Trying to Comb My Hair (LF).wav";
        level_info[5].sounds.offscreen_right[0] = "Hey I'm Trying to Concentrate (RT).wav";
        level_info[5].sounds.offscreen_right[1] = "Why Don't You Throw (RT).wav";
        level_info[5].sounds.offscreen_right[2] = "Toilet Flushing (RT).wav";
        level_info[5].background_image = "restroom.png";
        level_info[5].camera.height = 5.973f;
        level_info[5].camera.fov = 45.0f;
    }
}
