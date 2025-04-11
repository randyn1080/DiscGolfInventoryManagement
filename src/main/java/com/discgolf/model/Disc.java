package com.discgolf.model;

import java.util.Objects;

public class Disc {
    private int discID;
    private String manufacturer;
    private String mold;
    private String color;
    private int weight;
    private int speed;
    private int glide;
    private int turn;
    private int fade;
    private String specialNotes;

    public Disc(){}

    public Disc(String manufacturer, String mold, String color, int weight,
                int speed, int glide, int turn, int fade, String specialNotes) {
        this.manufacturer = manufacturer;
        this.mold = mold;
        this.color = color;
        this.weight = weight;
        this.speed = speed;
        this.glide = glide;
        this.turn = turn;
        this.fade = fade;
        this.specialNotes = specialNotes;
    }

    public Disc(int discID, String manufacturer, String mold, String color,
                int weight, int speed, int glide, int turn, int fade, String specialNotes) {
        this.discID = discID;
        this.manufacturer = manufacturer;
        this.mold = mold;
        this.color = color;
        this.weight = weight;
        this.speed = speed;
        this.glide = glide;
        this.turn = turn;
        this.fade = fade;
        this.specialNotes = specialNotes;
    }

    public int getDiscID() {
        return discID;
    }

    public void setDiscID(int discID) {
        this.discID = discID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMold() {
        return mold;
    }

    public void setMold(String mold) {
        this.mold = mold;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getGlide() {
        return glide;
    }

    public void setGlide(int glide) {
        this.glide = glide;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getFade() {
        return fade;
    }

    public void setFade(int fade) {
        this.fade = fade;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Disc disc = (Disc) o;
        return discID == disc.discID &&
                weight == disc.weight &&
                speed == disc.speed &&
                glide == disc.glide &&
                turn == disc.turn &&
                fade == disc.fade &&
                Objects.equals(manufacturer, disc.manufacturer) &&
                Objects.equals(mold, disc.mold) &&
                Objects.equals(color, disc.color) &&
                Objects.equals(specialNotes, disc.specialNotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discID, manufacturer, mold, color, weight,
                speed, glide, turn, fade, specialNotes);
    }

    @Override
    public String toString() {
        return "Disc{" +
                "discID=" + discID +
                ", manufacturer='" + manufacturer + '\'' +
                ", mold='" + mold + '\'' +
                ", color='" + color + '\'' +
                ", weight=" + weight +
                ", speed=" + speed +
                ", glide=" + glide +
                ", turn=" + turn +
                ", fade=" + fade +
                ", specialNotes='" + specialNotes + '\'' +
                '}';
    }
}
