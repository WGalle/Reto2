package com.example.reto2;

import java.util.Date;

public class Pokemon {

    private String name;
    private String image;
    private String type;
    private String defense;
    private String attack;
    private String speed;
    private String life;
    private Date catchDate;

    public Pokemon(){

    }

    public Pokemon(String name, String image, String type, String defense, String attack, String speed, String life) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.defense = defense;
        this.attack = attack;
        this.speed = speed;
        this.life = life;
        this.catchDate = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public Date getCatchDate() {
        return catchDate;
    }

    public void setCatchDate(Date catchDate) {
        this.catchDate = catchDate;
    }
}

