package edu.upb.lp.progra.ClashOfHeroes;

public class Ficha {
    private int vida = 10;
    private String name; //demon_rojo demon_morado demon_plomo
    private boolean preparandoAtaque = false;
   private int turnosParaAtacar = 0;

    public Ficha(String name){
        this.name = name;
    }

    // getter del name:
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setPreparandoAtaque(boolean preparandoAtaque){
        this.preparandoAtaque=preparandoAtaque;
    }
    public void setTurnosParaAtacar(int turnosParaAtacar){
        this.turnosParaAtacar=turnosParaAtacar;
    }
    public void finTurno(){
        if(preparandoAtaque)
            vida+=2;
    }

}

