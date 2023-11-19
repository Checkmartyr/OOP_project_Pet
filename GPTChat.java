import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class GPTChat extends JFrame{
    // private JFrame mainFrame;
    private JTextField inputField;
    private JTextArea chatArea;
    private PrintWriter out;
    private BufferedReader in;
    private Boolean vistBoolean = false;
    private Process pythonProcess;
    private Animationframe anime;
    public GPTChat(){

        SwingUtilities.invokeLater(() -> {
        setSize(400, 370);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setUndecorated(true);
        setBackground(new Color(0, 200, 0, 50));
        setAlwaysOnTop(true);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBackground(new Color(39, 40, 51, 255));
        chatArea.setForeground(Color.white);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);
        chatArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
        inputField = new JTextField();
        
        // inputField.setLineWrap(true);
        // inputField.setWrapStyleWord(true);
        inputField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        inputField.setBackground(new Color(39, 40, 51, 255));
        inputField.setForeground(Color.white);
        // pack();


        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        add(inputField, BorderLayout.SOUTH);

        long delayMillis = 20;
        try {

            // Start the Python process
            pythonProcess = Runtime.getRuntime().exec("python_script.exe");
            out = new PrintWriter(pythonProcess.getOutputStream());
            in = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream()));

            // Create a thread to read messages from Python and display them in the chat area
            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    
                    try {
                        Boolean start = true;
                        
                        while(true){
                        String line;
                        

  
                        getStr:
                        while ((line = in.readLine()) != null) {
                            
                            if (line.equals("END")){
                                // start = true;
                                anime.setCurAnimation(anime.getNomal());
                                break getStr;
                            }//

                            // if(line.contains("```")){
                            //     if (codeChack) {
                            //         chatArea.setForeground(Color.white);
                            //         System.out.println("some code out");
                            //         codeChack = !codeChack;
                            //     }else{
                            //        chatArea.setForeground(Color.yellow); 
                            //     }
                            //     codeChack = !codeChack;
                            // }//

                            // if(start){
                            //     appendToChatArea("Pet: ");   
                            //     start = false;
                            // }


                            for (char c : line.toCharArray()) {
                                appendToChatArea(""+c);
                                try {
                                    TimeUnit.MILLISECONDS.sleep(delayMillis);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            appendToChatArealn("");
                            // System.out.println(line);
                        }
                        // System.out.println("i am out");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            
            readThread.start();
            // pythonProcess.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    }
    public void Killpython(){
        pythonProcess.destroy();
    }
    public void Updatapostion(int x, int y){
        
        System.out.println();
        setLocation(x, y);
    }
    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            // System.out.println("สวัสดี, โลก!");
            out.println(message);
            out.flush();
            appendToChatArealn("You: " + message);
            inputField.setText("");
        }
    }

    private void appendToChatArealn(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chatArea.append(message + "\n");
            }
        });
    
    }

    private void appendToChatArea(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chatArea.append(message);
            }
        });
    }

    public void setVistBoolean(Boolean vistBoolean) {
        this.vistBoolean = vistBoolean;
        setVisible(vistBoolean);
    }
    public Boolean getVistBoolean() {
        return vistBoolean;
    }
    public void setAnime(Animationframe anime) {
        this.anime = anime;
    }
}
