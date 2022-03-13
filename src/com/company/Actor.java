package com.company;

public class Actor {
    int x, y, id;
    public Actor(int id, int x, int y){
        this.x = x;
        this.y = y;
        this.id = id;
    }
    public int get_x(){
        return this.x;
    }
    public int get_y(){
        return this.y;
    }
    public int get_id(){
        return this.id;
    }
}
