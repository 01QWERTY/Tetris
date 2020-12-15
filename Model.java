/*
 * Copyright (c) 2020 Nurdin Abdrasulov
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Tetris
 * This simple game is written in java with the MVC pattern.
 */

import java.util.Random;

public class Model {

    private Viewer viewer;
    private int[][] positionOfBlocks;
    private Random random;
    private boolean isGamePaused;
    private boolean isGameOver;

    public Model(Viewer viewer){
        this.viewer = viewer;
        random = new Random();
        isGamePaused = false;

        positionOfBlocks = new int[][]{
                { 0,0,1,1,0,0,0,0,0,0},
                { 0,1,1,1,1,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0}
        };
    }

    public void doAction(String command)  {
        boolean isPossibleToMove = true;
        switch (command){

            case "left":
                if(!isGamePaused) {
                    loop:
                    for (int i = 0; i < positionOfBlocks.length; i++)
                        for (int j = 0; j < positionOfBlocks[i].length; j++)
                            if ((positionOfBlocks[i][j] == 1) && (j == 0 || positionOfBlocks[i][j - 1] == 5)) {
                                isPossibleToMove = false;
                                break loop;
                            }
                    if (isPossibleToMove)
                        for (int i = 0; i < positionOfBlocks.length; i++)
                            for (int j = 0; j < positionOfBlocks[i].length; j++)
                                if (positionOfBlocks[i][j] == 1 && j != 0) {
                                    positionOfBlocks[i][j - 1] = positionOfBlocks[i][j];
                                    positionOfBlocks[i][j] = 0;
                                }
                }
                break;

            case "right":
                if(!isGamePaused) {
                    loop:
                    for (int i = 0; i < positionOfBlocks.length; i++)
                        for (int j = 0; j < positionOfBlocks[i].length; j++)
                            if ((positionOfBlocks[i][j] == 1) && (j == positionOfBlocks[i].length - 1 || positionOfBlocks[i][j + 1] == 5)) {
                                isPossibleToMove = false;
                                break loop;
                            }
                    if (isPossibleToMove)
                        for (int i = 0; i < positionOfBlocks.length; i++)
                            for (int j = positionOfBlocks[i].length - 1; j >= 0; j--)
                                if (positionOfBlocks[i][j] == 1 && j != positionOfBlocks[i].length - 1) {
                                    positionOfBlocks[i][j + 1] = positionOfBlocks[i][j];
                                    positionOfBlocks[i][j] = 0;
                                }
                }
                break;

            case "down":
                if(!isGamePaused) {
                    loop: for (int c = 0; c < positionOfBlocks.length; c++) {
                        down();
                        if (isBlockInTheBottom())
                            break loop;
                    }
                }
                break;

            case "timerDown":
                if(!isGamePaused)
                    down();
                break;

            case "pauseOrResume":
                if(viewer.getButtonPause().getText().equals("Pause")){
                    viewer.getTimer().stop();
                    viewer.getButtonPause().setText("Resume");
                    isGamePaused = true;
                } else if(viewer.getButtonPause().getText().equals("Resume")) {
                    viewer.getTimer().start();
                    viewer.getButtonPause().setText("Pause");
                    isGamePaused = false;
                }
                break;

            case "startOver":
                startOver();
                break;

            case "exit":
                System.exit(0);
            case "setFocus":
                viewer.getJframe().setFocusable(true);
        }

        check();
        viewer.update();
    }

    private void startOver() {
        for(int i = 0; i < positionOfBlocks.length; i++)
            for(int j = 0; j < positionOfBlocks[i].length; j++)
                positionOfBlocks[i][j] = 0;

        isGameOver = false;
        nextBlock();
        viewer.getTimer().start();
    }


    /**
     * push down movables tetris blocks
     */
    private void down(){
     loop: for(int i = positionOfBlocks.length  - 1; i >= 0; i --)
            for (int j = 0; j < positionOfBlocks[i].length; j++)
                if( i == positionOfBlocks.length - 1 && positionOfBlocks[i][j] == 1 )
                    break loop;
                else if (positionOfBlocks[i][j] == 1 ) {
                    positionOfBlocks[i + 1][j] = positionOfBlocks[i][j];
                    positionOfBlocks[i][j] = 0;
                }
    }


    private void check(){

        //if block reached the bottom, make it immovable and show new block
        if(isBlockInTheBottom()) {
            for (int i = 0; i < positionOfBlocks.length; i++)
                for (int j = 0; j < positionOfBlocks[i].length; j++)
                    if(positionOfBlocks[i][j] == 1)
                        positionOfBlocks[i][j] = 5;

            if(!nextBlock()){
                viewer.update();
                viewer.getTimer().stop();

                String nextCommand = viewer.showOptionPane();
                if(nextCommand.equals("startOver"))
                    startOver();
                else if(nextCommand.equals("exit"))
                    System.exit(0);
            }
        }

                //check if the line is full of tetris blocks, then clear this line and push down blocks, that are higher
        for(int i = 0; i < positionOfBlocks.length; i++){
            boolean isLineFull = true;
            for(int j = 0; j < positionOfBlocks[i].length; j++)
                if(positionOfBlocks[i][j] != 5)
                    isLineFull = false;

                if(isLineFull){
                    for(int j = 0; j < positionOfBlocks[i].length; j++)
                        positionOfBlocks[i][j] = 0;
                    for(int ii = i; ii > 0; ii--)
                        for(int j = 0; j < positionOfBlocks[ii].length; j++)
                            positionOfBlocks[ii][j] = positionOfBlocks[ii-1][j];
                }
        }


        //check if the game is lost,
        // then show the user a jOptionpane to choose next action: close game or restart.
        if(isGameOver()){
            viewer.getTimer().stop();

            String nextCommand = viewer.showOptionPane();
            if(nextCommand.equals("startOver"))
                startOver();
            else if(nextCommand.equals("exit"))
                System.exit(0);
        }
    }


    /**
     * check if a movable block reached the bottom or other stopped block
     *
     * @return
     */
    private boolean isBlockInTheBottom(){
        boolean isBlockInTheBottom = false;

        loop: for(int i = positionOfBlocks.length - 1; i >= 0; i--)
            for (int j = 0; j < positionOfBlocks[i].length; j++)
                if (positionOfBlocks[i][j] == 1 && (i == positionOfBlocks.length - 1 || positionOfBlocks[i + 1][j] == 5)) {
                    isBlockInTheBottom = true;
                    break loop;
                }
        return isBlockInTheBottom;
    }

    /**
     * randomly create one tetris block
     * @return created tetris block
     */
    private int[][] getRandomTetrisBlock(){
        int res[][] = new int[1][];

        int val = random.nextInt(5);
        switch (val){
            case 0:
                res = new int[][]{
                        {1,1,0},
                        {0,1,1}
                };
                break;
            case 1:
                res = new int[][]{
                        {1},
                        {1},
                        {1}
                };
                break;
            case 2:
                res = new int[][]{
                        {1,1,1},
                        {1,1,1},
                        {1,1,1}
                };
                break;
            case 3:
                res = new int[][]{
                        {1}
                };
                break;
            case 4:
                res = new int[][]{
                        {0,1,0},
                        {1,1,1},
                };
                break;
        }

        return res;
    }

    /**
     * bring new tetris block to the game field
     */
    private boolean nextBlock(){
        if(!isGameOver) {
            int[][] nextBlock = getRandomTetrisBlock();
            for (int i = 0; i < nextBlock.length; i++)
                for (int j = 0; j < nextBlock[i].length; j++)
                    if(positionOfBlocks[i][j] == 0)
                        positionOfBlocks[i][j] = nextBlock[i][j];
                    else 
                        return false;
        }
        return true;
    }

    /**
     *
     * check if the game is lost,
     * @return true if the game is lost else false
     */
    private boolean isGameOver(){
        isGameOver = false;
        for(int j = 0; j < positionOfBlocks.length; j ++)
            if(positionOfBlocks[0][j] == 5) {
                isGameOver = true;
                break;
            }

        return isGameOver;
    }


    /**
     * The method returns positions of tetris block. The method is required for Canvas object to paint them.
     * @return positions of tetris block
     */
    public int[][] getPositionOfBlocks() {
        return positionOfBlocks;
    }

}
