package org.Imanol;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MyUITest {

    Metodos m = new Metodos();
    String vacia;
    String arriba;
    String abajo;
    ArrayList<Ips> lista=null;
    LectorJson l = new LectorJson();

    @Before
    public void setUp() throws Exception {
        vacia="";
        arriba="255.255.255.260";
        abajo="0.0.0.-1";
        lista = l.leer_json("src/main/java/org/Imanol/LocalizaIP.json");
    }

    @After
    public void tearDown() throws Exception {
    }

    //Ip vacia
    @Test(expected = Exception.class)
    public void Vacia() throws Exception{

            m.Encontrar("",lista);

    }

    //Ip por encima
    @Test(expected = Exception.class)
    public void Mayor() throws Exception{
        m.Encontrar(arriba,lista);
    }

    //Ip por debajo
    @Test(expected = Exception.class)
    public void Menor() throws Exception{

            m.Encontrar(abajo,lista);

    }
}