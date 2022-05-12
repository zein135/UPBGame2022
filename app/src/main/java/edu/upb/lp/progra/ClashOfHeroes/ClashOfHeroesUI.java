package edu.upb.lp.progra.ClashOfHeroes;

import edu.upb.lp.progra.adapterFiles.AndroidGameGUI;
import edu.upb.lp.progra.adapterFiles.UI;

public class ClashOfHeroesUI implements UI {
    private ClashOfHeroes game = new ClashOfHeroes(this);
    private AndroidGameGUI gui;

    public ClashOfHeroesUI(AndroidGameGUI gui) {
        this.gui=gui;
    }

    @Override
    public void onButtonPressed(String name) {
        if(name.equals ("STAR")){
            //gui.configureScreen(1,1,0,0,true,0);
            //gui.setImageOnCell(0,0 ,"clash_of_hereos_fondo_desierto");
            gui.removeButton("STAR");
            gui.configureScreen(13,8,0,0,true,0.15);
            gui.addButton("RESTART",16,50);
            game.initGame();

        }
        if(name.equals("RESTART"))
            game.initGame();
        if(name.equals("Eliminar"))
            game.eliminar();
        if(name.equals("Mover"))
            game.mover();
        if(name.equals("Enviar"))
            game.enviar();
        if(name.equals("Jalar"))
            game.jalarHaciaAtras();
    }

    @Override
    public void onCellPressed(int vertical, int horizontal) {
        game.click(vertical,horizontal);
    }

    @Override
    public void initialiseInterface() {
      gui.configureScreen(1,1,0,0,true,0.1);
      gui.setImageOnCell(0,0,"clash_of_heroes_portada");
      gui.addButton("STAR" , 16,58 );
    }

    public void drawUnit(int row,int column,String name){
        gui.setImageOnCell(row,column,name);
    }

    public void dibujarBoton(String nombre) {
        gui.addButton(nombre,16,50);
    }

    public void removerBoton(String nombre) {
        gui.removeButton(nombre);
    }
    public void mensajeTemporal(String mensaje){
        gui.showTemporaryMessage(mensaje);
    }
}
