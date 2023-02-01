import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;
import java.util.Queue;
class BSTree extends JPanel {
    Node root;

    public BSTree() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1440, 1024));

        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(100, 30));
        inputField.addActionListener(e -> {
            int value = Integer.parseInt(inputField.getText());
            root = insert(root, value);
            inputField.setText("");
            repaint();
        });
        JButton preOrderButton = new JButton("Pre-Order");
        preOrderButton.addActionListener(e -> preOrderTraversal());

        JButton postOrderButton = new JButton("Post-Order");
        postOrderButton.addActionListener(e -> postOrderTraversal());

        JButton levelOrderButton = new JButton("Level-Order");
        levelOrderButton.addActionListener(e -> levelOrderTraversal());

        JButton inOrderButton = new JButton("In-Order");
        inOrderButton.addActionListener(e -> inOrderTraversal());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        preOrderButton.setFocusable(false);
        levelOrderButton.setFocusable(false);
        inOrderButton.setFocusable(false);
        postOrderButton.setFocusable(false);


        buttonPanel.add(preOrderButton);
        buttonPanel.add(postOrderButton);
        buttonPanel.add(levelOrderButton);
        buttonPanel.add(inOrderButton);

        add(inputField);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }
        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        }
        return node;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTree(g, root, getWidth() / 2, 100, 360);
    }

    private void drawTree(Graphics g, Node node, int x, int y, int spacing) {
        if (node == null) {
            return;
        }
        g.setColor(new Color(0xACACAC));
        g.fillOval(x - 10, y - 10, 30, 30);
        g.setColor(Color.black);
        g.drawString(Integer.toString(node.key), x - 4, y + 10);
        if (node.left != null) {
            g.drawLine(x, y+15, x - spacing, y + 50);
            drawTree(g, node.left, x - spacing, y + 50, spacing / 2);
        }
        if (node.right != null) {
            g.drawLine(x, y+15, x + spacing, y + 50);
            drawTree(g, node.right, x + spacing, y + 50, spacing / 2);
        }
    }
    public void preOrderTraversal() {
        String output = preOrder(root);
        JOptionPane.showMessageDialog(this, output,"Pre Order Traversal",JOptionPane.INFORMATION_MESSAGE);
    }

    public void postOrderTraversal() {
        String output = postOrder(root);

        JOptionPane.showMessageDialog(this, output,"Post Order Traversal",JOptionPane.INFORMATION_MESSAGE);
    }

    public void levelOrderTraversal() {
        String output = levelOrder(root);
        JOptionPane.showMessageDialog(this, output,"Level Order Traversal",JOptionPane.INFORMATION_MESSAGE);
    }
    public void inOrderTraversal() {
        String output = inOrder(root);
        JOptionPane.showMessageDialog(this, output,"In Order Traversal",JOptionPane.INFORMATION_MESSAGE);
    }

    public String preOrder(Node root) {
        if (root == null) {
            return "";
        }
        String output = root.key + " ";
        output += preOrder(root.left);
        output += preOrder(root.right);
        return output;
    }
    public String postOrder(Node root) {
        if (root == null) {
            return "";
        }
        String output = postOrder(root.left);
        output += postOrder(root.right);
        output += root.key + " ";
        return output;
    }
    public String inOrder(Node root) {
        if (root == null) {
            return "";
        }
        String output = inOrder(root.left);
        output += root.key + " ";
        output += inOrder(root.right);
        return output;
    }
    public String levelOrder(Node root) {
        String output = "";
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node current = q.remove();
            output += current.key + " ";
            if (current.left != null) {
                q.add(current.left);
            }
            if (current.right != null) {
                q.add(current.right);
            }
        }
        return output;
    }

    class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Binary Search Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new BSTree());
        frame.pack();
        frame.setVisible(true);
    }
}