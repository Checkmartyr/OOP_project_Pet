import javax.swing.*;
import java.awt.*;
// import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class TextBox extends JFrame {
    private JTextArea textArea;
    private Boolean vistBoolean = false;
    private boolean printingInProgress = false;

    public TextBox(){
        setUndecorated(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        
        textArea = new JTextArea(3,16);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(39, 40, 51, 255));
        textArea.setForeground(Color.white);
        textArea.setBorder(BorderFactory.createEmptyBorder());
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 20));

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateHeight();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateHeight();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateHeight();
            }

            private void updateHeight() {
                SwingUtilities.invokeLater(() -> {
                if (textArea.getLineCount()%7 == 0 ) {
                    textArea.setText("");
                }
                pack();
            });
            }
        });

        JPanel panel = new JPanel();
        panel.add(textArea);
        
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public void printlnstream(String message, int delayMillis) {
        SwingUtilities.invokeLater(() -> {
            if (!printingInProgress) {
                printingInProgress = true;

                Thread appThread = new Thread(() -> {
                    for (char c : message.toCharArray()) {
                        textArea.append("" + c);
                        try {
                            TimeUnit.MILLISECONDS.sleep(delayMillis);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    textArea.append("\n");
                    printingInProgress = false;
                });

                appThread.start();
            }
        });
    }

    public void printstream(String message, int delayMillis , int timeout) {
        SwingUtilities.invokeLater(() -> {
            if (!printingInProgress) {
                printingInProgress = true;

                Thread appThread = new Thread(() -> {
                    for (char c : message.toCharArray()) {
                        textArea.append("" + c);
                        try {
                            TimeUnit.MILLISECONDS.sleep(delayMillis);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    printingInProgress = false;
                });
                appThread.start();

                Timer timeoutTimer = new Timer(timeout, evt -> {
                    setVisible(false);
                    Clear();
                    printingInProgress = false;
                });
                timeoutTimer.setRepeats(false);
                timeoutTimer.start();
                
            }
        });
    }
    public void Clear(){
        textArea.setText("");
    }
    public void setVistBoolean(Boolean vistBoolean) {
        this.vistBoolean = vistBoolean;
        setVisible(vistBoolean);
    }
    public Boolean getVistBoolean() {
        return vistBoolean;
    }
    public void Updatapostion(int x, int y){
        System.out.println();
        setLocation(x, y);
    }

    // public static void main(String[] args) {
    //     TextBox box =  new TextBox();
    //     box.printstream("Text Length in Each Line: 1\r\n" ,50);
    //     box.printstream("Text Length in Each Line: 2\r\n" ,50);
    //     box.printstream("Text Length in Each Line: 3\r\n" ,50);
    // }
}
