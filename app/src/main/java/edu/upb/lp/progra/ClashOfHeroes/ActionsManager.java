package edu.upb.lp.progra.ClashOfHeroes;

public class ActionsManager {
    private Ficha fichaSelec;
    private Ficha[][] tablero;
    private int[] numUnits;
    private int verticalFichaSelec;
    private int horizontalFichaSelec;
    private JaladorHaciaAtras jalador;
    private Lanzador lanzador;
    private Player jugador;
    private Animador animador;
    //va a hacer las acciones
    // Cuando seleccionamos una celda 1 boton "Eliminar", si lo presionamos
    // se elimina la unidad y las unidades detras avanzan una casilla
    public ActionsManager(Player jugador,Animador animador){
        this.jugador=jugador;
        this.tablero=jugador.getTablero();
        this.numUnits=jugador.getNumUnits();
        this.animador=animador;
    }
    public Animador getAnimador(){
        return animador;
    }
    public void setJalador(JaladorHaciaAtras jalador){
        this.jalador=jalador;
    }
    public void setLanzador(Lanzador lanzador){
        this.lanzador = lanzador;
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
        //if(ficha1.PreparandoAtaque() || ficha2.isPreparandoAtaque()) return false;
        return ficha1.getName().equals(ficha2.getName());
    }
    public boolean verificarAtaque(int fila, int col){
        for(int i=fila;i<fila+3;i++){
            if(tablero[i][col]==null || tablero[i][col].getCargando() || tablero[i][col].siSoyMuro()) return false;
        }
        return igual(tablero[fila][col],tablero[fila+1][col]) && igual(tablero[fila][col],tablero[fila+2][col]);
    }
    private boolean verificarMuro(int fila, int col) {
        for(int i=col;i<col+3;i++){
            if(tablero[fila][i]==null || tablero[fila][i].getCargando() || tablero[fila][i].siSoyMuro()) return false;
        }
        return igual(tablero[fila][col],tablero[fila][col+1]) && igual(tablero[fila][col],tablero[fila][col+2]);
    }

    public void verificarTablero(){
        for(int fila=0; fila<tablero.length; fila++){
            for(int col=0;col<tablero[fila].length;col++){
                if(col+2<tablero[fila].length && verificarMuro(fila,col)){
                    formacionMuro(fila,col);
                }else{
                    if(fila+2<tablero.length && verificarAtaque(fila,col)){
                        formacionAtaque(fila,col);
                    }
               }
            }
        }
    }
    public void formacionMuro(int fila,int col){
        for(int i=fila-1;i>=0;i--){
            for(int j=col;j<col+3;j++){
                tablero[i+1][j]=tablero[i][j];
            }
        }
        for(int i=0;i<3;i++){
            tablero[0][col+i]=crearMuro();
        }
    }

    private Ficha crearMuro() {
        Ficha muro=new Ficha("demon_muro","muro");
        muro.setVida(20);
        muro.ahoraSoyMuro();
        return muro;
    }

    // formacionAtaque recibe las coordenadas de la cabeza de la formacion
    private void formacionAtaque(int fila, int col) {
        /*Ficha[] formacion  = new Ficha[3];
        for(int i=0;i<3;i++){
            formacion[i]=tablero[fila+i][col];
            tablero[fila+i][col].setCargando(true);
            tablero[fila+i][col].setTurnosParaAtacar(2);
            tablero[fila+i][col].setName(tablero[fila+i][col].getName()+"_charging");
        }
        for(int i=0;i<fila;i++){
            tablero[i+3][col]=tablero[i][col];
        }
        for(int i=0;i<3;i++){
            tablero[i][col]=formacion[i];
        }*/
        //Primera casilla que no es muro
        int filaNoEsMuro=0;
        while(filaNoEsMuro<tablero.length && tablero[filaNoEsMuro][col].siSoyMuro()){
            filaNoEsMuro++;
        }
        Ficha[] formacion  = new Ficha[3];
        for(int i=0;i<3;i++){
            formacion[i]=tablero[fila+i][col];
            tablero[fila+i][col].setCargando(true);
            tablero[fila+i][col].setTurnosParaAtacar(2);
            tablero[fila+i][col].setName(tablero[fila+i][col].getName()+"_charging");
        }
        for(int i=filaNoEsMuro;i<fila;i++){
            tablero[i+3][col]=tablero[i][col];
        }
        for(int i=0;i<3;i++){
            tablero[filaNoEsMuro+i][col]=formacion[i];
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
        horizontalFichaSelec=nullId;
        verticalFichaSelec=vertical;
        eliminar(nullId,vertical);
    }
    public void enviar(int vertical){
        int fila=primeraCasillaVacia(vertical);
        tablero[fila][vertical]=fichaSelec;
        verificarTablero();
        //horizontalFichaSelec=6;
    }
   /* public void lanzar(){
        //TODO
        horizontalFichaSelec--;
        System.out.println(horizontalFichaSelec+" "+ jugador.getMovimientos());
        if(horizontalFichaSelec<0 || tablero[horizontalFichaSelec][jugador.getUltimoVertical()]!=null){
            //Fix de Bug: al enviar creaba dos copias de la ficha ahora eliminamos una de ellas
            //tablero[horizontalFichaSelec+1][jugador.getUltimoVertical()]=null;
            //enviar(jugador.getUltimoVertical());
            verificarTablero();
            lanzador.stop();
        }
        else{
            tablero[horizontalFichaSelec][jugador.getUltimoVertical()]=fichaSelec;
            if(horizontalFichaSelec+1<tablero.length)
                tablero[horizontalFichaSelec+1][jugador.getUltimoVertical()]=null;
        }
    }*/
    public void jalarHaciaAtras(){
        if(horizontalFichaSelec<tablero.length) {
            tablero[horizontalFichaSelec][verticalFichaSelec] = null;
            horizontalFichaSelec++;
            if (horizontalFichaSelec < tablero.length)
                tablero[horizontalFichaSelec][verticalFichaSelec] = fichaSelec;
            else {
                jalador.stop();
            }
        }
    }
    public void meAtacan(int col, int ataqueEnemigo,String tipo) {
        int fila;
        for(fila=0; fila<tablero.length && ataqueEnemigo!=0 ;fila++){
            // if(condicion)
            if (tablero[fila][col]!=null && tablero[fila][col].getVida()<=ataqueEnemigo){
                ataqueEnemigo-=tablero[fila][col].getVida();
                //eliminar(fila,col);
                tablero[fila][col]=null;
                numUnits[col]--;
            } else {
                if(tablero[fila][col]!=null){
                    tablero[fila][col].setName("demon_muro_rajado");
                    tablero[fila][col].setVida(tablero[fila][col].getVida()-ataqueEnemigo);
                    ataqueEnemigo=0;
                }
            }
            //avanzar hacia adelante
        }
        //Mandar al animador
        animador.recibirFicha(col,fila,tipo);
        jugador.setVida(jugador.getVida()-ataqueEnemigo);
    }
    public void avanzarFichas(){

        for(int fila=1;fila<tablero.length;fila++){
            for(int col=0;col<tablero[fila].length;col++){
                if(tablero[fila][col]!=null){
                    int horizontal = fila;
                    while(horizontal!=0 && tablero[horizontal-1][col]==null) {
                        tablero[horizontal-1][col]=tablero[horizontal][col];
                        tablero[horizontal][col]=null;
                        horizontal--;
                    }
                }
            }
        }
    }

    public void animacionMeAtacan() {
        animador.meAtacan();
    }
}


