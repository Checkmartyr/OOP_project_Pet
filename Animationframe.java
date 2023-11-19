import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import java.net.URL;
import java.io.*;
import java.util.ArrayList;


class Animationframe extends JLabel{
    private Point mouseClickOffset;
    private ArrayList<ImageIcon> curAnimation;
    private ArrayList<ImageIcon> nomal;
    private ArrayList<ImageIcon> raise;
    private ArrayList<ImageIcon> angry;
    private ArrayList<ImageIcon> work;
    // private MainApp main;
    // private contPanel too;

    public Animationframe(MainApp main,ToolPanel too, GPTChat a,TextBox Box){
        SwingUtilities.invokeLater(() -> {
            // load animation image
            // this.main = main;
            // this.too = too;
            // TextBox preparingBox =  new TextBox();

            // preparingBox.printstream("Preparing", 50);
            
            // preparingBox.printstream("Animation londing ", 50);

            setNomal(imageList("Nomal")); 
            setRaise(imageList("Raise"));
            setAngry(imageList("Down")); 
            setWork(imageList("Work"));

            // preparingBox.printstream("Animation londing complete", 50);
            
            // JLabel imageLabel = new JLabel();
            main.setVisible(true);
            Point pos = main.getLocationOnScreen();
            Box.Updatapostion(pos.x-250, pos.y+50);
            // curAnimation = nomal;
            setCurAnimation(nomal);
                       // Thread animationThread = new Thread(() -> {    
            Timer timer = new Timer(200, new ActionListener() {
                    private int i = 0;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(i >= 100){
                            i =0;
                        } 
                        setIcon(curAnimation.get(i%curAnimation.size()));
                        i++;
                    }
                });
                timer.start();
                
                // timer.stop();
                // timer.start();
            // });  
            // animationThread.start();
            main.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {

                        if(e.getButton()==1){
                            setCurAnimation(raise);
                            // curAnimation = raise;
                        }
                        else if (e.getButton() == 3){

                            if (too.getVistBoolean()) {
                                too.setVistBoolean(false);
                                main.setSize(300,300);
                            }
                            else {
                                too.setVistBoolean(true);
                                main.setSize(300,370);
                            }
                            // main.add(main.label,BorderLayout.WEST);
                        }
                        mouseClickOffset = e.getPoint();
                        // main.setLocation( mouseClickOffset.x,mouseClickOffset.y);
                        // System.out.println(mouseClickOffset);
                        
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        setCurAnimation(nomal);
                        // curAnimation = nomal;
                        
                    }
                });

            main.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        Point currentMouseLocation = e.getLocationOnScreen();
                        Point pos = main.getLocationOnScreen();
                        // System.out.println(getSize());
                        a.Updatapostion(pos.x+300, pos.y);
                        Box.Updatapostion(pos.x-250, pos.y+50);
                        main.setLocation(currentMouseLocation.x - mouseClickOffset.x, currentMouseLocation.y - mouseClickOffset.y);
                        // a.Updatapostion(currentMouseLocation.x, currentMouseLocation.y);
                    }
                });
        });
    }
    
    public ArrayList<ImageIcon> imageList(String path) {
        String[] pathnames;
        File f = new File(path);

        FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File f, String name) {
            return name.endsWith(".png");
        }
        };

        pathnames = f.list(filter);
        ArrayList<ImageIcon> originalIcon = new ArrayList<ImageIcon>(); 



        for (String pathname : pathnames) {
            originalIcon.add(new ImageIcon(path+"\\" + pathname));
            // System.out.println(path+"\\" + pathname);
        }
        
        for (ImageIcon icon : originalIcon) {
                icon.setImage(icon.getImage().getScaledInstance(300, 300, Image.SCALE_AREA_AVERAGING));
        }
        return originalIcon;
    }

    public void setCurAnimation(ArrayList<ImageIcon> curAnimation) {
        this.curAnimation = curAnimation;
    }
    
    public ArrayList<ImageIcon> getCurAnimation() {
        return curAnimation;
    }

    public ArrayList<ImageIcon> getNomal() {
        return nomal;
    }
    public void setNomal(ArrayList<ImageIcon> nomal) {
        this.nomal = nomal;
    }


    public ArrayList<ImageIcon> getRaise() {
        return raise;
    }
    public void setRaise(ArrayList<ImageIcon> raise) {
        this.raise = raise;
    }


    public void setWork(ArrayList<ImageIcon> work) {
        this.work = work;
    }
    public ArrayList<ImageIcon> getWork() {
        return work;
    }


    public void setAngry(ArrayList<ImageIcon> angry) {
        this.angry = angry;
    }
    public ArrayList<ImageIcon> getAngry() {
        return angry;
    }
}