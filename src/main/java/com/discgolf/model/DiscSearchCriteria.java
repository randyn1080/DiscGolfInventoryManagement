package com.discgolf.model;

// encapsulation of various search criteria for discs
public class DiscSearchCriteria {
    private Integer speed;
    private Integer glide;
    private Integer turn;
    private Integer fade;
    private String manufacturer;
    private String mold;
    private String color;
    private Integer weight;

    public DiscSearchCriteria() {

    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getGlide() {
        return glide;
    }

    public void setGlide(Integer glide) {
        this.glide = glide;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public Integer getFade() {
        return fade;
    }

    public void setFade(Integer fade) {
        this.fade = fade;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    // builder pattern
    public static class Builder {
        private Integer speed;
        private Integer glide;
        private Integer turn;
        private Integer fade;
        private String manufacturer;
        private String mold;
        private String color;
        private Integer weight;

        public Builder speed(Integer speed) {
            this.speed = speed;
            return this;
        }

        public Builder glide(Integer glide) {
            this.glide = glide;
            return this;
        }

        public Builder turn(Integer turn) {
            this.turn = turn;
            return this;
        }

        public Builder fade(Integer fade) {
            this.fade = fade;
            return this;
        }

        public Builder manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Builder mold(String mold) {
            this.mold = mold;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder weight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public DiscSearchCriteria build() {
            DiscSearchCriteria criteria = new DiscSearchCriteria();
            criteria.speed = this.speed;
            criteria.glide = this.glide;
            criteria.turn = this.turn;
            criteria.fade = this.fade;
            criteria.manufacturer = this.manufacturer;
            criteria.mold = this.mold;
            criteria.color = this.color;
            criteria.weight = this.weight;
            return criteria;
        }
    }
    public static Builder builder() {
        return new Builder();
    }
}
