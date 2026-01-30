package com.bfs.papertoss.cpp;

import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.platform.Util;
import com.bfs.papertoss.vector.v2f;
import com.bfs.papertoss.vector.v2i;
import com.bfs.papertoss.vector.v3f;
import com.bfs.papertoss.vector.v4f;

import java.nio.FloatBuffer;

public class Sprite {
    FloatBuffer[] m_buffers;
    int m_current_frame;
    float m_duration;
    float m_elapsed;
    int m_frame_count;
    v2f m_frame_size;
    v2f m_scroll;
    Texture m_texture;
    boolean m_tile;
    private static final v2f DEFAULT_SCALE = new v2f(1.0f, 1.0f);
    private static final v3f DEFAULT_ROT = new v3f(0.0f, 0.0f, 0.0f);
    private static final v4f DEFAULT_COLOR = new v4f(1.0f, 1.0f, 1.0f, 1.0f);

    public static void killSprite(Sprite s) {
        if (s != null) {
            s.delete();
        }
    }

    private void constructor(String texture_name, v2i frame_size, float duration, boolean tile, int frame_count) {
        this.m_texture = null;
        this.m_frame_count = 0;
        this.m_frame_size = new v2f(0.0f, 0.0f);
        this.m_current_frame = 0;
        if (tile) {
            duration = 0.0f;
        }
        this.m_duration = duration;
        this.m_elapsed = 0.0f;
        this.m_scroll = new v2f(0.0f, 0.0f);
        this.m_tile = tile;
        this.m_texture = Globals.texture_mgr.get(texture_name);
        v2i size = this.m_texture.size();
        v2i pot_size = this.m_texture.potSize();
        this.m_frame_size = frame_size.equalsZero() ? new v2f(size.x, size.y) : new v2f(frame_size.x, frame_size.y);
        int col_count = Math.max(size.x / ((int) this.m_frame_size.x), 1);
        int row_count = Math.max(size.y / ((int) this.m_frame_size.y), 1);
        if (this.m_tile) {
            frame_count = 1;
        } else if (frame_count <= 0) {
            frame_count = col_count * row_count;
        }
        this.m_frame_count = frame_count;
        this.m_buffers = new FloatBuffer[this.m_frame_count];
        for (int i = 0; i < this.m_frame_count; i++) {
            int row = i / col_count;
            int col = i % col_count;
            float x_lo = (col * this.m_frame_size.x) / pot_size.x;
            float x_hi = ((col + 1) * this.m_frame_size.x) / pot_size.x;
            float y_hi = (row * this.m_frame_size.y) / pot_size.y;
            float y_lo = ((row + 1) * this.m_frame_size.y) / pot_size.y;
            float[] array = {x_lo, y_lo, x_hi, y_lo, x_hi, y_hi, x_lo, y_hi};
            this.m_buffers[i] = Util.getFloatBufferFromFloatArray(array);
        }
        this.m_frame_size.timesEquals(Globals.SCALE_FACTOR);
    }

    public Sprite(String texture_name, v2i frame_size, float duration, boolean tile, int frame_count) {
        this.m_texture = null;
        this.m_frame_size = new v2f();
        this.m_scroll = new v2f();
        this.m_buffers = null;
        constructor(texture_name, frame_size, duration, tile, frame_count);
    }

    public Sprite(String texture_name) {
        this.m_texture = null;
        this.m_frame_size = new v2f();
        this.m_scroll = new v2f();
        this.m_buffers = null;
        constructor(texture_name, new v2i(0, 0), 0.0f, false, 0);
    }

    public Sprite(String texture_name, v2i frame_size) {
        this.m_texture = null;
        this.m_frame_size = new v2f();
        this.m_scroll = new v2f();
        this.m_buffers = null;
        constructor(texture_name, frame_size, 0.0f, false, 0);
    }

    public Sprite(String texture_name, v2i frame_size, float duration) {
        this.m_texture = null;
        this.m_frame_size = new v2f();
        this.m_scroll = new v2f();
        this.m_buffers = null;
        constructor(texture_name, frame_size, duration, false, 0);
    }

    Sprite(int size, int glyph_offset, String font, String text, v4f color, int outline) {
        this.m_texture = null;
        this.m_frame_size = new v2f();
        this.m_scroll = new v2f();
        this.m_buffers = null;
        this.m_texture = null;
        this.m_frame_count = 0;
        this.m_frame_size = new v2f(0.0f, 0.0f);
        this.m_current_frame = 0;
        this.m_duration = 0.0f;
        this.m_elapsed = 0.0f;
        this.m_scroll = new v2f(0.0f, 0.0f);
        this.m_tile = false;
        float newSize = (size / Globals.SCALE_FACTOR) + 0.5f;
        this.m_texture = Globals.texture_mgr.getText(text, font, glyph_offset, (int) newSize, color, outline, ((color.x + color.y) + color.z) / 3.0f);
        v2i frame_size = this.m_texture.size();
        v2i pot_size = this.m_texture.potSize();
        float y_hi = frame_size.y / pot_size.y;
        float x_hi = frame_size.x / pot_size.x;
        this.m_frame_size = new v2f(frame_size.x, frame_size.y);
        this.m_frame_count = 1;
        this.m_buffers = new FloatBuffer[1];
        float[] array = {0.0f, 0.0f, x_hi, 0.0f, x_hi, y_hi, 0.0f, y_hi};
        this.m_buffers[0] = Util.getFloatBufferFromFloatArray(array);
        this.m_frame_size.timesEquals(Globals.SCALE_FACTOR);
    }

    public void delete() {
        Globals.texture_mgr.release(this.m_texture);
    }

    void setFrame(int frame) {
        this.m_current_frame = Math.max(Math.min(frame, this.m_frame_count - 1), 0);
        this.m_elapsed = (this.m_current_frame / this.m_frame_count) * this.m_duration;
    }

    void setScroll(v2f scroll) {
        if (this.m_frame_count == 1) {
            this.m_scroll = scroll;
            this.m_duration = 0.0f;
        }
    }

    boolean update(float elapsed) {
        if (this.m_duration > 0.0f) {
            this.m_elapsed += elapsed;
            if (this.m_elapsed >= this.m_duration) {
                this.m_elapsed %= this.m_duration;
                return true;
            }
        } else if (!this.m_scroll.equalsZero()) {
        }
        return false;
    }

    void draw(v3f pos) {
        draw(pos, DEFAULT_SCALE, DEFAULT_ROT, DEFAULT_COLOR);
    }

    void draw(v3f pos, v2f scale, v3f rot, v4f color) {
        if (this.m_frame_count != 0) {
            if (this.m_duration > 0.0f) {
                this.m_current_frame = Math.min((int) ((this.m_elapsed / this.m_duration) * this.m_frame_count), this.m_frame_count - 1);
            }
            Globals.GL.glPushMatrix();
            Globals.GL.glTranslatef(pos.x, pos.y, -pos.z);
            if (rot.z != 0.0f) {
                Globals.GL.glRotatef(rot.z, 0.0f, 0.0f, 1.0f);
            }
            if (rot.y != 0.0f) {
                Globals.GL.glRotatef(rot.y, 0.0f, 1.0f, 0.0f);
            }
            if (rot.x != 0.0f) {
                Globals.GL.glRotatef(rot.x, 1.0f, 0.0f, 0.0f);
            }
            float size_x = this.m_frame_size.x * scale.x;
            float size_y = this.m_frame_size.y * scale.y;
            Globals.GL.glScalef(size_x, size_y, 1.0f);
            Globals.GL.glColor4f(color.x, color.y, color.z, color.w);
            Globals.GL.glBindTexture(3553, this.m_texture.id());
            Globals.GL.glTexCoordPointer(2, 5126, 0, this.m_buffers[this.m_current_frame]);
            Globals.GL.glDrawArrays(6, 0, 4);
            Globals.GL.glPopMatrix();
        }
    }

    public boolean checkPoint(v3f pos, v2f point, float scale) {
        v2f size = this.m_texture.m_text_size != null ? new v2f(this.m_texture.m_text_size.x, this.m_texture.m_text_size.y) : this.m_frame_size;
        v2f half = new v2f((size.x / 2.0f) * scale, (size.y / 2.0f) * scale);
        v4f r = new v4f(pos.x - half.x, pos.y - half.y, pos.x + half.x, pos.y + half.y);
        return point.x >= r.x && point.x <= r.z && point.y >= r.y && point.y <= r.w;
    }

    public static boolean pointInRect(v2f point, v2f pos, v2f size) {
        v2f p = new v2f(pos.x, pos.y);
        v2f s = new v2f(size.x * 0.5f, size.y * 0.5f);
        v2f min = p.minus(s);
        v2f max = p.plus(s);
        return point.x >= min.x && point.x <= max.x && point.y >= min.y && point.y <= max.y;
    }

    public v2i frameSize() {
        return new v2i((int) this.m_frame_size.x, (int) this.m_frame_size.y);
    }
}
