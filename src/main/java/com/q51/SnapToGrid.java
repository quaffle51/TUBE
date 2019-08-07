package com.q51;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnapToGrid extends JPanel implements MouseMotionListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] camera;
    private int[] mouse;
    private final int gridSize = 26;

    SnapToGrid() {
  
        camera = new int[2];
        mouse  = new int[2];
        setFocusable(true);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //g2.translate(-camera[0], -camera[1]);
        g2.translate(camera[0], camera[1]);

        //draw background
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
            {
                Color c = ((j*9) + i) % 2 == 0 ? Color.black : Color.white;
                g2.setColor(c);
                g2.fillRect(i*gridSize, j*gridSize, gridSize, gridSize);
            }
        g2.setColor(Color.blue);
        int[] snappedPos = getSnappedMousePosition();
        g2.fillRect(snappedPos[0], snappedPos[1], gridSize, gridSize);
    }

    private int[] getSnappedMousePosition() {
        return new int[] {
            camera[0] + mouse[0] - ((camera[0] + mouse[0]) % gridSize),
            camera[1] + mouse[1] - ((camera[1] + mouse[1]) % gridSize)  
        };
    }

    public static void main (String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SnapToGrid());
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        //camera[0] -= e.getX() - mouse[0]; 
//        //camera[1] -= e.getY() - mouse[1];
//        camera[0] += e.getX() - mouse[0]; 
//        camera[1] += e.getY() - mouse[1];
//
//        mouse[0] = e.getX();
//        mouse[1] = e.getY();
//        repaint();
//
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse[0] = e.getX();
        mouse[1] = e.getY();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(gridSize * 18, gridSize * 18);
    }

}
