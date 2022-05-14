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
    public void dibujarVidasYMovimientos(){
        ui.eliminarMensaje("vidaJugador1");
        ui.mostrarMensaje("vidaJugador1","Vida Jugador 1: "+jugador1.getVida());
        ui.eliminarMensaje("vidaJugador2");
        ui.mostrarMensaje("vidaJugador2","Vida Jugador 2: "+jugador2.getVida());
        Player jugadorActual=obtenerJugadorActual();
        ui.eliminarMensaje("movimientos");
        ui.mostrarMensaje("movimientos","Movimientos: "+jugadorActual.getMovimientos());
        ui.eliminarMensaje("tropas");
        int reserva=MAX_UNITS-jugadorActual.obtenerNumeroFichas();
        ui.mostrarMensaje("tropas","Reserva: "+reserva);
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
        dibujarVidasYMovimientos();
        draw();
    }
    public Ficha[][] initPlayerBoard(Player jugador){
        jugador.initBoard();
        Ficha[][] tableroJugador=jugador.llenarTablero(MAX_UNITS);
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
        //int rowBoard=5;
        for(int row=0;row<6;row++){
            for(int col=0;col<8;col++){
                board[5-row][col]=tableroEnemigo[row][col];
            }
            //rowBoard--;
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
        Player jugadorActual=obtenerJugadorActual();
        boolean seSelecciono=false;
        if(turnoJugador1){
            if(horizontal>=7 && horizontal<=12){
                jugador1.setUltimoVertical(vertical);
                jugador1.setUltimoHorizontal(horizontal-7);
                seSelecciono=true;
            }
        } else{
            if(horizontal<=5){
                jugador2.setUltimoHorizontal(5-horizontal);
                jugador2.setUltimoVertical(vertical);
                seSelecciono=true;
            }
        }
        if(seSelecciono) {
            if (board[horizontal][vertical] != null) {
                //ui.drawUnit(horizontal,vertical,"colors_blue");//board[horizontal][vertical].getName()+"_selected");
                if(!jugadorActual.getGuardeFicha()) {
                    ui.dibujarBoton("Eliminar");
                    if (!board[horizontal][vertical].siSoyMuro()) {
                        ui.dibujarBoton("Mover");
                    }
                }
            }
            draw();
        }
    }
    public Player obtenerJugadorActual(){
        Player jugadorActual;
        if(turnoJugador1) jugadorActual=jugador1;
        else jugadorActual=jugador2;
        return jugadorActual;
    }
    public Player obtenerJugadorEnemigo(){
        Player jugadorEnemigo;
        if(turnoJugador1) jugadorEnemigo=jugador2;
        else jugadorEnemigo=jugador1;
        return jugadorEnemigo;
    }
    public void siCambiaTurnoJugador(){
        Player jugadorActual=obtenerJugadorActual();
        Player jugadorEnemigo=obtenerJugadorEnemigo();
        if(!jugadorActual.tengoTurnos()){
            if(turnoJugador1)
                ui.mensajeTemporal("Turno de Jugador 2");
            else
                ui.mensajeTemporal("Turno de Jugador 1");
            turnoJugador1=!turnoJugador1;
            jugadorEnemigo.setMovimientos(3);
            jugadorEnemigo.verFichasCargadas();
            if(jugadorEnemigo.obtenerNumeroFichas()<MAX_UNITS){
                ui.dibujarBoton("Llamar");
            }
            actualizarTableroJugador(jugador1.getTablero());
            actualizarTableroEnemigo(jugador2.getTablero());
        }
        dibujarVidasYMovimientos();
        draw();
    }
    public void mover(){
        Player jugadorActual=obtenerJugadorActual();
        jugadorActual.setGuardeFicha(true);
        jugadorActual.mover();
        if(turnoJugador1){
            jalador.start();
            actualizarTableroJugador(jugador1.getTablero());
        }
        else{
            actualizarTableroEnemigo(jugador2.getTablero());
        }
        draw();
        quitarbotones();
        ui.dibujarBoton("Enviar");
    }

    public void enviar(){
        Player jugadorActual = obtenerJugadorActual();
        if(jugadorActual.getNumUnits()[jugadorActual.getUltimoVertical()]<6) {
            jugadorActual.setGuardeFicha(false);
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
        quitarbotones();
    }

    private void quitarbotones() {
        ui.removerBoton("Enviar");
        ui.removerBoton("Mover");
        ui.removerBoton("Eliminar");
    }

    public void eliminar() {
        Player jugadorActual=obtenerJugadorActual();
        jugadorActual.eliminar();
        if(turnoJugador1)
            actualizarTableroJugador(jugadorActual.getTablero());
        else
            actualizarTableroEnemigo(jugadorActual.getTablero());
        draw();
        siCambiaTurnoJugador();
        if(jugadorActual.obtenerNumeroFichas()<MAX_UNITS){
            ui.dibujarBoton("Llamar");
        }
        quitarbotones();
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

    public void llamar() {
        Player jugadorActual=obtenerJugadorActual();
        ui.removerBoton("Llamar");
        jugadorActual.llenarTablero(MAX_UNITS-jugadorActual.obtenerNumeroFichas());
        dibujarVidasYMovimientos();
        if(turnoJugador1)
            actualizarTableroJugador(jugadorActual.getTablero());
        else
            actualizarTableroEnemigo(jugadorActual.getTablero());
        draw();
    }
}

// TODO para avanzr con los ataques no debe contar con los muros