import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;
import java.util.Queue;
class BSTree extends JPanel {
    BinaryNode root;

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

        JTextField deleteField = new JTextField();
        deleteField.setPreferredSize(new Dimension(100, 50));
        deleteField.setLocation(400,500);
        deleteField.addActionListener(e -> {
            int value = Integer.parseInt(deleteField.getText());
            remove(root,value);
            deleteField.setText("");
            repaint();
        });
        JLabel deleteLabel = new JLabel("Delete:");

        deleteLabel.setSize(100,500);
        deleteLabel.setVisible(true);

        JPanel deletePanel=new JPanel();
        deletePanel.setLayout(new GridLayout(1, 2));
        deletePanel.add(deleteLabel);
        deletePanel.add(deleteField);
        add(deletePanel);
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
        add(deleteField);
        add(inputField);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    BinaryNode insert(BinaryNode node, int key) {
        if (node == null) {
            return new BinaryNode(key);
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
        drawTree(g, root, getWidth() / 2, 100, getWidth()/4);
    }

    private void drawTree(Graphics g, BinaryNode node, int x, int y, int spacing) {
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

    public String preOrder(BinaryNode root) {
        if (root == null) {
            return "";
        }
        String output = root.key + " ";
        output += preOrder(root.left);
        output += preOrder(root.right);
        return output;
    }
    public String postOrder(BinaryNode root) {
        if (root == null) {
            return "";
        }
        String output = postOrder(root.left);
        output += postOrder(root.right);
        output += root.key + " ";
        return output;
    }
    public String inOrder(BinaryNode root) {
        if (root == null) {
            return "";
        }
        String output = inOrder(root.left);
        output += root.key + " ";
        output += inOrder(root.right);
        return output;
    }
    public String levelOrder(BinaryNode root) {
        String output = "";
        Queue<BinaryNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BinaryNode current = q.remove();
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
    private BinaryNode remove(BinaryNode startNode, int target)
    {
        BinaryNode nodeToRemove, inorderSuccessor;
        BinaryNode parent = search(startNode,target);

        if(parent == null) {
            return null;
        }

        //decide if it is a left or right child
        boolean isLeft = parent.left()!=null &&
                parent.left().getValue()==target;

        nodeToRemove = isLeft ? parent.left() : parent.right();

        //degree 0
        if(nodeToRemove.left() == null && nodeToRemove.right() == null)
        {
            if(isLeft)
                parent.setLeft(null);
            else
                parent.setRight(null);
            return nodeToRemove;
        }

        //degree 1
        else if(nodeToRemove.left() == null)
        {
            if(isLeft)
                parent.setLeft(nodeToRemove.right());
            else
                parent.setRight(nodeToRemove.right());
            nodeToRemove.setRight(null);
            return nodeToRemove;
        }
        else if(nodeToRemove.right() == null)
        {
            if(isLeft)
                parent.setLeft(nodeToRemove.left());
            else
                parent.setRight(nodeToRemove.left());
            nodeToRemove.setLeft(null);
            return nodeToRemove;
        }

        //degree 2
        else
        {
            inorderSuccessor = successor(nodeToRemove);
            swap(inorderSuccessor, nodeToRemove);
            if(nodeToRemove.right()==inorderSuccessor)
            {
                nodeToRemove.setRight(inorderSuccessor.left());
                inorderSuccessor.setRight(null);
                return inorderSuccessor;
            }
            return remove(nodeToRemove.right(), target);
        }

    }
    private BinaryNode search(BinaryNode parent, int target)
    {
        if(parent == null) return null;
        if(parent.left()!=null && parent.left().getValue()==(target)||
                parent.right()!=null && parent.right().getValue()==(target))
            return parent;
        else if(target<parent.getValue())
            return search(parent.left(),target);
        else
            return search(parent.right(),target);
    }

    private BinaryNode successor(BinaryNode k)
    {
        BinaryNode temp = k;
        temp = temp.right();
        while(temp.left() != null)
            temp = temp.left();
        return temp;
    }

    private void swap(BinaryNode x, BinaryNode y)
    {
        Comparable k = x.getValue();
        x.setValue(y.getValue());
        y.setValue((Integer) k);
    }

    class BinaryNode {
        int key;
        BinaryNode left, right;

        public BinaryNode(int item) {
            key = item;
            left = right = null;
        }
        public int getValue(){
            return key;
        }
        public void setValue(int k){
            key=k;
        }
        public BinaryNode left(){
            return left;
        }
        public BinaryNode right(){
            return right;
        }
        public void setLeft(BinaryNode k){
            left=k;
        }
        public void setRight(BinaryNode k){
            right=k;
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