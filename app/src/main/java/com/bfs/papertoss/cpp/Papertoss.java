package com.bfs.papertoss.cpp;

import com.bfs.papertoss.cpp.LevelDefs;
import com.bfs.papertoss.platform.Evt;
import com.bfs.papertoss.platform.EvtListener;
import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.platform.SaveData;
import com.bfs.papertoss.platform.Util;
import com.bfs.papertoss.vector.v2f;
import com.bfs.papertoss.vector.v3f;
import com.millennialmedia.android.R;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
public class Papertoss {
    static final float TRANSITION_SPEED = 960.0f;
    static GotoLevel gotoLevel;
    static GotoMenu gotoMenu;
    static GotoScores gotoScores;
    static OnTutorialShown onTutorialShown;
    static PostScore postScore;
    static SetBest setBest;
    static SetSound setSound;
    private static boolean UNLOCK_ALL = false;
    static Menu menu = null;
    static ScoreMenu score_menu = null;
    public static Level level = null;
    public static GameState state = GameState.MENU;
    static int current_level = -1;
    static v2f offset = new v2f(0.0f, 0.0f);
    static int highest_level = 0;
    public static boolean m_shutdown = false;
    static final float[] quad_verts = {-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f};
    static LevelDefs.LevelInfo[] level_info = LevelDefs.level_info;
    public static SizeGL sizeGl = new SizeGL();

    public enum GameState {
        MENU,
        MENU_TO_SCORE,
        SCORE,
        SCORE_TO_MENU,
        MENU_TO_LEVEL,
        LEVEL,
        LEVEL_TO_MENU
    }

    static {
        AnonymousClass1 anonymousClass1 = null;
        setBest = new SetBest(anonymousClass1);
        setSound = new SetSound(anonymousClass1);
        postScore = new PostScore(anonymousClass1);
        onTutorialShown = new OnTutorialShown(anonymousClass1);
        gotoScores = new GotoScores(anonymousClass1);
        gotoMenu = new GotoMenu(anonymousClass1);
        gotoLevel = new GotoLevel(anonymousClass1);
    }

    static void unlockLevel(int i) {
        if (i < LevelDefs.NUM_LEVELS) {
            menu.setMenuButton(i, LevelDefs.level_info[i].menu_info.image, LevelDefs.level_info[i].menu_info.pos, LevelDefs.level_info[i].menu_info.score_pos, Menu.UNSELECTION_COLOR);
            highest_level = Math.max(highest_level, i);
        }
    }

    private static class SetBest implements EvtListener {
        private SetBest() {
        }

        /* synthetic */ SetBest(AnonymousClass1 x0) {
            this();
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object o) throws NoSuchAlgorithmException, IOException {
            int score = ((Integer) o).intValue();
            Papertoss.menu.setBest(Papertoss.current_level, score);
            Papertoss.score_menu.setBest(Papertoss.current_level, score);
            Scores.saveBest(score, Papertoss.current_level);
            if (score == Papertoss.level_info[Papertoss.current_level].menu_info.score_to_unlock_next) {
                int new_level = Papertoss.current_level + 1;
                Papertoss.unlockLevel(new_level);
                Papertoss.menu.setNewLevel(new_level);
                LevelDefs.ScoreMenuLevelInfo si = Papertoss.level_info[new_level].score_menu_info;
                Papertoss.score_menu.setLevel(new_level, si.pos, si.size, si.score_pos);
                Papertoss.score_menu.setBest(new_level, 0);
                Evt.getInstance().publish("onLevelUnlocked");
                int basket = (Papertoss.highest_level - Papertoss.current_level) + 1;
                Papertoss.level.setBasket(basket);
            }
        }
    }

    private static class SetSound implements EvtListener {
        private SetSound() {
        }

        /* synthetic */ SetSound(AnonymousClass1 x0) {
            this();
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) throws IOException {
            boolean on = ((Boolean) object).booleanValue();
            SaveData.write(Boolean.valueOf(on), "sound");
            SaveData.save();
        }
    }

    private static class PostScore implements EvtListener {
        private PostScore() {
        }

        /* synthetic */ PostScore(AnonymousClass1 x0) {
            this();
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) throws IOException {
            Scores.saveSubmitted(Scores.readBest(Papertoss.current_level), Papertoss.current_level);
        }
    }

    private static class OnTutorialShown implements EvtListener {
        private OnTutorialShown() {
        }

        /* synthetic */ OnTutorialShown(AnonymousClass1 x0) {
            this();
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) throws IOException {
            SaveData.write(true, "tutorial_shown");
            SaveData.save();
            Papertoss.level_info[0].tutorial_image = null;
        }
    }

    private static class GotoScores implements EvtListener {
        private GotoScores() {
        }

        /* synthetic */ GotoScores(AnonymousClass1 x0) {
            this();
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            if (Papertoss.state == GameState.MENU) {
                Papertoss.state = GameState.MENU_TO_SCORE;
                Papertoss.offset = new v2f(0.0f, -1.0f);
                Papertoss.menu.deactivate();
            }
        }
    }

    private static class GotoMenu implements EvtListener {
        private GotoMenu() {
        }

        /* synthetic */ GotoMenu(AnonymousClass1 x0) {
            this();
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            Evt evt = Evt.getInstance();
            evt.publish("setBackgroundSound", "OfficeNoise.mp3");
            if (Papertoss.state == GameState.LEVEL || Papertoss.state == GameState.SCORE) {
                if (Papertoss.state == GameState.LEVEL) {
                    Papertoss.level.deactivate();
                    Papertoss.state = GameState.LEVEL_TO_MENU;
                } else if (Papertoss.state == GameState.SCORE) {
                    Papertoss.score_menu.deactivate();
                    Papertoss.state = GameState.SCORE_TO_MENU;
                }
                Papertoss.offset = new v2f(-320.0f, -1.0f);
                if (Globals.HI_RES) {
                    Papertoss.offset = new v2f(-294.25f, -1.0f);
                }
            }
        }
    }

    private static class GotoLevel implements EvtListener {
        private GotoLevel() {
        }

        /* synthetic */ GotoLevel(AnonymousClass1 x0) {
            this();
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            int lvl = ((Integer) object).intValue();
            if (Papertoss.state == GameState.MENU) {
                Papertoss.state = GameState.MENU_TO_LEVEL;
                Papertoss.offset = new v2f(0.0f, -1.0f);
                Papertoss.menu.deactivate();
                if (lvl != Papertoss.current_level) {
                    if (Papertoss.current_level != -1) {
                        Papertoss.level.destroy();
                    }
                    Papertoss.current_level = lvl;
                    String.format("level_%d", Integer.valueOf(Papertoss.current_level));
                    int best = Scores.readBest(Papertoss.current_level);
                    int submitted = Scores.readSubmitted(Papertoss.current_level);
                    int basket = (Papertoss.highest_level - Papertoss.current_level) + 1;
                    Papertoss.level.create(Papertoss.level_info[Papertoss.current_level], best, best > submitted, basket);
                }
                Evt.getInstance().publish("setBackgroundSound", Papertoss.level_info[lvl].sounds.loop);
            }
        }
    }

    public static void update(double elapsed) {
        if (!m_shutdown) {
            switch (AnonymousClass1.$SwitchMap$com$bfs$papertoss$cpp$Papertoss$GameState[state.ordinal()]) {
                case 1:
                    menu.update(elapsed);
                    break;
                case 2:
                    level.update((float) elapsed);
                    break;
                case 3:
                    score_menu.update((float) elapsed);
                    break;
                case 4:
                case 5:
                    if (offset.y == -1.0f) {
                        offset.y = 0.0f;
                    } else {
                        offset.x = (float) (r0.x - (960.0d * elapsed));
                    }
                    if ((offset.x <= -320.0f && !Globals.HI_RES) || (offset.x <= -294.25f && Globals.HI_RES)) {
                        if (state == GameState.MENU_TO_SCORE) {
                            state = GameState.SCORE;
                            Globals.texture_mgr.cleanup();
                            score_menu.activate();
                            break;
                        } else if (state == GameState.MENU_TO_LEVEL) {
                            state = GameState.LEVEL;
                            Globals.texture_mgr.cleanup();
                            level.activate();
                            break;
                        }
                    }
                    break;
                case 6:
                case R.styleable.MMAdView_gender /* 7 */:
                    if (offset.y == -1.0f) {
                        offset.y = 0.0f;
                    } else {
                        offset.x = (float) (r0.x + (960.0d * elapsed));
                    }
                    if (offset.x >= 0.0f) {
                        state = GameState.MENU;
                        Globals.texture_mgr.cleanup();
                        menu.activate();
                        break;
                    }
                    break;
            }
        }
    }

    /* renamed from: com.bfs.papertoss.cpp.Papertoss$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$bfs$papertoss$cpp$Papertoss$GameState = new int[GameState.values().length];

        static {
            try {
                $SwitchMap$com$bfs$papertoss$cpp$Papertoss$GameState[GameState.MENU.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$bfs$papertoss$cpp$Papertoss$GameState[GameState.LEVEL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$bfs$papertoss$cpp$Papertoss$GameState[GameState.SCORE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$bfs$papertoss$cpp$Papertoss$GameState[GameState.MENU_TO_SCORE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$bfs$papertoss$cpp$Papertoss$GameState[GameState.MENU_TO_LEVEL.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$bfs$papertoss$cpp$Papertoss$GameState[GameState.LEVEL_TO_MENU.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$bfs$papertoss$cpp$Papertoss$GameState[GameState.SCORE_TO_MENU.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static void render() {
        if (!m_shutdown) {
            switch (AnonymousClass1.$SwitchMap$com$bfs$papertoss$cpp$Papertoss$GameState[state.ordinal()]) {
                case 1:
                    menu.render(new v2f(0.0f, 0.0f));
                    break;
                case 2:
                    level.render(new v2f(0.0f, 0.0f));
                    break;
                case 3:
                    score_menu.render(new v2f(0.0f, 0.0f));
                    break;
                case 4:
                case R.styleable.MMAdView_gender /* 7 */:
                    float ortho_width = 320.0f;
                    if (Globals.HI_RES) {
                        ortho_width = 294.25287f;
                    }
                    menu.render(offset);
                    score_menu.render(offset.plus(new v2f(ortho_width, 0.0f)));
                    break;
                case 5:
                case 6:
                    float ortho_width2 = 320.0f;
                    if (Globals.HI_RES) {
                        ortho_width2 = 294.25287f;
                    }
                    menu.render(offset);
                    level.render(offset.plus(new v2f(ortho_width2, 0.0f)));
                    break;
            }
        }
    }

    public static void shutdown() {
        m_shutdown = true;
        if (level != null) {
            level.destroy();
        }
        if (score_menu != null) {
            score_menu.destroy();
        }
        if (menu != null) {
            menu.destroy();
        }
        Globals.texture_mgr.cleanup();
    }

    public static void unShutdown() {
        if (m_shutdown) {
            if (menu != null) {
                menu.unDestroy();
            }
            if (score_menu != null) {
                score_menu.unDestroy();
            }
            if (level != null) {
                level.unDestroy();
            }
            m_shutdown = false;
        }
    }

    public static class SizeGL implements EvtListener {
        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            Globals.GL.glMatrixMode(5889);
            Globals.GL.glLoadIdentity();
            if (Globals.HI_RES) {
                Globals.GL.glOrthof(12.873565f, 307.12643f, 0.0f, 480.0f, 0.0f, 450.0f);
            } else {
                Globals.GL.glOrthof(0.0f, 320.0f, 0.0f, 480.0f, 0.0f, 450.0f);
            }
            Globals.GL.glMatrixMode(5888);
            Globals.GL.glLoadIdentity();
        }
    }

    public static boolean initialize() throws Throwable {
        Globals.GL.glCullFace(1029);
        Globals.GL.glPointSize(5.0f);
        Globals.GL.glEnable(3042);
        Globals.GL.glBlendFunc(770, 771);
        Globals.GL.glPixelStorei(3317, 1);
        Globals.GL.glEnable(3553);
        Globals.GL.glEnableClientState(32884);
        Globals.GL.glEnableClientState(32888);
        Globals.GL.glDisable(2884);
        Globals.GL.glDisable(2929);
        Globals.GL.glLoadIdentity();
        Globals.GL.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Globals.GL.glClearColor(0.9f, 0.0f, 0.0f, 1.0f);
        Globals.GL.glClear(16640);
        Globals.GL.glVertexPointer(2, 5126, 0, Util.getFloatBufferFromFloatArray(quad_verts));
        sizeGl.run(0);
        Util.ASSERT(Util.checkGL());
        if (menu == null) {
            Evt evt = Evt.getInstance();
            evt.subscribe("sizeGl", sizeGl);
            evt.subscribe("postScore", postScore);
            evt.subscribe("setBest", setBest);
            evt.subscribe("setSound", setSound);
            evt.subscribe("gotoLevel", gotoLevel);
            evt.subscribe("gotoScores", gotoScores);
            evt.subscribe("gotoMenu", gotoMenu);
            evt.subscribe("onTutorialShown", onTutorialShown);
            menu = new Menu();
            score_menu = new ScoreMenu();
            SaveData.load();
            Scores.init();
            LevelDefs.initializeData();
            level_info = LevelDefs.level_info;
            if (!SaveData.read(false, "tutorial_shown")) {
                level_info[0].tutorial_image = "tutorial.png";
            }
            boolean sound_on = SaveData.read(true, "sound");
            evt.publish("setSound", Boolean.valueOf(sound_on));
            LevelDefs.MenuInfo menu_info = LevelDefs.menu_info;
            v3f pos = new v3f(menu_info.score_button.pos.x, menu_info.score_button.pos.y);
            menu.create(menu_info.image, menu_info.score_button.image, pos, sound_on);
            LevelDefs.ScoreMenuInfo score_menu_info = LevelDefs.score_menu_info;
            score_menu.create(score_menu_info.image, score_menu_info.back_button.pos, score_menu_info.back_button.size);
            for (int i = 0; i < LevelDefs.NUM_LEVELS; i++) {
                unlockLevel(i);
                int best = Scores.readBest(i);
                menu.setBest(i, best);
                LevelDefs.ScoreMenuLevelInfo si = level_info[i].score_menu_info;
                score_menu.setLevel(i, si.pos, si.size, si.score_pos);
                score_menu.setBest(i, best);
            }
            level = new Level();
            evt.publish("setBackgroundSound", "OfficeNoise.mp3");
        }
        return true;
    }

    public static void activate() {
        if (menu != null) {
            menu.activate();
        }
    }

    public static void deactiviate() {
    }

    public static int getBestScore() {
        return Scores.readBest(current_level);
    }

    public static int getLevel() {
        return current_level;
    }

    public static void setSound(boolean sound) {
        menu.setSound(sound);
    }

    public static boolean getSound() {
        return menu != null ? menu.m_sound_on : SaveData.read(true, "sound");
    }
}
