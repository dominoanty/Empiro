package gui_pkg;

//package gui_pkg;
import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;
import empiro_pkg.*;
import java.util.Iterator;





public class LoadGameFrame extends JFrame
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
    Kingdom loadedMyKingdom;
    ArrayList<BotKingdom> loadedbkList;
    JButton load;
    

    public LoadGameFrame()throws Exception
    {
        
       
        imageLoc=Misc.imageLoc();
        try{

        folder=new File(Misc.saveFolderLoc());
        setupFont();
        }catch(Exception oe)
        {
            System.out.println("ioexception happened..+0"+oe);
        }
     if(!folder.exists())
        {
            JOptionPane.showMessageDialog(this,"No Saved Games Yet...Please Start A new Game.");
            this.dispose();
        }
       else
        {
        
        
        
        this.setTitle("LOad Game");
        lp = getLayeredPane();
        
         try{
        setupHeading();
        setupFont();
        setupButton();
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
        listeners();
        
    
       }
      }
    
    void setupFont()throws Exception
    {
        fisfont=new FileInputStream(new File(imageLoc+"geminahalf.ttf"));
        gemina=Font.createFont(Font.TRUETYPE_FONT,fisfont);
        gemina=gemina.deriveFont(15f);
        fisfont.close();
    }
    void setupNamesList()
    {
      savSTRINGlist=folder.list();
      
        for(int i=0;i<savSTRINGlist.length;i++)
        {
           savSTRINGlist[i]=savSTRINGlist[i].substring(0,savSTRINGlist[i].lastIndexOf("."));
        }  
     
      
      
      listBox=new JList<>(savSTRINGlist);
      //scroller=new JScrollPane(listBox);
      //scroller.setViewportView(listBox);
      listBox.setSize(300,350);
      listBox.setBounds(450,30,300,400);
      listBox.setSelectionBackground(Color.black);
      listBox.setSelectionForeground(Color.lightGray);
      listBox.setFixedCellHeight(25);
      listBox.setFont(gemina);
      listBox.setBackground(Color.BLACK);
      listBox.setForeground(Color.DARK_GRAY);
      
      lp.add(listBox,1);
    }
    
    
    void setupButton()
    {
        load=new JButton("Load");
        lp.add(load,new Integer(1));
        load.setBounds(600, 400,75,25);
     
    }
    void setupHeading()
    {
        heading = new JLabel("Load Game..");
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
    
    void listeners()
    {
        load.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                String loadSTR = listBox.getSelectedValue();
                loadSTR=loadSTR+".sav";
                
                File f= new File(Misc.saveFolderLoc(),loadSTR);
                try
                {
                    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
                   System.err.println("About to load..");
                    loadedMyKingdom=(Kingdom)ois.readObject();
                    System.err.println("Loaded my kdom");
                    loadedbkList=(ArrayList<BotKingdom>)ois.readObject();
                    System.err.println("Laoded Botkdom");
                    ArrayList<Boolean> gameProgression = new ArrayList<>();
                    gameProgression = (ArrayList<Boolean>)ois.readObject();
                    Iterator<Boolean> iter = gameProgression.iterator();
                    Misc.DiplomacyCalled = iter.next();
                    Misc.DiplomacyDone = iter.next();
                    Misc.warCalled = iter.next();
                    Misc.onGoingWar = iter.next();
                    GameFrame LoadedGF=new GameFrame(imageLoc,loadedMyKingdom,loadedbkList);
                    System.err.println("Loaded Gframe)"); 
                    
                    
                }
                catch(Exception e)
                {
                    System.err.println("Exception..while loading....**"+e);
                    e.printStackTrace();
                }
            }
        });
    }
    
   
    
}


class LoadGameListener implements ActionListener
{
    
    
    public LoadGameListener()
    {
        
        
    }
    
    public void actionPerformed(ActionEvent ae)
    {  try{
       LoadGameFrame LGF = new LoadGameFrame();
    }catch(Exception e)
    {
        System.err.println("Exception happened..in LoadGamelistener../"+e);
    }
        
    }
}

class SaveGameListener implements ActionListener
{ long currentTime;
  Kingdom myKingdom;
  ArrayList<BotKingdom> bkList;
  GameFrame GF;
  File folder;
  File sav;

    public SaveGameListener(GameFrame GF,Kingdom myKingdom,ArrayList<BotKingdom> bkList) 
    {
        this.GF=GF;
        this.myKingdom=myKingdom;
        this.bkList=bkList;
        
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        Misc.EmergencyDataSaver(GF, myKingdom, bkList,Misc.saveFolderLoc());
        
    }
}

