package com.bfs.papertoss.cpp;

import com.bfs.papertoss.vector.v3f;

public class Anim {
    int m_count;
    float m_duration;
    float m_elapsed;
    v3f[] m_frames;

    Anim() {
        this.m_frames = null;
        this.m_count = 0;
        this.m_duration = 0.0f;
        this.m_elapsed = 0.0f;
    }

    Anim(v3f[] frames, int count, float duration) {
        this.m_frames = frames;
        this.m_count = count;
        this.m_duration = duration;
        this.m_elapsed = 0.0f;
    }

    v3f get(float elapsed) {
        this.m_elapsed += elapsed;
        float i = Math.min(this.m_elapsed / this.m_duration, 1.0f);
        int s = Math.min((int) ((this.m_count - 1) * i), this.m_count - 2);
        int e = s + 1;
        float si = (float) s / (this.m_count - 1);
        float ei = (float) e / (this.m_count - 1);
        float d = i - si;
        float td = ei - si;
        float li = d / td;
        v3f result = this.m_frames[e].minus(this.m_frames[s]);
        return this.m_frames[s].plus(result.times(li));
    }

    boolean isDone() {
        return this.m_elapsed >= this.m_duration;
    }
}
