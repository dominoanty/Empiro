/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_pkg;

import empiro_pkg.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

/**
 *
 * @author Anty
 */


class DiploImagePanel extends JPanel{

    private BufferedImage image;
    int setHeight;
    int setWidth;

    public DiploImagePanel(String fileLoc, int height) {
       try {                
          image = ImageIO.read(new File(fileLoc));
          setHeight = height;
       } catch (IOException ex) {
            // handle exception...
           System.out.println("Error in Image Panel");
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        float aspectRatio = image.getWidth()/image.getHeight();
         setWidth =(int)((float)setHeight * aspectRatio);
        
        g.drawImage(image, 0, 0,setWidth,setHeight, null); // see javadoc for more info on the parameters            
    }

}
class DiploStatsPanel extends JPanel
{
    protected JLabel goldRes;
    protected ImagePanel goldImg;
    protected JLabel woodRes;
    protected ImagePanel woodImg;
    protected JLabel foodRes;
    protected ImagePanel foodImg;
    protected ImagePanel armyImg;
    protected JLabel armyCount;
    protected String imageLoc;
    
    public DiploStatsPanel(BotKingdom bk)
    {
        imageLoc = Misc.imageLoc();

        woodRes = new JLabel(((Integer)bk.resource.getWood()).toString());
        woodImg = new ImagePanel(imageLoc + "Wood-icon.png",20);
        foodRes = new JLabel(((Integer)bk.resource.getFood()).toString());
        foodImg = new ImagePanel(imageLoc + "food_chicken_thig-512.png",20);
        goldRes = new JLabel(((Integer)bk.resource.getGold()).toString());
        goldImg = new ImagePanel(imageLoc +  "Gold_Bar_Icon.png",20);
        armyCount = new JLabel(((Integer)bk.army.getMilitaryCount()).toString());;
        armyImg = new ImagePanel(imageLoc + "shield-icon.png",20);
        setUpLayout();
    }
    
    void setUpLayout()
    {
        this.setLayout(new GridLayout(5,3));
        woodImg.setAlignmentX(JLabel.CENTER);
        this.add(new JLabel(" "));
        this.add(woodImg);
        this.add(woodRes);
                this.add(new JLabel(" "));

        this.add(foodImg);
        this.add(foodRes);
                this.add(new JLabel(" "));

        this.add(goldImg);
        this.add(goldRes);
               this.add(new JLabel(" "));

        this.add(armyImg);
        this.add(armyCount);
        //this.setSize(3000, 300);
        this.setVisible(true);
        
    }
}
class dipDetailsPanel extends JPanel{
    protected JLabel kingName;
    protected JLabel kingPop;
    protected DiploStatsPanel statsPanel;
    protected JLabel armyLevel;

    dipDetailsPanel(BotKingdom bk){
        kingName = new JLabel(bk.king.kingName);
        kingPop = new JLabel (((Integer)bk.king.getPopularity()).toString());
        armyLevel = new JLabel(((Integer)bk.army.getMilitaryLvl()).toString());
                statsPanel = new DiploStatsPanel(bk);

        setUpLayout();
    }
    protected void setUpLayout()
    {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.add(kingName);
        this.add(kingPop);
        this.add(statsPanel);
        this.add(armyLevel);
        this.setVisible(true);
        this.setBorder(BorderFactory.createLineBorder(Color.black));

    }
}
 class DiplomacyFrame extends JFrame
{
    ArrayList<dipDetailsPanel> panelList;
    ArrayList<JButton> buttonList;
    ArrayList<BotKingdom> bkList;
    Kingdom mykdom;
    GameFrame GF;
    
    //JButton diploBTN1;
    //JButton diplo
    protected JLabel dispInfo;
    
    public DiplomacyFrame(ArrayList<BotKingdom> bkList,Kingdom mykdom,GameFrame GF)
    {
      this.bkList = bkList;  
      this.mykdom=mykdom;
      this.GF=GF;
      panelList = new ArrayList<dipDetailsPanel>();
      buttonList = new ArrayList<JButton>();
      dispInfo = new JLabel("");//You Have to Choose Your ally now. But note that Choosing a Stronger ally will cost you resources that will be demanded from you.");
     
    
      Iterator<BotKingdom> botITR = bkList.iterator();
     
      
      while(botITR.hasNext())     
      {
          BotKingdom bk = (BotKingdom)botITR.next();
          panelList.add(new dipDetailsPanel(bk));
          buttonList.add(new JButton("Extend Alliance"));
          
      }
      listeners();
      setUpLayout();
      this.setLocationRelativeTo(null);
    }
    void setUpLayout()
    {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        Iterator<dipDetailsPanel> iter  = panelList.iterator();
        while(iter.hasNext())
        {
            this.add(iter.next(),gbc);
            gbc.gridx ++;
            
        }
        gbc.gridy = 1;
        gbc.gridx = 0;
        Iterator<JButton> iter2 = buttonList.iterator();
        while(iter2.hasNext()){
            this.add(iter2.next(),gbc);
            System.out.println("Witnessed");
            gbc.gridx++;

        }
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        this.add(dispInfo, gbc);
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }
    
    void listeners()
    { Iterator botITR = bkList.iterator();
       for(JButton btn :buttonList)
       {   BotKingdom bk = (BotKingdom)botITR.next();
           btn.addActionListener(new DiploActionListeners(bkList, bk, mykdom,this , GF));
       }
    }


 }

class DiploActionListeners implements ActionListener
{
    ArrayList<BotKingdom> bkList;
    BotKingdom bkdom;
    Kingdom mykdom;
    JFrame diploFrame;
    GameFrame GF;
    boolean bothTeamsSet;
    
    public DiploActionListeners(ArrayList<BotKingdom> bkList,BotKingdom bkdom,Kingdom mykdom,JFrame diploFrame,GameFrame GF) 
    {
        this.bkList=bkList;
        this.bkdom=bkdom;
        this.mykdom=mykdom;
        this.diploFrame=diploFrame;
        this.GF= GF;
        bothTeamsSet=false;
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
      bkdom.setAsAlly(mykdom);
      for(BotKingdom bktemp:bkList)
      {
          if(bktemp != bkdom)
          {
              for(BotKingdom bktemp2:bkList)
              {
                  if(bktemp2!=bkdom && bktemp2!=bktemp)
                  { bktemp.setAsAlly(bktemp2);
                    bothTeamsSet = true;
                    break;
                  }
                  
              }
              
          }
        if(bothTeamsSet)
            break;
      }
      
      
      JOptionPane.showMessageDialog(GF,bkdom.king.kingName + " is Your Ally Henceforth!");
      String rcrsMsg = mykdom.showGainOrLossResources(bkdom);
      JOptionPane.showMessageDialog(GF,rcrsMsg);
                          Misc.DiplomacyDone=true;
      diploFrame.dispose();
      GF.dipDone();

    }
}
