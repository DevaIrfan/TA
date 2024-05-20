import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolve extends JFrame implements ActionListener {
    private JTextField[][] sudokuBoard;
    private JButton solveButton;
    private JButton clearButton;
    private int[][] board;
    private JLabel watermarkLabel;

    public SudokuSolve() {
        setTitle("Sudoku Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        solveButton = new JButton("Solve");
        solveButton.addActionListener(this);
        solveButton.setBackground(new Color(34, 139, 34));
        solveButton.setForeground(Color.WHITE);
        solveButton.setFont(new Font("Time New Roman", Font.BOLD, 16));
        buttonPanel.add(solveButton);

        clearButton = new JButton("Clear Board");
        clearButton.addActionListener(this);
        clearButton.setBackground(new Color(178, 34, 34));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(9, 9, 2, 2));
        boardPanel.setBackground(Color.DARK_GRAY);

        sudokuBoard = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard[i][j] = new JTextField(1);
                sudokuBoard[i][j].setHorizontalAlignment(JTextField.CENTER);
                sudokuBoard[i][j].setFont(new Font("Times New Roman", Font.BOLD, 20));
                if ((i / 3 + j / 3) % 2 == 0) {
                    sudokuBoard[i][j].setBackground(Color.WHITE);
                } else {
                    sudokuBoard[i][j].setBackground(new Color(220, 220, 220));
                }
                sudokuBoard[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boardPanel.add(sudokuBoard[i][j]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        watermarkLabel = new JLabel("Nama: Caesar Deva Irfan Putra | NIM: 21120123130062");
        watermarkLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        watermarkLabel.setForeground(Color.BLACK);
        watermarkLabel.setHorizontalAlignment(SwingConstants.LEFT);
        watermarkLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(watermarkLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == solveButton) {
            board = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String value = sudokuBoard[i][j].getText();
                    if (!value.isEmpty()) {
                        board[i][j] = Integer.parseInt(value);
                    } else {
                        board[i][j] = 0;
                    }
                }
            }

            if (solveSudoku(board)) {
                updateBoard();
            } else {
                JOptionPane.showMessageDialog(this, "No solution exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == clearButton) {
            clearBoard();
        }
    }

    private boolean solveSudoku(int[][] board) {
        int N = board.length;
        int[] emptyCell = findEmptyCell(board);
        int row = emptyCell[0];
        int col = emptyCell[1];

        if (row == -1 && col == -1) {
            return true;
        }

        for (int num = 1; num <= 9; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }

        return false;
    }

    private boolean isSafe(int[][] board, int row, int col, int num) {
        return !usedInRow(board, row, num) &&
                !usedInCol(board, col, num) &&
                !usedInBox(board, row - row % 3, col - col % 3, num);
    }

    private boolean usedInRow(int[][] board, int row, int num) {
        for (int col = 0; col < board.length; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInCol(int[][] board, int col, int num) {
        for (int row = 0; row < board.length; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInBox(int[][] board, int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + boxStartRow][col + boxStartCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[] findEmptyCell(int[][] board) {
        int[] cell = {-1, -1};
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == 0) {
                    cell[0] = row;
                    cell[1] = col;
                    return cell;
                }
            }
        }
        return cell;
    }

    private void updateBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard[i][j].setText(String.valueOf(board[i][j]));
            }
        }
    }

    private void clearBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard[i][j].setText("");
            }
        }
    }

    public static void main(String[] args) {

        new SudokuSolve();
    }
}
