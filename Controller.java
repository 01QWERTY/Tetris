/*
 * Copyright (c) 2020 Nurdin Abdrasulov
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Tetris
 * This simple game is written in java with the MVC pattern.
 */

import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class Controller implements KeyListener, ActionListener {
    private Model model;

    public Controller(Viewer viewer){
        model = new Model(viewer);
    }

    public Model getModel() {
        return model;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String command = "";
        switch (e.getKeyCode()){
            case 37:
                command = "left";
                break;
            case 39:
                command = "right";
                break;
            case 40:
                command = "down";
                break;
            default:
                return;
        }
        model.doAction(command);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        model.doAction(command);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }



}
