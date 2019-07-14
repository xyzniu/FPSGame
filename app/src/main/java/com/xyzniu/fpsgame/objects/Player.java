package com.xyzniu.fpsgame.objects;

public class Player {
    
    private int hp;
    private int kill;
    
    public Player(int hp) {
        this.hp = hp;
        kill = 0;
    }
    
    public void hit() {
        hp--;
    }
    
    public void killEnemy() {
        kill++;
    }
    
    public boolean dead() {
        return hp <= 0;
    }
    
    public int getKill() {
        return kill;
    }
}
