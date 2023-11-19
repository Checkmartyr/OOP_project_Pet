import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame{

public MainApp(){
    GPTChat Gpt = new GPTChat();
    ToolPanel too = new ToolPanel();
    TextBox Box = new TextBox(); 
    Animationframe pet = new Animationframe(this,too,Gpt,Box);
    too.setGpt(Gpt);
    too.setAnimation(pet);
    too.setBox(Box);
    Gpt.setAnime(pet);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setAlwaysOnTop(true);
    setSize(300, 300);
    setUndecorated(true);
    setBackground(new Color(0, 0, 0, 0));
    add(pet,BorderLayout.CENTER);
    add(too,BorderLayout.SOUTH);
    
    
}
 public static void main(String[] args) {
    new MainApp();
 }

}



