package edu.upb.lp.progra.ClashOfHeroes;

public class JaladorHaciaAtras implements Runnable{
    private ClashOfHeroes game;
    private boolean running = false;
    public JaladorHaciaAtras(ClashOfHeroes game) {
        this.game = game;
    }

    public void start() {
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
        if (running) {
            game.jalarHaciaAtras();
            game.executeLater(this, 200);
        }
    }
}
