package edu.upb.lp.progra.ClashOfHeroes;

public class Ficha {
    private int vida = 10;
    private String name; //demon_rojo demon_morado demon_plomo
    public Ficha(String name){
        this.name = name;
    }

    // getter del name:
    public String getName(){
        return name;
    }

}

