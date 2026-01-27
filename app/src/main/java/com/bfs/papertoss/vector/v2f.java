package com.bfs.papertoss.vector;

/* loaded from: classes.dex */
public class v2f {
    public float x;
    public float y;

    public v2f(float a_x, float a_y) {
        this.x = a_x;
        this.y = a_y;
    }

    public v2f() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public v2f(v2i v) {
        this.x = v.x;
        this.y = v.y;
    }

    public boolean equals(v2f v) {
        return this.x == v.x && this.y == v.y;
    }

    public boolean equalsZero() {
        return this.x == 0.0f && this.y == 0.0f;
    }

    public v2f minus(v2f v) {
        return new v2f(this.x - v.x, this.y - v.y);
    }

    public v2f dividedBy(v2f v) {
        return new v2f(this.x / v.x, this.y / v.y);
    }

    public v2f times(float f) {
        return new v2f(this.x * f, this.y * f);
    }

    public void plusEquals(v2f v) {
        this.x += v.x;
        this.y += v.y;
    }

    public v2f plus(v2f v) {
        return new v2f(this.x + v.x, this.y + v.y);
    }

    public void timesEquals(v2f v) {
        this.x *= v.x;
        this.y *= v.y;
    }

    public void timesEquals(float f) {
        this.x *= f;
        this.y *= f;
    }

    public v2f dividedBy(float f) {
        return new v2f(this.x / f, this.y / f);
    }

    public v2f rotated(double angle) {
        return new v2f((this.x * ((float) Math.cos(angle))) - (this.y * ((float) Math.sin(angle))), (this.y * ((float) Math.cos(angle))) + (this.x * ((float) Math.sin(angle))));
    }

    public void normalize() {
        float length = length();
        this.x /= length;
        this.y /= length;
    }

    public float length() {
        return (float) Math.sqrt((this.x * this.x) + (this.y * this.y));
    }

    public v2f normalized() {
        v2f normalized = new v2f(this.x, this.y);
        normalized.normalize();
        return normalized;
    }

    public static float getNegativeRotation(v2f v) {
        return (v.y < 0.0f ? -1 : 1) * ((float) Math.acos(dot(v.normalized(), new v2f(1.0f, 0.0f)))) * (-1.0f);
    }

    public static float dot(v2f a, v2f b) {
        return (a.x * b.x) + (a.y * b.y);
    }

    public v2f times(v2f v) {
        return new v2f(this.x * v.x, this.y * v.y);
    }
}
