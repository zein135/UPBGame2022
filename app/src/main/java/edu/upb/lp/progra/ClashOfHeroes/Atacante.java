package edu.upb.lp.progra.ClashOfHeroes;

public class Atacante implements Runnable{
    //private ClashOfHeroes game;
    private Animador animador;
    private boolean running = false;
    private boolean animacionTurnoJugador1;
    /*public Lanzador(ClashOfHeroes game){
        this.game=game;
    }*/
    public Atacante(Animador animador){
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
    @Override
    public void run() {
        if(running){
            //animador.prepararAnimacionMeAtacan();
            animacionMeAtacan();
            animador.executeLater(this,500);
        }
    }

    private void animacionMeAtacan() {
        if(animacionTurnoJugador1)
            animador.meAtacanJugador2();
        else
            animador.meAtacanJugador1();
    }
}
