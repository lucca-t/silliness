import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.*;

public class RBTreeDisplay extends JPanel {
    RedBlackTree tree;
    int diameter = 40;

    public RBTreeDisplay() {
        setBackground(new Color(0xB6B6B6));
        setPreferredSize(new Dimension(1440, 1024));

        tree = new RedBlackTree();

        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(100, 30));
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(inputField.getText());
                tree.insert(value);
                inputField.setText("");
                repaint();
            }
        });

        add(inputField);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintTree(g, tree.root, getWidth() / 2, 100, getWidth() / 4);
    }

    private void paintTree(Graphics g, Node node, int x, int y, int gap) {
        if (node == null)
            return;

        g.setColor(node.isRed ? Color.RED : Color.BLACK);
        g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(node.data), x - 5, y + 5);

        if (node.left != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x - diameter / 2, y + diameter / 2, x - gap, y + 2 * diameter);
            paintTree(g, node.left, x - gap, y + 2 * diameter, gap / 2);
        }
        if (node.right != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x + diameter / 2, y + diameter / 2, x + gap, y + 2 * diameter);
            paintTree(g, node.right, x + gap, y + 2 * diameter, gap / 2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new RBTreeDisplay());
        frame.pack();
        frame.setVisible(true);
    }

}