package org.Imanol;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import java.util.ArrayList;

import static com.vaadin.ui.Notification.POSITION_CENTERED;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        LectorJson lector =new LectorJson();
        ArrayList<Ips> lista = lector.leer_json("src/main/java/org/Imanol/LocalizaIP.json");
        Label l =new Label(longToIp(lista.get(5).getIp_from()));
        TextField Texto1= new TextField("Introduce la Ip en formato '192.168.100.1'");
        TextField Resultado = new TextField("El resultado es: ");

        Button Calculo = new Button("Click me");
        Calculo.addClickListener(e -> {
            long num = Dot2LongIP(Texto1.getValue());
            System.out.println(Texto1.getValue());
            System.out.println("Largo: "+num+"");
            Ips encontrada = Encontrar(num,lista);
            Ips no = new Ips();
            if (!encontrada.equals(no)){
                String desde = longToIp(encontrada.getIp_from());
                String hasta = longToIp(encontrada.getIp_to());
                String mensaje = "La ip proporcionada se encuentra entre "+desde+" y "+hasta;

                Notification t = new Notification(mensaje);
                t.setDelayMsec(3000);
                t.setPosition(Position.MIDDLE_CENTER);
                t.show(mensaje);
                Resultado.setValue("La ip proporcionada se encuentra entre "+desde+" y "+hasta);
            }
            else {
                System.out.println(encontrada.toString());
                String aviso= "No encaja con ningún rango de IPs";
                Notification t = new Notification(aviso);
                t.setDelayMsec(3000);
                t.setPosition(Position.MIDDLE_CENTER);
                t.show(aviso);
                Resultado.setValue("No encaja con ningún rango de IPs");

            }

        });


        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button,l, Texto1, Calculo, Resultado);
        
        setContent(layout);
    }

    public static Ips Encontrar(long ip, ArrayList<Ips> lista){
        Ips nueva = new Ips();
        for (Ips i : lista){

            if (i.getIp_from()<= ip && i.getIp_to()>=ip){
                System.out.println("Encontrada");
                return i;

            }
        }
        return nueva;
    }

    public static String longToIp(long ip) {
        StringBuilder result = new StringBuilder(15);
        for (int i = 0; i < 4; i++) {
            result.insert(0, Long.toString(ip & 0xff));
            if (i < 3) {
                result.insert(0, '.');
            }
            ip = ip >> 8;
        }
        return result.toString();
    }

    public static Long Dot2LongIP(String dottedIP) {
        String[] addrArray = dottedIP.split("\\.");
        long num = 0;
        for (int i=0;i<addrArray.length;i++) {
            int power = 3-i;
            num += ((Integer.parseInt(addrArray[i]) % 256) *
                    Math.pow(256,power));
        }
        return num;
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
