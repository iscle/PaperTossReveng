package com.bfs.papertoss.vector;

public class v4f {
    public float w;
    public float x;
    public float y;
    public float z;

    public v4f(float a_x, float a_y, float a_z, float a_w) {
        this.x = a_x;
        this.y = a_y;
        this.z = a_z;
        this.w = a_w;
    }

    public v4f() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 0.0f;
    }

    public v4f times(v4f v) {
        return new v4f(this.x * v.x, this.y * v.y, this.z * v.z, this.w * v.w);
    }

    public v4f plus(v4f v) {
        return new v4f(this.x + v.x, this.y + v.y, this.z + v.z, this.w + v.w);
    }

    public v4f minus(v4f v) {
        return new v4f(this.x - v.x, this.y - v.y, this.z - v.z, this.w - v.w);
    }

    public v4f times(float i) {
        return new v4f(this.x * i, this.y * i, this.z * i, this.w * i);
    }

    public boolean equals(v4f v) {
        return this.x == v.x && this.y == v.y && this.z == v.z && this.w == v.w;
    }

    public boolean equalsZero() {
        return this.x == 0.0f && this.y == 0.0f && this.z == 0.0f && this.w == 0.0f;
    }
}
