package edu.upb.lp.progra.ClashOfHeroes;

public class ClashOfHeroes {
    private static final int MAX_UNITS = 20;
    private ClashOfHeroesUI ui;
    private Ficha[][] board=new Ficha[13][8];
    private Player jugador1 = new Player();
    private Player jugador2 = new Player();
    private boolean turnoJugador1=true;
    private JaladorHaciaAtras jalador = new JaladorHaciaAtras(this);
    //private ActionsManager actionsManager=new ActionsManager();
    public ClashOfHeroes(ClashOfHeroesUI ui){
        this.ui=ui;
    }
    public int generateRandom(int min,int max){
        int random=(int)Math.floor(Math.random()*(max-min+1)+min);
        return random;
    }
    public void initGame(){
        for(int i=0;i<13;i++){
            for(int j=0;j<8;j++){
                board[i][j]=null;
            }
        }
        jugador1.setEnemigo(jugador2);
        jugador2.setEnemigo(jugador1);
        jugador1.asignarJalador(jalador);
        Ficha[][] tableroJugador1=initPlayerBoard(jugador1);
        Ficha[][] tableroJugador2=initPlayerBoard(jugador2);
        actualizarTableroJugador(tableroJugador1);
        actualizarTableroEnemigo(tableroJugador2);
        ui.dibujarBoton("Jalar");
        draw();
    }
    public Ficha[][] initPlayerBoard(Player jugador){
        jugador.initBoard();
        Ficha[][] tableroJugador=jugador.LlenarTablero(MAX_UNITS);
        //actualizarTableroJugador(tableroJugador);
        jugador.setMovimientos(3);
        return tableroJugador;
    }

    private void actualizarTableroJugador(Ficha[][] tableroJugador) {
        for(int row=0;row<6;row++){
            for(int col=0;col<8;col++){
                board[row+7][col]=tableroJugador[row][col];
            }
        }
    }
    private void actualizarTableroEnemigo(Ficha[][] tableroEnemigo){
        int rowBoard=5;
        for(int row=0;row<6;row++){
            for(int col=0;col<8;col++){
                board[rowBoard][col]=tableroEnemigo[row][col];
            }
            rowBoard--;
        }
    }
    public void draw(){
        for( int row = 0; row < 13 ; row++){
            for(int col=0;col<8;col++){
                if(board[row][col]==null){
                    //== null no tiene nada XD y si no tenemos nada que nos lo dibuje el fonfo
                    ui.drawUnit(row,col,"clash_of_heroes_fondo_desierto");
                }else{
                    ui.drawUnit(row,col,board[row][col].getName());
                }
            }
        }
    }
    public void click(int horizontal,int vertical){
        boolean seSelecciono=false;
        draw();
        if(turnoJugador1){
            if(horizontal>=7 && horizontal<=12){
                jugador1.setUltimoVertical(vertical);
                jugador1.setUltimoHorizontal(horizontal-7);
                seSelecciono=(board[horizontal][vertical]!=null);
            }
        } else{
            if(horizontal<=5){
                jugador2.setUltimoHorizontal(5-horizontal);
                jugador2.setUltimoVertical(7-vertical);
                seSelecciono=(board[horizontal][vertical]!=null);
            }
        }
        if(seSelecciono){
            if(board[horizontal][vertical]!=null){
                //ui.drawUnit(horizontal,vertical,"colors_blue");//board[horizontal][vertical].getName()+"_selected");
                ui.dibujarBoton("Eliminar");
                ui.dibujarBoton("Mover");
            }
            else{
                ui.removerBoton("Eliminar");
                ui.removerBoton("Mover");
            }
        }
    }
    public Player obtenerJugadorActual(){
        Player jugadorActual;
        if(turnoJugador1) jugadorActual=jugador1;
        else jugadorActual=jugador2;
        return jugadorActual;
    }
    public void siCambiaTurnoJugador(){
        /*
           Player jugadorActual -> jugador1 -> jugador2
           Player contrincante  -> jugador2 -> jugador1
           coordenadas para el jugador2 arreglar la fila
           jalarhaciaatras jalarhaciaadelante

        */
        if(turnoJugador1){
            if(!jugador1.tengoTurnos()){
                turnoJugador1=false;
                //TODO empieza turno del jugador
                jugador2.setMovimientos(3);
                ui.mensajeTemporal("Turno de Jugador 2");
            }
        }
        else{
            if(!jugador2.tengoTurnos()){
                turnoJugador1=true;
                jugador1.setMovimientos(3);
                ui.mensajeTemporal("Turno de Jugador 1");
                jugador1.verFichasCargadas();
                actualizarTableroEnemigo(jugador2.getTablero());
                actualizarTableroJugador(jugador1.getTablero());
            }
        }
        draw();
    }
    public void mover(){
        if(turnoJugador1){
            jugador1.mover();
            actualizarTableroJugador(jugador1.getTablero());
        }
        else{
            jugador2.mover();
            actualizarTableroEnemigo(jugador2.getTablero());
        }
        draw();
        ui.removerBoton("Mover");
        jalador.start();
        ui.dibujarBoton("Enviar");
    }

    public void enviar(){
        Player jugadorActual = obtenerJugadorActual();
        if(jugadorActual.getNumUnits()[jugadorActual.getUltimoVertical()]<6) {
            jugadorActual.enviar();
            //actionsManager.enviar(jugador.getTablero(),jugador.getUltimoVertical());
            if(turnoJugador1)
                actualizarTableroJugador(jugadorActual.getTablero());
            else
                actualizarTableroEnemigo(jugadorActual.getTablero());
            draw();
            ui.removerBoton("Enviar");
            siCambiaTurnoJugador();
        }
        else{
            ui.mensajeTemporal("Te saliste del tablero xD");
        }
    }

    public void eliminar() {
        Player jugadorActual=obtenerJugadorActual();
        jugadorActual.eliminar();
        if(turnoJugador1)
            actualizarTableroJugador(jugadorActual.getTablero());
        else
            actualizarTableroEnemigo(jugadorActual.getTablero());
        draw();
        ui.removerBoton("Eliminar");
        siCambiaTurnoJugador();
    }
    public void jalarHaciaAtras(){
        Player jugadorActual=obtenerJugadorActual();
        jugadorActual.jalarHaciaAtras();
        actualizarTableroJugador(jugadorActual.getTablero());
        draw();
    }
    public void executeLater(Runnable r, int ms){
        ui.executeLater(r,ms);
    }
}

