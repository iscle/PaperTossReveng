package com.bfs.papertoss.cpp;

import android.util.Log;
import com.bfs.papertoss.cpp.Level;

/* loaded from: classes.dex */
public class RenderInfoQueue {
    private Level.RenderInfo m_first = null;
    private int m_size = 0;

    public int size() {
        return this.m_size;
    }

    public Level.RenderInfo poll() {
        this.m_size--;
        Level.RenderInfo result = this.m_first;
        this.m_first = this.m_first.next;
        return result;
    }

    public void add(Level.RenderInfo info) {
        this.m_size++;
        if (this.m_first == null) {
            this.m_first = info;
        } else if (info.priority > this.m_first.priority) {
            info.next = this.m_first;
            this.m_first = info;
        } else {
            insert(info);
        }
    }

    private void insert(Level.RenderInfo info) {
        for (Level.RenderInfo pointer = this.m_first; pointer != null; pointer = pointer.next) {
            if (pointer.next == null) {
                pointer.next = info;
                return;
            } else {
                if (pointer.priority >= info.priority && pointer.next.priority <= info.priority) {
                    info.next = pointer.next;
                    pointer.next = info;
                    return;
                }
            }
        }
        Log.d("BFS", "Something weird happend inserting");
    }
}
