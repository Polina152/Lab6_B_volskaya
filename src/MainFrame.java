import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

public class MainFrame extends JFrame {
    private int width = 500;
    private int height = 450;
    private int time=0;

    private JMenuBar menu = new JMenuBar();
    private JMenu ballsMenu = new JMenu("ћ€чи");
    private JMenu controlMenu = new JMenu("”правление");

    private Field field=new Field();

    private JMenuItem newBallMenuItem;
    private JCheckBoxMenuItem stopSlowBalls;
    private JButton speedPlus = new JButton("-");
    private JButton speedMinus = new JButton("+");
    private JButton pause = new JButton("Pause");

    MainFrame() {
        field.setPreferredSize(new Dimension(width, height));
        setTitle("lAB 6");
        setJMenuBar(menu);
        menu.add(ballsMenu);
        menu.add(controlMenu);
        newBallMenuItem = ballsMenu.add(newBallMenuItemAction);

        stopSlowBalls=new JCheckBoxMenuItem("ќстановить медленные м€чи");
        controlMenu.add(stopSlowBalls);
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalStrut(width / 2));
        buttonBox.add(speedMinus);
        buttonBox.add(Box.createHorizontalStrut(5));
        buttonBox.add(speedPlus);
        buttonBox.add(Box.createHorizontalStrut(5));
        buttonBox.add(pause);

        speedMinus.addActionListener(new SpeedMinusListener());
        speedPlus.addActionListener(new SpeedPlusListener());
        pause.addActionListener(new PauseListener());
        addMouseMotionListener(new MouseListener() );
        stopSlowBalls.addItemListener(new StopSlowBallsListener());
        add(field, BorderLayout.CENTER);
        add(buttonBox, BorderLayout.SOUTH);
        pack();
    }

    Action newBallMenuItemAction = new AbstractAction("ƒобавить м€ч") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            field.AddBall();
        }
    };

    class charismaMenuItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            JCheckBoxMenuItem helper=(JCheckBoxMenuItem) itemEvent.getSource();
            if(helper.isSelected()){
                System.out.println("charisma on");
                field.IsCharismaOn();
            }
            else{
                System.out.println("charisma off");
                field.IsCharismaOff();
            }
        }
    }

    class grossFeederMenuItemListener implements ItemListener {
    @Override
     public void itemStateChanged(ItemEvent itemEvent) {
        JCheckBoxMenuItem helper=(JCheckBoxMenuItem) itemEvent.getSource();
        if(helper.isSelected()){
            System.out.println("grossFeeder on");
            field.IsGrossFeederOn();
            field.AddBall(120);
        }
        else{
            System.out.println("grossFeeder off");
            field.IsGrossFeederOff();
        }
     }
    }

    class SpeedPlusListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ++time;
            field.SetTimeMachine(time);
        }
    }
    class SpeedMinusListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            --time;
            field.SetTimeMachine(time);
        }
    }
    class PauseListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(pause.getText().equals("Pause")){
            field.pause();
            pause.setText("Continue");
            }else{
                field.resume();
                pause.setText("Pause");
            }
        }
    }

    class MouseListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
        }
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            field.SetCords(mouseEvent.getX(),mouseEvent.getY());
        }
    }

    class StopSlowBallsListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            JCheckBoxMenuItem helper=(JCheckBoxMenuItem) itemEvent.getSource();
            if(helper.isSelected()){
                System.out.println("StopSlowBallsOn");
                field.IsStopSlowBallsOn();
            }
            else{
                System.out.println("StopSlowBallsOff");
                field.IsStopSlowBallsOff();
            }
        }
    }
}