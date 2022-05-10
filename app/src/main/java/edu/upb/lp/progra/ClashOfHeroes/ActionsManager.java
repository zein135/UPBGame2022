package edu.upb.lp.progra.ClashOfHeroes;

public class ActionsManager {
    private Ficha fichaSelec;
    //va a hacer las acciones
    // Cuando seleccionamos una celda 1 boton "Eliminar", si lo presionamos
    // se elimina la unidad y las unidades detras avanzan una casilla
    public void eliminar(Ficha[][] tablero, int horizontal, int vertical) {
        //eliminar tenga dos parametros la fila y la columna que vamos a eliminar
        //y al eliminar se vayan para adelnate
        for(int row=horizontal;row<5;row++){
            tablero[row][vertical]=tablero[row+1][vertical];
            System.out.println();
        }
    }
    public void mover(Ficha[][] tablero, int vertical){
        int nullId=0;
        while(nullId<6 && tablero[nullId][vertical]!=null){
            nullId++;
        }
        nullId--;
        fichaSelec=tablero[nullId][vertical];
        eliminar(tablero,nullId,vertical);
    }

    public void enviar(Ficha[][] tablero,int vertical){
        int fila=0;
        while(fila<6 && tablero[fila][vertical]!=null){
            fila++;
        }
        tablero[fila][vertical]=fichaSelec;


    }
}