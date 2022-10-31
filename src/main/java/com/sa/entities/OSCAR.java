package com.sa.entities;

public class OSCAR {

    private String o;
    private String s;
    private String c;
    private String a;
    private String r;

    public OSCAR() {
        this.o = " ";
        this.s = " ";
        this.c = " ";
        this.a = " ";
        this.r = " ";
    }

    public OSCAR(String oscar) {
        this.o = oscar.contains("O") ? "O" : " ";
        this.s = oscar.contains("S") ? "S" : " ";
        this.c = oscar.contains("C") ? "C" : " ";
        this.a = oscar.contains("A") ? "A" : " ";
        this.r = oscar.contains("R") ? "R" : " ";
    }

    public OSCAR(String o, String s, String c, String a, String r) {
        this.o = o;
        this.s = s;
        this.c = c;
        this.a = a;
        this.r = r;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return o + s + c + a + r;
    }
}
