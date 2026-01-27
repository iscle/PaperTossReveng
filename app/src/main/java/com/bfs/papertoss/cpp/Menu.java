package com.bfs.papertoss.cpp;

import com.bfs.papertoss.PaperTossApplication;
import com.bfs.papertoss.platform.Config;
import com.bfs.papertoss.platform.Evt;
import com.bfs.papertoss.platform.EvtListener;
import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.vector.v2f;
import com.bfs.papertoss.vector.v3f;
import com.bfs.papertoss.vector.v4f;

import java.util.HashMap;

/* loaded from: classes.dex */
public class Menu {
    private static final int ACTIVE = 1;
    static v4f EXIT_COLOR = null;
    static final String EXIT_FILENAME = "Exit.png";
    static v3f EXIT_POS = null;
    static float MENU_POPUP_DELAY = 0.0f;
    static float MENU_POPUP_DUR = 0.0f;
    private static final int NONE = 0;
    static final String SCORE_FONT = "zerothre";
    public static final int SCORE_FONT_SIZE = 14;
    static final int SCORE_GLYPH_OFFSET = -32;
    static v4f SELECTION_COLOR;
    static v4f SOUND_COLOR;
    static String SOUND_FONT;
    static int SOUND_FONT_SIZE;
    static int SOUND_GLYPH_OFFSET;
    static v3f SOUND_POS;
    String m_background_filename;
    int m_destroy_state;
    v4f m_exit_color;
    float m_new_level_timer;
    v4f m_scores_color;
    String m_scores_filename;
    v3f m_scores_pos;
    v4f m_sound_color;
    OnPtrDown onPtrDown;
    OnPtrUp onPtrUp;
    static final float NEW_LEVEL_BLINK_DUR = 1.0f;
    static final v4f GREYED_OUT_COLOR = new v4f(0.33f, 0.33f, 0.33f, NEW_LEVEL_BLINK_DUR);
    static final v4f UNSELECTION_COLOR = new v4f(NEW_LEVEL_BLINK_DUR, NEW_LEVEL_BLINK_DUR, NEW_LEVEL_BLINK_DUR, NEW_LEVEL_BLINK_DUR);
    Sprite[] m_level_button = new Sprite[LevelDefs.NUM_LEVELS];
    Sprite[] m_level_score = new Sprite[LevelDefs.NUM_LEVELS];
    String[] m_level_button_filenames = new String[LevelDefs.NUM_LEVELS];
    String[] m_level_score_filenames = new String[LevelDefs.NUM_LEVELS];
    v4f[] m_level_button_color = new v4f[LevelDefs.NUM_LEVELS];
    v3f[] m_level_button_pos = new v3f[LevelDefs.NUM_LEVELS];
    float[] m_level_button_scale = new float[LevelDefs.NUM_LEVELS];
    float[] m_level_button_delay = new float[LevelDefs.NUM_LEVELS];
    float[] m_level_button_time = new float[LevelDefs.NUM_LEVELS];
    v3f[] m_level_score_pos = new v3f[LevelDefs.NUM_LEVELS];
    Sprite m_background = null;
    Sprite m_scores = null;
    Sprite m_sound = null;
    Sprite m_exit = null;
    int m_state = 0;
    int m_selected_level = -1;
    int m_new_level = -1;
    boolean m_sound_on = false;

    public Menu() {
        this.m_scores_pos = new v3f();
        this.m_scores_color = new v4f();
        this.m_sound_color = new v4f();
        this.m_exit_color = new v4f();
        this.onPtrDown = new OnPtrDown();
        this.onPtrUp = new OnPtrUp();
        this.m_scores_pos = new v3f(0.0f, 0.0f, 0.0f);
        this.m_scores_color = new v4f(NEW_LEVEL_BLINK_DUR, NEW_LEVEL_BLINK_DUR, NEW_LEVEL_BLINK_DUR, NEW_LEVEL_BLINK_DUR);
        this.m_sound_color = SOUND_COLOR;
        this.m_exit_color = EXIT_COLOR;
        EXIT_COLOR = new v4f(NEW_LEVEL_BLINK_DUR, NEW_LEVEL_BLINK_DUR, NEW_LEVEL_BLINK_DUR, NEW_LEVEL_BLINK_DUR);
        SOUND_COLOR = new v4f(NEW_LEVEL_BLINK_DUR, 0.0f, 0.0f, NEW_LEVEL_BLINK_DUR);
        if (Globals.HI_RES) {
            SOUND_POS = new v3f(277.0f, 376.0f);
            SOUND_FONT_SIZE = 18;
            EXIT_POS = new v3f(278.0f, 420.0f);
        } else {
            SOUND_POS = new v3f(283.0f, 376.0f);
            SOUND_FONT_SIZE = 20;
            EXIT_POS = new v3f(283.0f, 455.0f);
        }
        SOUND_GLYPH_OFFSET = -29;
        SOUND_FONT = "fawn";
        SELECTION_COLOR = new v4f(0.6f, 0.6f, 0.7f, NEW_LEVEL_BLINK_DUR);
        MENU_POPUP_DUR = 0.0f;
        MENU_POPUP_DELAY = 0.0f;
        Evt evt = Evt.getInstance();
        evt.subscribe("onPtrDown", this.onPtrDown);
        evt.subscribe("onPtrUp", this.onPtrUp);
        for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
            this.m_level_button[i] = null;
            this.m_level_button_color[i] = UNSELECTION_COLOR;
            this.m_level_button_pos[i] = new v3f(0.0f, 0.0f, 0.0f);
            this.m_level_button_scale[i] = 0.0f;
            this.m_level_button_time[i] = 0.0f;
            this.m_level_button_delay[i] = i * MENU_POPUP_DELAY;
            this.m_level_score[i] = null;
            this.m_level_score_pos[i] = new v3f(0.0f, 0.0f, 0.0f);
        }
    }

    private class OnPtrDown implements EvtListener {
        private OnPtrDown() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            if (Menu.this.m_state == 1) {
                v2f v = (v2f) object;
                Menu.this.m_selected_level = -1;
                int i = 0;
                while (true) {
                    if (i >= LevelDefs.NUM_LEVELS) {
                        break;
                    }
                    if (Menu.this.m_level_button[i] == null || !Menu.this.m_level_button[i].checkPoint(Menu.this.m_level_button_pos[i], v, Menu.NEW_LEVEL_BLINK_DUR)) {
                        i++;
                    } else {
                        Menu.this.m_selected_level = i;
                        break;
                    }
                }
                for (int i2 = 0; i2 < LevelDefs.NUM_LEVELS; i2++) {
                    if (i2 == Menu.this.m_selected_level) {
                        Menu.this.m_level_button_color[i2] = Menu.SELECTION_COLOR;
                    } else {
                        Menu.this.m_level_button_color[i2] = Menu.UNSELECTION_COLOR;
                    }
                }
                Menu.this.m_scores_color = new v4f(Menu.NEW_LEVEL_BLINK_DUR, Menu.NEW_LEVEL_BLINK_DUR, Menu.NEW_LEVEL_BLINK_DUR, Menu.NEW_LEVEL_BLINK_DUR);
                Menu.this.m_sound_color = Menu.SOUND_COLOR;
                if (Menu.this.m_selected_level == -1) {
                    if (Menu.this.m_scores != null && Menu.this.m_scores.checkPoint(Menu.this.m_scores_pos, v, Menu.NEW_LEVEL_BLINK_DUR)) {
                        Menu.this.m_scores_color = new v4f(0.25f, 0.25f, 0.25f, Menu.NEW_LEVEL_BLINK_DUR);
                        return;
                    }
                    if (Menu.this.m_sound != null && Menu.this.m_sound.checkPoint(Menu.SOUND_POS, v, 2.0f)) {
                        Menu.this.m_sound_color = new v4f(0.5f, 0.5f, 0.5f, Menu.NEW_LEVEL_BLINK_DUR).times(Menu.SOUND_COLOR);
                    } else if (Menu.this.m_exit != null && Menu.this.m_exit.checkPoint(Menu.EXIT_POS, v, Menu.NEW_LEVEL_BLINK_DUR)) {
                        Menu.this.m_exit_color = new v4f(0.5f, 0.5f, 0.5f, Menu.NEW_LEVEL_BLINK_DUR).times(Menu.EXIT_COLOR);
                    }
                }
            }
        }
    }

    private class OnPtrUp implements EvtListener {
        private OnPtrUp() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            Evt evt = Evt.getInstance();
            for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
                Menu.this.m_level_button_color[i] = new v4f(Menu.NEW_LEVEL_BLINK_DUR, Menu.NEW_LEVEL_BLINK_DUR, Menu.NEW_LEVEL_BLINK_DUR, Menu.NEW_LEVEL_BLINK_DUR);
            }
            Menu.this.m_scores_color = new v4f(Menu.NEW_LEVEL_BLINK_DUR, Menu.NEW_LEVEL_BLINK_DUR, Menu.NEW_LEVEL_BLINK_DUR, Menu.NEW_LEVEL_BLINK_DUR);
            Menu.this.m_sound_color = Menu.SOUND_COLOR;
            Menu.this.m_exit_color = Menu.EXIT_COLOR;
            if (Menu.this.m_state == 1) {
                v2f v = (v2f) object;
                if (Menu.this.m_selected_level != -1 && Menu.this.m_level_button[Menu.this.m_selected_level] != null && Menu.this.m_level_button[Menu.this.m_selected_level].checkPoint(Menu.this.m_level_button_pos[Menu.this.m_selected_level], v, Menu.NEW_LEVEL_BLINK_DUR)) {
                    evt.publish("paperTossPlaySound", "Crumple.wav");
                    evt.publish("gotoLevel", Integer.valueOf(Menu.this.m_selected_level));
                    if (Menu.this.m_selected_level == Menu.this.m_new_level) {
                        Menu.this.m_new_level = -1;
                    }
                } else if (Menu.this.m_scores != null && Menu.this.m_scores.checkPoint(Menu.this.m_scores_pos, v, Menu.NEW_LEVEL_BLINK_DUR)) {
                    evt.publish("paperTossPlaySound", "Computer.wav");
                    evt.publish("gotoScores");
                } else if (Menu.this.m_sound != null && Menu.this.m_sound.checkPoint(Menu.SOUND_POS, v, 2.0f)) {
                    Menu.this.setSound(Menu.this.m_sound_on ? false : true);
                    evt.publish("setSound", Boolean.valueOf(Menu.this.m_sound_on));
                    evt.publish("paperTossPlaySound", "Crumple.wav");
                } else if (Menu.this.m_exit != null && Menu.this.m_exit.checkPoint(Menu.EXIT_POS, v, Menu.NEW_LEVEL_BLINK_DUR)) {
                    evt.publish("onExitPressed");
                }
                Menu.this.m_selected_level = -1;
            }
        }
    }

    public void setSound(boolean on) {
        this.m_sound_on = on;
        Sprite.killSprite(this.m_sound);
        int i = SOUND_FONT_SIZE;
        int i2 = SOUND_GLYPH_OFFSET;
        String str = SOUND_FONT;
        Object[] objArr = new Object[1];
        objArr[0] = this.m_sound_on ? "on" : "off";
        this.m_sound = new Sprite(i, i2, str, String.format("Sound: %s", objArr), new v4f(NEW_LEVEL_BLINK_DUR, 0.0f, 0.0f, NEW_LEVEL_BLINK_DUR), 0);
    }

    public void setMenuButton(int level, String image_file, v3f pos, v3f score_pos, v4f color) {
        Sprite.killSprite(this.m_level_button[level]);
        this.m_level_button[level] = new Sprite(image_file);
        this.m_level_button_filenames[level] = image_file;
        this.m_level_button_pos[level] = pos;
        this.m_level_button_color[level] = color;
        if (score_pos != null && !score_pos.equalsZero()) {
            this.m_level_score_pos[level] = score_pos;
        }
    }

    void activate() {
        if (this.m_state == 0) {
            this.m_state = 1;
        }
    }

    void deactivate() {
        if (this.m_state == 1) {
            this.m_state = 0;
            for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
                this.m_level_button_scale[i] = 0.0f;
                this.m_level_button_time[i] = 0.0f;
                this.m_level_button_delay[i] = i * MENU_POPUP_DELAY;
            }
        }
    }

    void create(String background, String scores_image, v3f scores_pos, boolean sound) {
        destroy();
        this.m_exit_color = EXIT_COLOR;
        this.m_sound_color = SOUND_COLOR;
        this.m_scores_pos = scores_pos;
        this.m_background_filename = background;
        this.m_background = new Sprite(background);
        this.m_scores_filename = scores_image;
        this.m_scores = new Sprite(scores_image);
        this.m_exit = new Sprite(EXIT_FILENAME);
        setSound(sound);
    }

    void destroy() {
        for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
            Sprite.killSprite(this.m_level_button[i]);
            this.m_level_button[i] = null;
            Sprite.killSprite(this.m_level_score[i]);
            this.m_level_score[i] = null;
        }
        Sprite.killSprite(this.m_background);
        this.m_background = null;
        Sprite.killSprite(this.m_scores);
        this.m_scores = null;
        Sprite.killSprite(this.m_sound);
        this.m_sound = null;
        Sprite.killSprite(this.m_exit);
        this.m_exit = null;
        this.m_destroy_state = this.m_state;
    }

    public void unDestroy() {
        for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
            this.m_level_button[i] = new Sprite(this.m_level_button_filenames[i]);
        }
        this.m_background = new Sprite(this.m_background_filename);
        this.m_scores = new Sprite(this.m_scores_filename);
        setSound(this.m_sound_on);
        this.m_state = this.m_destroy_state;
        this.m_exit = new Sprite(EXIT_FILENAME);
    }

    void setBest(int level, int score) {
        if (!this.m_level_score_pos[level].equalsZero()) {
            Sprite.killSprite(this.m_level_score[level]);
            this.m_level_score[level] = new Sprite(14, SCORE_GLYPH_OFFSET, SCORE_FONT, "" + score, new v4f(0.0f, NEW_LEVEL_BLINK_DUR, 0.0f, NEW_LEVEL_BLINK_DUR), 0);
        }
    }

    void update(double elapsed) {
        float si;
        if (this.m_state == 1) {
            if (this.m_new_level >= 0) {
                if (this.m_new_level != this.m_selected_level) {
                    this.m_new_level_timer = (float) (this.m_new_level_timer - elapsed);
                    while (this.m_new_level_timer <= 0.0f) {
                        this.m_new_level_timer += NEW_LEVEL_BLINK_DUR;
                    }
                    float i = Math.abs((this.m_new_level_timer / NEW_LEVEL_BLINK_DUR) - 0.5f) * 2.0f;
                    this.m_level_button_color[this.m_new_level] = SELECTION_COLOR.times(UNSELECTION_COLOR.minus(SELECTION_COLOR).times(i));
                } else {
                    this.m_new_level_timer = 0.5f;
                }
            }
            for (int i2 = 0; i2 < LevelDefs.NUM_LEVELS; i2++) {
                if (this.m_level_button[i2] != null) {
                    this.m_level_button_time[i2] = (float) (m_level_button_time[i2] + elapsed);
                    if (MENU_POPUP_DUR != 0.0f) {
                        si = Math.max(Math.min((this.m_level_button_time[i2] - this.m_level_button_delay[i2]) / MENU_POPUP_DUR, NEW_LEVEL_BLINK_DUR), 0.0f);
                    } else {
                        si = NEW_LEVEL_BLINK_DUR;
                    }
                    this.m_level_button_scale[i2] = (si - 2.1f) * si * (si - 2.0f);
                }
            }
        }
    }

    void render(v2f offset) {
        v3f o = new v3f(offset.x, offset.y, 0.0f);
        v2f s = new v2f(NEW_LEVEL_BLINK_DUR, NEW_LEVEL_BLINK_DUR);
        v3f r = new v3f(0.0f, 0.0f, 0.0f);
        try {
            this.m_background.draw(new v3f(160.0f, 240.0f, Config.BACKGROUND_DEPTH).plus(o));
            this.m_scores.draw(this.m_scores_pos.plus(o), s, r, this.m_scores_color);
            this.m_sound.draw(SOUND_POS.plus(o), s, r, this.m_sound_color);
            this.m_exit.draw(EXIT_POS.plus(o), s, r, this.m_exit_color);
            for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
                if (this.m_level_button[i] != null) {
                    v2f bs = new v2f(this.m_level_button_scale[i], this.m_level_button_scale[i]);
                    this.m_level_button[i].draw(this.m_level_button_pos[i].plus(o), bs, r, this.m_level_button_color[i]);
                }
                if (this.m_level_score[i] != null) {
                    this.m_level_score[i].draw(this.m_level_score_pos[i].plus(o), s, r, this.m_scores_color);
                }
            }
        } catch (Exception e) {
            HashMap<String, String> map = new HashMap<>();
            map.put("Trace", PaperTossApplication.getFirstLineOfException(e));
        }
    }

    void setNewLevel(int level) {
        this.m_new_level = level;
        this.m_new_level_timer = NEW_LEVEL_BLINK_DUR;
    }
}
