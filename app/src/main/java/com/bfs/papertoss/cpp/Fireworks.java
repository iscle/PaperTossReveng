package com.bfs.papertoss.cpp;

import com.bfs.papertoss.platform.Evt;
import com.bfs.papertoss.platform.EvtListener;
import com.bfs.papertoss.platform.Random;
import com.bfs.papertoss.vector.v2f;
import com.bfs.papertoss.vector.v3f;
import com.bfs.papertoss.vector.v4f;

/* loaded from: classes.dex */
public class Fireworks {
    static final float FW_DUR = 1.1333333f;
    static final float FW_WAIT_DELAY = 0.25f;
    static final int num_colors = 8;
    static Fireworks m_instance = null;
    static boolean fireworks_active = false;
    static final float FW_NO_WAIT = -100000.0f;
    static final Firework default_firework = new Firework(new v3f(0.0f, 0.0f, 0.0f), new v4f(0.0f, 0.0f, 0.0f, 1.0f), 1.0f, FW_NO_WAIT, null);
    static Firework[] fireworks = {default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, default_firework, new Firework(new v3f(0.0f, 0.0f, 0.0f), new v4f(0.0f, 0.0f, 0.0f, 0.0f), 0.0f, FW_NO_WAIT, null)};
    static v4f[] colors = {new v4f(1.0f, 0.0f, 0.0f, 1.0f), new v4f(1.0f, 1.0f, 0.0f, 1.0f), new v4f(0.0f, 1.0f, 0.0f, 1.0f), new v4f(0.0f, 1.0f, 1.0f, 1.0f), new v4f(0.0f, 0.0f, 1.0f, 1.0f), new v4f(1.0f, 0.0f, 1.0f, 1.0f), new v4f(0.5f, 1.0f, 0.0f, 1.0f), new v4f(1.0f, 1.0f, 1.0f, 1.0f)};
    public StartFireworks startFireworks = new StartFireworks();
    public StopFireworks stopFireworks = new StopFireworks();
    UpdateFireworks updateFireworks = new UpdateFireworks();
    DrawFireworks drawFireworks = new DrawFireworks();

    public class StartFireworks implements EvtListener {
        public StartFireworks() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            if (!Fireworks.fireworks_active) {
                float wait = Fireworks.FW_WAIT_DELAY;
                int i = 0;
                Firework fw = Fireworks.fireworks[0];
                while (fw.scale != 0.0f) {
                    fw.pos = new v3f(Random.randomi(20, 300), Random.randomi(290, 460), 0.0f);
                    fw.color = Fireworks.colors[Random.randomi(0, 7)];
                    fw.scale = Random.randomf(0.75f, 3.0f);
                    fw.wait = wait;
                    wait += Fireworks.FW_WAIT_DELAY;
                    i++;
                    fw = Fireworks.fireworks[i];
                }
                Fireworks.fireworks_active = true;
            }
        }
    }

    public class StopFireworks implements EvtListener {
        public StopFireworks() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            if (Fireworks.fireworks_active) {
                int i = 0;
                Firework fw = Fireworks.fireworks[0];
                while (fw.scale != 0.0f) {
                    fw.wait = Fireworks.FW_NO_WAIT;
                    Sprite.killSprite(fw.sprite);
                    i++;
                    fw = Fireworks.fireworks[i];
                }
                Fireworks.fireworks_active = false;
            }
        }
    }

    public class UpdateFireworks implements EvtListener {
        public UpdateFireworks() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            double elapsed = ((Double) object).doubleValue();
            if (Fireworks.fireworks_active) {
                Fireworks.fireworks_active = false;
                int i = 0;
                Firework fw = Fireworks.fireworks[0];
                while (fw.scale != 0.0f) {
                    if (fw.wait <= 0.0f) {
                        fw.wait = (float) (fw.wait - elapsed);
                        if (Math.abs(fw.wait) >= 0.56666666f) {
                            Sprite.killSprite(fw.sprite);
                            fw.wait = Fireworks.FW_NO_WAIT;
                        } else {
                            fw.sprite.update((float) elapsed);
                        }
                    } else {
                        fw.wait = (float) (fw.wait - elapsed);
                        if (fw.wait <= 0.0f) {
                            fw.wait = 0.0f;
                        }
                    }
                    Fireworks.fireworks_active = (fw.wait != Fireworks.FW_NO_WAIT) | Fireworks.fireworks_active;
                    i++;
                    fw = Fireworks.fireworks[i];
                }
            }
        }
    }

    public class DrawFireworks implements EvtListener {
        public DrawFireworks() {
        }

        @Override // com.bfs.papertoss.platform.EvtListener
        public void run(Object object) {
            if (Fireworks.fireworks_active) {
                int i = 0;
                Firework fw = Fireworks.fireworks[0];
                while (fw.scale != 0.0f) {
                    if (fw.sprite != null) {
                        fw.sprite.draw(fw.pos, new v2f(fw.scale, fw.scale), new v3f(0.0f, 0.0f, 0.0f), fw.color);
                    }
                    i++;
                    fw = Fireworks.fireworks[i];
                }
            }
        }
    }

    public static void initializeFireworks() throws Throwable {
        if (m_instance == null) {
            m_instance = new Fireworks();
        }
        Evt evt = Evt.getInstance();
        evt.subscribe("startFireworks", m_instance.startFireworks);
        evt.subscribe("stopFireworks", m_instance.stopFireworks);
        evt.subscribe("updateFireworks", m_instance.updateFireworks);
        evt.subscribe("drawFireworks", m_instance.drawFireworks);
    }

    public static class Firework {
        v4f color;
        v3f pos;
        float scale;
        Sprite sprite;
        float wait;

        public Firework(v3f a_pos, v4f a_color, float a_scale, float a_wait, Sprite a_sprite) {
            this.pos = new v3f();
            this.color = new v4f();
            this.pos = a_pos;
            this.color = a_color;
            this.scale = a_scale;
            this.wait = a_wait;
            this.sprite = a_sprite;
        }
    }
}
