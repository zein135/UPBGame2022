package edu.upb.lp.progra.ClashOfHeroes;

public class Animador {
    private ClashOfHeroes game;
    //private JaladorHaciaAtras jaladorHaciaAtras;
    private Lanzador lanzador=new Lanzador(this);
    private Atacante atacante=new Atacante(this);
    private Ficha[][] board;
    private ClashOfHeroesUI ui;
    private int animacionHorizontal;
    private int animacionVertical;
    private int limite;
    private boolean animacionTurnoJugador1;
    private String animacionNombre;
    private String[] fichasEnMovimiento= new String[20];
    private int[] colFichasEnMovimiento=new int[20];
    private int[] limFichasEnMovimiento=new int[20];
    private int idFichaEnMovimiento;
    private int meAtacanHorizontal;
    private int numDeFichasEnMovimiento;
    private boolean mostrarMuroRajado=false;

    public Animador(ClashOfHeroes game){
        this.game=game;
        this.board=game.getBoard();
        this.ui=game.getUi();
        fichasEnMovimiento=new String[20];
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
    public void setAnimacionNombre(String animacionNombre){
        this.animacionNombre=animacionNombre;
    }
    public void lanzar(){
        lanzador.start(animacionTurnoJugador1);
    }
    public void lanzarJugador(){
        if(animacionHorizontal+1<board.length)
            ui.drawUnit(animacionHorizontal+1,animacionVertical,"clash_of_heroes_fondo_desierto");
        ui.drawUnit(animacionHorizontal,animacionVertical,animacionNombre);
        animacionHorizontal--;
        if(animacionHorizontal<6 || board[animacionHorizontal+1][animacionVertical]!=null) {
            game.draw();
            lanzador.stop();
        }
    }
    public void lanzarEnemigo(){
        if(animacionHorizontal-1>=0)
            ui.drawUnit(animacionHorizontal-1,animacionVertical,"clash_of_heroes_fondo_desierto");
        ui.drawUnit(animacionHorizontal,animacionVertical,animacionNombre);
        animacionHorizontal++;
        if(animacionHorizontal>5 || board[animacionHorizontal-1][animacionVertical]!=null) {
            game.draw();
            lanzador.stop();
        }
    }

    public void recibirFicha(int col,int lim,String nombre){
        colFichasEnMovimiento[numDeFichasEnMovimiento]=col;
        limFichasEnMovimiento[numDeFichasEnMovimiento]=lim;
        fichasEnMovimiento[numDeFichasEnMovimiento]=nombre;
        numDeFichasEnMovimiento++;
    }
    public void meAtacan(){
        if(numDeFichasEnMovimiento!=0) {
            System.out.println("Atacando");
            idFichaEnMovimiento = 0;
            if (animacionTurnoJugador1)
                meAtacanHorizontal = 5;
            else
                meAtacanHorizontal = 7;
            for (int i = 0; i < numDeFichasEnMovimiento; i++) {
                if (animacionTurnoJugador1) {
                    //mapeo en el board
                    this.limFichasEnMovimiento[i] = 6 - limFichasEnMovimiento[i];
                } else {
                    //mapeo en el board
                    this.limFichasEnMovimiento[i] = limFichasEnMovimiento[i] + 7;
                }
            }
            atacante.start(animacionTurnoJugador1);
        }
    }
    public void meAtacanJugador2() {
        if(meAtacanHorizontal<limFichasEnMovimiento[idFichaEnMovimiento]) {
            if(idFichaEnMovimiento>=numDeFichasEnMovimiento-1){
               detenerMeAtacan();
            }
            else {
                if(mostrarMuroRajado) {
                    ui.drawUnit(meAtacanHorizontal + 1, colFichasEnMovimiento[idFichaEnMovimiento], "muro_rajado");
                    mostrarMuroRajado=false;
                }
                else
                    ui.drawUnit(meAtacanHorizontal+1,colFichasEnMovimiento[idFichaEnMovimiento],"clash_of_heroes_fondo_desierto");
                idFichaEnMovimiento++;
            }
            meAtacanHorizontal=5;
        }
        else {
            if (meAtacanHorizontal + 1 < 6)
                ui.drawUnit(meAtacanHorizontal + 1, colFichasEnMovimiento[idFichaEnMovimiento], "clash_of_heroes_fondo_desierto");
            if (board[meAtacanHorizontal][colFichasEnMovimiento[idFichaEnMovimiento]] == null && meAtacanHorizontal != 0)
                ui.drawUnit(meAtacanHorizontal, colFichasEnMovimiento[idFichaEnMovimiento], fichasEnMovimiento[idFichaEnMovimiento] + "_running_2");
            else {
                ui.drawUnit(meAtacanHorizontal, colFichasEnMovimiento[idFichaEnMovimiento], fichasEnMovimiento[idFichaEnMovimiento] + "_attacking_2");
                if(board[meAtacanHorizontal][colFichasEnMovimiento[idFichaEnMovimiento]]!=null && board[meAtacanHorizontal][colFichasEnMovimiento[idFichaEnMovimiento]].getTipo()=="muro"){
                    mostrarMuroRajado=true;
                }
            }
            meAtacanHorizontal--;
        }

    }

    public void meAtacanJugador1() {
        if(meAtacanHorizontal>=limFichasEnMovimiento[idFichaEnMovimiento]) {
            if(idFichaEnMovimiento>=numDeFichasEnMovimiento){
                detenerMeAtacan();
            }
            else {
                if(mostrarMuroRajado) {
                    mostrarMuroRajado=false;
                    ui.drawUnit(meAtacanHorizontal - 1, colFichasEnMovimiento[idFichaEnMovimiento], "demon_muro_rajado");
                }
                else
                    ui.drawUnit(meAtacanHorizontal-1,colFichasEnMovimiento[idFichaEnMovimiento],"clash_of_heroes_fondo_desierto");
                idFichaEnMovimiento++;
            }
            meAtacanHorizontal=7;
        }
        else {
            if (meAtacanHorizontal - 1 > 6) {
                ui.drawUnit(meAtacanHorizontal - 1, colFichasEnMovimiento[idFichaEnMovimiento], "clash_of_heroes_fondo_desierto");
            }
            if (board[meAtacanHorizontal][colFichasEnMovimiento[idFichaEnMovimiento]] == null && meAtacanHorizontal != 12)
                ui.drawUnit(meAtacanHorizontal, colFichasEnMovimiento[idFichaEnMovimiento], fichasEnMovimiento[idFichaEnMovimiento] + "_running_2");
            else {
                ui.drawUnit(meAtacanHorizontal, colFichasEnMovimiento[idFichaEnMovimiento], fichasEnMovimiento[idFichaEnMovimiento] + "_attacking_2");
                if(board[meAtacanHorizontal][colFichasEnMovimiento[idFichaEnMovimiento]]!=null && board[meAtacanHorizontal][colFichasEnMovimiento[idFichaEnMovimiento]].getTipo()=="muro"){
                    mostrarMuroRajado=true;
                }
            }
            meAtacanHorizontal++;
        }

    }
    public void detenerMeAtacan(){
        atacante.stop();
        game.actualizarTablero();
        game.draw();
    }
    public void executeLater(Runnable r, int ms){
        ui.executeLater(r,ms);
    }
}
