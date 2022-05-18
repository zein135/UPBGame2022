package edu.upb.lp.progra.ClashOfHeroes;

public class ClashOfHeroes {
    private static final int MAX_UNITS = 20;
    private ClashOfHeroesUI ui;
    private Ficha[][] board=new Ficha[13][8];
    private Player jugador1 = new Player();
    private Player jugador2 = new Player();
    private boolean turnoJugador1=true;
    private Animador animador;
    private JaladorHaciaAtras jalador = new JaladorHaciaAtras(this);
    public ClashOfHeroes(ClashOfHeroesUI ui){
        this.ui=ui;
    }
    public Ficha[][] getBoard(){
        return board;
    }
    public ClashOfHeroesUI getUi(){
        return ui;
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
        animador = new Animador(this);
        //EL ANIMADOR ES EL MISMO PARA EL JUGADOR 1 Y 2
        jugador1.asignarActionsManagerYAnimador(animador);
        jugador2.asignarActionsManagerYAnimador(animador);
        jugador1.setEnemigo(jugador2);
        jugador2.setEnemigo(jugador1);
        //jalador el mismo para jugador 1 y 2
        jugador1.asignarJalador(jalador);
        jugador2.asignarJalador(jalador);
        Ficha[][] tableroJugador1=initPlayerBoard(jugador1);
        //el tablero es distinto para jugador 1 y 2
        Ficha[][] tableroJugador2=initPlayerBoard(jugador2);
        //recorremos el tablero del jugador 1 y 2 de los cuales actualizamos el board
        actualizarTablero();
        dibujarVidasYMovimientos();
        draw();
        //el draw hace que muestre las imagenes que estan en le board
    }
    public Ficha[][] initPlayerBoard(Player jugador){
        //lo llena con la mayor cantidad de fichas
        jugador.initBoard();
        Ficha[][] tableroJugador=jugador.llenarTablero(MAX_UNITS);
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
        for(int row=0;row<6;row++){
            for(int col=0;col<8;col++){
                board[5-row][col]=tableroEnemigo[row][col];
            }
        }
    }
    public void draw(){
        for( int row = 0; row < 13 ; row++){
            for(int col=0;col<8;col++){
                //si es igual a null la casilla esta vacia
                if(board[row][col]==null){
                    ui.drawUnit(row,col,"clash_of_heroes_fondo_desierto");
                }else{
                    ui.drawUnit(row,col,board[row][col].getName());
                }
            }
        }
        dibujarVidasYMovimientos();
    }
    public void click(int horizontal,int vertical){
        //si el animador ya no esta animando va a accionar el click XD
        if(!animador.estoyAnimando()) {
            Player jugadorActual = obtenerJugadorActual();
            // nos indica si una casilla fue selccionada valida
            boolean seSelecciono = false;
            // pregunta si el jugador hizo click en su tablero
            if (turnoJugador1) {
                //el if pregunta si esta dentro del tablero del jugador 1
                if (horizontal >= 7 && horizontal <= 12) {
                    jugador1.setUltimoVertical(vertical);
                    jugador1.setUltimoHorizontal(horizontal - 7);
                    seSelecciono = true;
                }
            } else {
                // y lo mismo para este if pregunta si se encuentra en el jugador 2
                if (horizontal <= 5) {
                    jugador2.setUltimoHorizontal(5 - horizontal);
                    jugador2.setUltimoVertical(vertical);
                    seSelecciono = true;
                }
            }
                //si no hay nada en esa poscion quiero que me dbujes un selcted que esta con borde azul
            if (seSelecciono) {
                draw();
                if (board[horizontal][vertical] != null) {
                    ui.drawUnit(horizontal, vertical, board[horizontal][vertical].getName() + "_selected");
                    //si el jugador actual a guardado una ficha puede eliminar
                    if (!jugadorActual.getGuardeFicha()) {
                        ui.dibujarBoton("Eliminar");
                        //si es que no es un muro podemos mover
                        if (!board[horizontal][vertical].siSoyMuro()) {
                            ui.dibujarBoton("Mover");
                        }
                    }
                }
            }
            //no puedo mover una casilla vacia y tampoco eliminarla XD
            if (board[horizontal][vertical] == null) {
                ui.removerBoton("Mover");
                ui.removerBoton("Eliminar");
            }
        }
    }
    public Player obtenerJugadorActual(){
        //lo que nos indica quien es el jugador actual
        Player jugadorActual;
        if(turnoJugador1) jugadorActual=jugador1;
        else jugadorActual=jugador2;
        return jugadorActual;
    }
    public Player obtenerJugadorEnemigo(){
        //y lo mismo para este metodo
        Player jugadorEnemigo;
        if(turnoJugador1) jugadorEnemigo=jugador2;
        else jugadorEnemigo=jugador1;
        return jugadorEnemigo;
    }
    //preguntamos si tiene movimientos, de lo cual hace que cambien los valores, y eso hace que roten de turnos XD
    public void siCambiaTurnoJugador(){
        Player jugadorActual=obtenerJugadorActual();
        Player jugadorEnemigo=obtenerJugadorEnemigo();
        if(!jugadorActual.tengoTurnos()){
            if(turnoJugador1)
                ui.mensajeTemporal("Turno de Jugador 2");
            else
                ui.mensajeTemporal("Turno de Jugador 1");
            turnoJugador1=!turnoJugador1;
            //ahi le decimos al animador de quien es el turno
            animador.setAnimacionTurnoJugador1(turnoJugador1);
            animador.iniciarId();
            //y despues vemos si el jugador tiene ficchas cargadas si tiene movimientos
            jugadorEnemigo.setMovimientos(3);
            jugadorEnemigo.verFichasCargadas();
            if(jugadorEnemigo.obtenerNumeroFichas()<MAX_UNITS){
                ui.dibujarBoton("Llamar");
            }
            actualizarTablero();
        }
        dibujarVidasYMovimientos();
        if(jugadorEnemigo.getVida()<=0){
            if(turnoJugador1)
                ui.mensajeTemporal("Gano Jugador 1");
            else
                ui.mensajeTemporal("Gano Jugador 2");
            ui.restart();
        }
    }
    //guarda al jugador actual, de la cual le decimos el nombre de la ficha, y le dcimos a la ficha moverte
    public void mover(){
        Player jugadorActual=obtenerJugadorActual();
        jugadorActual.setGuardeFicha(true);
        animador.setAnimacionNombre(jugadorActual.getTablero()[jugadorActual.getUltimoHorizontal()][jugadorActual.getUltimoVertical()].getName());
        jugadorActual.mover();
        jalador.start();
        if(turnoJugador1){
            actualizarTableroJugador(jugador1.getTablero());
        }
        else{
            actualizarTableroEnemigo(jugador2.getTablero());
        }
        quitarbotones();
        //al guardar elimino todos los botones y la unica accion que podemos hacer es enviar
        ui.dibujarBoton("Enviar");
    }

    public void enviar() {
        Player jugadorActual = obtenerJugadorActual();
        //si tiene espacio le decimos al amimador cual vertical y le decimos cual es la ultina cassilla que emos seleccionado
        if(jugadorActual.getNumUnits()[jugadorActual.getUltimoVertical()]<6) {
            animador.setAnimacionVertical(jugadorActual.getUltimoVertical());
            //le decimos al animador depende de los valores, de la cual
            if(turnoJugador1)
                animador.setAnimacionHorizontal(board.length-1);
            else
                animador.setAnimacionHorizontal(0);
            //le decimos al animador a quien le toca
            animador.setAnimacionTurnoJugador1(turnoJugador1);
            jugadorActual.enviar();
            animador.lanzar();
            jugadorActual.setGuardeFicha(false);
            if(turnoJugador1)
                actualizarTableroJugador(jugadorActual.getTablero());
            else
                actualizarTableroEnemigo(jugadorActual.getTablero());
            ui.removerBoton("Enviar");
            siCambiaTurnoJugador();
            quitarbotones();
        }
        else{
            ui.mensajeTemporal("Te saliste del tablero xD");
        }
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
        //obtiene jugador actual XD
        Player jugadorActual=obtenerJugadorActual();
        jugadorActual.jalarHaciaAtras();
        if(turnoJugador1)
            actualizarTableroJugador(jugadorActual.getTablero());
        else
            actualizarTableroEnemigo(jugadorActual.getTablero());
        draw();
    }
        //hace que haya un bucle, y una vez que termine el programa vuelva a corer otra vez
    public void executeLater(Runnable r, int ms){
        ui.executeLater(r,ms);
    }

    public void llamar() {
        Player jugadorActual=obtenerJugadorActual();
        ui.removerBoton("Llamar");
        jugadorActual.llenarTablero(MAX_UNITS-jugadorActual.obtenerNumeroFichas());
        if(turnoJugador1)
            actualizarTableroJugador(jugadorActual.getTablero());
        else
            actualizarTableroEnemigo(jugadorActual.getTablero());
        draw();
    }

    public void actualizarTablero() {
        actualizarTableroJugador(jugador1.getTablero());
        actualizarTableroEnemigo(jugador2.getTablero());
    }
}
