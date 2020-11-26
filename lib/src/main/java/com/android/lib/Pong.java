package com.android.lib;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Pong extends JPanel implements KeyListener, ActionListener {
    // 遊戲畫面大小
    final int WIDTH = 600;
    final int HEIGHT = 600;

    // 球的初始位置
    final int INIT_Y_POS = HEIGHT / 2;
    final int INIT_X_POS = WIDTH / 2;

    // 擊球板子的大小
    final int PAD_WIDTH = 20;
    final int PAD_HEIGHT = 100;
    final int PLAYER_NUM = 2;
    final int PAD_OFFSET = 10;

    // 更新球位置的計時器
    Timer ballTimer;

    // 球移動的速度
    int ballSpeedX = 1, ballSpeedY = 1;
    final int DELAY_MS = 10;

    // 球的位置
    int ballPosX = INIT_X_POS;
    int ballPosY = INIT_Y_POS;

    // 球的大小
    final int BALL_RADIUS = 20;

    // 玩家移動板子的速度
    int playerSpeedY = 20;

    // 玩家板子的位置
    int[] playerPosX = new int[PLAYER_NUM];
    int[] playerPosY = new int[PLAYER_NUM];

    // 玩家分數
    int[] playerScore = new int[PLAYER_NUM];

    public Pong() {
        JFrame frame = new JFrame();
        JButton button = new JButton("restart");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.add(button, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restart();
                Pong.this.requestFocus();
            }
        });
        this.addKeyListener(this);
        this.setFocusable(true);
        initPlayerPos();
        initPlayerScore();
        initBallTimer();
    }

    private void initBallTimer() {
        ballTimer = new Timer(DELAY_MS, this);
        ballTimer.setInitialDelay(190);
        ballTimer.start();
    }

    private void initPlayerScore() {
        for (int i = 0; i < PLAYER_NUM; i++) {
            playerScore[i] = 0;
        }
    }

    private void initPlayerPos() {
        for (int i = 0; i < PLAYER_NUM; i++) {
            playerPosY[i] = INIT_Y_POS;
        }

        playerPosX[0] = PAD_WIDTH - PAD_OFFSET;
        playerPosX[1] = WIDTH - PAD_WIDTH - PAD_OFFSET;
    }

    public void update(Graphics g) {
        repaint();
    }

    // defines what we want to happen anytime we draw the game
    // you simply need to fill in a few parameters here
    public void paint(Graphics g) {
        super.paint(g);
        drawPlayerPad(g);
        drawBall(g);
        drawScore(g);
    }

    private void drawPlayerPad(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(playerPosX[0], playerPosY[0], PAD_WIDTH, PAD_HEIGHT);
        g.setColor(Color.BLUE);
        g.fillRect(playerPosX[1], playerPosY[1], PAD_WIDTH, PAD_HEIGHT);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillOval(ballPosX, ballPosY, BALL_RADIUS, BALL_RADIUS);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.red);
        g.drawString("P1 Score:" + playerScore[0], playerPosX[0], 50);
        g.drawString("P2 Score: " + playerScore[1], playerPosX[1] - 50, 50);
    }

    public static void main(String[] args) {
        new Pong().show();
    }

    // defines what we want to happen if a keyboard button has
    // been pressed - you need to complete this
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // changes paddle direction based on what button is pressed
        if (keyCode == KeyEvent.VK_DOWN)
            // fill this in
            playerPosY[1] += playerSpeedY;
        if (keyCode == KeyEvent.VK_UP)
            // fill this in
            playerPosY[1] -= playerSpeedY;
        if (e.getKeyChar() == 'w')
            // move paddle down
            playerPosY[0] -= playerSpeedY;
        if (e.getKeyChar() == 's')
            // fill this in
            playerPosY[0] += playerSpeedY;
        // turn 1-player mode on
//        if (e.getKeyChar() == '1')
//            // fill this in
//
//            // turn 2-player mode on
//            if (e.getKeyChar() == '2')
//            // fill this in
//            {
//
//            }
        checkPadPosRange();
        repaint();
    }

    // restarts the game, including scores
    public void restart() {
        // your code here
        initPlayerPos();
        initPlayerScore();
        ballPosX = INIT_X_POS;
        ballPosY = INIT_Y_POS;
        repaint();
    }

    // 檢查板子是否超出遊戲畫面的範圍
    private void checkPadPosRange() {
        for (int i = 0; i < PLAYER_NUM; i++) {
            if (playerPosY[i] < 0) playerPosY[i] = 0;
            if (playerPosY[i] > HEIGHT - PAD_HEIGHT) playerPosY[i] = HEIGHT - PAD_HEIGHT;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballPosX += ballSpeedX;
        ballPosY += ballSpeedY;

        // 球是否碰到遊戲畫面邊界
        if (ballPosX >= WIDTH - BALL_RADIUS || ballPosX <= 0) {
            ballSpeedX = -ballSpeedX;

            if (ballPosX <= 0) {
                playerScore[1]++;
            } else {
                playerScore[0]++;
            }
        }

        if (ballPosY >= HEIGHT - BALL_RADIUS || ballPosY <= BALL_RADIUS) ballSpeedY = -ballSpeedY;

        // 球是否碰到左邊板子
        if (ballPosX <= playerPosX[0] + PAD_WIDTH && ballPosX >= playerPosX[0] &&
                ballPosY <= playerPosY[0] + PAD_HEIGHT && ballPosY >= playerPosY[0])
            ballSpeedX = -ballSpeedX;

        // 球是否碰到右邊板子
        if (ballPosX <= playerPosX[1] - BALL_RADIUS + PAD_WIDTH && ballPosX >= playerPosX[1] - BALL_RADIUS &&
                ballPosY <= playerPosY[1] + PAD_HEIGHT && ballPosY >= playerPosY[1])
            ballSpeedX = -ballSpeedX;

        this.repaint();
    }
}