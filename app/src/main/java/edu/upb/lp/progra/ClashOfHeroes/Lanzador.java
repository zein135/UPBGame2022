package edu.upb.lp.progra.ClashOfHeroes;

public class Lanzador implements Runnable{
    //private ClashOfHeroes game;
    private Animador animador;
    private boolean running = false;
    private boolean animacionTurnoJugador1;
    /*public Lanzador(ClashOfHeroes game){
        this.game=game;
    }*/
    public Lanzador(Animador animador){
        this.animador=animador;
    }
    public void start(boolean animacionTurnoJugador1){
        this.animacionTurnoJugador1=animacionTurnoJugador1;
        if (!running) {
            running = true;
            run();
        }
    }
    public void stop() {
        running = false;
    }
    public void animacionLanzar(){
        if(animacionTurnoJugador1)
            animador.lanzarJugador();
        else
            animador.lanzarEnemigo();
    }
    @Override
    public void run() {
        if(running){
            animacionLanzar();
            animador.executeLater(this,200);
        }
    }
}
