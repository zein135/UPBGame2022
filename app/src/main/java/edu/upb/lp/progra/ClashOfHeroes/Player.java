package edu.upb.lp.progra.ClashOfHeroes;

public class Player {
    private Ficha[][] tablero=new Ficha[6][8];
    private int[] numUnits = new int[8];
    private int ultimoHorizontal;
    private int ultimoVertical;
    private ActionsManager actionsManager=new ActionsManager(tablero);
    public int movimientos=0;
    public void setUltimoVertical(int vertical){
        ultimoVertical = vertical;
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
    public void initBoard(){
        for(int i=0;i<numUnits.length;i++){
            numUnits[i]=0;
        }
        for(int row=0;row<6;row++){
            for(int col=0;col<8;col++)
                tablero[row][col]=null;
        }
    }
    public Ficha[][] LlenarTablero (int unidades){
        String[] colors = new String[]{"demon_red", "demon_purple", "demon_grey"};
        int idColor=0;
        int units=unidades;
        int columna=0;
        while(units!=0){
            int colocarFicha=generateRandom(0,1);
            if(colocarFicha==1 && numUnits[columna]<6){
                tablero[numUnits[columna]][columna]=new Ficha(colors[idColor]);
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
        actionsManager.enviar(ultimoVertical);
        movimientos--;
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
       // for( declaracion de variable ; condicion ; instruccion)
        for(int fila=0; fila< tablero.length;fila++ ){
            for(int col=0;col< tablero.length;col++){
               if(tablero[fila][col]!= null && tablero[fila][col].getCargando()){
                   
               }
            }

        }
    }



    public void jalarHaciaAtras(){
        actionsManager.jalarHaciaAtras();
    }
}


