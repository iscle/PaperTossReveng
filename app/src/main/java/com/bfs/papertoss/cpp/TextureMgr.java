package com.bfs.papertoss.cpp;

import com.bfs.papertoss.vector.v4f;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class TextureMgr {
    ConcurrentHashMap<String, TextureInfo> m_textures = new ConcurrentHashMap<>();

    private class TextureInfo {
        int ref_count;
        Texture texture;

        private TextureInfo() {
            this.texture = null;
            this.ref_count = 0;
        }
    }

    public Texture get(String filename) {
        TextureInfo ti = this.m_textures.get(filename);
        if (ti == null) {
            ti = new TextureInfo();
            this.m_textures.put(filename, ti);
        }
        if (ti.texture == null) {
            ti.texture = new Texture(filename);
        }
        ti.ref_count++;
        return ti.texture;
    }

    public Texture getText(String text, String font, int glyph_offset, int size, v4f color, int outline, float fill) {
        StringBuilder builder = new StringBuilder(text).append(font).append(size).append(color.x).append(color.y).append(color.z).append(color.w).append(outline);
        String name = builder.toString();
        TextureInfo ti = this.m_textures.get(name);
        if (ti == null) {
            ti = new TextureInfo();
            this.m_textures.put(name, ti);
        }
        if (ti.texture == null) {
            ti.texture = new Texture(text, font, glyph_offset, size, color, outline, fill);
        }
        ti.ref_count++;
        return ti.texture;
    }

    public void release(Texture texture) {
        Collection<TextureInfo> collection = this.m_textures.values();
        for (TextureInfo ti : collection) {
            if (ti.texture == texture) {
                ti.ref_count--;
                return;
            }
        }
    }

    public void cleanup() {
        Set<String> key_set = this.m_textures.keySet();
        for (String key : key_set) {
            TextureInfo ti = this.m_textures.get(key);
            if (ti.ref_count < 1) {
                ti.texture.delete();
                this.m_textures.remove(key);
            }
        }
    }
}
