import sun.util.locale.provider.SPILocaleProviderAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainTwo extends JFrame {

    Container mainPane;
    JPanel numberInput;
    private CardLayout card;
    private JTextField nField;
    private JButton nButton, back, sum;
    private static int N;
    private JPanel tableDisplay;
    private java.util.List<JTextField> list;
    private SpringLayout springLayout;
    private JLabel resultLabels[];

    MainTwo() {
        super("Table Addition");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        setSize(ge.getMaximumWindowBounds().width, ge.getMaximumWindowBounds().height);
        mainPane = getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        placeUIComponents();
        setVisible(true);
    }

    private void placeUIComponents() {

        list = new ArrayList<JTextField>();
        card = new CardLayout();
        mainPane.setLayout(card);

        //// First Card: Number Input  ////
        numberInput = new JPanel();
        numberInput.setSize(mainPane.getSize());
        mainPane.add("NumberInput", numberInput);
        card.show(mainPane, "NumberInput");
        numberInput.add(new JLabel("Number of rows: "));
        nField = new NumberField(10, false, false);
        numberInput.add(nField);
        nButton = new JButton("Continue");
        numberInput.add(nButton);
        nField.addActionListener(e -> {
            String n = nField.getText();
            N=0;
            if (!"".equals(n))
                N = Integer.parseInt(n);
            System.out.println(N + " is set for N");
            card.next(mainPane);
            drawTable();
        });
        nButton.addActionListener(e -> {
            String n = nField.getText();
            N=0;
            if (!"".equals(n))
                N = Integer.parseInt(n);
            System.out.println(N + " is set for N");
            card.next(mainPane);
            drawTable();
        });

        ////  Second Card: Table Display  ////
        tableDisplay = new JPanel();
        tableDisplay.setSize(mainPane.getSize());
        springLayout = new SpringLayout();
        tableDisplay.setLayout(springLayout);
        back = new JButton("back");
        back.addActionListener(e -> {
            card.next(mainPane);
        });
        springLayout.putConstraint(SpringLayout.WEST, back, 100, SpringLayout.WEST, tableDisplay);
        springLayout.putConstraint(SpringLayout.NORTH, back, 20, SpringLayout.NORTH, tableDisplay);
        tableDisplay.add(back);
        sum = new JButton(" sum");
        sum.addActionListener(e -> {
            doAddition();
        });
        tableDisplay.add(sum);
        springLayout.putConstraint(SpringLayout.WEST, sum, 20, SpringLayout.EAST, back);
        springLayout.putConstraint(SpringLayout.NORTH, sum, 20, SpringLayout.NORTH, tableDisplay);
        mainPane.add("TableDisplay", tableDisplay);


    }

    private void doAddition() {
        JLabel res;
        String string;
        double colsum[]=new double[]{0,0,0,0};
        double rowsum=0, nums=0;
        int i=0, resLblCount=0;
        for(JTextField cell:list) {
            nums=0;
            string=cell.getText();
            if(!string.equals(""))
                nums=Double.parseDouble(string);
            colsum[i%4]=colsum[i%4]+nums;
            rowsum=rowsum+nums;
            resultLabels[resLblCount].setText(""+rowsum);
            resLblCount++;
            i++;
        }
        i=0;
        while (resLblCount<resultLabels.length) {
            resultLabels[i].setText(""+colsum[i]);
        }
    }

    public void drawTable() {
        for(JTextField jt:list)
            tableDisplay.remove(jt);
        JTextField num[] = new NumberField[4], col[] = new JTextField[4];
        JComponent backupCol;
        for (int i = 0; i < 5; i++) {
            col[i] = new JTextField(10);
            col[i].setText("Column " + (i + 1));
            col[i].setEnabled(false);
            springLayout.putConstraint(SpringLayout.NORTH, col[i], 10, SpringLayout.SOUTH, back);
        }
        backupCol=col[3];
        springLayout.putConstraint(SpringLayout.WEST, col[0], 50, SpringLayout.WEST, tableDisplay);
        tableDisplay.add(col[0]);
        for (int i = 1; i < 5; i++) {
            springLayout.putConstraint(SpringLayout.WEST, col[i], 2, SpringLayout.EAST, col[i - 1]);
            tableDisplay.add(col[i]);
        }

        for (int j=0;j<N;j++)
            for (int i=0;i<4;i++) {
                num[i]=new NumberField(10, true, true);
                springLayout.putConstraint(SpringLayout.WEST, num[i], 0, SpringLayout.WEST, col[i]);
                springLayout.putConstraint(SpringLayout.NORTH, num[i], 2, SpringLayout.SOUTH, col[i]);
                tableDisplay.add(num[i]);
                list.add(num[i]);
                col[i]=num[i];
            }
            /*
        resultLabels=new JLabel[N+4];
        for(int i=0;i<N;i++) {
            resultLabels[i]=new JLabel("");
            springLayout.putConstraint(SpringLayout.WEST, resultLabels[i], 0, SpringLayout.WEST, backupCol);
            springLayout.putConstraint(SpringLayout.NORTH, resultLabels[i], 2, SpringLayout.SOUTH, backupCol);
            backupCol=resultLabels[i];
            tableDisplay.add(resultLabels[i]);
        }
        for(int i=N;i<N+4;i++) {
            resultLabels[i]=new JLabel("");
            backupCol=list.get(((N-1)*4)+i);
            springLayout.putConstraint(SpringLayout.WEST, resultLabels[i], 2, SpringLayout.WEST, backupCol);
            springLayout.putConstraint(SpringLayout.NORTH, resultLabels[i], 2, SpringLayout.SOUTH, backupCol);
            tableDisplay.add(resultLabels[i]);
        }*/
    }
    public static void main(String ar[]) {
        MainTwo m = new MainTwo();
    }
}
