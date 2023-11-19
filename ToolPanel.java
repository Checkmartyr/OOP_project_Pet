import javax.swing.*;
// import javax.swing.plaf.TreeUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class ToolPanel extends JPanel{
    private Boolean vistBoolean = false;
    private Animationframe animation;
    private GPTChat gpt;
    private TextBox Box;

    public ToolPanel(){
    
    SwingUtilities.invokeLater(() -> {

    setBackground(new Color(0, 0, 0, 0));
    setMaximumSize(new Dimension(100,60));
    Box.setVisible(false);
    

    ChatButton ChatBT = new ChatButton();
    ChatBT.setButtonName("CHAT");
    ChatBT.setPanel(this);
    ChatBT.setTraget(this.gpt);


    WorkButton workBT = new WorkButton();
    workBT.setButtonName("Work");
    workBT.setTraget(this.animation);
    workBT.setTextBox(this.Box);

    ExitButton ExitBT = new ExitButton();
    ExitBT.setButtonName("Exit");
    ExitBT.setTraget(this.gpt);


    add(ChatBT,BorderLayout.WEST);
    add(workBT,BorderLayout.WEST);
    add(ExitBT,BorderLayout.WEST);
    setVisible(vistBoolean);
    });
    }  


    
    public void setVistBoolean(Boolean vistBoolean) {
        this.vistBoolean = vistBoolean;
        setVisible(vistBoolean);
    }

    public Boolean getVistBoolean() {
        return vistBoolean;
    }

    public void setAnimation(Animationframe animation) {
        this.animation = animation;
    }
    public void setGpt(GPTChat gpt) {
        this.gpt = gpt;
    }
    public void setBox(TextBox box) {
        Box = box;
    }
}





//Abatraction 

abstract class ToolsButton extends JButton {
    protected String ButtonName;

    public ToolsButton(){
        setBackground(new Color(0, 1, 0, 0.8f));
        setBounds(10,0,30,50);
        OnClick();
    }

    public void setButtonName(String buttonName) {
        this.ButtonName = buttonName;
        setText(ButtonName);
    }

    
    public void OnClick(){};
}



class ChatButton extends ToolsButton{
    private GPTChat traget;
    private ToolPanel panel;
    
    public void setTraget(GPTChat traget) {
        this.traget = traget;
    }
    public void setPanel(ToolPanel panel) {
        this.panel = panel;
    }


    @Override // Polymorphism  henthod Onclick form abstract class ToolsButton
    public void OnClick(){
        addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
                Point pos = panel.getLocationOnScreen();
                traget.Updatapostion(pos.x+300, pos.y-310);
                
                if(traget.getVistBoolean()){
                    traget.setVistBoolean(false);
                }
                else{
                    traget.setVistBoolean(true);
                }
            
            
            }
        });
    };
}

class ExitButton extends ToolsButton {
    private GPTChat traget;
    //encapsulation set method to set traget value
    public void setTraget(GPTChat traget) {
        this.traget = traget;
    }

    @Override // Polymorphism  henthod Onclick form abstract class ToolsButton
    public void OnClick(){
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                traget.Killpython();
                System.exit(0);
            }
        });
    }

}

class WorkButton extends ToolsButton{
    private Animationframe traget;
    private Process pythonProcess;
    private TextBox textBox;
    // private PrintWriter out;
    private BufferedReader in;
    private boolean mode;
    
    public void setTraget(Animationframe traget) {
        this.traget = traget;
        
    }

    public WorkButton(){
        mode = false;
        // setBackground(new Color(1, 0, 0, 0.8f));
    }

    public void setTextBox(TextBox textBox) {
        this.textBox = textBox;
    }
    @Override // Polymorphism  henthod Onclick form abstract class ToolsButton
    public void OnClick(){
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // traget.setCurAnimation(traget.getWork());
                if(!mode){
                    mode=true;
                    try {
                    pythonProcess = Runtime.getRuntime().exec("pythno puse_shorts_video.py");
                    // pythonProcess = Runtime.getRuntime().exec("puse_shorts_video.exe");
                    // pythonProcess.
                    System.out.println(mode);
                    System.out.println(pythonProcess.isAlive());
                    setBackground(new Color(1, 0, 0, 0.8f));
                    // i don't know if not have it,it will not work 
                    //form here
                    in = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream()));
                    
                    Thread readThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String line;
                                // String currAnimetion = "nomal";
                                boolean animetion_swapped = false;
                                while ((line = in.readLine()) != null) {
                                    System.out.println(mode);
                                    System.out.println(line);
                                    if (line.equals("[OS]")){
                                        if(!animetion_swapped){
                                            textBox.setVisible(true);
                                            textBox.printstream("Hey Master! You are open short video \n I'll close this in 1 minute. \n PLEASE REMEMBER ", 50,10000);
                                            traget.setCurAnimation(traget.getAngry());
                                            animetion_swapped = true;
                                        }

                                        if(traget.getCurAnimation() != traget.getAngry()){
                                            traget.setCurAnimation(traget.getAngry());
                                        }
                                        
                                    }
                                    else if(line.equals("[NOS]")){
                                        if(animetion_swapped){
                                        traget.setCurAnimation(traget.getNomal());
                                        textBox.setVisible(false);
                                        textBox.Clear();
                                        animetion_swapped= false;
                                        }
                                    }
                                }
                                 in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            
                        }
                        
                    });
                    readThread.start();
                    // to here don't touch it
                }
                catch (IOException eee) {
                        eee.printStackTrace();
                }
                }
                else{
                    // traget.setCurAnimation(traget.getNomal());
                    if (pythonProcess.isAlive()) {
                        pythonProcess.destroy();
                    }
                    mode=false;
                    System.out.println(mode);
                    setBackground(new Color(0, 1, 0, 0.8f));
                }

                            
            }
        });
    }
}