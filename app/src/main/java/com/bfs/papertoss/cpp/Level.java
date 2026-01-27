package com.bfs.papertoss.cpp;

import com.bfs.papertoss.PaperTossApplication;
import com.bfs.papertoss.platform.Evt;
import com.bfs.papertoss.platform.EvtListener;
import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.platform.Random;
import com.bfs.papertoss.platform.Util;
import com.bfs.papertoss.vector.v2f;
import com.bfs.papertoss.vector.v2i;
import com.bfs.papertoss.vector.v3f;
import com.bfs.papertoss.vector.v4f;

import java.lang.reflect.Array;
import java.util.HashMap;

/* loaded from: classes.dex */
public class Level {
    static final float ARROW_OFFSET = 56.0f;
    static final float ARROW_SPEED = 3.0f;
    static final float BALL_CURVE = 240.0f;
    static final float BALL_SCALE_MIN = 0.034883723f;
    static final float BALL_START = 32.0f;
    static final float BALL_TOUCH_SCALE = 2.0f;
    static final float BALL_X_MOD = 0.9f;
    static final float BOUNCE_DUR = 0.125f;
    static final float DEFAULT_TOSS_HEIGHT = 140.0f;
    static final int FIREWORKS_START_SCORE = 3;
    static final String FONT = "fawn";
    static final int FONT_SIZE = 24;
    static final int GLYPH_OFFSET = -29;
    public static final int IN = 1;
    static final float MAX_ANGLE = 75.0f;
    static final float MAX_WIND = 6.0f;
    static final float MIN_FLICK_DIST = 4.0f;
    static final int NO_COLLISION = -10000;
    public static final int NUM_USTATE = 3;
    public static final int OUT = 2;
    static final int UNLOCKED_FONT_SIZE = 60;
    static final int UNLOCKED_OUTLINE = 4;
    static final float UNLOCKED_SCALE = 5.0f;
    public static final int UNONE = 0;
    static final String WIND_FONT = "fawn";
    static final int WIND_FONT_SIZE = 20;
    static final int WIND_GLYPH_OFFSET = -29;
    static final float WIND_POWER = 200.0f;
    static final int WIND_SPEED_OUTLINE = 3;
    int m_active_wind;
    v3f m_ball_dir;
    float m_ball_end;
    float m_ball_mid;
    v3f m_ball_pos;
    float m_ball_x_mod;
    boolean m_bounce;
    State m_destroy_last_state;
    State m_destroy_state;
    Evt m_evt;
    LevelDefs.LevelInfo m_info;
    Anim m_land_anim;
    boolean m_last_shot_best;
    boolean m_swiping;
    float m_wait_time;
    v4f m_wind_color;
    OnLevelUnlocked onLevelUnlocked;
    OnPtrDown onPtrDown;
    OnPtrMove onPtrMove;
    OnPtrUp onPtrUp;
    public static float time_since_fps = 0.0f;
    public static int num_frames = 0;
    static final float MAX_FLICK_TIME = 1.0f;
    private static final v2f DEFAULT_SCALE = new v2f(MAX_FLICK_TIME, MAX_FLICK_TIME);
    private static final v3f DEFAULT_ROT = new v3f(0.0f, 0.0f, 0.0f);
    private static final v4f DEFAULT_COLOR = new v4f(MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME);
    static final v2f MENU_BTN_SIZE = new v2f(48.0f, 48.0f);
    static final v2i SUBMIT_EXT = new v2i(32, 32);
    static final v4f HUD_BUTTON_COLOR = new v4f(MAX_FLICK_TIME, 0.0f, 0.0f, MAX_FLICK_TIME);
    static final float HUD_UPDATE_DUR = 0.5f;
    static final v4f HUD_BUTTON_COLOR_DISABLED = new v4f(HUD_UPDATE_DUR, HUD_UPDATE_DUR, HUD_UPDATE_DUR, MAX_FLICK_TIME);
    static final v4f UNLOCKED_COLOR = new v4f(MAX_FLICK_TIME, MAX_FLICK_TIME, 0.0f, MAX_FLICK_TIME);
    static final float BALL_DUR = 1.5f;
    static final float[] UNLOCKED_DUR = {0.0f, BALL_DUR, BALL_DUR};
    static final v4f HUD_BOARD_COLOR = new v4f(MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME, 0.7f);
    static v4f HUD_TEXT_COLOR = null;
    static int HUD_TEXT_OUTLINE = 0;
    static final v2i[] POP_ANIMATION = {new v2i(-2, 13), new v2i(-3, 27), new v2i(-4, 41), new v2i(-6, 45), new v2i(-7, 49), new v2i(-8, 53), new v2i(-12, 55), new v2i(-16, 57), new v2i(-17, 53), new v2i(-19, 49), new v2i(-21, 45), new v2i(-23, 31), new v2i(-25, 15), new v2i(-26, -2), new v2i(-27, -21), new v2i(-28, -41), new v2i(-28, -61)};
    static final int POP_ANIMATION_COUNT = POP_ANIMATION.length;
    static final v2i[] BOUNCE_ANIMATION = {new v2i(0, 1), new v2i(0, 10), new v2i(0, 18), new v2i(0, 10), new v2i(0, 1)};
    static final int BOUNCE_ANIMATION_COUNT = BOUNCE_ANIMATION.length;
    Sprite[][] m_wind = (Sprite[][]) Array.newInstance((Class<?>) Sprite.class, 2, 8);
    Sprite[] m_foreground = new Sprite[2];
    int[] m_collision = new int[2];
    v2f[][] m_wind_scroll = (v2f[][]) Array.newInstance((Class<?>) v2f.class, 2, 8);
    v2f[][] m_wind_scroll_target = (v2f[][]) Array.newInstance((Class<?>) v2f.class, 2, 8);
    boolean m_is_ortho = true;
    boolean USE_SWIPE = true;
    float CONST_ROT = Float.MAX_VALUE;
    float CONST_SPEED = -1.0f;
    int CONST_DIR = -1;
    Sprite m_background = null;
    Sprite m_basket = null;
    Sprite m_ball = null;
    Sprite m_splash = null;
    Sprite m_tutorial = null;
    Sprite m_arrow = null;
    Sprite m_wind_speed = null;
    Sprite m_wind_arrow = null;
    Sprite m_hud_board = null;
    Sprite m_hud_menu = null;
    Sprite m_hud_score = null;
    Sprite m_hud_score_update = null;
    Sprite m_hud_best = null;
    Sprite m_hud_best_update = null;
    Sprite m_hud_level_unlocked = null;
    v4f m_hud_menu_color = HUD_BUTTON_COLOR;
    float m_ball_time = -1.0f;
    float m_arrow_rot = 0.0f;
    float m_arrow_time = 0.0f;
    float m_wind_accel_rate = MAX_FLICK_TIME;
    State m_state = State.NONE;
    State m_last_state = State.NONE;
    int m_unlocked_state = 0;
    float m_unlocked_time = 0.0f;
    int m_score = 0;
    int m_best = 0;
    float m_swipe_time = 0.0f;
    v2f m_move_last = new v2f(0.0f, 0.0f);
    v2f m_move_accum = new v2f(0.0f, 0.0f);
    int m_move_count = 0;
    float m_hud_update_time = 0.0f;
    int m_offscreen_ct_left = 0;
    int m_offscreen_ct_right = 0;

    private enum State {
        NONE,
        INPUT,
        TOSS,
        LAND,
        SPLASH,
        WAIT
    }

    public void activate() {
        this.m_state = this.m_last_state;
    }

    public void deactivate() {
        this.m_last_state = this.m_state;
        this.m_state = State.NONE;
    }

    static Anim createBounceAnim(v3f pos, float scale) {
        v3f[] animation = new v3f[BOUNCE_ANIMATION_COUNT];
        for (int i = 0; i < BOUNCE_ANIMATION_COUNT; i++) {
            animation[i] = new v3f((BOUNCE_ANIMATION[i].x * scale) + pos.x, (BOUNCE_ANIMATION[i].y * scale) + pos.y, pos.z);
        }
        return new Anim(animation, BOUNCE_ANIMATION_COUNT, BOUNCE_DUR);
    }

    static v2f getPotScale(v2i size, v2i pot_size) {
        return new v2f(size.x / pot_size.x, size.y / pot_size.y);
    }

    static int collision(int axis, int pos, float size) {
        int delta = pos - axis;
        return ((float) Math.abs(delta)) < size / BALL_TOUCH_SCALE ? delta : NO_COLLISION;
    }

    private class OnPtrDown implements EvtListener {
        private OnPtrDown() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            System.gc();
            v2f v = (v2f) object;
            if (Level.this.m_state == State.INPUT) {
                if (Level.this.m_ball != null && Level.this.m_ball.checkPoint(Level.this.m_ball_pos, v, Level.BALL_TOUCH_SCALE)) {
                    if (Level.this.USE_SWIPE) {
                        Level.this.m_swiping = true;
                        Level.this.m_swipe_time = (float) Util.getTime();
                        Level.this.m_move_last = v;
                        Level.this.m_move_accum = new v2f(0.0f, 0.0f);
                        Level.this.m_move_count = 0;
                    } else {
                        Level.this.m_ball_time = 0.0f;
                        Level.this.m_state = State.TOSS;
                    }
                }
                if (Level.this.m_hud_menu != null && Level.this.m_hud_menu.checkPoint(Level.this.m_info.hud.menu_pos, v, Level.BALL_DUR)) {
                    Level.this.m_hud_menu_color = Level.HUD_BUTTON_COLOR.times(Level.HUD_UPDATE_DUR);
                    return;
                } else {
                    Level.this.m_hud_menu_color = Level.HUD_BUTTON_COLOR;
                    return;
                }
            }
            Level.this.m_hud_menu_color = Level.HUD_BUTTON_COLOR;
        }
    }

    private class OnPtrMove implements EvtListener {
        private OnPtrMove() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            if (Level.this.m_state == State.INPUT) {
                if (Level.this.m_move_count == 0) {
                    Level.this.m_swipe_time = (float) Util.getTime();
                }
                v2f v = (v2f) object;
                v2f delta = v.minus(Level.this.m_move_last);
                Level.this.m_move_accum.plusEquals(delta);
                Level.this.m_move_count++;
            }
        }
    }

    private class OnPtrUp implements EvtListener {
        private OnPtrUp() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            v2f dir;
            if (Level.this.m_state != State.NONE) {
                v2f v = (v2f) object;
                if (Level.this.USE_SWIPE && Level.this.m_swiping) {
                    if (Level.this.CONST_ROT == Float.MAX_VALUE) {
                        dir = Level.this.m_move_count != 0 ? Level.this.m_move_accum.dividedBy(Level.this.m_move_count) : new v2f(0.0f, 0.0f);
                    } else {
                        v2f dir2 = new v2f(0.0f, Level.UNLOCKED_SCALE);
                        dir = dir2.rotated((Level.this.CONST_ROT * 3.141592653589793d) / 180.0d);
                    }
                    if (dir.length() > Level.MIN_FLICK_DIST && Util.getTime() - Level.this.m_swipe_time <= 1.0d) {
                        dir.normalize();
                        Level.this.m_ball_time = 0.0f;
                        Level.this.m_arrow_rot = -(Util.degrees(v2f.getNegativeRotation(dir)) + 90.0f);
                        float a = v2f.dot(dir.times(Level.BALL_CURVE), new v2f(0.0f, Level.MAX_FLICK_TIME));
                        Level.this.m_ball_mid = (dir.x < 0.0f ? -1 : 1) * ((float) Math.sqrt(57600.0f - (a * a)));
                        Level.this.m_ball_end = (Level.this.m_active_wind == 0 ? 1 : -1) * ((Level.WIND_POWER * Level.this.m_wind_accel_rate) / Level.MAX_WIND);
                        Level.this.m_state = State.TOSS;
                    }
                    Level.this.m_swiping = false;
                } else if ((Level.this.m_hud_menu != null && Level.this.m_hud_menu.checkPoint(Level.this.m_info.hud.menu_pos, v, Level.BALL_DUR)) || Sprite.pointInRect(v, new v2f(Level.this.m_info.hud.menu_pos.x, Level.this.m_info.hud.menu_pos.y), Level.MENU_BTN_SIZE)) {
                    Level.this.m_evt.publish("paperTossPlaySound", "Crumple.wav");
                    Level.this.m_evt.publish("gotoMenu");
                }
            }
            Level.this.m_hud_menu_color = Level.HUD_BUTTON_COLOR;
        }
    }

    private class OnLevelUnlocked implements EvtListener {
        private OnLevelUnlocked() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            if (Level.this.m_state != State.NONE) {
                Level.this.m_unlocked_time = 0.0f;
                Level.this.m_unlocked_state = 1;
                Level.this.m_evt.publish("paperTossPlaySound", "unlock.wav");
            }
        }
    }

    void setOrtho() {
        Papertoss.sizeGl.run(null);
        this.m_is_ortho = true;
    }

    void setPerspective() {
        float h = 0.1f * ((float) Math.tan(Util.radians(this.m_info.camera.fov * HUD_UPDATE_DUR)));
        float w = h * 0.6666667f;
        Globals.GL.glMatrixMode(5889);
        Globals.GL.glLoadIdentity();
        Globals.GL.glFrustumf(-w, w, -h, h, 0.1f, 450.1f);
        Globals.GL.glMatrixMode(5888);
        Globals.GL.glLoadIdentity();
        Globals.GL.glTranslatef(0.0f, -this.m_info.camera.height, -this.m_info.basket.distance);
        float r = 90.0f - Util.degrees((float) Math.atan(this.m_info.basket.distance / this.m_info.camera.height));
        Globals.GL.glRotatef(r, MAX_FLICK_TIME, 0.0f, 0.0f);
        this.m_is_ortho = false;
    }

    void setWind() {
        if (this.CONST_SPEED >= 0.0f) {
            this.m_wind_accel_rate = this.CONST_SPEED;
        } else {
            this.m_wind_accel_rate = Random.randomf(0.0f, MAX_WIND);
        }
        if (this.CONST_DIR >= 0 && this.CONST_DIR <= 1) {
            this.m_active_wind = this.CONST_DIR;
        } else if (this.CONST_DIR == 2) {
            this.m_active_wind = (this.m_active_wind + 1) % 2;
        } else {
            this.m_active_wind = Random.randomi(0, 1);
        }
        float scroll_mod = this.m_wind_accel_rate / MAX_WIND;
        for (int dir = 0; dir < 2; dir++) {
            for (int a = 0; a < 8 && this.m_wind[dir][a] != null; a++) {
                LevelDefs.WindAnim wi = this.m_info.wind[dir][a];
                v2f target = this.m_wind_scroll_target[dir][a];
                target.x = wi.scroll.x * scroll_mod;
                target.y = wi.scroll.y;
                if (this.m_active_wind == 0) {
                    target.x = -target.x;
                }
            }
        }
        Sprite.killSprite(this.m_wind_speed);
        this.m_wind_color = new v4f(MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME);
        this.m_wind_speed = new Sprite(20, -29, "fawn", String.format("%.2f", Float.valueOf(this.m_wind_accel_rate)), this.m_wind_color, 3);
        Sprite.killSprite(this.m_wind_arrow);
        this.m_wind_arrow = new Sprite("wind_arrow.png");
    }

    void setScore() {
        int new_best = Math.max(this.m_best, this.m_score);
        Sprite score = new Sprite(24, -29, "fawn", "Score " + this.m_score, HUD_TEXT_COLOR, HUD_TEXT_OUTLINE);
        Sprite best = new Sprite(24, -29, "fawn", "Best " + new_best, HUD_TEXT_COLOR, HUD_TEXT_OUTLINE);
        if (this.m_hud_score == null || this.m_score == 0) {
            Sprite.killSprite(this.m_hud_score);
            this.m_hud_score = score;
        } else {
            Sprite.killSprite(this.m_hud_score_update);
            this.m_hud_score_update = score;
            this.m_hud_update_time = 0.0f;
        }
        if (this.m_hud_best == null || this.m_score <= this.m_best) {
            Sprite.killSprite(this.m_hud_best);
            this.m_hud_best = best;
            return;
        }
        Util.ASSERT(this.m_score > this.m_best);
        this.m_evt.publish("setBest", Integer.valueOf(new_best));
        this.m_best = this.m_score;
        Sprite.killSprite(this.m_hud_best_update);
        this.m_hud_best_update = best;
        this.m_hud_update_time = 0.0f;
    }

    float ballScale() {
        return MAX_FLICK_TIME - (this.m_ball_pos.z / (this.m_info.basket.distance + ARROW_SPEED));
    }

    /* JADX WARN: Removed duplicated region for block: B:79:0x03bc  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0408  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0422  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0427  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void doCollision() {
        boolean splash_left;
        boolean splash_right;
        v3f[] animation = new v3f[POP_ANIMATION_COUNT];
        float size = ballScale() * (this.m_ball != null ? this.m_ball.frameSize().x : 86.0f);
        boolean in = this.m_ball_pos.x >= ((float) this.m_collision[0]) && this.m_ball_pos.x <= ((float) this.m_collision[1]);
        boolean pop = this.m_ball_pos.y >= this.m_info.basket.pos.y + this.m_info.basket.height_offset;
        boolean left = this.m_ball_pos.x <= this.m_info.basket.pos.x;
        boolean col = (collision(this.m_collision[0], (int) this.m_ball_pos.x, size) == NO_COLLISION && collision(this.m_collision[1], (int) this.m_ball_pos.x, size) == NO_COLLISION) ? false : true;
        if (col && pop) {
            float s = ballScale() * MIN_FLICK_DIST;
            if ((in && left) || (!in && !left)) {
                for (int i = 0; i < POP_ANIMATION_COUNT; i++) {
                    animation[i] = new v3f((-POP_ANIMATION[i].x) * s, POP_ANIMATION[i].y * s, 0.0f).plus(this.m_ball_pos);
                }
            } else {
                for (int i2 = 0; i2 < POP_ANIMATION_COUNT; i2++) {
                    animation[i2] = new v3f(POP_ANIMATION[i2].x * s, POP_ANIMATION[i2].y * s, 0.0f).plus(this.m_ball_pos);
                }
            }
            this.m_land_anim = new Anim(animation, POP_ANIMATION_COUNT, 0.7f);
            this.m_evt.publish("paperTossPlaySound", in ? "RimIn.wav" : "RimOut.wav");
        } else {
            animation[0] = new v3f(this.m_ball_pos);
            if (in) {
                animation[1] = new v3f(this.m_info.basket.pos);
                animation[1].z = this.m_ball_pos.z;
                this.m_evt.publish("paperTossPlaySound", col ? "BounceIn.wav" : "In.wav");
            } else {
                float y = this.m_info.basket.pos.y - this.m_info.basket.base_offset;
                float dir = col ? MAX_FLICK_TIME : -1.0f;
                float xoff = this.m_ball_dir.x * ((this.m_ball_pos.y - y) / this.m_ball_dir.y) * dir;
                animation[1] = new v3f(this.m_ball_pos.x + xoff, y, this.m_ball_pos.z);
                boolean splash_is_on = this.m_info.splash.range.left > 0 || this.m_info.splash.range.right > 0;
                float distance = this.m_info.basket.pos.x - animation[1].x;
                if (distance <= 0.0f) {
                    splash_left = false;
                    if (distance < 0.0f) {
                        if (((-distance) < ((float) this.m_info.splash.range.right)) == this.m_info.splash.range.inside) {
                            splash_right = true;
                        }
                        if (splash_is_on) {
                            this.m_evt.publish("paperTossPlaySound", "Out.wav");
                        }
                    } else {
                        splash_right = false;
                        if (splash_is_on || (!splash_left && !splash_right)) {
                            this.m_evt.publish("paperTossPlaySound", "Out.wav");
                        }
                    }
                } else {
                    if ((distance < ((float) this.m_info.splash.range.left)) == this.m_info.splash.range.inside) {
                        splash_left = true;
                    }
                    if (distance < 0.0f) {
                    }
                }
            }
            this.m_land_anim = new Anim(animation, 2, 0.16666667f);
        }
        this.m_state = State.LAND;
        this.m_bounce = !in;
        if (in) {
            this.m_score++;
        } else {
            this.m_score = 0;
        }
    }

    public Level() {
        this.m_active_wind = 1;
        this.m_bounce = true;
        this.m_swiping = true;
        this.m_last_shot_best = true;
        this.onPtrDown = new OnPtrDown();
        this.onPtrMove = new OnPtrMove();
        this.onPtrUp = new OnPtrUp();
        this.onLevelUnlocked = new OnLevelUnlocked();
        this.m_active_wind = 0;
        this.m_bounce = false;
        this.m_swiping = false;
        this.m_last_shot_best = false;
        for (int dir = 0; dir < 2; dir++) {
            for (int a = 0; a < 8; a++) {
                this.m_wind[dir][a] = null;
                this.m_wind_scroll[dir][a] = new v2f(0.0f, 0.0f);
                this.m_wind_scroll_target[dir][a] = new v2f(0.0f, 0.0f);
            }
        }
        for (int fg = 0; fg < 2; fg++) {
            this.m_foreground[fg] = null;
        }
        this.m_evt = Evt.getInstance();
        this.m_evt.subscribe("onPtrDown", this.onPtrDown);
        this.m_evt.subscribe("onPtrMove", this.onPtrMove);
        this.m_evt.subscribe("onPtrUp", this.onPtrUp);
        this.m_evt.subscribe("onLevelUnlocked", this.onLevelUnlocked);
        HUD_TEXT_COLOR = new v4f(0.0f, 0.0f, 0.0f, MAX_FLICK_TIME);
        HUD_TEXT_OUTLINE = 0;
    }

    void create(LevelDefs.LevelInfo lvl, int best, boolean submit, int basket) {
        boolean z;
        destroy();
        this.m_ball_time = -1.0f;
        this.m_info = lvl;
        if (Globals.HI_RES) {
            this.m_ball = new Sprite("paper_ball.png", new v2i(141, 141), MAX_FLICK_TIME, false, 16);
        } else {
            this.m_ball = new Sprite("paper_ball.png", new v2i(86, 86), MAX_FLICK_TIME, false, 16);
        }
        this.m_background = new Sprite(lvl.background_image);
        if (lvl.splash.image != null) {
            this.m_splash = new Sprite(lvl.splash.image, lvl.splash.size, lvl.splash.duration);
        }
        for (int fg = 0; fg < 2 && lvl.foreground[fg].image != null; fg++) {
            this.m_foreground[fg] = new Sprite(lvl.foreground[fg].image);
        }
        if (lvl.tutorial_image != null) {
            this.m_tutorial = new Sprite(lvl.tutorial_image);
        }
        if (lvl.hud.image != null) {
            this.m_hud_board = new Sprite(lvl.hud.image);
        }
        for (int dir = 0; dir < 2; dir++) {
            LevelDefs.WindAnim[] anims = lvl.wind[dir];
            for (int a = 0; a < 8 && anims[a].image != null; a++) {
                LevelDefs.WindAnim w = anims[a];
                Sprite[] spriteArr = this.m_wind[dir];
                String str = w.image;
                v2i v2iVar = w.size;
                float f = w.duration;
                if (w.scroll == null) {
                    z = false;
                } else {
                    z = !w.scroll.equalsZero();
                }
                spriteArr[a] = new Sprite(str, v2iVar, f, z, w.frame_count);
            }
        }
        this.m_offscreen_ct_left = 0;
        for (int i = 0; i < 4 && lvl.sounds.offscreen_left[i] != null; i++) {
            this.m_offscreen_ct_left++;
        }
        this.m_offscreen_ct_right = 0;
        for (int i2 = 0; i2 < 4 && lvl.sounds.offscreen_right[i2] != null; i2++) {
            this.m_offscreen_ct_right++;
        }
        this.m_basket = new Sprite(lvl.basket.image, lvl.basket.size);
        setBasket(basket);
        this.m_arrow = new Sprite("arrow.png");
        this.m_hud_level_unlocked = new Sprite(UNLOCKED_FONT_SIZE, -29, "fawn", "Level Unlocked", new v4f(MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME), 4);
        if (lvl.hud.show_menu) {
            this.m_hud_menu = new Sprite(24, -29, "fawn", "Menu", new v4f(MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME), HUD_TEXT_OUTLINE);
        }
        this.m_collision[0] = (int) (this.m_info.basket.pos.x - this.m_info.basket.half_width);
        this.m_collision[1] = (int) (this.m_info.basket.pos.x + this.m_info.basket.half_width);
        this.m_last_shot_best = false;
        this.m_wait_time = 0.0f;
        this.m_score = 0;
        this.m_best = best;
        this.m_ball_pos = new v3f(this.m_info.basket.pos.x, BALL_START, 0.0f);
        this.m_ball_x_mod = MAX_FLICK_TIME;
        this.m_arrow_time = 0.75f;
        this.m_arrow_rot = 0.0f;
        this.m_state = State.NONE;
        this.m_last_state = State.INPUT;
        setScore();
        setWind();
        setOrtho();
    }

    public void destroy() {
        this.m_destroy_state = this.m_state;
        this.m_destroy_last_state = this.m_last_state;
        for (int dir = 0; dir < 2; dir++) {
            for (int a = 0; a < 8; a++) {
                Sprite.killSprite(this.m_wind[dir][a]);
                this.m_wind[dir][a] = null;
                this.m_wind_scroll[dir][a] = new v2f(0.0f, 0.0f);
                this.m_wind_scroll_target[dir][a] = new v2f(0.0f, 0.0f);
            }
        }
        for (int fg = 0; fg < 2; fg++) {
            Sprite.killSprite(this.m_foreground[fg]);
            this.m_foreground[fg] = null;
        }
        Sprite.killSprite(this.m_background);
        this.m_background = null;
        Sprite.killSprite(this.m_basket);
        this.m_basket = null;
        Sprite.killSprite(this.m_ball);
        this.m_ball = null;
        Sprite.killSprite(this.m_splash);
        this.m_splash = null;
        Sprite.killSprite(this.m_tutorial);
        this.m_tutorial = null;
        Sprite.killSprite(this.m_arrow);
        this.m_arrow = null;
        Sprite.killSprite(this.m_wind_speed);
        this.m_wind_speed = null;
        Sprite.killSprite(this.m_wind_arrow);
        this.m_wind_arrow = null;
        Sprite.killSprite(this.m_hud_board);
        this.m_hud_board = null;
        Sprite.killSprite(this.m_hud_menu);
        this.m_hud_menu = null;
        Sprite.killSprite(this.m_hud_score);
        this.m_hud_score = null;
        Sprite.killSprite(this.m_hud_best);
        this.m_hud_best = null;
        Sprite.killSprite(this.m_hud_score_update);
        this.m_hud_score_update = null;
        Sprite.killSprite(this.m_hud_best_update);
        this.m_hud_best_update = null;
        Sprite.killSprite(this.m_hud_level_unlocked);
        this.m_hud_level_unlocked = null;
    }

    public void unDestroy() {
        boolean z;
        try {
            if (this.m_info != null) {
                this.m_state = this.m_destroy_state;
                this.m_last_state = this.m_destroy_last_state;
                for (int dir = 0; dir < 2; dir++) {
                    LevelDefs.WindAnim[] anims = this.m_info.wind[dir];
                    for (int a = 0; a < 8 && anims[a].image != null; a++) {
                        LevelDefs.WindAnim w = anims[a];
                        Sprite[] spriteArr = this.m_wind[dir];
                        String str = w.image;
                        v2i v2iVar = w.size;
                        float f = w.duration;
                        if (w.scroll == null) {
                            z = false;
                        } else {
                            z = !w.scroll.equals(new v2f(0.0f, 0.0f));
                        }
                        spriteArr[a] = new Sprite(str, v2iVar, f, z, w.frame_count);
                    }
                }
                for (int fg = 0; fg < 2 && this.m_info.foreground[fg].image != null; fg++) {
                    this.m_foreground[fg] = new Sprite(this.m_info.foreground[fg].image);
                }
                this.m_background = new Sprite(this.m_info.background_image);
                this.m_basket = new Sprite(this.m_info.basket.image, this.m_info.basket.size);
                if (Globals.HI_RES) {
                    this.m_ball = new Sprite("paper_ball.png", new v2i(141, 141), MAX_FLICK_TIME, false, 16);
                } else {
                    this.m_ball = new Sprite("paper_ball.png", new v2i(86, 86), MAX_FLICK_TIME, false, 16);
                }
                if (this.m_info.splash.image != null) {
                    this.m_splash = new Sprite(this.m_info.splash.image, this.m_info.splash.size, this.m_info.splash.duration);
                }
                if (this.m_info.tutorial_image != null) {
                    this.m_tutorial = new Sprite(this.m_info.tutorial_image);
                }
                this.m_arrow = new Sprite("arrow.png");
                this.m_wind_speed = new Sprite(20, -29, "fawn", String.format("%.2f", Float.valueOf(this.m_wind_accel_rate)), this.m_wind_color, 3);
                this.m_wind_arrow = new Sprite("wind_arrow.png");
                if (this.m_info.hud.image != null) {
                    this.m_hud_board = new Sprite(this.m_info.hud.image);
                }
                if (this.m_info.hud.show_menu) {
                    this.m_hud_menu = new Sprite(24, -29, "fawn", "Menu", new v4f(MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME), HUD_TEXT_OUTLINE);
                }
                setScore();
                this.m_hud_level_unlocked = new Sprite(UNLOCKED_FONT_SIZE, -29, "fawn", "Level Unlocked", new v4f(MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME), 4);
            }
        } catch (Exception e) {
            PaperTossApplication.logErrorWithFlurry("unDestroy_Level", e, "Level");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:135:0x0637  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x063f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void update(float elapsed) {
        boolean splash_left = false;
        boolean splash_right = false;
        float x;
        float i;
        Evt evt = Evt.getInstance();
        for (int dir = 0; dir < 2; dir++) {
            for (int a = 0; a < 8 && this.m_wind[dir][a] != null; a++) {
                v2f scroll = this.m_wind_scroll[dir][a];
                v2f target = this.m_wind_scroll_target[dir][a];
                LevelDefs.WindAnim wi = this.m_info.wind[dir][a];
                Sprite w = this.m_wind[dir][a];
                if (wi.scroll.x != 0.0f) {
                    float speed_delta = Math.abs(wi.scroll.x);
                    if (scroll.x < target.x) {
                        scroll.x = Math.min(scroll.x + (speed_delta * elapsed), target.x);
                    } else if (scroll.x > target.x) {
                        scroll.x = Math.max(scroll.x - (speed_delta * elapsed), target.x);
                    }
                    float speed_delta2 = Math.abs(wi.scroll.x);
                    if (scroll.y < target.y) {
                        scroll.y = Math.min(scroll.y + (speed_delta2 * elapsed), target.y);
                    } else if (scroll.y > target.y) {
                        scroll.y = Math.max(scroll.y - (speed_delta2 * elapsed), target.y);
                    }
                    w.setScroll(scroll);
                } else {
                    w.setScroll(target);
                }
                w.update(elapsed);
            }
        }
        if (this.m_hud_score_update != null || this.m_hud_best_update != null) {
            this.m_hud_update_time += elapsed;
            float score_i = Math.min(this.m_hud_update_time / HUD_UPDATE_DUR, MAX_FLICK_TIME);
            if (score_i == MAX_FLICK_TIME) {
                if (this.m_hud_score_update != null) {
                    Sprite.killSprite(this.m_hud_score);
                    this.m_hud_score = this.m_hud_score_update;
                    this.m_hud_score_update = null;
                }
                if (this.m_hud_best_update != null) {
                    Sprite.killSprite(this.m_hud_best);
                    this.m_hud_best = this.m_hud_best_update;
                    this.m_hud_best_update = null;
                }
            }
        }
        if (this.m_unlocked_state != 0) {
            this.m_unlocked_time += elapsed;
            if (this.m_unlocked_time > UNLOCKED_DUR[this.m_unlocked_state]) {
                this.m_unlocked_time -= UNLOCKED_DUR[this.m_unlocked_state];
                this.m_unlocked_state++;
                if (this.m_unlocked_state > 2) {
                    this.m_unlocked_state = 0;
                }
            }
        }
        switch (this.m_state) {
            case INPUT:
                this.m_ball_x_mod = MAX_FLICK_TIME;
                this.m_arrow_time = (this.m_arrow_time + elapsed) % ARROW_SPEED;
                if (this.CONST_ROT != Float.MAX_VALUE) {
                    this.m_arrow_rot = this.CONST_ROT;
                    return;
                } else {
                    if (!this.USE_SWIPE) {
                        float i2 = ((this.m_arrow_time / ARROW_SPEED) - HUD_UPDATE_DUR) * MIN_FLICK_DIST;
                        this.m_arrow_rot = (i2 < 0.0f ? MAX_FLICK_TIME + i2 : MAX_FLICK_TIME - i2) * MAX_ANGLE;
                        return;
                    }
                    return;
                }
            case TOSS:
                if (this.m_tutorial != null) {
                    Sprite.killSprite(this.m_tutorial);
                    Evt.getInstance().publish("onTutorialShown");
                    this.m_tutorial = null;
                }
                this.m_ball.update(elapsed);
                this.m_ball_time += elapsed;
                float i3 = this.m_ball_time / BALL_DUR;
                if (i3 >= MAX_FLICK_TIME) {
                    i3 = MAX_FLICK_TIME;
                    this.m_ball_time = -1.0f;
                }
                if (i3 < HUD_UPDATE_DUR) {
                    this.m_ball_x_mod = BALL_X_MOD;
                } else {
                    float j = (BALL_TOUCH_SCALE * i3) - MAX_FLICK_TIME;
                    this.m_ball_x_mod = MAX_FLICK_TIME + ((MAX_FLICK_TIME - (j * j)) * (-0.100000024f));
                }
                v3f old_ball_pos = new v3f(this.m_ball_pos);
                boolean wrong_dir = (this.m_ball_mid > 0.0f && this.m_ball_end > 0.0f) || (this.m_ball_mid < 0.0f && this.m_ball_end < 0.0f);
                if (wrong_dir) {
                    x = i3 * (this.m_ball_mid + this.m_ball_end);
                } else if (i3 < HUD_UPDATE_DUR) {
                    float j2 = (BALL_TOUCH_SCALE * i3) - MAX_FLICK_TIME;
                    x = (MAX_FLICK_TIME - (j2 * j2)) * this.m_ball_mid;
                } else {
                    float j3 = (i3 - HUD_UPDATE_DUR) * BALL_TOUCH_SCALE;
                    x = (this.m_ball_mid + this.m_ball_end) - ((MAX_FLICK_TIME - (j3 * j3)) * this.m_ball_end);
                }
                this.m_ball_pos.x = 160.0f + x;
                float basket_y = this.m_info.basket.pos.y + this.m_info.basket.height_offset;
                float toss_height = this.m_info.toss_height != 0 ? this.m_info.toss_height : DEFAULT_TOSS_HEIGHT;
                this.m_ball_pos.z = (this.m_info.basket.distance * i3) + 0.01f;
                if (i3 < HUD_UPDATE_DUR) {
                    i = (i3 - HUD_UPDATE_DUR) * BALL_TOUCH_SCALE;
                    this.m_ball_pos.y = ((MAX_FLICK_TIME - (i * i)) * ((basket_y - BALL_START) + toss_height)) + BALL_START;
                } else {
                    i = (i3 - HUD_UPDATE_DUR) * BALL_TOUCH_SCALE;
                    this.m_ball_pos.y = ((MAX_FLICK_TIME - (i * i)) * toss_height) + basket_y;
                }
                if (i < MAX_FLICK_TIME) {
                    this.m_ball_dir = this.m_ball_pos.minus(old_ball_pos);
                    return;
                } else {
                    doCollision();
                    return;
                }
            case LAND:
                this.m_ball_pos = this.m_land_anim.get(elapsed);
                if (this.m_land_anim.isDone()) {
                    if (this.m_bounce) {
                        this.m_bounce = false;
                        boolean splash_is_on = this.m_info.splash.range.left > 0 || this.m_info.splash.range.right > 0;
                        float distance = this.m_info.basket.pos.x - this.m_ball_pos.x;
                        if (distance <= 0.0f) {
                            splash_left = false;
                        } else if ((distance < ((float) this.m_info.splash.range.left)) == this.m_info.splash.range.inside) {
                            splash_left = true;
                        }
                        if (distance >= 0.0f) {
                            splash_right = false;
                        } else if (((-distance) < ((float) this.m_info.splash.range.right)) == this.m_info.splash.range.inside) {
                            splash_right = true;
                        }
                        boolean on_screen = this.m_ball_pos.x >= -32.0f && this.m_ball_pos.x <= 352.0f;
                        if (splash_is_on && on_screen && (splash_left || splash_right)) {
                            this.m_splash.setFrame(0);
                            this.m_wait_time = this.m_info.splash.duration;
                            this.m_state = State.SPLASH;
                            Evt.getInstance().publish("paperTossPlaySound", this.m_info.sounds.splash);
                            return;
                        }
                        this.m_land_anim = createBounceAnim(this.m_ball_pos, ballScale());
                        return;
                    }
                    this.m_wait_time = HUD_UPDATE_DUR;
                    this.m_state = State.WAIT;
                    Globals.texture_mgr.cleanup();
                    return;
                }
                return;
            case SPLASH:
                this.m_splash.update(elapsed);
                this.m_wait_time -= elapsed;
                if (this.m_wait_time <= 0.0f) {
                    this.m_state = State.WAIT;
                    break;
                } else {
                    return;
                }
            case WAIT:
                break;
            default:
                return;
        }
        this.m_wait_time -= elapsed;
        if (this.m_wait_time <= 0.0f) {
            boolean missed_shot = this.m_score == 0;
            if (!missed_shot) {
                if (this.m_score >= this.m_best) {
                    if (this.m_info.use_fireworks && this.m_score >= 3) {
                        evt.publish("startFireworks");
                    } else {
                        evt.publish("paperTossPlaySound", "Applause.wav");
                    }
                    this.m_last_shot_best = true;
                } else if (this.m_info.use_fireworks) {
                    evt.publish("paperTossPlaySound", "Applause.wav");
                }
            } else if (this.m_last_shot_best) {
                evt.publish("paperTossPlaySound", "Aww.wav");
                this.m_last_shot_best = false;
            } else if (this.m_ball_pos.x < -32.0f) {
                if (this.m_offscreen_ct_left > 0) {
                    evt.publish("paperTossPlaySound", this.m_info.sounds.offscreen_left[Random.randomi(0, this.m_offscreen_ct_left - 1)]);
                }
            } else if (this.m_ball_pos.x > 352.0f && this.m_offscreen_ct_right > 0) {
                evt.publish("paperTossPlaySound", this.m_info.sounds.offscreen_right[Random.randomi(0, this.m_offscreen_ct_right - 1)]);
            }
            this.m_wait_time = 0.0f;
            this.m_ball_pos = new v3f(this.m_info.basket.pos.x, BALL_START, 0.0f);
            setScore();
            setWind();
            this.m_arrow_time = 0.75f;
            this.m_arrow_rot = 0.0f;
            this.m_state = State.INPUT;
            Globals.texture_mgr.cleanup();
        }
    }

    class RenderInfo implements Comparable<RenderInfo> {
        v4f color;
        RenderInfo next;
        boolean ortho;
        v3f pos;
        float priority;
        v3f rot;
        v2f scale;
        Sprite sprite;

        void constructor(Sprite a_sprite, v3f a_pos, v2f a_scale, v3f a_rot, v4f a_color, float a_priority, boolean a_ortho) {
            this.sprite = a_sprite;
            this.pos = a_pos;
            this.scale = a_scale;
            this.rot = a_rot;
            this.color = a_color;
            if (a_priority == -1.0f) {
                a_priority = a_pos.z;
            }
            this.priority = a_priority;
            this.ortho = a_ortho;
            this.next = null;
        }

        RenderInfo(Sprite a_sprite, v3f a_pos, v2f a_scale, v3f a_rot, v4f a_color, float a_priority, boolean a_ortho) {
            constructor(a_sprite, a_pos, a_scale, a_rot, a_color, a_priority, a_ortho);
        }

        RenderInfo(Sprite a_sprite, v3f a_pos, v2f a_scale, v3f a_rot, v4f a_color) {
            constructor(a_sprite, a_pos, a_scale, a_rot, a_color, -1.0f, true);
        }

        RenderInfo(Sprite a_sprite, v3f a_pos, v2f a_scale, v3f a_rot) {
            constructor(a_sprite, a_pos, a_scale, a_rot, Level.DEFAULT_COLOR, -1.0f, true);
        }

        RenderInfo(Sprite a_sprite, v3f a_pos, v2f a_scale) {
            constructor(a_sprite, a_pos, a_scale, Level.DEFAULT_ROT, Level.DEFAULT_COLOR, -1.0f, true);
        }

        RenderInfo(Sprite a_sprite, v3f a_pos) {
            constructor(a_sprite, a_pos, Level.DEFAULT_SCALE, Level.DEFAULT_ROT, Level.DEFAULT_COLOR, -1.0f, true);
        }

        @Override // java.lang.Comparable
        public int compareTo(RenderInfo another) {
            float first = this.priority;
            float second = another.priority;
            if (first < second) {
                return 1;
            }
            if (first > second) {
            }
            return -1;
        }

        public String toString() {
            return String.format("SpriteName=%s pos=(%f,%f,%f) priority=%f", this.sprite.m_texture.m_name, Float.valueOf(this.pos.x), Float.valueOf(this.pos.y), Float.valueOf(this.pos.z), Float.valueOf(this.priority));
        }
    }

    void render(v2f offset) {
        if (this.m_background != null) {
            v3f o = new v3f(offset.x, offset.y, 0.0f);
            v2f s = DEFAULT_SCALE;
            v3f r = DEFAULT_ROT;
            RenderInfoQueue render_queue = new RenderInfoQueue();
            render_queue.add(new RenderInfo(this.m_background, new v3f(160.0f, BALL_CURVE, 449.9f)));
            if (!this.USE_SWIPE) {
                if (this.m_state == State.INPUT) {
                    v2f arrow_dir = new v2f(0.0f, ARROW_OFFSET).rotated(Util.radians(this.m_arrow_rot));
                    v3f arrow_pos = new v3f(arrow_dir.x + this.m_info.basket.pos.x, arrow_dir.y + BALL_START, 0.02f);
                    render_queue.add(new RenderInfo(this.m_arrow, arrow_pos, s, new v3f(0.0f, 0.0f, this.m_arrow_rot)));
                }
            } else {
                v2f arrow_dir2 = new v2f(0.0f, ARROW_OFFSET).rotated(Util.radians(this.m_arrow_rot));
                v3f arrow_pos2 = new v3f(arrow_dir2.x + this.m_info.basket.pos.x, arrow_dir2.y + BALL_START, 0.02f);
                render_queue.add(new RenderInfo(this.m_arrow, arrow_pos2, s, new v3f(0.0f, 0.0f, this.m_arrow_rot)));
            }
            for (int a = 0; a < 8 && this.m_wind[this.m_active_wind][a] != null; a++) {
                LevelDefs.WindAnim wi = this.m_info.wind[this.m_active_wind][a];
                if (wi.scroll.equalsZero()) {
                    render_queue.add(new RenderInfo(this.m_wind[this.m_active_wind][a], wi.pos, wi.scale));
                } else if (wi.pos.z < 449.9f) {
                    v2f scroll = this.m_wind_scroll[this.m_active_wind][a];
                    float alpha_i = wi.scroll.x != 0.0f ? Math.abs(scroll.x) / wi.scroll.x : this.m_wind_accel_rate / MAX_WIND;
                    float alpha = wi.alpha_range.x + ((wi.alpha_range.y - wi.alpha_range.x) * alpha_i);
                    if (wi.scroll.x == 0.0f) {
                        r.z = 60.0f * (this.m_wind_accel_rate / MAX_WIND);
                        if (this.m_active_wind == 1) {
                            r.z = -r.z;
                        }
                    }
                    render_queue.add(new RenderInfo(this.m_wind[this.m_active_wind][a], wi.pos, wi.scale, r, new v4f(MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME, alpha)));
                } else {
                    render_queue.add(new RenderInfo(this.m_wind[this.m_active_wind][a], wi.pos, wi.scale));
                }
            }
            render_queue.add(new RenderInfo(this.m_basket, this.m_info.basket.pos));
            for (int fg = 0; fg < 2 && this.m_foreground[fg] != null; fg++) {
                render_queue.add(new RenderInfo(this.m_foreground[fg], this.m_info.foreground[fg].pos));
            }
            if (this.m_tutorial != null) {
                render_queue.add(new RenderInfo(this.m_tutorial, new v3f(160.0f, BALL_CURVE, 0.0f)));
            }
            if (this.m_hud_board != null) {
                render_queue.add(new RenderInfo(this.m_hud_board, this.m_info.hud.pos, s, r, HUD_BOARD_COLOR));
            }
            if (this.m_hud_level_unlocked != null && this.m_unlocked_state != 0) {
                v3f up = new v3f(160.0f, BALL_CURVE, 0.0f);
                v4f uc = UNLOCKED_COLOR;
                float i = this.m_unlocked_time / UNLOCKED_DUR[this.m_unlocked_state];
                if (this.m_unlocked_state == 1) {
                    float f = ((MAX_FLICK_TIME - i) * UNLOCKED_SCALE) + MAX_FLICK_TIME;
                    s.y = f;
                    s.x = f;
                    r.z = (MAX_FLICK_TIME - i) * (-90.0f);
                    uc.w = i;
                } else {
                    uc.w = MAX_FLICK_TIME - i;
                }
                render_queue.add(new RenderInfo(this.m_hud_level_unlocked, up, s, r, uc));
            }
            if (this.m_hud_score_update != null || this.m_hud_best_update != null) {
                float i2 = Math.min(this.m_hud_update_time / HUD_UPDATE_DUR, MAX_FLICK_TIME);
                float si = ((MAX_FLICK_TIME - i2) * 10.0f) + MAX_FLICK_TIME;
                v2f sv = new v2f(si, si);
                v4f c = new v4f(MAX_FLICK_TIME, MAX_FLICK_TIME, MAX_FLICK_TIME, i2);
                if (this.m_hud_score_update != null) {
                    render_queue.add(new RenderInfo(this.m_hud_score_update, this.m_info.hud.score_pos, sv, this.m_info.hud.score_rot, c));
                }
                if (this.m_hud_best_update != null) {
                    render_queue.add(new RenderInfo(this.m_hud_best_update, this.m_info.hud.best_pos, sv, this.m_info.hud.best_rot, c));
                }
            }
            if (this.m_hud_menu != null) {
                render_queue.add(new RenderInfo(this.m_hud_menu, this.m_info.hud.menu_pos, s, this.m_info.hud.menu_rot, this.m_hud_menu_color));
            }
            render_queue.add(new RenderInfo(this.m_hud_score, this.m_info.hud.score_pos, s, this.m_info.hud.score_rot));
            render_queue.add(new RenderInfo(this.m_hud_best, this.m_info.hud.best_pos, s, this.m_info.hud.best_rot));
            float bs = ballScale();
            v3f ball_pos = new v3f(this.m_ball_pos);
            ball_pos.x = ((ball_pos.x - 160.0f) * this.m_ball_x_mod) + 160.0f;
            if (this.m_state == State.SPLASH) {
                float ss = Math.max(this.m_info.splash.scale * bs, BALL_SCALE_MIN);
                ball_pos.z = 449.88998f;
                render_queue.add(new RenderInfo(this.m_splash, ball_pos, new v2f(ss, ss)));
            } else {
                float ss2 = Math.max(bs, BALL_SCALE_MIN);
                render_queue.add(new RenderInfo(this.m_ball, ball_pos, new v2f(ss2, ss2)));
            }
            if (this.m_state != State.NONE && this.m_wind_speed != null) {
                v2i size = this.m_wind_speed.m_texture.m_text_size;
                v2f scale = new v2f(this.m_info.wind_speed.scale.x / size.x, this.m_info.wind_speed.scale.y / size.y);
                float priority = this.m_info.wind_speed.depth != 0.0f ? this.m_info.wind_speed.depth : this.m_info.basket.pos.z - 0.01f;
                render_queue.add(new RenderInfo(this.m_wind_speed, this.m_info.wind_speed.number_pos, scale, new v3f(-90.0f, 0.0f, 0.0f), this.m_info.wind_speed.color, priority, false));
                render_queue.add(new RenderInfo(this.m_wind_arrow, this.m_info.wind_speed.arrow_pos, scale, new v3f(-90.0f, this.m_active_wind != 0 ? 180.0f : 0.0f, 0.0f), this.m_info.wind_speed.color, priority, false));
            }
            while (render_queue.size() > 0) {
                try {
                    RenderInfo ri = render_queue.poll();
                    if (ri.ortho && !this.m_is_ortho) {
                        setOrtho();
                    } else if (!ri.ortho && this.m_is_ortho) {
                        setPerspective();
                    }
                    v3f shiftedPos = ri.pos.plus(o);
                    ri.pos = shiftedPos;
                    ri.sprite.draw(ri.pos, ri.scale, ri.rot, ri.color);
                } catch (Exception e) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Trace", PaperTossApplication.getFirstLineOfException(e));
                    return;
                }
            }
        }
    }

    void setBasket(int basket) {
        if (this.m_basket != null) {
            this.m_basket.setFrame(basket);
        }
    }
}
