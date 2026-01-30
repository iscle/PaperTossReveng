package com.bfs.papertoss.cpp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLUtils;
import android.util.Log;

import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.platform.Util;
import com.bfs.papertoss.vector.v2i;
import com.bfs.papertoss.vector.v4f;

import java.io.IOException;

public class Texture {
    int m_components;
    public int m_id;
    public String m_name;
    v2i m_pot_size;
    v2i m_size;
    public v2i m_text_size;

    public void create(Bitmap data, int components, v2i size, v2i pot_size) {
        this.m_components = components;
        this.m_size = size;
        this.m_pot_size = pot_size;
        int[] int_array = {this.m_id};
        Globals.GL.glGenTextures(1, int_array, 0);
        this.m_id = int_array[0];
        Globals.GL.glBindTexture(3553, this.m_id);
        GLUtils.texImage2D(3553, 0, data, 0);
        data.recycle();
        Globals.GL.glTexParameterf(3553, 10241, 9729.0f);
        Globals.GL.glTexParameterf(3553, 10240, 9729.0f);
        Globals.GL.glTexParameterf(3553, 10242, 33071.0f);
        Globals.GL.glTexParameterf(3553, 10243, 33071.0f);
    }

    public Texture(String filename) {
        this.m_id = 0;
        this.m_components = 4;
        new v2i(0, 0);
        Bitmap data = null;
        if (!filename.contains("img")) {
            if (Globals.HI_RES) {
                filename = "img_hi_res/" + filename;
            } else {
                filename = "img/" + filename;
            }
        }
        try {
            data = BitmapFactory.decodeStream(Globals.m_activity.getAssets().open(filename));
        } catch (IOException e) {
            Log.w("BFS", e);
        }
        v2i size = new v2i(data.getWidth(), data.getHeight());
        int components = getComponents(data);
//        Util.ASSERT(data != null);
        if (data != null) {
            Bitmap data2 = Util.makePowerOfTwo(data);
            v2i pot_size = new v2i(data2.getWidth(), data2.getHeight());
            create(data2, components, size, pot_size);
            this.m_name = filename;
        }
    }

    public Texture(String text, String font, int glyph_offset, int font_size, v4f color, int outline, float fill) {
        this.m_components = 4;
        Paint paint = new Paint();
        paint.setTextSize(font_size);
        paint.setARGB((int) (color.w * 255.0f), (int) (color.x * 255.0f), (int) (color.y * 255.0f), (int) (color.z * 255.0f));
        paint.setTextAlign(Paint.Align.CENTER);
        int text_width = (int) paint.measureText(text);
        int width = Util.nextPowerOfTwo(Math.max(text_width, font_size));
        Typeface typeface = Typeface.createFromAsset(Globals.m_activity.getAssets(), "fonts/" + font + ".ttf");
        paint.setTypeface(typeface);
        paint.setAntiAlias(true);
        if (outline > 0) {
            paint.setFakeBoldText(true);
        }
        Bitmap data = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(data);
        canvas.scale(1.0f, -1.0f);
        canvas.drawText(text, width / 2, (-1.0f) * ((width / 2) - (font_size / 4)), paint);
        this.m_pot_size = new v2i(data.getWidth(), data.getHeight());
        this.m_size = this.m_pot_size;
        this.m_text_size = new v2i(text_width, font_size);
        create(data, this.m_components, this.m_size, this.m_pot_size);
        this.m_name = text;
    }

    public void delete() {
        if (this.m_id != 0) {
            int[] int_array = {this.m_id};
            Globals.GL.glDeleteTextures(1, int_array, 0);
        }
    }

    public int id() {
        return this.m_id;
    }

    public v2i size() {
        return this.m_size;
    }

    public v2i potSize() {
        return this.m_pot_size;
    }

    private int getComponents(Bitmap data) {
        Bitmap.Config config = data.getConfig();
        switch (config) {
            case ALPHA_8:
                return 1;
            case RGB_565:
                return 3;
            case ARGB_4444:
            case ARGB_8888:
            default:
                return 4;
        }
    }
}
