package edu.upb.lp.progra.ClashOfHeroes;

public class Ficha {
    private int vida = 10;
    private int ataque = 10;
    private String name; //demon_rojo demon_morado demon_plomo
    private boolean cargando = false;
    private int turnosParaAtacar = 0;
    private boolean soyMuro=false;

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
    public void setCargando(boolean cargando){
        this.cargando = cargando;
    }
    public void setTurnosParaAtacar(int turnosParaAtacar){
        this.turnosParaAtacar=turnosParaAtacar;
    }

    // get conseguir -> getter consigue el atributo
    // set colocar -> setter coloca un valor al atributo
    public boolean getCargando(){
        return this.cargando;
    }

    public int getVida() {
        return vida;
    }
    public int getAtaque() {
        return ataque;
    }

    public void setVida(int vida) {
        this.vida=vida;
    }
    public void ahoraSoyMuro(){
        soyMuro=true;
    }
    public boolean siSoyMuro(){
        return soyMuro;
    }
}

