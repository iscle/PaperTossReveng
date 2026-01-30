package com.bfs.papertoss.vector;

/* JADX INFO: loaded from: classes.dex */
public class v2i {
    public int x;
    public int y;

    public v2i(int a_x, int a_y) {
        this.x = a_x;
        this.y = a_y;
    }

    public v2i() {
        this.x = 0;
        this.y = 0;
    }

    public boolean equals(v2i v) {
        return this.x == v.x && this.y == v.y;
    }

    public boolean equalsZero() {
        return this.x == 0 && this.y == 0;
    }

    public v2i plus(v2i v) {
        return new v2i(this.x + v.x, this.y + v.y);
    }

    public v2i minus(v2i v) {
        return new v2i(this.x - v.x, this.y - v.y);
    }
}
