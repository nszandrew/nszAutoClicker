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


    public TecladoListener(AutoClickerProject autoClicker) {
        this.autoClicker = autoClicker;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_X) { // Use NativeKeyEvent.VC_ para referenciar códigos de tecla
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

    // Método para o loop do auto clicker
    private void loopAutoClicker() {
        while (ativo) {
            autoClicker.autoClicker(true);
            try {
                Thread.sleep(80); // Delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        AutoClickerProject autoClicker = new AutoClickerProject();
        TecladoListener tecladoListener = new TecladoListener(autoClicker);

        GlobalScreen.addNativeKeyListener(tecladoListener);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("Erro ao registrar o gancho nativo: " + ex.getMessage());
            System.exit(1);
        }

        JFrame frame = new JFrame("nszAutoClicker 1.0 BETA");
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
        JLabel label = new JLabel("Bem-vindo ao nszAutoClicker");
        label.setFont(new Font("Arial", Font.BOLD, 16)); // Define a fonte e o tamanho do texto
        label.setHorizontalAlignment(JLabel.CENTER); // Centraliza o texto horizontalmente
        frame.add(label, BorderLayout.CENTER);

        // Adiciona um JLabel com instruções abaixo da mensagem de boas-vindas
        JLabel instructionLabel = new JLabel("Pressione 'X' para ativar/desativar o Auto Clicker");
        instructionLabel.setHorizontalAlignment(JLabel.CENTER); // Centraliza o texto horizontalmente
        frame.add(instructionLabel, BorderLayout.NORTH);

        JLabel infoLabel = new JLabel("12CPS");
        infoLabel.setHorizontalAlignment(JLabel.CENTER); // Centraliza o texto horizontalmente
        frame.add(infoLabel, BorderLayout.SOUTH);



        // Torna o JFrame visível
        frame.setVisible(true);
        }
    }
