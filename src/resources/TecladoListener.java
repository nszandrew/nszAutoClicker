package resources;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;

public class TecladoListener implements NativeKeyListener {
    private boolean ativo = false;
    private AutoClickerProject autoClicker;
    public int delay;


    public TecladoListener(AutoClickerProject autoClicker) {
        this.autoClicker = autoClicker;
    }


    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_X) { // NativeKeyEvent.VC_ para referenciar códigos de tecla
            ativo = !ativo;
            if (ativo) {
                System.out.println("Ativado");
                new Thread(this::loopAutoClicker).start(); // Inicia o loop em uma thread separada
            } else {
                autoClicker.autoClicker(false);
                System.out.println("Desativado");
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getCPS() {
        return delay * 1000;
    }


    private void loopAutoClicker() {
        while (ativo) {
            autoClicker.autoClicker(true);
            try {
                Thread.sleep(getDelay()); // Delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setDelay(getCPS() / 1000);
        }
    }



    public static void main(String[] args) {
        AutoClickerProject autoClicker = new AutoClickerProject();
        TecladoListener tecladoListener = new TecladoListener(autoClicker);


        String cps = JOptionPane.showInputDialog("Enter your desired CPS");

        int main = 0;
        try {
            if (cps == null || cps.equals("")) {
                JOptionPane.showMessageDialog(null, "Invalid Number \n12CPS by default");
                int tempoMilisegundos = 1000 / 12;
                tecladoListener.setDelay(tempoMilisegundos);
            } else {
                main = 1000 / Integer.parseInt(cps);
                tecladoListener.setDelay(main);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Number \n12CPS by default");
            int tempoMilisegundos = 1000 / 12;
            tecladoListener.setDelay(tempoMilisegundos);

        }

        GlobalScreen.addNativeKeyListener(tecladoListener);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("Error registering native hook: " + ex.getMessage());
            System.exit(1);
        }

        JFrame frame = new JFrame("nszAutoClicker 1.1 BETA");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/resources/AutoClick.png"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configura o tamanho e centraliza o JFrame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = 700;
        int frameHeight = 300;
        int x = (screenSize.width - frameWidth) / 2;
        int y = (screenSize.height - frameHeight) / 2;
        frame.setBounds(x, y, frameWidth, frameHeight);

        // Adiciona um JLabel com uma mensagem
        JLabel label = new JLabel("Welcome to nszAutoClicker");
        label.setFont(new Font("Arial", Font.BOLD, 16)); // Define a fonte e o tamanho do texto
        label.setHorizontalAlignment(JLabel.CENTER); // Centraliza o texto horizontalmente
        frame.add(label, BorderLayout.CENTER);

        // Adiciona um JLabel com instruções abaixo da mensagem de boas-vindas
        JLabel instructionLabel = new JLabel("Press 'X' to enable/disable Auto Clicker");
        instructionLabel.setHorizontalAlignment(JLabel.CENTER); // Centraliza o texto horizontalmente
        frame.add(instructionLabel, BorderLayout.NORTH);

        JLabel infoLabel = new JLabel(1000 / tecladoListener.getDelay()  + "CPS selected");
        infoLabel.setHorizontalAlignment(JLabel.CENTER); // Centraliza o texto horizontalmente
        frame.add(infoLabel, BorderLayout.SOUTH);


        // Torna o JFrame visível
        frame.setVisible(true);
    }
    }
