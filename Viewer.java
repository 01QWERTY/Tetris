/*
 * Copyright (c) 2020 Nurdin Abdrasulov
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Tetris
 * This simple game is written in java with the MVC pattern.
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;
import java.awt.Color;

public class Viewer {
    private Canvas canvas;
    private Timer timer;
    private JButton buttonPause;
    private JButton buttonStartOver;
    private JFrame frame;

    public  Viewer(){
        Controller controller = new Controller(this);
        Model model = controller.getModel();
        canvas = new Canvas(model);
        canvas.setFocusable(true);

        buttonPause = new JButton("Pause");
        buttonPause.addActionListener(controller);
        buttonPause.setActionCommand("pauseOrResume");
        buttonPause.setBackground(Color.DARK_GRAY);
        buttonPause.setForeground(Color.white);
        buttonPause.setFocusable(false);

        buttonStartOver = new JButton("Start over");
        buttonStartOver.addActionListener(controller);
        buttonStartOver.setActionCommand("startOver");
        buttonStartOver.setBackground(Color.DARK_GRAY);
        buttonStartOver.setForeground(Color.white);
        buttonStartOver.setFocusable(false);


        JPanel panel = new JPanel();
        panel.setBackground(Color.yellow);
        panel.add(buttonPause);
        panel.add(buttonStartOver);

        frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(650, 700);
        frame.setLocation(300, 50);
        frame.addKeyListener(controller);
        frame.add("North", panel);
        frame.add("Center",canvas);
        frame.setVisible(true);
        frame.setFocusable(true);

        timer = new Timer(800,controller);
        timer.setActionCommand("timerDown");
        timer.start();
    }

    public void update() {
        canvas.repaint();
    }

    public Timer getTimer(){ return timer;}

    public JButton getButtonPause(){
        return buttonPause;
    }

    public String showOptionPane() {
        String options[] = {"Start over", "Close game"};
        int selectedOption = JOptionPane.showOptionDialog(frame, "Game over!",
                "\t\t!!!Tetris!!!",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]);

        if( selectedOption == 0)
            return "startOver";
        else if( selectedOption == 1)
            return "exit";
        System.out.println(selectedOption);
        return "";
    }
}
