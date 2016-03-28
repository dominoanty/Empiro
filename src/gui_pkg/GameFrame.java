/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_pkg;
import empiro_pkg.*;
import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;



/**
 *
 * @author Anty
 */


class ImagePanel extends JPanel{

    private BufferedImage image;
    int setHeight;
    int setWidth;

    public ImagePanel(String fileLoc, int height) {
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
class statsPanel extends JPanel
{
    protected JPanel statsPanel ;
    protected JLabel woodRes ;
    protected ImagePanel woodImg ;
    protected JLabel foodRes ;
    protected ImagePanel foodImg ;
    protected JLabel goldRes ;
    protected ImagePanel goldImg ;
    protected JLabel citizenRes ;
    protected ImagePanel citizenImg;
    protected JLabel armyRes ;
    protected ImagePanel armyImg ;
    
    public statsPanel(String imageLoc,Kingdom myKingdom)
    {
        woodRes = new JLabel(((Integer)myKingdom.resource.getWood()).toString());
        woodImg = new ImagePanel(imageLoc + "Wood-icon.png",20);
        foodRes = new JLabel(((Integer)myKingdom.resource.getFood()).toString());
        foodImg = new ImagePanel(imageLoc + "food_chicken_thig-512.png",20);
        goldRes = new JLabel(((Integer)myKingdom.resource.getGold()).toString());
        goldImg = new ImagePanel(imageLoc +  "Gold_Bar_Icon.png",20);
        citizenRes = new JLabel(((Integer)myKingdom.resource.getCitizens()).toString());
        citizenImg = new ImagePanel(imageLoc + "citizen.png",20);
        armyRes = new JLabel(((Integer)myKingdom.army.getMilitaryCount()).toString());
        armyImg = new ImagePanel(imageLoc + "shield-icon.png",20);
        
       
        setUpLayout();
    }
    
    void setUpLayout()
    {
        this.setLayout(new GridLayout(5,2));
        
        this.add(woodImg);
        this.add(woodRes);
        this.add(foodImg);
        this.add(foodRes);
        this.add(goldImg);
        this.add(goldRes);
        this.add(citizenImg);
        this.add(citizenRes);
        
        this.add(armyImg);
        this.add(armyRes);
        this.setSize(3000, 300);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        
    }
}
class detailsPanel extends JPanel
{
    protected ImagePanel kingImagePanel; 
    protected JLabel kingName ;
    protected JProgressBar kingPop ;
    protected statsPanel statsPanel;
    protected JButton createCitizen;

    detailsPanel(String imageLoc, Kingdom myKingdom)
    {
         kingImagePanel = new ImagePanel(imageLoc + "King-icon.png",100);
        
        kingName = new JLabel(myKingdom.king.kingName);
        kingPop = new JProgressBar(0,100);
        kingPop.setValue(myKingdom.king.getPopularity());
        statsPanel = new statsPanel(imageLoc,myKingdom);
        createCitizen=new JButton("Create Citizen")  ;
        createCitizen.setToolTipText("Required 100 Food,50 Wood");
        
        setUpLayout();
    }
    void setUpLayout()
    {
        //this.setPreferredSize(new Dimension(120,220));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        kingImagePanel.setPreferredSize(new Dimension(100,100));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.CENTER;
        this.add(kingImagePanel,gbc);
        gbc.gridy++;
        this.add(kingName,gbc);
        gbc.gridy++;
                gbc.fill = GridBagConstraints.HORIZONTAL;

        this.add(kingPop,gbc);
        gbc.gridy++;
        this.add(statsPanel,gbc);
        gbc.gridy++;
        this.add(createCitizen,gbc);
        
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  
        
    }
    public void updateKingPop(int pop)
    {
        kingPop.setValue(pop*100);
    }
}
class healthPanel extends JPanel
    {
        protected JLabel militaryHealth;
        protected JProgressBar healthBar;
        protected Kingdom myKingdom;
        healthPanel(Kingdom myKingdom)
        {
            this.myKingdom = myKingdom;
            initData();
            setLayout();
        }
        void setLayout()
        {
            this.setLayout(new FlowLayout());
            this.add(militaryHealth);
            this.add(healthBar);
        }
        void initData()
        {
        militaryHealth = new JLabel("Military Health : ");
        healthBar = new JProgressBar(0, 1000);
        healthBar.setValue((int)myKingdom.army.getMilitaryHealth());
        healthBar.setForeground(Color.red);
        }

    }

class armyPane extends JPanel
{
    protected JLabel armyHeading;
    protected JLabel militaryCount;
    protected JLabel militaryLvl;
    protected healthPanel healthPanel;
    protected JLabel militaryPower;
    protected JButton addArmyUnit;
    protected JButton upgradeArmy;
    protected Kingdom myKingdom;
    
    
    public void updateHealthBar()
    {
            int currenthealth=(int)myKingdom.army.getMilitaryHealth();
            healthPanel.healthBar.setValue(currenthealth);
            
            if(currenthealth>800)
            {
                healthPanel.healthBar.setForeground(Color.GREEN);
            }
            else if(currenthealth>400)
            {
                healthPanel.healthBar.setForeground(Color.BLUE);
            }
            else
            {
                healthPanel.healthBar.setForeground(Color.RED);
            }
    }
    
    public armyPane(Kingdom myKingdom)
    {
        this.myKingdom = myKingdom;
        armyHeading = new JLabel("Army");
        militaryCount  = new JLabel("Battalion Count : " + myKingdom.army.getMilitaryCount());
        militaryLvl = new JLabel("Military Level : " + myKingdom.army.getMilitaryLvl());
        militaryPower = new JLabel("Military Power : " + myKingdom.army.calcMilitaryPower());
        healthPanel = new healthPanel(myKingdom);
        addArmyUnit = new JButton("Create Battalion");
        addArmyUnit.setToolTipText("Require 100 gold,50 wood,50 food");
        upgradeArmy = new JButton("Upgrade Army");
        int lvl=myKingdom.army.getMilitaryLvl();
      
        upgradeArmy.setToolTipText("Require 300x gold,200x food,200x wood  :x=current Level");
        setUpLayout();
        
    }
    void setUpLayout()
    {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc =  new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(armyHeading,gbc);
        gbc.gridy++;
        this.add(militaryCount,gbc);
        gbc.gridy++;
        this.add(militaryLvl,gbc);
        gbc.gridy++;
        this.add(healthPanel,gbc);
        gbc.gridy++;
        this.add(militaryPower,gbc);
        gbc.gridy++;
        this.add(addArmyUnit,gbc);
        gbc.gridy++;
        this.add(upgradeArmy,gbc);
        
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                
    }
}
class enemyPane extends JPanel
{
    JLabel kingName;
    JProgressBar kingPopularity;
    JLabel goldRes;
    JLabel foodRes;
    JLabel woodRes;
    GridBagConstraints  gbc;
    
    enemyPane(Kingdom tempKingdom)
    {
        kingName =new JLabel(tempKingdom.king.kingName) ;
        kingPopularity = new JProgressBar(0,100);
        kingPopularity.setValue(tempKingdom.king.getPopularity());
        goldRes = new JLabel("Gold : "+ tempKingdom.resource.getGold());
        foodRes = new JLabel("Food : "+ tempKingdom.resource.getFood());
        woodRes = new JLabel("Wood : "+ tempKingdom.resource.getWood());
        this.setUpLayout();
    }
    
    public void setUpLayout()
    {
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        this.add(kingName,gbc);
        gbc.gridy++;
        this.add(kingPopularity,gbc);
        gbc.gridy++;
        this.add(goldRes,gbc);
        gbc.gridy++;
        this.add(woodRes,gbc);
        gbc.gridy++;
        this.add(foodRes,gbc);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                this.setVisible(true);

    }
}
class allyPane extends JPanel
{
    JLabel kingName;
    JProgressBar kingPopularity;
    JLabel goldRes;
    JLabel woodRes;
    JLabel foodRes;
    JLabel armyCount;
    JLabel armyLevel;
    JLabel citizenCount;
    GridBagConstraints gbc;
    allyPane(Kingdom tempKingdom)
    {
        kingName =new JLabel(tempKingdom.king.kingName) ;
        kingPopularity = new JProgressBar(0,100);
        kingPopularity.setValue(tempKingdom.king.getPopularity());
        goldRes = new JLabel("Gold : "+ tempKingdom.resource.getGold());
        foodRes = new JLabel("Food : "+ tempKingdom.resource.getFood());
        woodRes = new JLabel("Wood : "+ tempKingdom.resource.getWood());
        
        armyCount = new JLabel("Battalion Count :" + ((Integer)(tempKingdom.army.getMilitaryCount())).toString());
        armyLevel = new JLabel("Military Level :" + ((Integer)(tempKingdom.army.getMilitaryLvl())).toString());
        citizenCount = new JLabel("Citizen Count :" + ((Integer)(tempKingdom.resource.getCitizens())).toString() );
        setUpLayout();
    }
    public void setUpLayout()
    {
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        this.add(kingName,gbc);
        gbc.gridy++;
        this.add(kingPopularity,gbc);
        gbc.gridy++;
        this.add(goldRes,gbc);
        gbc.gridy++;
        this.add(woodRes,gbc);
        gbc.gridy++;
        this.add(foodRes,gbc);
        gbc.gridy++;
        this.add(armyCount,gbc);
        gbc.gridy++;
        this.add(armyLevel,gbc);
        gbc.gridy++;
        this.add(citizenCount,gbc);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
                this.setVisible(true);

    }
}
class dipPane extends JPanel
{
    ArrayList<enemyPane> enemyPaneList;
    allyPane allyPane;
    Iterator<enemyPane> iter;
    ArrayList<BotKingdom> bkList;
     Kingdom myKingdom;
     GridBagConstraints gbc ;
     JButton fightWar;
    public void dipdone()
    {
        
        allyPane = new allyPane(myKingdom.ally);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        this.add(allyPane,gbc);
        gbc.gridy+=3;
        fightWar.setEnabled(true);
        gbc.gridheight=1;
        this.add(fightWar,gbc);
        Iterator<enemyPane> iter4 = enemyPaneList.iterator();
        while(iter4.hasNext())
        {
            enemyPane temp = ((enemyPane)(iter4.next()));
            if(myKingdom.ally.king.kingName.equals(temp.kingName.getText()))
                temp.setVisible(false);
                
        }
        fightWar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                Misc.triggerWar  = 1;
            }
        });
            gbc.gridy++;        
    }
    public void updateBotLabels()
    {
        Iterator iterPane = enemyPaneList.iterator();
        for(BotKingdom bkTemp : bkList)
        {
            enemyPane temp = (enemyPane) iterPane.next();
            temp.goldRes.setText("Gold : "+ bkTemp.resource.getGold());
            temp.foodRes.setText("Food : "+ bkTemp.resource.getFood());
            temp.woodRes.setText("Wood : "+ bkTemp.resource.getWood());
            temp.kingPopularity.setValue(bkTemp.king.getPopularity());
        }
        if(allyPane!=null)
        {
            allyPane.kingPopularity.setValue(myKingdom.ally.king.getPopularity());
        allyPane.goldRes.setText("Gold : "+ myKingdom.ally.resource.getGold());
        allyPane.foodRes.setText("Food : "+ myKingdom.ally.resource.getFood());
        allyPane.woodRes.setText("Gold : "+ myKingdom.ally.resource.getWood());

        allyPane.armyCount.setText("Battalion Count :" + ((Integer)(myKingdom.ally.army.getMilitaryCount())).toString());
        allyPane.armyLevel.setText("Military Level :" + ((Integer)(myKingdom.ally.army.getMilitaryLvl())).toString());
        allyPane.citizenCount.setText("Citizen Count :" + ((Integer)(myKingdom.ally.resource.getCitizens())).toString() );
        }
    }
    dipPane(ArrayList<BotKingdom> bkList, Kingdom myKingdom)
    {
        this.bkList = bkList;
        this.myKingdom = myKingdom;
        enemyPaneList = new ArrayList<>();
        for(BotKingdom bkTemp: bkList)
        {
            if(bkTemp!= myKingdom.ally)
            {
                enemyPaneList.add(new enemyPane(bkTemp));
            }
        }
        fightWar = new JButton("Fight War");
        fightWar.setEnabled(false);
        setUpLayout();
           
    }
    public void setUpLayout()
    {
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx =10;
        iter = enemyPaneList.iterator();
        while(iter.hasNext())
        {
            
            this.add((enemyPane) iter.next(),gbc);
            gbc.gridy++;
        }
        this.setVisible(true);
    }
}
class tradePane extends JComponent
{
    protected JLabel foodGoldText;
    protected JLabel woodGoldText;
    protected JSlider gFoodSlider;
    protected JSlider fFoodSlider;
    protected JSlider gWoodSlider;
    protected JSlider wWoodSlider;
    protected JButton sellFood1;
    protected JButton sellGold1;
    protected JButton sellWood2;
    protected JButton sellGold2;
    protected JButton reset;
    
    tradePane()
    {
        foodGoldText = new JLabel("Food <-> Gold");
        gFoodSlider = new JSlider(JSlider.HORIZONTAL,0,500,0);
        fFoodSlider = new JSlider(JSlider.HORIZONTAL,0,1000,0);
        sellFood1=new JButton("Sell Food");
        sellGold1=new JButton("Sell Gold");
        woodGoldText = new JLabel("Wood <-> Gold");
        gWoodSlider = new JSlider(JSlider.HORIZONTAL,0,500,0);
        wWoodSlider = new JSlider(JSlider.HORIZONTAL,0,1000,0);
        sellWood2= new JButton("Sell Wood");
        sellGold2= new JButton("Sell Gold");
        reset =new JButton("reset");
        
        

        setUpLayout();

    }
    void setUpLayout()
    {
                this.setLayout(new GridBagLayout());
        gFoodSlider.setMaximumSize(new Dimension(240,40));
        fFoodSlider.setMaximumSize(new Dimension(240,40));
        gWoodSlider.setMaximumSize(new Dimension(240,40));
        wWoodSlider.setMaximumSize(new Dimension(240,40));
        
        gFoodSlider.setToolTipText("GOLD");
        fFoodSlider.setToolTipText("FOOD");
        gWoodSlider.setToolTipText("GOLD");
        wWoodSlider.setToolTipText("WOOD");
        
        gFoodSlider.setMajorTickSpacing(250);
        gFoodSlider.setMinorTickSpacing(25);
        gFoodSlider.setPaintTicks(true);
        gFoodSlider.setPaintLabels(true);
        fFoodSlider.setMajorTickSpacing(250);
        fFoodSlider.setMinorTickSpacing(25);
        fFoodSlider.setPaintTicks(true);
        fFoodSlider.setPaintLabels(true);
        gWoodSlider.setMajorTickSpacing(250);
        gWoodSlider.setMinorTickSpacing(25);
        gWoodSlider.setPaintTicks(true);
        gWoodSlider.setPaintLabels(true);
        wWoodSlider.setMajorTickSpacing(250);
        wWoodSlider.setMinorTickSpacing(25);
        wWoodSlider.setPaintTicks(true);
        wWoodSlider.setPaintLabels(true);
        
                GridBagConstraints tradeGBC = new GridBagConstraints();
        //tradeGBC.fill = GridBagConstraints.CENTER;
        tradeGBC.gridx = 0;
        tradeGBC.gridy = 0;
        
        this.add(foodGoldText,tradeGBC);
        tradeGBC.fill=GridBagConstraints.CENTER;
        tradeGBC.gridx = 1;
        this.add(fFoodSlider,tradeGBC);
        tradeGBC.gridy = 1;
        this.add(gFoodSlider,tradeGBC);
        tradeGBC.gridy = 0;
        tradeGBC.gridx = 2;
        this.add(sellFood1,tradeGBC);
        tradeGBC.gridy = 1;
        this.add(sellGold1,tradeGBC);
        tradeGBC.gridx=0;
        
        tradeGBC.gridy=2;
        
        this.add(woodGoldText,tradeGBC);
        tradeGBC.gridx=1;
        this.add(wWoodSlider,tradeGBC);
        tradeGBC.gridy=3;
        this.add(gWoodSlider,tradeGBC);
        tradeGBC.gridy = 2;
        tradeGBC.gridx=2;
        this.add(sellWood2,tradeGBC);
        tradeGBC.gridy=3;
        this.add(sellGold2,tradeGBC);
        
        tradeGBC.gridx=4;
        tradeGBC.gridy=6;
        reset.setToolTipText("Resets all Trade Sliders and Trade Buttons");
        this.add(reset,tradeGBC);
        
    }

}
class resPanel extends JPanel
{
    protected JLabel resHeading;
    protected JLabel currResHarvLevel;
    protected JLabel foodStock;
    protected JLabel woodStock;
    protected JButton collectFood;
    protected JButton collectWood;
    protected JLabel foodWorkers;
    protected JLabel woodWorkers;
    protected JButton addFoodWorkers;
    protected JButton addWoodWorkers;
    protected JButton remFoodWorkers;
    protected JButton remWoodWorkers;
    protected JLabel resHarvLevel;
    protected JLabel foodProdRate;
    protected JLabel woodProdRate;
    protected JButton upgradeHarvester;
    resPanel(Kingdom myKingdom)
    {
        resHeading = new JLabel("Resources");
        resHarvLevel = new JLabel("Resource Harvester Level : " + ((Integer)myKingdom.harvester.getCurrLvl()).toString());
        foodProdRate = new JLabel("Food Production Rate : " + ((Integer)myKingdom.harvester.getFoodProdRate()).toString());
        woodProdRate = new JLabel("Wood Production Rate : " + ((Integer)myKingdom.harvester.getWoodProdRate()).toString());
        foodStock = new JLabel ("Food Stock " + ((Integer)myKingdom.harvester.getFoodStock()).toString());
        woodStock = new JLabel ("Wood Stock " + ((Integer)myKingdom.harvester.getWoodStock()).toString());
        collectFood = new JButton();
        collectWood = new JButton();
        foodWorkers = new JLabel("Citizens collecting Food : " + (((Integer)myKingdom.harvester.getCitizensCountInFoodProd()).toString()));
        woodWorkers = new JLabel("Citizens collecting Wood : " + (((Integer)myKingdom.harvester.getCitizensCountInWoodProd()).toString()));
        addFoodWorkers = new JButton("Add");
        remFoodWorkers  = new JButton("Remove");
        addWoodWorkers = new JButton("Add");
        upgradeHarvester = new JButton("Upgrade Harvester");
        
        upgradeHarvester.setToolTipText("500x gold and 500x wood required !   x:current level");
        remWoodWorkers = new JButton("Remove");
        collectFood.setText("Collect Food");
        collectWood.setText("Collect Wood");
        
        setUpLayout();
        
        
    }
    void setUpLayout()
    {
        this.setLayout(new GridBagLayout());
               // this.setPreferredSize(new Dimension(400,120));

        GridBagConstraints resGBC = new GridBagConstraints();
        resGBC.fill = GridBagConstraints.CENTER;
        resGBC.ipadx = 30;
        resGBC.ipady = 5;
        resGBC.gridwidth = 4;
        resGBC.gridx = 0 ;
        resGBC.gridy = 0;
        this.add(resHeading,resGBC);
        resGBC.gridy = 1;
        this.add(resHarvLevel,resGBC);
        resGBC.gridy = 2;
        resGBC.gridx = 0;
        resGBC.gridwidth = 2;
        this.add(foodProdRate,resGBC);
        resGBC.gridx = 2;
        this.add(woodProdRate,resGBC);
        //this.add(currResHarvLevel,resGBC);
        resGBC.gridwidth = 2;
        resGBC.fill = GridBagConstraints.CENTER;
        resGBC.gridx = 0;
        resGBC.gridy = 3;
        resGBC.weightx = 0.5;
        this.add(foodStock,resGBC);
        resGBC.gridx = 2;
        resGBC.gridy = 3;
        
        this.add(woodStock,resGBC);
        

        
        
        resGBC.gridx = 0;
        resGBC.gridy = 5;
        this.add(foodWorkers, resGBC);
        resGBC.gridx = 2;
        this.add(woodWorkers, resGBC);
        
        resGBC.ipadx = 0;
        resGBC.ipady = 0;
        resGBC.gridx = 0;
        resGBC.gridy = 4;
        this.add(collectFood,resGBC);
        resGBC.gridx = 2;
        resGBC.gridy = 4;
        this.add(collectWood,resGBC);
        
        
        resGBC.gridwidth  = 1 ;
        resGBC.gridx = 0;
        resGBC.gridy = 6;
        resGBC.weightx = 0.5;
        resGBC.anchor = GridBagConstraints.EAST;
        this.add(addFoodWorkers,resGBC);
        resGBC.anchor = GridBagConstraints.WEST;
        resGBC.gridx = 1;
        
        this.add(remFoodWorkers,resGBC);
        
        resGBC.gridx = 2;
        resGBC.gridy = 6;
        resGBC.anchor = GridBagConstraints.EAST;
        this.add(addWoodWorkers,resGBC);
        resGBC.anchor = GridBagConstraints.WEST;        
        resGBC.gridx = 3;
        
        this.add(remWoodWorkers,resGBC);
        resGBC.gridx = 0;
        resGBC.gridwidth = 4;
        resGBC.gridy = 7;
        resGBC.anchor = GridBagConstraints.CENTER;
        resGBC.fill = GridBagConstraints.CENTER;
        this.add(upgradeHarvester,resGBC);
                
        
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
}
public class GameFrame extends JFrame
{
    private detailsPanel detailsPanel ;        
    
    private JPanel tabPanel ;
        private JTabbedPane actionsPane ;
            private armyPane armyPane ;
            private dipPane dipPane ;
            private tradePane tradePane ;
           
    private JPanel msgPanel;
        private JLabel msgDisplay;
               
    private resPanel resPanel;
    private JButton saveGameButton;

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
    
     public void updatePopularityBar()
    {   int pop=myKingdom.king.getPopularity();
        detailsPanel.kingPop.setValue(pop);
        if(pop>300)
            detailsPanel.kingPop.setForeground(Color.GREEN);
        else
            detailsPanel.kingPop.setForeground(Color.RED);
    }
    public void updateMilitaryHealthBar()
    {
        armyPane.updateHealthBar();
    }
    public void updatePopLabels()
    {
        detailsPanel.kingPop.setValue(myKingdom.king.getPopularity());
        updatePopularityBar();
        dipPane.updateBotLabels();
        
    }
    public void updateHarvesterStockLabels(Integer fStock,Integer wStock)
    {
        resPanel.foodStock.setText("Food Stock :" + fStock.toString());
        resPanel.woodStock.setText("Wood Stock :" + wStock.toString());
    }
    public void updateResLabels()
    {
        detailsPanel.statsPanel.foodRes.setText(((Integer)myKingdom.resource.getFood()).toString());
        detailsPanel.statsPanel.woodRes.setText(((Integer)myKingdom.resource.getWood()).toString());
        detailsPanel.statsPanel.goldRes.setText(((Integer)myKingdom.resource.getGold()).toString());
        detailsPanel.statsPanel.citizenRes.setText(((Integer)myKingdom.resource.getCitizens()).toString());
        detailsPanel.statsPanel.armyRes.setText(((Integer)myKingdom.army.getMilitaryCount()).toString());
    }

    private String imageLoc; 
    private Kingdom myKingdom; 
    private ArrayList<BotKingdom> bkList;
    
    public GameFrame(String recImageLoc, Kingdom kdom,ArrayList<BotKingdom> bkList)
    {   long GameStartTime = System.currentTimeMillis();
        
    
        myKingdom = kdom;
        this.bkList= bkList;
        for(BotKingdom bk :this.bkList)
        {
            bk.isAlive=true;
            bk.setKingProperties(Misc.KingNamesList[new Random().nextInt(32)],0.25f,1);
            bk.startBotThread();
        }
        myKingdom.isAlive = true;
        Updater detailsUpdater =new Updater(kdom,bkList,this,GameStartTime);     
        
        
        imageLoc = recImageLoc;
        initializeData();
        saveGameButton=new JButton("Save");
        
        setUpLayout();
        Listeners();
    }  
    public void initializeData()
    {
        
    detailsPanel = new detailsPanel(imageLoc,myKingdom);   
    tabPanel = new JPanel();
        actionsPane = new JTabbedPane();
            tradePane = new tradePane();          
    msgPanel = new JPanel();   
        msgDisplay = new JLabel();
    
    dipPane = new dipPane(bkList,myKingdom);
    resPanel = new resPanel(myKingdom);
    armyPane = new armyPane(myKingdom);
    
    }
    void setUpLayout()
    {JLayeredPane jlp = getLayeredPane();
    jlp.setLayout(null);
    saveGameButton.setBounds(5,5,100,30); 
    saveGameButton.setBackground(Color.BLACK);
    saveGameButton.setForeground(Color.GRAY);
    jlp.add(saveGameButton,3);

        
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty=1;
        gbc.gridheight = 2;
      
        this.add(detailsPanel,gbc);
        
        
        
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty=1;
        gbc.gridheight = 1;
        ImageIcon ovIcon = new ImageIcon(imageLoc + "Editing-Overview-Pages-3-icon.png");
        ImageIcon dipIcon = new ImageIcon(imageLoc + "Editing-Overview-Pages-3-icon.png");
        ImageIcon tradeIcon = new ImageIcon(imageLoc + "Editing-Overview-Pages-3-icon.png");

                
        
        
        //actionsPane.addTab("Army",ovIcon,armyPane,"Manage Army");
        actionsPane.addTab("Diplomacy",dipIcon,dipPane,"Manage relations");
        actionsPane.addTab("Trading",tradeIcon, tradePane,"Trade goods");
        
        tabPanel.setLayout(new FlowLayout());
        tabPanel.add(actionsPane);
        //actionsPane.setPreferredSize(new Dimension(actionsPane.getPreferredSize().width,200));
        this.add(tabPanel,gbc);
        
        //msgPanel.setPreferredSize(new Dimension(600,80));
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        
        
        msgPanel.setLayout(new BoxLayout(msgPanel,BoxLayout.PAGE_AXIS));
        msgPanel.add(msgDisplay);
        
        msgDisplay.setText("No Messages To Display");
        msgDisplay.setBorder(BorderFactory.createTitledBorder("MESSAGES"));
        //msgPanel.add(msgJList);
        
       
        
        this.add(msgPanel,gbc);
        
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
       
        this.add(armyPane,gbc);
        gbc.fill = GridBagConstraints.LINE_END;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.gridwidth = 2;
        this.add(resPanel,gbc);
        this.setVisible(true);
        this.pack();
        //this.setSize(1000, 600);
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        
    }
    void dipDone()
    {
        dipPane.dipdone();
    }
  
      void heal()
    {
        if(myKingdom.army.getMilitaryHealth() < 1000)
            myKingdom.army.setMilitaryHealth(myKingdom.army.getMilitaryHealth() + 5);
        for(BotKingdom bkTemp : bkList)
        {
            if(bkTemp.army.getMilitaryHealth() < 1000)
                bkTemp.army.setMilitaryHealth(myKingdom.army.getMilitaryHealth() + 5);   
        }
        if(myKingdom.army.getMilitaryHealth() < 400)
            dipPane.fightWar.setEnabled(false);
        else
            dipPane.fightWar.setEnabled(true);
        armyPane.updateHealthBar();
    }
    void enableAllTradeButtons()
    {
        tradePane.sellFood1.setEnabled(true);
        tradePane.sellGold1.setEnabled(true);
        tradePane.sellWood2.setEnabled(true);
        tradePane.sellGold2.setEnabled(true);
    }
    
    
   void Listeners()
   {//****************************SaveGameButton***************
     saveGameButton.addActionListener(new SaveGameListener(this, myKingdom, bkList));
     //**********************************************************
     armyPane.addArmyUnit.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            
                if(myKingdom.resource.getGold() < 100 )
                {
                    msgDisplay.setText("Not enough gold.");
                    
                }
                else if ( myKingdom.resource.getWood() < 50)
                {
                    msgDisplay.setText("Not enough wood.");
                }
                else if(myKingdom.resource.getFood() < 50)
                {
                    msgDisplay.setText("Not enough food.");
                }
                else
                {
                    myKingdom.createBattalion();
                    
                    detailsPanel.statsPanel.armyRes.setText(((Integer)myKingdom.army.getMilitaryCount()).toString());
                    detailsPanel.statsPanel.goldRes.setText(((Integer)myKingdom.resource.getGold()).toString());
                    detailsPanel.statsPanel.woodRes.setText(((Integer)myKingdom.resource.getWood()).toString());
                    detailsPanel.statsPanel.citizenRes.setText(((Integer)myKingdom.resource.getCitizens()).toString());
                    armyPane.militaryPower.setText("Military Power : " + myKingdom.army.calcMilitaryPower());
                    armyPane.militaryCount.setText("Battalion Count : " +  myKingdom.army.getMilitaryCount());
                    msgDisplay.setText("BATALLION Trained");
                }
                
            
            
        }
     });
     armyPane.upgradeArmy.addActionListener(new ActionListener(){
       @Override
       public void actionPerformed(ActionEvent ae){
           
           if(myKingdom.canUpgradeArmyLevel())
           {
               myKingdom.upgradeArmyLevel();
               armyPane.militaryLvl.setText("Military Level : " + myKingdom.army.getMilitaryLvl());
               armyPane.militaryPower.setText("Military Power : " + myKingdom.army.calcMilitaryPower());
           }
           else
           {
               int lvl=myKingdom.army.getMilitaryLvl();
               msgDisplay.setText("Need "+lvl*lvl*300+"gold, "+lvl*lvl*200+"food,"+lvl*lvl*200+"wood");
           }
       
       }
     });
   
     detailsPanel.createCitizen.addActionListener(new ActionListener(){ 
          @Override 
          public void actionPerformed(ActionEvent ae)
           { 
              if(myKingdom.resource.createCitizen())
              {
                  detailsPanel.statsPanel.citizenRes.setText(((Integer)myKingdom.resource.getCitizens()).toString());
                  detailsPanel.statsPanel.foodRes.setText(((Integer)myKingdom.resource.getFood()).toString());
                  detailsPanel.statsPanel.woodRes.setText(((Integer)myKingdom.resource.getWood()).toString());
                 
                  
                  msgDisplay.setText("A Citizen was Just Created,,at the cost of 100 food and 50 wood");

              }
              else
                  msgDisplay.setText("Need 100 food and 50 Wood to make 1 citizen.");

                  
        } }); 
       
       
       resPanel.addFoodWorkers.addActionListener(new ActionListener(){ 
          @Override 
          public void actionPerformed(ActionEvent ae)
           { 
              if(myKingdom.resource.hasCitizens())
              {
                  myKingdom.assignCitizenToFoodProd();

                  detailsPanel.statsPanel.citizenRes.setText(((Integer)myKingdom.resource.getCitizens()).toString());
                  resPanel.foodWorkers.setText("Workers Collecting Food :" + ((Integer)myKingdom.harvester.getCitizensCountInFoodProd()).toString());
                  resPanel.foodProdRate.setText("Food Production Rate :" + (((Integer)myKingdom.harvester.getFoodProdRate()).toString()));
                  
                  msgDisplay.setText("A citizen is now gathering food" );

              }
              else
                  msgDisplay.setText("No idle citizens left.");

                  
        } }); 
      
      
      resPanel.addWoodWorkers.addActionListener(new ActionListener(){ 
          @Override 
          public void actionPerformed(ActionEvent ae)
           {  
               if(myKingdom.resource.hasCitizens())
               {
                  myKingdom.assignCitizenToWoodProd();

                  detailsPanel.statsPanel.citizenRes.setText(((Integer)myKingdom.resource.getCitizens()).toString());
                  resPanel.woodWorkers.setText("Workers Collecting Wood :" + ((Integer)myKingdom.harvester.getCitizensCountInWoodProd()).toString()); 
                  resPanel.woodProdRate.setText("Wood Production Rate :" + (((Integer)myKingdom.harvester.getWoodProdRate()).toString()));
                  
                  msgDisplay.setText("A citizen is now harvesting wood.");

               }
               else
                  msgDisplay.setText("No idle Citizens left.");
           }
        });
      
      resPanel.remFoodWorkers.addActionListener(new ActionListener(){ 
          @Override
          public void actionPerformed(ActionEvent ae)
           { 
              if(myKingdom.harvester.hasFoodWorker())
              {  
                  myKingdom.fireCitizenFromFoodProd();

                  
                  detailsPanel.statsPanel.citizenRes.setText(((Integer)myKingdom.resource.getCitizens()).toString());
                  resPanel.foodWorkers.setText("Workers Collecting Food :" + ((Integer)myKingdom.harvester.getCitizensCountInFoodProd()).toString());
                  resPanel.foodProdRate.setText("Food Production Rate :" + (((Integer)myKingdom.harvester.getFoodProdRate()).toString()));
                 
                  msgDisplay.setText("A citizen was put back into the idle pool.");

              }
              else
                  msgDisplay.setText("No citizens harvesting wood at the moment.");
        } }); 
      resPanel.upgradeHarvester.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent ae)
         {
             if(myKingdom.canUpgradeHarvester())
             {
                 myKingdom.upgradeResourceHarvester();
                 resPanel.resHarvLevel.setText("Resource Harvester Level : " + ((Integer)myKingdom.harvester.getCurrLvl()).toString());
                 msgDisplay.setText("Resource Harvester has been upgraded to level +  " + ((Integer) myKingdom.harvester.getCurrLvl()).toString() );
                  resPanel.foodProdRate.setText("Food Production Rate :" + (((Integer)myKingdom.harvester.getFoodProdRate()).toString()));
                  resPanel.woodProdRate.setText("Wood Production Rate :" + (((Integer)myKingdom.harvester.getWoodProdRate()).toString()));
             }
             msgDisplay.setText("You don't have the required resources");
         }
      });
      resPanel.remWoodWorkers.addActionListener(new ActionListener(){ 
          @Override 
          public void actionPerformed(ActionEvent ae)
           { 
              if(myKingdom.harvester.hasWoodWorker())
              {
                  myKingdom.fireCitizenFromWoodProd();
                  
                  
                  detailsPanel.statsPanel.citizenRes.setText(((Integer)myKingdom.resource.getCitizens()).toString());               
                  resPanel.woodWorkers.setText("Workers Collecting Wood :" + ((Integer)myKingdom.harvester.getCitizensCountInWoodProd()).toString());
                  resPanel.woodProdRate.setText("Wood Production Rate :" + (((Integer)myKingdom.harvester.getWoodProdRate()).toString()));

                  msgDisplay.setText("A citizen was put back into the idle pool.");
                  
              }
              else
                  msgDisplay.setText("No citizens harvesting wood at the moment.");
        } }); 
      
      resPanel.collectFood.addActionListener(new ActionListener(){ 
          @Override
          public void actionPerformed(ActionEvent ae)
           {
              if(myKingdom.harvester.hasFoodStock())
              {
                myKingdom.resource.increaseFood(myKingdom.harvester.harvestFood());
                 
                 detailsPanel.statsPanel.foodRes.setText(((Integer)myKingdom.resource.getFood()).toString());
                 
                 msgDisplay.setText("Food stock has been collected.");

              }  
              else
                  msgDisplay.setText("No more Food Stock left.");

           }
        });
      
      resPanel.collectWood.addActionListener(new ActionListener(){ 
          @Override
          public void actionPerformed(ActionEvent ae)
           {  if(myKingdom.harvester.hasWoodStock())
               {
                 myKingdom.resource.increaseWood(myKingdom.harvester.harvestWood());
                 
                 detailsPanel.statsPanel.woodRes.setText(((Integer)myKingdom.resource.getWood()).toString());
                 
                 msgDisplay.setText("Wood stock has been collected.");
               }  
              else
                  msgDisplay.setText("No more Wood Stock left.");
           }    
        });
        
        
      /*-----------------------------------------------------------------------------
      trading.......
      */
      
      tradePane.reset.addActionListener(new ActionListener(){ 
          @Override
          public void actionPerformed(ActionEvent ae)
           {
              
             enableAllTradeButtons();
             tradePane.fFoodSlider.setValue(0);
             tradePane.gFoodSlider.setValue(0);
             tradePane.wWoodSlider.setValue(0);
             tradePane.gWoodSlider.setValue(0);
             msgDisplay.setText("No message to display.");
           }
        });
      tradePane.sellFood1.addActionListener(new ActionListener(){ 
          @Override
          public void actionPerformed(ActionEvent ae)
           {
              int fSell = tradePane.fFoodSlider.getValue();
              int gBuy = tradePane.gFoodSlider.getValue();
              
              
              
              myKingdom.resource.decreaseFood(fSell);
              myKingdom.resource.increaseGold(gBuy);
              
              detailsPanel.statsPanel.foodRes.setText(((Integer)myKingdom.resource.getFood()).toString());
              detailsPanel.statsPanel.goldRes.setText(((Integer)myKingdom.resource.getGold()).toString());
              
              tradePane.fFoodSlider.setValue(0);
              tradePane.gFoodSlider.setValue(0);
              
             enableAllTradeButtons();
              
             if(gBuy > 100)
                 myKingdom.king.smallIncPop();
              
              msgDisplay.setText("You just traded your "+fSell+ " food for "+gBuy+" gold.");
              
           }
        });
      tradePane.sellGold1.addActionListener(new ActionListener(){ 
          @Override
          public void actionPerformed(ActionEvent ae)
           {
              int gSell = tradePane.gFoodSlider.getValue();
              int fBuy = tradePane.fFoodSlider.getValue();
              myKingdom.resource.decreaseGold(gSell);
              myKingdom.resource.increaseFood(fBuy);
              
              detailsPanel.statsPanel.foodRes.setText(((Integer)myKingdom.resource.getFood()).toString());
              detailsPanel.statsPanel.goldRes.setText(((Integer)myKingdom.resource.getGold()).toString());
              
              tradePane.fFoodSlider.setValue(0);
              tradePane.gFoodSlider.setValue(0);
              enableAllTradeButtons();
              
             msgDisplay.setText("You just traded your "+gSell+ " gold for "+fBuy+" good.");

           }
        });
      tradePane.sellWood2.addActionListener(new ActionListener(){ 
          @Override 
          public void actionPerformed(ActionEvent ae)
           {
              
              
              int wSell = tradePane.wWoodSlider.getValue();
              int gBuy = tradePane.gWoodSlider.getValue();
              myKingdom.resource.decreaseWood(wSell);
              myKingdom.resource.increaseGold(gBuy);
              
              detailsPanel.statsPanel.woodRes.setText(((Integer)myKingdom.resource.getWood()).toString());
              detailsPanel.statsPanel.goldRes.setText(((Integer)myKingdom.resource.getGold()).toString());
                          
              tradePane.wWoodSlider.setValue(0);
              tradePane.gWoodSlider.setValue(0);
              enableAllTradeButtons();
              
              msgDisplay.setText("You just traded your "+wSell+ " wood for "+gBuy+" gold.");
  
           }
        });
      tradePane.sellGold2.addActionListener(new ActionListener(){ 
          @Override
          public void actionPerformed(ActionEvent ae)
           {
              int gSell = tradePane.gWoodSlider.getValue();
              int wBuy = tradePane.wWoodSlider.getValue();
              myKingdom.resource.decreaseGold(gSell);
              myKingdom.resource.increaseWood(wBuy);
              
              detailsPanel.statsPanel.woodRes.setText(((Integer)myKingdom.resource.getWood()).toString());
              detailsPanel.statsPanel.goldRes.setText(((Integer)myKingdom.resource.getGold()).toString());
              
              tradePane.wWoodSlider.setValue(0);
              tradePane.gWoodSlider.setValue(0);
              enableAllTradeButtons();
             
              msgDisplay.setText("You just traded your "+gSell+ " gold for "+wBuy+" wood.");

           }
        });
      
      
      
      /*-------------------------------------------------------
      --------trading Sliders*/
      
      tradePane.fFoodSlider.addMouseListener(new SliderDragListener(myKingdom,tradePane.fFoodSlider,tradePane.gFoodSlider,tradePane.sellGold1,false,'F','F','G',msgDisplay));
      tradePane.gFoodSlider.addMouseListener(new SliderDragListener(myKingdom,tradePane.gFoodSlider,tradePane.fFoodSlider,tradePane.sellFood1,true,'F','G','F',msgDisplay));
      
      tradePane.wWoodSlider.addMouseListener(new SliderDragListener(myKingdom,tradePane.wWoodSlider,tradePane.gWoodSlider,tradePane.sellGold2,false,'W','W','G',msgDisplay));
      tradePane.gWoodSlider.addMouseListener(new SliderDragListener(myKingdom,tradePane.gWoodSlider,tradePane.wWoodSlider,tradePane.sellWood2,true,'W','G','W',msgDisplay));
      
      
      
      tradePane.fFoodSlider.addMouseMotionListener(new SliderDragListener(myKingdom,tradePane.fFoodSlider,tradePane.gFoodSlider,tradePane.sellGold1,false,'F','F','G',msgDisplay));
      tradePane.gFoodSlider.addMouseMotionListener(new SliderDragListener(myKingdom,tradePane.gFoodSlider,tradePane.fFoodSlider,tradePane.sellFood1,true,'F','G','F',msgDisplay));
      
      tradePane.wWoodSlider.addMouseMotionListener(new SliderDragListener(myKingdom,tradePane.wWoodSlider,tradePane.gWoodSlider,tradePane.sellGold2,false,'W','W','G',msgDisplay));
      tradePane.gWoodSlider.addMouseMotionListener(new SliderDragListener(myKingdom,tradePane.gWoodSlider,tradePane.wWoodSlider,tradePane.sellWood2,true,'W','G','W',msgDisplay));
      
          
      
      
   }
    
}


class SliderDragListener implements MouseMotionListener,MouseListener
{  Kingdom kdom;
   JSlider fromSlider;
   JSlider toSlider;
   JButton button;
   JLabel msgDisplay;
   int val;
   int fromVal;
   boolean shouldRaise;
   int limit;
   char R;
   char from;
   char to;
   

    public SliderDragListener(Kingdom kdom,JSlider fromSlider,JSlider toSlider,JButton button,boolean shouldRaise,char R,char from,char to,JLabel msgDisplay) 
    {
        this.kdom = kdom;
        this.fromSlider =fromSlider;
        this.toSlider = toSlider;
        this.msgDisplay=msgDisplay;
        this.shouldRaise=shouldRaise;
        this.R=R;
        this.from=from;
        this.to=to;
        this.button=button;
       
    }
    
 @Override   
    public void mouseDragged(MouseEvent e)
    {
       
      val=fromSlider.getValue();
        if(shouldRaise)
            val=val*2;
        else
            val=val/2;
        toSlider.setValue(val);
    }
   @Override
    public void mouseReleased(MouseEvent e)
    {      
        
              switch(from)
              {
                  case 'F' : limit = kdom.resource.getFood();
                             break;
                  case 'W' : limit = kdom.resource.getWood();
                             break;
                  case 'G' : limit = kdom.resource.getGold();
                             break;
        
               }
        
        
        fromVal = fromSlider.getValue();
              if(fromVal > limit)
              {
                  fromVal=limit;
                  fromSlider.setValue(fromVal);
              }
              
        
        
        val=fromVal;
        if(shouldRaise)
            val=val*2;
        else
            val=val/2;
        toSlider.setValue(val);   
        
        switch(to)
        {
            case 'F' : if(val > kdom.resource.getFood())
                       {
                          button.setEnabled(false);
                        }
                       break;
            case 'W' : if(val >kdom.resource.getWood())
                      {
                         button.setEnabled(false);
                      }
                      break;
            case 'G' : if(val > kdom.resource.getGold())
                       {
                           button.setEnabled(false);
                           
                       }
                       break;
        }
        if(fromVal > val)   //then,fromval is NON gold
        {
            if(R=='F')
            {
                msgDisplay.setText("Selling "+ fromVal +" Food ,Will Yield you "+ val +" Gold" );
            }
            else if(R=='W')
            {
                msgDisplay.setText("Selling "+ fromVal +" Wood, Will Yield you "+ val +" Gold");
            }
        }
        else                //fromVal is gold
        {if(R=='F')
            {
                msgDisplay.setText("Selling "+ fromVal +" Gold,Will Yield you "+ val +" Food" );
            }
            else if(R=='W')
            {
                msgDisplay.setText("Selling "+ fromVal +" Gold, Will Yield you "+ val +" Wood");
            }
            
        }
    }
    @Override
    public void mousePressed(MouseEvent e)
    {
            
    }
    @Override
    public void mouseMoved(MouseEvent e)
    {
        
    }
   @Override 
    public void mouseEntered(MouseEvent e)
    {
        
    }
    @Override
    public void mouseClicked(MouseEvent e)
    {
        
    }
    @Override
    public void mouseExited(MouseEvent e)
    {
       
        
    }
    
    
}