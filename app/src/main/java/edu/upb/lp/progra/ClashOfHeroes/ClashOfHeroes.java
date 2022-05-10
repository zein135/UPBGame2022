package edu.upb.lp.progra.ClashOfHeroes;

public class ClashOfHeroes {
    private static final int MAX_UNITS = 20;
    private ClashOfHeroesUI ui;
    private Ficha[][] board=new Ficha[13][8];
    private Player jugador = new Player();
    private Enemy enemigo = new Enemy();
    private ActionsManager actionsManager=new ActionsManager();
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
        initEnemyBoard();
        initPlayerBoard();
        draw();
    }
    public void initPlayerBoard(){
        jugador.initBoard();
        Ficha[][] tableroJugador=jugador.LlenarTablero(MAX_UNITS);
        actualizarTableroJugador(tableroJugador);
    }
    public void initEnemyBoard(){
        enemigo.initBoard();
        Ficha[][] tableroEnemy= enemigo.LlenarTablero(MAX_UNITS);
        actualizarTableroEnemigo(tableroEnemy);
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
        draw();

        jugador.setUltimoHorizontal(horizontal-7);
        jugador.setUltimoVertical(vertical);
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

    public void mover(){
        actionsManager.mover(jugador.getTablero(), jugador.getUltimoVertical());
        actualizarTableroJugador(jugador.getTablero());
        draw();
        ui.removerBoton("Mover");
        ui.dibujarBoton("Enviar");
    }

    public void enviar(){
        actionsManager.enviar(jugador.getTablero(),jugador.getUltimoVertical());
        actualizarTableroJugador(jugador.getTablero());
        draw();
        ui.removerBoton("Enviar");
    }

    public void eliminar() {
        actionsManager.eliminar(jugador.getTablero(), jugador.getUltimoHorizontal(),jugador.getUltimoVertical());
        actualizarTableroJugador(jugador.getTablero());
        draw();
        ui.removerBoton("Eliminar");
    }

}

