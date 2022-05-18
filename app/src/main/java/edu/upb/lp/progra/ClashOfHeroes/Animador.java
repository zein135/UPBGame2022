package edu.upb.lp.progra.ClashOfHeroes;

public class Animador {
    private ClashOfHeroes game;
    private Lanzador lanzador=new Lanzador(this);
    private Atacante atacante=new Atacante(this);
    private Ficha[][] board;
    private ClashOfHeroesUI ui;
    private int animacionHorizontal;
    private int animacionVertical;
    private boolean animacionTurnoJugador1;
    private String animacionNombre;
    private String[] fichasEnMovimiento=new String[20];
    private int[] colFichasEnMovimiento=new int[20];
    private int[] limFichasEnMovimiento=new int[20];
    private int[] filaFichaEnMovimiento=new int[20];
    private boolean[] golpeoMuro=new boolean[20];
    private int idFichaEnMovimiento;
    private int meAtacanHorizontal;
    private int numDeFichasEnMovimiento;
    private boolean animandoMeAtacan=false;
    private boolean animandoLanzar=false;
    //no toca el tablero,y lo unico que hace es modificar la pantalla
    public Animador(ClashOfHeroes game){
        this.game=game;
        this.board=game.getBoard();
        this.ui=game.getUi();
    }
    public void iniciarId(){
        numDeFichasEnMovimiento=0;
    }
    public void setAnimacionHorizontal(int animacionHorizontal){
        this.animacionHorizontal=animacionHorizontal;
    }
    public void setAnimacionVertical(int animacionVertical){
        this.animacionVertical=animacionVertical;
    }
    public void setAnimacionTurnoJugador1(boolean animacionTurnoJugador1){
        this.animacionTurnoJugador1=animacionTurnoJugador1;
    }
    //true esta animando y digo al lanzador que resive el parametro para quien esta animando
    public void setAnimacionNombre(String animacionNombre){
        this.animacionNombre=animacionNombre;
    }
    public void lanzar(){
        animandoLanzar=true;
        lanzador.start(animacionTurnoJugador1);
    }
    //
    public void lanzarJugador(){

        if(animacionHorizontal+1<board.length)
            ui.drawUnit(animacionHorizontal+1,animacionVertical,"clash_of_heroes_fondo_desierto");
        ui.drawUnit(animacionHorizontal,animacionVertical,animacionNombre);
        animacionHorizontal--;
        //si me salgo del tablero o la siguien te posision es difernete de null, entonces detengo lanzar
        if(animacionHorizontal<6 || board[animacionHorizontal+1][animacionVertical]!=null) {
            detenerLanzar();
        }
    }
    public void lanzarEnemigo(){
        //aca es lo mismo de arriba solo que cambia las coordenadas
        if(animacionHorizontal-1>=0)
            ui.drawUnit(animacionHorizontal-1,animacionVertical,"clash_of_heroes_fondo_desierto");
        ui.drawUnit(animacionHorizontal,animacionVertical,animacionNombre);
        animacionHorizontal++;
        if(animacionHorizontal>5 || board[animacionHorizontal-1][animacionVertical]!=null) {
            detenerLanzar();
        }
    }

    public void recibirFicha(int fila, int col, int lim, String nombre, boolean golpeoMuro){
        //recibe la ficha que a atacado y lo guarda la informacion de la ficha en varios arreglos
        //y se la llama en accion manager
        //de la cual
        filaFichaEnMovimiento[numDeFichasEnMovimiento]=fila;
        colFichasEnMovimiento[numDeFichasEnMovimiento]=col;
        limFichasEnMovimiento[numDeFichasEnMovimiento]=lim;
        //numero de indice
        fichasEnMovimiento[numDeFichasEnMovimiento]=nombre;
        this.golpeoMuro[numDeFichasEnMovimiento]=golpeoMuro;
        numDeFichasEnMovimiento++;
    }
    public void meAtacan(){
        //pregunta si es que una ficha a atacdo entonces empieza la animacion
        if(numDeFichasEnMovimiento!=0) {
            animandoMeAtacan=true;
            idFichaEnMovimiento = 0;
            for (int i = 0; i < numDeFichasEnMovimiento; i++) {
                if (animacionTurnoJugador1) {
                    //conversion de coordenadas para el tablero del jugador 2
                    this.limFichasEnMovimiento[i] = 6 - limFichasEnMovimiento[i];
                    filaFichaEnMovimiento[i]=filaFichaEnMovimiento[i]+7;
                } else {
                    //conversion de coordeadas para el tablero del jugador 1
                    this.limFichasEnMovimiento[i] = limFichasEnMovimiento[i] + 7;
                    filaFichaEnMovimiento[i]=5-filaFichaEnMovimiento[i];
                }
            }
            //Aca pasa la animacion y de quien es el turno
            meAtacanHorizontal=filaFichaEnMovimiento[idFichaEnMovimiento];
            atacante.start(animacionTurnoJugador1);
        }
    }
    public void meAtacanJugador2() {
        //si es que me sobrepase de mi limite o se topa con un muro dibuja un muro rajado
        if(meAtacanHorizontal<limFichasEnMovimiento[idFichaEnMovimiento]) {
            if(golpeoMuro[idFichaEnMovimiento]) {
                ui.drawUnit(meAtacanHorizontal + 1, colFichasEnMovimiento[idFichaEnMovimiento], "demon_muro_rajado");
            }
            else
                ui.drawUnit(meAtacanHorizontal+1,colFichasEnMovimiento[idFichaEnMovimiento],"clash_of_heroes_fondo_desierto");
            idFichaEnMovimiento++;
            if(idFichaEnMovimiento>=numDeFichasEnMovimiento)
                detenerMeAtacan();
            //aca si se animo todu la animcion
            else
                meAtacanHorizontal=filaFichaEnMovimiento[idFichaEnMovimiento];
        }
        else {
            //si es que no me salgo del tablero dibujo decierto, y si no dibujara decierto, se dibujarian cosas que no son
            if (meAtacanHorizontal + 1 <= filaFichaEnMovimiento[idFichaEnMovimiento])
                ui.drawUnit(meAtacanHorizontal + 1, colFichasEnMovimiento[idFichaEnMovimiento], "clash_of_heroes_fondo_desierto");
            //&& habia un bug, y preguntamos si estamos dentro de mi area, y de la cual si hay un muro se salta y no hace que se ataque a su propio muro
            if(meAtacanHorizontal>=7 &&
                    board[meAtacanHorizontal][colFichasEnMovimiento[idFichaEnMovimiento]]!=null &&
                    board[meAtacanHorizontal][colFichasEnMovimiento[idFichaEnMovimiento]].getTipo()=="muro")
                meAtacanHorizontal--;
            //si no llegamos al vorde dibujamos corriendo
            if (meAtacanHorizontal!=limFichasEnMovimiento[idFichaEnMovimiento] && meAtacanHorizontal != 0)
                ui.drawUnit(meAtacanHorizontal, colFichasEnMovimiento[idFichaEnMovimiento], fichasEnMovimiento[idFichaEnMovimiento] + "_running_2");
            else {
                //si llego al limte ataca
                ui.drawUnit(meAtacanHorizontal, colFichasEnMovimiento[idFichaEnMovimiento], fichasEnMovimiento[idFichaEnMovimiento] + "_attacking_2");
            }
            meAtacanHorizontal--;
        }

    }
    public void meAtacanJugador1() {
        if(meAtacanHorizontal>limFichasEnMovimiento[idFichaEnMovimiento]-1) {
            if(golpeoMuro[idFichaEnMovimiento])
                ui.drawUnit(meAtacanHorizontal-1,colFichasEnMovimiento[idFichaEnMovimiento],"demon_muro_rajado");
            else
                ui.drawUnit(meAtacanHorizontal-1,colFichasEnMovimiento[idFichaEnMovimiento],"clash_of_heroes_fondo_desierto");
            idFichaEnMovimiento++;
            if(idFichaEnMovimiento>=numDeFichasEnMovimiento)
                detenerMeAtacan();
            else
                meAtacanHorizontal=filaFichaEnMovimiento[idFichaEnMovimiento];
        }
        else {
            if (meAtacanHorizontal - 1 >=filaFichaEnMovimiento[idFichaEnMovimiento]) {
                ui.drawUnit(meAtacanHorizontal - 1, colFichasEnMovimiento[idFichaEnMovimiento], "clash_of_heroes_fondo_desierto");
            }
            if(meAtacanHorizontal<=5 &&
                    board[meAtacanHorizontal][colFichasEnMovimiento[idFichaEnMovimiento]]!=null &&
                    board[meAtacanHorizontal][colFichasEnMovimiento[idFichaEnMovimiento]].getTipo()=="muro")
                meAtacanHorizontal++;
            if (meAtacanHorizontal!=limFichasEnMovimiento[idFichaEnMovimiento]-1 && meAtacanHorizontal != 12)
                ui.drawUnit(meAtacanHorizontal, colFichasEnMovimiento[idFichaEnMovimiento], fichasEnMovimiento[idFichaEnMovimiento] + "_running_2");
            else {
                ui.drawUnit(meAtacanHorizontal, colFichasEnMovimiento[idFichaEnMovimiento], fichasEnMovimiento[idFichaEnMovimiento] + "_attacking_2");
            }
            meAtacanHorizontal++;
        }

    }
    public void detenerMeAtacan(){
        //pregunta si esta animando y dice un stop para que no sea algo infinito
        animandoMeAtacan=false;
        atacante.stop();
        if(!estoyAnimando()) {
            game.actualizarTablero();
            game.draw();
        }
    }
    public void detenerLanzar(){
        animandoLanzar=false;
        lanzador.stop();
        if(!estoyAnimando()) {
            game.actualizarTablero();
            game.draw();
        }
    }
    public boolean estoyAnimando(){
        return animandoMeAtacan || animandoLanzar;
    }
    public void executeLater(Runnable r, int ms){
        ui.executeLater(r,ms);
    }
}
