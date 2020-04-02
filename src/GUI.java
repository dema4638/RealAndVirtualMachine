import javax.swing.*;
import java.awt.event.*;

public class GUI {

    private JButton b;

    public void createGUI() {
        JFrame f = new JFrame();

        b = new JButton("Start VM");//creating instance of JButton
        b.setBounds(130, 100, 100, 40);//x axis, y axis, width, height

        f.add(b);//adding button in JFrame

        f.setSize(400, 500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               b.setVisible(false);
            }
        });
    }
}
