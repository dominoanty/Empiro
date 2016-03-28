package gui_pkg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import empiro_pkg.*;
import java.util.ArrayList;
import java.util.Iterator;



public class MainWindow
{
    JFrame startFrame;
    
    Kingdom kdom;
    ArrayList<BotKingdom> bkList;
    JLabel background;
    JButton newgameButton;
    JButton loadgameButton;
    JButton docsButton;
    JButton ldrbrdButton;   //leaderboard == ldrbrd
    JButton quitButton;
    JLayeredPane lp;
    String imageLoc;
   
    
    
    MainWindow(Kingdom kingdom,ArrayList<BotKingdom> bkList)         //constructor
    { start:
       
    imageLoc = Misc.imageLoc();
    kdom = kingdom;
    this.bkList=bkList;
    startFrame=new JFrame("Empiro");
    startFrame.setSize(1000,500);
    startFrame.setLocationRelativeTo(null);
    startFrame.setResizable(false);
    startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    startFrame.setLayout(null);
    lp=startFrame.getLayeredPane();
    buttonsAdder();

    startFrame.setVisible(true);
    
    
    background=new JLabel(new ImageIcon(imageLoc + "kingdom-nature.jpg"));
    background.setBounds(0,0,startFrame.getWidth(),startFrame.getHeight());
    lp.add(background,JLayeredPane.DEFAULT_LAYER);
    lp.setVisible(true);
    }
    
    void buttonsAdder()
    {
        newgameButton=new JButton("New Game");
        loadgameButton=new JButton("Load Game");
        docsButton=new JButton("How to Play");
        ldrbrdButton=new JButton("LeaderBoard");
        quitButton=new JButton("Quit Game");
        
        newgameButton.setBackground(Color.BLACK);
        loadgameButton.setBackground(Color.BLACK);
        docsButton.setBackground(Color.BLACK);
        ldrbrdButton.setBackground(Color.BLACK);
        quitButton.setBackground(Color.BLACK);
        
        newgameButton.setForeground(Color.LIGHT_GRAY);
        loadgameButton.setForeground(Color.LIGHT_GRAY);
        docsButton.setForeground(Color.LIGHT_GRAY);
        ldrbrdButton.setForeground(Color.LIGHT_GRAY);
        quitButton.setForeground(Color.LIGHT_GRAY); 
        
        
        
        newgameButton.setBounds(800, 200,130,30);
        loadgameButton.setBounds(800,240,130,30);
        docsButton.setBounds(800, 280,130,30);
        ldrbrdButton.setBounds(800,320,130,30);
        quitButton.setBounds(800,360,130,30);
        
        newgameButton.addActionListener(new ActionListener(){ 
           public void actionPerformed(ActionEvent ae)
           {
             String name=new String("");
                while(name.length()==0)
                {
                        name=JOptionPane.showInputDialog(startFrame,"Enter Name: ");
                        
                            
                            if(name!= null && name.length()==0)
                            {
                            JOptionPane.showMessageDialog(startFrame,"Alright. Sure. Your citizens will trust a nameless person. Right. ");
                            }
                            
                }
                kdom.setKingProperties(name,0.25f,1);
                GameFrame mFrame = new GameFrame(imageLoc,kdom,bkList);
                
                
                startFrame.setVisible(false);
           }
        });
        
        loadgameButton.addActionListener(new LoadGameListener());
        
        docsButton.addActionListener(new ActionListener(){ 
           public void actionPerformed(ActionEvent ae)
           {
               try{
                   Howtoplay htplay=new Howtoplay();
                   
               }
               catch(Exception e)
               {
                  System.err.println("Error is how to play instantiation...");
                  e.printStackTrace();
               }
               
        }
        });
        ldrbrdButton.addActionListener(new ActionListener(){ 
           public void actionPerformed(ActionEvent ae)
           {  try{
               LeaderBoardFrame ldrbdrfrm = new LeaderBoardFrame();
           }catch(Exception e)
           {
               
           }
           }
        });
        quitButton.addActionListener(new ActionListener(){ 
           public void actionPerformed(ActionEvent ae)
           {
              System.exit(0);        
           }
        });
        
       lp.add(newgameButton,JLayeredPane.PALETTE_LAYER);
       lp.add(loadgameButton,JLayeredPane.PALETTE_LAYER);
       lp.add(docsButton,JLayeredPane.PALETTE_LAYER);
       lp.add(ldrbrdButton,JLayeredPane.PALETTE_LAYER);
       lp.add(quitButton,JLayeredPane.PALETTE_LAYER); 
        
        
    }
    
     
}


class Startgame
{static Kingdom kdom;
 static ArrayList<BotKingdom> bkdomList;
 
 
 
public static void main(String args[])
 {
     kdom=new Kingdom();
     kdom.setPlayerGID();                        //your GID became 0 ...was initially 1 for all types of Kingdoms...hence bots are  1 GID
     bkdomList = new ArrayList<BotKingdom>();
     bkdomList.add(new BotKingdom());
     bkdomList.add(new BotKingdom());
     bkdomList.add(new BotKingdom());

     
     SwingUtilities.invokeLater(new Runnable(){
         @Override
         public void run()
         {
             
             MainWindow mainWin = new MainWindow(kdom,bkdomList);
             
             
         }
     });
 }
}


class Updater implements Runnable
{
    Thread updatingThread;
    long startTime;
    long presentTime;
    boolean DiplomacyDone;
    Kingdom K;
    ArrayList<BotKingdom> bkList;
    GameFrame GF;
    
   Updater(Kingdom K,ArrayList<BotKingdom> bkList,GameFrame GF,long GameStartTime)
     { 
       updatingThread = new Thread(this);
       this.K=K;
       this.bkList=bkList;
       this.GF=GF;
       this.startTime=GameStartTime;
       DiplomacyDone=false;
       updatingThread.start();
   }
   boolean check_enemy_DEATH()
   {Kingdom BK;
    boolean status=false;
   Iterator itr=bkList.iterator();
   while(itr.hasNext())
   {
       BK=(Kingdom)itr.next();
       status=BK.king.check_END_GAME();
       if(status)
           break;
       
   }
       return status;
   }
    @Override
    public void run()
    { while(GF!=null)
    {try
        { 
            if(K.king.getPopularity() < 0.1)
            {
                //gameOver();
            }
            if(!Misc.DiplomacyDone)
            {
                presentTime=System.currentTimeMillis();
                if(presentTime - startTime > (360*1000) && !Misc.DiplomacyCalled)    //make it 10*60*1000
                {
                    Misc.msgPopup(GF,Misc.DiploString);
                    DiplomacyFrame diploFrame = new DiplomacyFrame(bkList,K,GF);
                    Misc.DiplomacyCalled = true;
                }
            }
            else
            {
                  if(Misc.triggerWar == 2)
                  {
                    if(!Misc.warCalled)
                    {
                        WarSetUpFrame wFrame = new WarSetUpFrame(bkList,K,GF);
                        Misc.warCalled = true;
                        Misc.onGoingWar = true;
                        Misc.triggerWar = 0;
                    }
                  }
                  else if(Misc.triggerWar == 1)
                  {
                      if(!Misc.warCalled)
                      {
                          Misc.msgPopup(null, "War has begun ! ");
                          warGame wGame = new warGame(K,bkList,GF);
                          wGame.start();
                          Misc.warCalled = true;
                          Misc.onGoingWar = true;
                          Misc.triggerWar = 0;
                      }
                  }
            }
            Thread.sleep(1000);
            K.harvester.updateAllStocks();
            if(!Misc.onGoingWar)
                GF.heal();
            GF.updateHarvesterStockLabels(K.harvester.getFoodStock(), K.harvester.getWoodStock());
            GF.updateResLabels();
            GF.updatePopLabels();
            if(K.king.check_END_GAME() && !Misc.onGoingWar)
            {
                //game_ending_frames.....you are Kicked
                GF.dispose();
                JOptionPane.showMessageDialog(null,"you were DETHRONED...GAME ENDED..");
                Misc.EmergencyDataSaver(GF, K, bkList,Misc.LeaderBoardFolderLoc());
                System.exit(0);
            }
            if(check_enemy_DEATH() && !Misc.onGoingWar)
            {
                GF.dispose();
                JOptionPane.showMessageDialog(null,"One of the bots was DETHRONED..GAME ENDED...");
                Misc.EmergencyDataSaver(GF, K, bkList,Misc.LeaderBoardFolderLoc());
                System.exit(0);
            }
           
           
        }catch(InterruptedException ie)
        {
            
        }
        
    }}
}
