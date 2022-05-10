package edu.upb.lp.progra.ClashOfHeroes;

public class Enemy {
    private Ficha[][] tablero=new Ficha[6][8];
    private int[] numUnits = new int[8];
    public int generateRandom(int min,int max){
        int random=(int)Math.floor(Math.random()*(max-min+1)+min);
        return random;
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
}
