package edu.upb.lp.progra.ClashOfHeroes;

public class ActionsManager {
    private Ficha fichaSelec;
    private Ficha[][] tablero;
    private int[] numUnits;
    private int verticalFichaSelec;
    private int horizontalFichaSelec;
    private JaladorHaciaAtras jalador;
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
    public void setJalador(JaladorHaciaAtras jalador){
        this.jalador=jalador;
    }
    public void eliminar(int horizontal, int vertical) {
        //eliminar tenga dos parametros la fila y la columna que vamos a eliminar
        //y al eliminar se vayan para adelnate
        //en una posicon elimina hacenos que la ficha de atras suba XD Y EN SI NO HACEMOS QUE SE MUEVA LAS imagenes sino se copian
        for(int row=horizontal;row<5;row++){
            //verificamos si se formo linea de ataque o muro
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
    //cmopara nombres de fichas de las imagenes XD
    public boolean igual(Ficha ficha1,Ficha ficha2){
        return ficha1.getName().equals(ficha2.getName());
    }
    //va a las tres casillas de abaja de la cual si son iguales forma un ataque
    public boolean verificarAtaque(int fila, int col){
        for(int i=fila;i<fila+3;i++){
            if(tablero[i][col]==null || tablero[i][col].getCargando() || tablero[i][col].siSoyMuro()) return false;
        }
        return igual(tablero[fila][col],tablero[fila+1][col]) && igual(tablero[fila][col],tablero[fila+2][col]);
    }
    //aca ferifica si las fichas de manera de vertical hay tres fichas iguales, y forma un muro
    private boolean verificarMuro(int fila, int col) {
        for(int i=col;i<col+3;i++){
            if(tablero[fila][i]==null || tablero[fila][i].getCargando() || tablero[fila][i].siSoyMuro()) return false;
        }
        return igual(tablero[fila][col],tablero[fila][col+1]) && igual(tablero[fila][col],tablero[fila][col+2]);
    }
        //va por toido el tablero y verifica si hay muros u ataques
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
        //el for hace que las fichs retrocedan una posicion
        for(int i=fila-1;i>=0;i--){
            for(int j=col;j<col+3;j++){
                tablero[i+1][j]=tablero[i][j];
            }
        }
        //y este fgor crea el muro y los lleva hacia adelante
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
        //Primera casilla que no es muro
        int filaNoEsMuro=0;
        while(filaNoEsMuro<tablero.length && tablero[filaNoEsMuro][col].siSoyMuro()){
            filaNoEsMuro++;
        }
        Ficha[] formacion  = new Ficha[3];
        //el for crea la formacion de la cual cuenta con 3fichas del mismo nombre XD
        for(int i=0;i<3;i++){
            formacion[i]=tablero[fila+i][col];
            tablero[fila+i][col].setCargando(true);
            tablero[fila+i][col].setName(tablero[fila+i][col].getName()+"_charging");
        }
        //recorre las fichas que no formen nada obviamente el muro no se mueve hacia atras
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
        //voy a tener una casilla vacia, por qwue al momento de mover tengo que saber la ultima ficha y cual es es la que essta uno antes
        int nullId=primeraCasillaVacia(vertical);
        nullId--;
        //guardamos la ficha
        fichaSelec=tablero[nullId][vertical];
        horizontalFichaSelec=nullId;
        verticalFichaSelec=vertical;
        eliminar(nullId,vertical);
    }
    public void enviar(int vertical){
        int fila=primeraCasillaVacia(vertical);
        tablero[fila][vertical]=fichaSelec;
        verificarTablero();
    }
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
    public void meAtacan(int filaOriginal,int col, int ataqueEnemigo,String tipo) {
        int fila;
        boolean golpeoMuro=false;
        //resive la fila origuinal  ademas de la ficha que esta atacando, de la cual revisamos toda la columna
        //si es que hay una ficha verifica si hay una ficha o un muro ya queno no podemos matar a un muro de un muro
        // y como iumpacto con un muro ela ficha o el ataque pasa a null y se eilimna
        for(fila=0; fila<tablero.length && ataqueEnemigo!=0 ;fila++){
            if (tablero[fila][col]!=null && tablero[fila][col].getVida()<=ataqueEnemigo){
                ataqueEnemigo-=tablero[fila][col].getVida();
                tablero[fila][col]=null;
                numUnits[col]--;
            } else {
                if(tablero[fila][col]!=null){
                    tablero[fila][col].setName("demon_muro_rajado");
                    tablero[fila][col].setVida(tablero[fila][col].getVida()-ataqueEnemigo);
                    golpeoMuro=true;
                    ataqueEnemigo=0;
                }
            }
        }
        //Mandar al animador
        //muestra hasta donde pudo llegar y la cual realiza la cinematica
        animador.recibirFicha(filaOriginal,col,fila,tipo,golpeoMuro);
        //ACA MANDO LA ANIMACION
        // y aca avaza hasta cierto puntop de la cual si llega al luimite
        jugador.setVida(jugador.getVida()-ataqueEnemigo);
    }
    public void avanzarFichas(){
       //solo avanza las asfihcas y ya xd Y pasa por cadad casilla del tablero
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


