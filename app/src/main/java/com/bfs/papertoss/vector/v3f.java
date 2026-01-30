package com.bfs.papertoss.vector;

/* JADX INFO: loaded from: classes.dex */
public class v3f {
    public float x;
    public float y;
    public float z;

    public v3f(float a_x, float a_y, float a_z) {
        this.x = a_x;
        this.y = a_y;
        this.z = a_z;
    }

    public v3f(float a_x, float a_y) {
        this.x = a_x;
        this.y = a_y;
        this.z = 0.0f;
    }

    public v3f() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public v3f(v3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public static v3f iv3f(float a_x, float a_y, float a_z) {
        return new v3f(a_x, 480.0f - a_y, a_z);
    }

    public v3f minus(v3f v) {
        return new v3f(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public boolean equals(v3f v) {
        return this.x == v.x && this.y == v.y && this.z == v.z;
    }

    public boolean equalsZero() {
        return this.x == 0.0f && this.y == 0.0f && this.z == 0.0f;
    }

    public v3f plus(v3f v) {
        return new v3f(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public v3f times(float f) {
        return new v3f(this.x * f, this.y * f, this.z * f);
    }

    public v3f times(v3f v) {
        return new v3f(this.x * v.x, this.y * v.y, this.z * v.z);
    }
}
