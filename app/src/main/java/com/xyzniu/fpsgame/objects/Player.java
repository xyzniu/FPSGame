package com.xyzniu.fpsgame.objects;

public class Player {
    
    private static Player player = new Player();
    
    public static Player getPlayer() {
        return player;
    }
    
    private Player() {
        hp = 3;
        kill = 0;
    }
    
    private int hp;
    private int kill;
    
    public int getHp() {
        return hp;
    }
    
    public void setHp(int hp) {
        this.hp = hp;
    }
    
    public int getKill() {
        return kill;
    }
    
    public void setKill(int kill) {
        this.kill = kill;
    }
}
