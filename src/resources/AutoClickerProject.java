package resources;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClickerProject {


    public boolean autoClicker(boolean ativo) {

        if (ativo) {
            try {
                Robot robot = new Robot(); //A biblioteca Robot em Java permite a automação de interações do usuário com o teclado e o mouse
                int button = InputEvent.BUTTON1_DOWN_MASK; //// InputEvent serve para definir o botao
                robot.mousePress(button); //Este código simula o pressionamento de um botão do mouse.
                //Thread.sleep(50); //Este código faz o programa esperar por 100 milissegundos (ou 0,1 segundo).
                robot.mouseRelease(button); //Este código simula a liberação do botão do mouse que foi pressionado anteriormente.
                //Thread.sleep(50);
                return true;


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erro " + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }
}