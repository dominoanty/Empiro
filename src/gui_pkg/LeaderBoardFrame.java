package gui_pkg;

//package gui_pkg;
import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import empiro_pkg.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;






public class LeaderBoardFrame extends JFrame
{
    JLayeredPane lp;
    String imageLoc;
    JLabel background;
    JLabel heading;
    JList<String> listBox;
    //JScrollPane scroller;
    BufferedImage img;
    File folder;
    File sav;
    Font gemina;
    File[] savList;
    String[] savSTRINGlist;
    FileInputStream fisfont;
    

    public LeaderBoardFrame()
    {

        imageLoc=Misc.imageLoc();
        try{
   
        folder=new File(Misc.LeaderBoardFolderLoc());
        setupFont();
        }catch(Exception oe)
        {
            System.out.println("ioexception happened..+0"+oe);
        }
     if(!folder.exists())
        {
            JOptionPane.showMessageDialog(this,"<html>No Completed Games Yet...<br>Play well and see your name here</html>");
            this.dispose();
        }
       else
        {
        
        
        
        this.setTitle("LeaderBoard");
        lp = getLayeredPane();
        
         try{
        setupHeading();
        setupFont();
        setupNamesList();  
        setupBackground();
       
         }catch(Exception e)
         {
             System.out.println("Exception Occurred...+"+e);
             e.printStackTrace();
         }
        
        
        
        
        
       
        
        this.setVisible(true);
        this.setSize(background.getSize());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    
       }//end else
      }
    
    void setupFont()throws Exception
    {
        fisfont=new FileInputStream(new File(imageLoc+"geminahalf.ttf"));
        gemina=Font.createFont(Font.TRUETYPE_FONT,fisfont);
        gemina=gemina.deriveFont(25f);
        fisfont.close();
    }
    
    void setupNamesList()
    {
        ArrayList<Kingdom> playerKdomList  =new ArrayList<>();
    
     savList=folder.listFiles();
     

     for(File savFILE :savList)
     {
       try(ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(new FileInputStream(savFILE)))){
              playerKdomList.add((Kingdom)ois.readObject());
        }catch(Exception e)
        {
         System.err.println("Error while Leaderbord...."+e);
        }
     }
     
     Collections.sort(playerKdomList);
     savSTRINGlist=new String[playerKdomList.size()];
     
     Iterator ITR = playerKdomList.iterator();
     int i=0;
     while(ITR.hasNext())
     {
         savSTRINGlist[i]= (i+1)+".  "+((Kingdom)ITR.next()).king.kingName;
         i++;
     }
     
     
        
        
      
       
      listBox=new JList<>(savSTRINGlist);
      //scroller=new JScrollPane(listBox);
      //scroller.setViewportView(listBox);
      listBox.setSize(300,350);
      listBox.setBounds(450,30,300,400);
      listBox.setSelectionBackground(Color.black);
      listBox.setSelectionForeground(Color.lightGray);
      listBox.setFixedCellHeight(40);
      listBox.setFont(gemina);
      listBox.setBackground(Color.BLACK);
      listBox.setForeground(Color.DARK_GRAY);
      
      lp.add(listBox,1);
    }
    
    
    void setupHeading()
    {
        heading = new JLabel("LeaderBoard.");
        heading.setBounds(10,10,500,100);
      
        gemina=gemina.deriveFont(40f);
          heading.setFont(gemina);
        heading.setForeground(Color.GRAY);
        lp.add(heading,1);
    }
    void setupBackground() throws Exception
    {
        img = ImageIO.read(new File(imageLoc+"leaderboard.jpg"));
        Image dimg =img.getScaledInstance(800,500,Image.SCALE_SMOOTH);
        ImageIcon imageicon = new ImageIcon(dimg);
        
        background =new JLabel(imageicon);
        background.setSize(new Dimension(800,500));
        setSize(800,500);

        
        lp.setLayout(null);
        lp.add(background,new Integer(0));
        background.setBounds(0,0,800,500); 
    }
    

    
}

