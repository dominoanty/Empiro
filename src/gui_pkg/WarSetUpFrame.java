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


public class WarSetUpFrame extends JFrame{
    protected JLabel warMsg;
    protected JButton dealButton;
    protected JButton warButton;
    protected warFrame wFrame;
    protected dealFrame dealFrame;
    protected ArrayList<BotKingdom> bkList;
    protected Kingdom myKingdom;
    protected GameFrame gameFrame;
    JFrame getWFrame(){
        return wFrame;
    }
    WarSetUpFrame(ArrayList<BotKingdom> bkList,Kingdom mKingdom, JFrame gameFrame){
        this.bkList = bkList;
        this.myKingdom = mKingdom;
        this.gameFrame = (GameFrame) gameFrame;
        initializeData();
        setUpLayout();
        setUpListeners();
    }
    

    void warCancelled(int foodAsked,int woodAsked, int goldAsked){
        myKingdom.resource.decreaseFood(foodAsked);
        myKingdom.resource.decreaseGold(goldAsked);
        myKingdom.resource.decreaseWood(woodAsked);
        
        Iterator<BotKingdom> bkIter = bkList.iterator();
        while(bkIter.hasNext()){
            BotKingdom bkTemp = bkIter.next();
            if(bkTemp != myKingdom.ally){
                bkTemp.resource.increaseFood((foodAsked)/2);
                bkTemp.resource.increaseGold((goldAsked)/2);
                bkTemp.resource.increaseWood(goldAsked/2);
            }
        }
        this.setVisible(false);
        gameFrame.setVisible(true);
        wFrame.dispose();
        dealFrame.dispose();
        this.dispose();
    }
    void setUpListeners(){
        
        class warButtonListener implements ActionListener{
            WarSetUpFrame warSetUpFrame;

            warButtonListener(WarSetUpFrame setUpWarFrame){
                   this.warSetUpFrame = setUpWarFrame;
            }
            public void actionPerformed(ActionEvent ae){
                warSetUpFrame.setVisible(false);
                warSetUpFrame.wFrame.setVisible(true);
            }
        };
        class dealButtonListener implements ActionListener{
            WarSetUpFrame warSetUpFrame;
            dealButtonListener(WarSetUpFrame warSetUpFrame){
                this.warSetUpFrame = warSetUpFrame;
            }
            public void actionPerformed (ActionEvent ae){
                warSetUpFrame.setVisible(false);
                warSetUpFrame.dealFrame.setVisible(true);
            }
        }
          dealButton.addActionListener(new dealButtonListener(this));
          warButton.addActionListener(new warButtonListener(this));
    }
    void initializeData(){
        
        dealFrame = new dealFrame(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        wFrame = new warFrame(this);
        warMsg = new JLabel("You have been sent a notice of war by King ");
        warMsg.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        dealButton = new JButton("Cut a Deal");
        warButton = new JButton("Fight War");
    }
    void setUpLayout(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = 50;
        gbc.ipady = 50;
        
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        
        this.add(warMsg,gbc);
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridwidth =1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.5;
        this.add(dealButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        this.add(warButton,gbc);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

    }
}