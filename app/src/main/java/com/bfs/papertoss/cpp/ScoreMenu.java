package com.bfs.papertoss.cpp;

import com.bfs.papertoss.PaperTossApplication;
import com.bfs.papertoss.platform.Evt;
import com.bfs.papertoss.platform.EvtListener;
import com.bfs.papertoss.vector.v2f;
import com.bfs.papertoss.vector.v2i;
import com.bfs.papertoss.vector.v3f;
import com.bfs.papertoss.vector.v4f;
import java.util.HashMap;

/* loaded from: classes.dex */
public class ScoreMenu {
    static v4f SCORE_COLOR = null;
    static final String SCORE_FONT = "zerothre";
    static final int SCORE_FONT_SIZE = 24;
    static final int SCORE_GLYPH_OFFSET = -32;
    private String m_background_filename;
    private final int NONE = 0;
    private final int ACTIVE = 1;
    Sprite[] m_score = new Sprite[LevelDefs.NUM_LEVELS];
    v2i m_back_pos = new v2i();
    v2f m_back_size = new v2f();
    v2i[] m_name_pos = new v2i[LevelDefs.NUM_LEVELS];
    v2f[] m_name_size = new v2f[LevelDefs.NUM_LEVELS];
    v2i[] m_score_pos = new v2i[LevelDefs.NUM_LEVELS];
    OnPtrUp onPtrUp = new OnPtrUp();
    Sprite m_background = null;
    int m_state = 0;

    void activate() {
        if (this.m_state == 0) {
            this.m_state = 1;
        }
    }

    void deactivate() {
        if (this.m_state == 1) {
            this.m_state = 0;
        }
    }

    private class OnPtrUp implements EvtListener {
        private OnPtrUp() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object o) {
            if (ScoreMenu.this.m_state == 1) {
                v2f v = (v2f) o;
                Evt evt = Evt.getInstance();
                if (Sprite.pointInRect(v, new v2f(ScoreMenu.this.m_back_pos), ScoreMenu.this.m_back_size)) {
                    evt.publish("paperTossPlaySound", "Computer.wav");
                    evt.publish("gotoMenu");
                    return;
                }
                for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
                    if (!ScoreMenu.this.m_name_pos[i].equalsZero() && Sprite.pointInRect(v, new v2f(ScoreMenu.this.m_name_pos[i]), ScoreMenu.this.m_name_size[i])) {
                        evt.publish("paperTossPlaySound", "Computer.wav");
                        evt.publish("showScores", Integer.valueOf(i));
                        return;
                    }
                }
            }
        }
    }

    public ScoreMenu() throws Throwable {
        SCORE_COLOR = new v4f(0.0f, 1.0f, 0.0f, 1.0f);
        Evt evt = Evt.getInstance();
        evt.subscribe("onPtrUp", this.onPtrUp);
        for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
            this.m_score[i] = null;
            this.m_name_pos[i] = new v2i(0, 0);
            this.m_name_size[i] = new v2f(0.0f, 0.0f);
            this.m_score_pos[i] = new v2i(0, 0);
        }
    }

    void setLevel(int level, v2i pos, v2f size, v2i score_pos) {
        this.m_name_pos[level] = pos;
        this.m_name_size[level] = size;
        this.m_score_pos[level] = score_pos;
    }

    void setBest(int level, int score) {
        if (!this.m_score_pos[level].equals(new v3f(0.0f, 0.0f, 0.0f))) {
            Sprite.killSprite(this.m_score[level]);
            this.m_score[level] = new Sprite(24, SCORE_GLYPH_OFFSET, SCORE_FONT, "" + score, SCORE_COLOR, 0);
        }
    }

    void create(String background, v2i back_pos, v2f back_size) {
        this.m_background = new Sprite(background, new v2i(), 0.0f, false, 0);
        this.m_background_filename = background;
        this.m_back_pos = back_pos;
        this.m_back_size = back_size;
    }

    void destroy() {
        Sprite.killSprite(this.m_background);
        this.m_background = null;
        for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
            Sprite.killSprite(this.m_score[i]);
            this.m_score[i] = null;
        }
    }

    public void unDestroy() {
        this.m_background = new Sprite(this.m_background_filename);
        for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
            this.m_score[i] = new Sprite(24, SCORE_GLYPH_OFFSET, SCORE_FONT, "" + Scores.readBest(i), SCORE_COLOR, 0);
        }
    }

    void update(float elapsed) {
    }

    void render(v2f offset) {
        try {
            v3f o = new v3f(offset.x, offset.y, 0.0f);
            this.m_background.draw(new v3f(160.0f, 240.0f, 449.9f).plus(o), new v2f(1.0f, 1.0f), new v3f(0.0f, 0.0f, 0.0f), new v4f(1.0f, 1.0f, 1.0f, 1.0f));
            for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
                if (this.m_score[i] != null) {
                    int x = (int) o.x;
                    int y = (int) o.y;
                    v2i vi = this.m_score_pos[i].plus(new v2i(x, y));
                    v3f vf = new v3f(vi.x, vi.y);
                    this.m_score[i].draw(vf, new v2f(1.0f, 1.0f), new v3f(0.0f, 0.0f, 0.0f), new v4f(1.0f, 1.0f, 1.0f, 1.0f));
                }
            }
        } catch (Exception e) {
            HashMap<String, String> map = new HashMap<>();
            map.put("Trace", PaperTossApplication.getFirstLineOfException(e));
        }
    }
}
