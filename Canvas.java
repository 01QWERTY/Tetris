/*
 * Copyright (c) 2020 Nurdin Abdrasulov
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Tetris
 * This simple game is written in java with the MVC pattern.
 */

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class Canvas extends JPanel {
    private Model model;

    Canvas(Model model) {
        this.model = model;
        setOpaque(true);
        setBackground(Color.black);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int x = 50;
        int y = 50;
        int width = 50;
        int height = 50;
        int offset = 5;

        int[][] positionOfBlocks = model.getPositionOfBlocks();

        for(int i = 0; i < positionOfBlocks.length;i++) {
            for(int j = 0; j < positionOfBlocks[i].length;j++) {
                g.setColor(Color.white);
                g.drawRect(x, y,width,height);

                if( positionOfBlocks[i][j] == 1){
                    g.setColor(Color.green);
                    g.fillRect(x,y,width,height);
                } else if( positionOfBlocks[i][j] == 5){
                    g.setColor(Color.red);
                    g.fillRect(x,y,width,height);
                }
                x = x + width + offset;
            }
            x = 50;
            y = y + height + offset;
        }
    }

}