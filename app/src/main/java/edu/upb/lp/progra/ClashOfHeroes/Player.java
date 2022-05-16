package edu.upb.lp.progra.ClashOfHeroes;

public class Player {
    private Ficha[][] tablero=new Ficha[6][8];
    private int[] numUnits = new int[8];
    private int ultimoHorizontal;
    private int ultimoVertical;
    private ActionsManager actionsManager;
    private int movimientos=0;
    private Player enemigo;
    private boolean guardeFicha=false;
    private int vida=50;

    public int obtenerNumeroFichas(){
        int totalFichas=0;
        for(int i = 0; i< numUnits.length; i++){
            totalFichas+=numUnits[i];
        }
        return totalFichas;
    }
    public void asignarActionsManagerYAnimador(Animador animador){
        actionsManager=new ActionsManager(this,animador);
    }
    public int getVida(){
        return vida;
    }
    public void setVida(int vida){
        this.vida=vida;
    }
    public void setUltimoVertical(int vertical){
        ultimoVertical = vertical;
    }
    public void setGuardeFicha(boolean guardeFicha){
        this.guardeFicha=guardeFicha;
    }
    public boolean getGuardeFicha(){
        return guardeFicha;
    }
    public void setUltimoHorizontal(int horizontal){
        ultimoHorizontal = horizontal;
    }
    public Ficha[][] getTablero(){
        return tablero;
    }
    public int getUltimoHorizontal(){
        return ultimoHorizontal;
    }
    public int getUltimoVertical() {
        return ultimoVertical;
    }
    public int generateRandom(int min,int max){
        int random=(int)Math.floor(Math.random()*(max-min+1)+min);
        return random;
    }
    public int[] getNumUnits(){
        return numUnits;
    }
    public void setMovimientos(int movimientos){
        this.movimientos=movimientos;
    }
    public void setEnemigo(Player enemigo){
        this.enemigo=enemigo;
    }
    public void initBoard(){
        for(int i=0;i<numUnits.length;i++){
            numUnits[i]=0;
        }
        for(int row=0;row<6;row++){
            for(int col=0;col<8;col++)
                tablero[row][col]=null;
        }
    }
    public Ficha[][] llenarTablero(int unidades){
        String[] colors = new String[]{"demon_red", "imp_purple", "dog_grey"};
        int idColor=0;
        int units=unidades;
        int columna=0;
        while(units!=0){
            int colocarFicha=generateRandom(0,1);
            if(colocarFicha==1 && numUnits[columna]<6){
                tablero[numUnits[columna]][columna]=new Ficha(colors[idColor],colors[idColor]);
                idColor++;
                idColor%=3;
                numUnits[columna]++;
                units--;

            }
            columna++;
            columna%=8;
        }
        return tablero;
    }
    public void mover(){
        numUnits[ultimoVertical]--;
        actionsManager.mover(ultimoVertical);
    }
    public void enviar(){
        numUnits[ultimoVertical]++;
        movimientos--;
        actionsManager.enviar(ultimoVertical);
    }
    public void eliminar(){
        numUnits[ultimoVertical]--;
        actionsManager.eliminar(ultimoHorizontal,ultimoVertical);
        movimientos--;
    }
    public boolean tengoTurnos(){
        return movimientos!=0;
    }

    public void verFichasCargadas(){
        ActionsManager actionsManagerEnemigo=enemigo.getActionsManager();
        for(int fila=0; fila< tablero.length;fila++ ){
            for(int col=0;col< tablero[fila].length;col++){
               if(tablero[fila][col]!=null && tablero[fila][col].getCargando()){

                    actionsManagerEnemigo.meAtacan(col,tablero[fila][col].getAtaque(),tablero[fila][col].getTipo());
                    tablero[fila][col]=null;
                    //actionsManager.eliminar(fila,col);
                    numUnits[col]--;
               }
            }
        }
        actionsManagerEnemigo.animacionMeAtacan();
        actionsManagerEnemigo.avanzarFichas();
        actionsManager.avanzarFichas();
    }
    private ActionsManager getActionsManager() {
        return actionsManager;
    }
    public void asignarJalador(JaladorHaciaAtras jalador){
        actionsManager.setJalador(jalador);
    }


    public void jalarHaciaAtras(){
        actionsManager.jalarHaciaAtras();
    }
    public void asignarLanzador(Lanzador lanzador){
        actionsManager.setLanzador(lanzador);
    }
    /*public void lanzar(){
        actionsManager.lanzar();
    }*/
    public int getMovimientos() {
        return movimientos;
    }

}


