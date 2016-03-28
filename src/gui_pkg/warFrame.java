/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_pkg;

import empiro_pkg.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.*;

/**
 *
 * @author Anty
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class dealFrame extends JFrame{
    JLabel dealMsg;
    JButton dealButton;
    JButton warButton;
    WarSetUpFrame warSetUpFrame;
    int goldAsked;
    int woodAsked;
    int foodAsked;
    
    dealFrame(WarSetUpFrame recWSUFrame){
        this.warSetUpFrame = recWSUFrame;
        initData();
        setUpLayout();
        setUpListeners();
    }
    void initData(){
        dealButton = new JButton("Cut a Deal");
        warButton = new JButton("Fight War");
        goldAsked = (new Random().nextInt(300)+100)*1;
        woodAsked = (new Random().nextInt(300)+100)*1;
        foodAsked = (new Random().nextInt(300)+100)*1;
        if(!warSetUpFrame.myKingdom.resource.hasEnoughResources(foodAsked,woodAsked, goldAsked)){
            dealButton.setEnabled(false);
        }
                dealMsg = new JLabel("The enemy is demanding " + this.goldAsked + "g, " + this.foodAsked + " food, " + this.woodAsked + " wood.");

    }
    void setUpLayout(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.ipadx = 40;
        gbc.ipady = 40;
        gbc.weighty = 0.7;
        this.add(dealMsg,gbc);
        gbc.gridwidth =1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        this.add(dealButton,gbc);
        gbc.gridx = 1;
        this.add(warButton,gbc);
        this.pack();
        this.setVisible(false);
        this.setLocationRelativeTo(null);

        
    }
    void setUpListeners(){
        class warButtonListener implements ActionListener{
            WarSetUpFrame warSetUpFrame;

            warButtonListener(WarSetUpFrame setUpWarFrame){
                   this.warSetUpFrame = setUpWarFrame;
            }
            public void actionPerformed(ActionEvent ae){
                warSetUpFrame.dealFrame.setVisible(false);
                warSetUpFrame.wFrame.setVisible(true);
            }
        };
        class dealButtonListener implements ActionListener{
            WarSetUpFrame warSetUpFrame;

            dealButtonListener(WarSetUpFrame setUpWarFrame){
                   this.warSetUpFrame = setUpWarFrame;
            }
            public void actionPerformed(ActionEvent ae){
                    warSetUpFrame.warCancelled(foodAsked,woodAsked,goldAsked);
            }
        };
        
        dealButton.addActionListener(new dealButtonListener(warSetUpFrame));
        warButton.addActionListener(new warButtonListener(warSetUpFrame));
    }
}
class militaryPanel extends JPanel
{
    JLabel teamLabel;
    JLabel militaryCount;
    JLabel militaryHealth;
    JLabel militaryLevel;
    JLabel goldRes;
    Kingdom k1;
    Kingdom k2;
    
    militaryPanel(Kingdom k1, Kingdom k2){
        this.k1 = k1;
        this.k2 = k2;
        initData();
        setUpLayout();
    }
    void initData(){
        teamLabel = new JLabel(k1.king.kingName +" and " +k2.king.kingName);
        militaryCount = new JLabel("Battalion  Count : " + (k1.army.getMilitaryCount() + k2.army.getMilitaryCount()));
        militaryHealth  = new JLabel("Military health : " + ((k1.army.getMilitaryHealth() + k2.army.getMilitaryHealth())/2) );
        militaryLevel = new JLabel("Military level : " + (k1.army.getMilitaryLvl() + k2.army.getMilitaryLvl())/2);       
        goldRes = new JLabel ("Gold :" + ((k1.resource.getGold() + k2.resource.getGold())));
        
    }
    void setUpLayout(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill =  GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(teamLabel,gbc);
        gbc.gridy++;
        this.add(militaryCount,gbc);
        gbc.gridy++;
        this.add(militaryHealth,gbc);
        gbc.gridy++;
        this.add(militaryLevel,gbc);
        gbc.gridy++;
        this.add(goldRes,gbc);
        this.setVisible(true);
              
    }
}
class warFrame extends JFrame {

    militaryPanel mPanel1;
    militaryPanel mPanel2;
    JLabel drainRes;
    JButton dealButton;
    JButton warButton;
    WarSetUpFrame warSetUpFrame;
    
    warFrame(WarSetUpFrame recWSUFrame){
        this.warSetUpFrame = recWSUFrame;
        initData();
        setUpLayout();
        setUpListeners();
        
    }
    void initData(){
        
        mPanel1 = new militaryPanel(warSetUpFrame.myKingdom,warSetUpFrame.myKingdom.ally);
        BotKingdom bkPrint = new BotKingdom();
        for (BotKingdom bkTemp: warSetUpFrame.bkList){
            if(warSetUpFrame.myKingdom.ally != bkTemp)
            {    
                bkPrint = bkTemp;
            
            }
        }
        mPanel2 = new militaryPanel(bkPrint,bkPrint.ally);

        drainRes = new JLabel("Your resources will be drained while fighting the war.");
        dealButton = new JButton("Cut a Deal");
        warButton = new JButton("Fight War");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }
    void setUpLayout(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.ipadx = 40;
        gbc.ipady = 40;
        this.add(mPanel1,gbc);
        gbc.gridx = 1;
        this.add(mPanel2,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.gridwidth = 2;
        this.add(drainRes,gbc);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.CENTER;
        this.add(dealButton,gbc);
        gbc.gridx = 1;
        this.add(warButton,gbc);
        this.pack();
        this.setVisible(false);
        this.setLocationRelativeTo(null);
        
        
    }
    void setUpListeners(){
        class warButtonListener implements ActionListener{
            WarSetUpFrame warSetUpFrame;

            warButtonListener(WarSetUpFrame setUpWarFrame){
                   this.warSetUpFrame = setUpWarFrame;
            }
            @Override
            public void actionPerformed(ActionEvent ae){
                warSetUpFrame.wFrame.setVisible(false);
                Misc.msgPopup(warSetUpFrame, "War has begun ! ");
                warGame wGame = new warGame(warSetUpFrame.myKingdom,warSetUpFrame.bkList,warSetUpFrame.gameFrame);
                wGame.start();
                
            }
        };
        class dealButtonListener implements ActionListener{
            WarSetUpFrame warSetUpFrame;

            dealButtonListener(WarSetUpFrame setUpWarFrame){
                   this.warSetUpFrame = setUpWarFrame;
            }
            @Override
            public void actionPerformed(ActionEvent ae){
                    warSetUpFrame.wFrame.setVisible(false);
                    warSetUpFrame.dealFrame.setVisible(true);
            }
        };
        warButton.addActionListener(new warButtonListener(warSetUpFrame));
        dealButton.addActionListener(new dealButtonListener(warSetUpFrame));
    }
}
class warGame implements Runnable{
    Kingdom myKingdom;
    ArrayList<BotKingdom> bkList;
    boolean allyAlive;
    boolean enemyAlive;
    float allyPower;
    float enemyPower;
    float allyMaxHealth;
    float enemyMaxHealth;
    float resDrain;
    GameFrame gameFrame;
    Thread war;
    boolean youWinCalled;
    boolean youLoseCalled;
    
    warGame(Kingdom myKingdom, ArrayList<BotKingdom> bkList,GameFrame gameFrame){
        this.myKingdom = myKingdom;
        this.bkList = bkList;
        allyPower = 0;
        enemyPower = 0;
        war = new Thread(this,"War");
        allyAlive = true;
        enemyAlive = true;
        enemyMaxHealth = 1000;
        allyMaxHealth = 1000;
        this.gameFrame = gameFrame;
       youWinCalled = false;
       youLoseCalled = false;
        
    }
    public void start(){
        war.start();
    }
    
    @Override
    public void run(){
      while(allyAlive && enemyAlive){
         allyPower = 0;
         enemyPower = 0;
          
        drainResources();
        battle();
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ie)
        { 
            System.out.println("Exception caught");
        }
      }
    }
    void drainResources(){
        
        resDrain = myKingdom.army.getMilitaryPower()/5;
        for(BotKingdom bkTemp : bkList){
           
            int tempDrain = (int)bkTemp.army.getMilitaryPower()/5;
            if(bkTemp.resource.getFood() > tempDrain && bkTemp.resource.getWood() > tempDrain && bkTemp.resource.getGold() > tempDrain )
            {
                bkTemp.resource.decreaseGold(tempDrain);
                bkTemp.resource.decreaseWood(tempDrain);
                bkTemp.resource.decreaseFood(tempDrain);
            }
            else{
                bkTemp.army.setMilitaryHealth(bkTemp.army.getMilitaryHealth() - tempDrain);
            }
  
            bkTemp.king.decreasePopularity(0.002);
        }

        if(myKingdom.resource.getFood() > resDrain && myKingdom.resource.getWood() > resDrain && myKingdom.resource.getGold() > resDrain )
        {
            myKingdom.resource.decreaseGold((int)resDrain);
            myKingdom.resource.decreaseWood((int)resDrain);
            myKingdom.resource.decreaseFood((int)resDrain);
        }
        else{
            myKingdom.army.setMilitaryHealth(myKingdom.army.getMilitaryHealth() - resDrain);
        }
        myKingdom.king.decreasePopularity(0.002);
        gameFrame.updateResLabels();
    }

    void battle(){
        allyPower = myKingdom.army.calcMilitaryPower();
        for(BotKingdom bkTemp: bkList){
            if(bkTemp != myKingdom.ally)
                    enemyPower += bkTemp.army.calcMilitaryPower();
            else if(allyAlive)
                    allyPower += bkTemp.army.calcMilitaryPower();
        }
        allyPower *= new Random().nextInt(10);
        enemyPower *= new Random().nextInt(10);
        for(BotKingdom bkTemp: bkList){
           if(bkTemp != myKingdom.ally)
           {
                if(bkTemp.army.getMilitaryHealth() < 0.10 * enemyMaxHealth )
                {
                       //Bot Loses 
                    youWin();
                    enemyAlive = false;
                }
                else
                {
                   bkTemp.army.setMilitaryHealth(bkTemp.army.getMilitaryHealth() - allyPower);            
                    System.out.println("Enemy : " + bkTemp.army.getMilitaryHealth());
                }
           }
           else
           {
                if(bkTemp.army.getMilitaryHealth() < 0.10 * enemyMaxHealth )
                {
                    Misc.msgPopup(gameFrame,"Your ally has been defeated !");
                    allyAlive = false;
                    
                }
                else
                {
                   bkTemp.army.setMilitaryHealth(bkTemp.army.getMilitaryHealth() - enemyPower);       
                    System.out.println("Ally : " + bkTemp.army.getMilitaryHealth());
                }
           }

        }
        if(myKingdom.army.getMilitaryHealth() < 0.10 * enemyMaxHealth )
        {
            allyAlive = false;
            youLose();
        }
        else
        {
           myKingdom.army.setMilitaryHealth(myKingdom.army.getMilitaryHealth() - enemyPower);   
           gameFrame.updateMilitaryHealthBar();
           System.out.println("Hit");
           System.out.println("You : " + myKingdom.army.getMilitaryHealth());
        }

    }
    void youWin()
    {
        if(!youWinCalled)
        {   
            Misc.msgPopup(gameFrame,"You have won the war!!");


            for(BotKingdom bkTemp:bkList)
            {
                if(bkTemp!= myKingdom.ally)
                {
                    myKingdom.resource.append(bkTemp.resource, 0.375);
                    myKingdom.ally.resource.append(bkTemp.resource,0.375);
                    bkTemp.resource.remove(0.75);
                    bkTemp.king.decreasePopularity(0.1);

                }
            }
            Misc.onGoingWar = false;
            Misc.warCalled = false;
            Misc.triggerWar = 0;
            myKingdom.king.increasePopularity(0.25);
            myKingdom.ally.king.increasePopularity(0.25);
            youWinCalled = true;

        }
        else
        {
        }
    }
    void youLose()
    {
        if(!youLoseCalled)
        {
           Misc.msgPopup(gameFrame,"You have lost the war ...");
        for(BotKingdom bkTemp:bkList)
        {
            if(bkTemp!= myKingdom.ally)
            {
                bkTemp.resource.append(myKingdom.resource, 0.375);
                bkTemp.resource.append(myKingdom.ally.resource,0.375);
                myKingdom.resource.remove(0.375);
                myKingdom.ally.resource.remove(0.375);
                bkTemp.king.increasePopularity(0.25);
            }
        }
        myKingdom.king.decreasePopularity(0.1);
        myKingdom.ally.king.decreasePopularity(0.1);
        Misc.onGoingWar = false;
        Misc.warCalled = false;
        Misc.triggerWar = 0;
        youLoseCalled = true;
        }
        
    }
}