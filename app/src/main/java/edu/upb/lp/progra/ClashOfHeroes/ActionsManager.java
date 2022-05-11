package edu.upb.lp.progra.ClashOfHeroes;

public class ActionsManager {
    private Ficha fichaSelec;
    private Ficha[][] tablero;
    //va a hacer las acciones
    // Cuando seleccionamos una celda 1 boton "Eliminar", si lo presionamos
    // se elimina la unidad y las unidades detras avanzan una casilla
    public ActionsManager(Ficha[][] tablero){
        this.tablero=tablero;
    }
    public void eliminar(int horizontal, int vertical) {
        //eliminar tenga dos parametros la fila y la columna que vamos a eliminar
        //y al eliminar se vayan para adelnate
        for(int row=horizontal;row<5;row++){
            tablero[row][vertical]=tablero[row+1][vertical];
        }
        verificarTablero();
    }
    public int primeraCasillaVacia(int vertical){
        int fila=0;
        while(fila<6 && tablero[fila][vertical]!=null){
            fila++;
        }
        return fila;
    }
    public boolean igual(Ficha ficha1,Ficha ficha2){
        return ficha1.getName()==ficha2.getName();
    }
    public boolean verficarHorizontal(int fila,int col){
        for(int i=fila;i<fila+3;i++){
            if(tablero[i][col]==null) return false;
        }
        return igual(tablero[fila][col],tablero[fila+1][col]) && igual(tablero[fila][col],tablero[fila+2][col]);
    }
    public void verificarTablero(){
        for(int fila=0; fila<tablero.length; fila++){
            for(int col=0;col<tablero[fila].length;col++){
                /*if(tablero[fila][col]==tablero[fila][col+1] && tablero[fila][col]==tablero[fila][col+2]){

                }else{*/
                System.out.println();
                    if(fila+2<tablero.length && verficarHorizontal(fila,col)){
                        formacionAtaque(fila,col);
                    }
               //}
            }
        }
    }
    // formacionAtaque recibe las coordenadas de la cabeza de la formacion
    private void formacionAtaque(int fila, int col) {
        Ficha[] formacion  = new Ficha[3];
        for(int i=0;i<3;i++){
            formacion[i]=tablero[fila+i][col];
            tablero[fila+i][col].setPreparandoAtaque(true);
            tablero[fila+i][col].setTurnosParaAtacar(2);
            tablero[fila+i][col].setName(tablero[fila+i][col].getName()+"_charging");
        }
        for(int i=0;i<fila;i++){
            tablero[i+3][col]=tablero[i][col];
        }
        for(int i=0;i<3;i++){
            tablero[i][col]=formacion[i];
        }
        // Las 3 fichas avancen lo mas q puedan
        // No haya muros tope }elese{
        // Si hay muros hasta choque a un muro
        // col vertical
        //fila horizontal
    }

    public void mover(int vertical){
        int nullId=primeraCasillaVacia(vertical);
        nullId--;
        fichaSelec=tablero[nullId][vertical];
        eliminar(nullId,vertical);
    }

    public void enviar(int vertical){
        int fila=primeraCasillaVacia(vertical);
        tablero[fila][vertical]=fichaSelec;
        verificarTablero();
    }
}


