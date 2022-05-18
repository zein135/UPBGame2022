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
            gui.removeButton("STAR");
            gui.configureScreen(13,8,0,0,true,0.15);
            gui.addButton("RESTART",16,50);
            game.initGame();

        }
        if(name.equals("RESTART")) {
            restart();
        }
        else if(name.equals("Eliminar"))
            game.eliminar();
        else if(name.equals("Mover"))
            game.mover();
        else if(name.equals("Enviar"))
            game.enviar();
        else if(name.equals("Jalar"))
            game.jalarHaciaAtras();
        else if(name.equals("Llamar"))
            game.llamar();
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
    public void restart(){
        game=new ClashOfHeroes(this);
        game.initGame();
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

    public void executeLater(Runnable r, int ms) {
       gui.executeLater(r,ms);
    }
    public void eliminarMensaje(String name){
        gui.removeTextField(name);
    }

    public void mostrarMensaje(String name,String mensaje){
        gui.addTextField(name,mensaje,15,20);
    }
}
