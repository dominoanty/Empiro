
package empiro_pkg;

import gui_pkg.Misc;
import gui_pkg.WarSetUpFrame;
import java.util.Random;
import java.io.*;
public class BotKingdom extends Kingdom implements Runnable,Serializable
{
    transient Thread botThread;
    Random randGenerator;
    int randChoice;
    int randSleep;
    int randAttack;
    @Override
    public void run()
    {
        while(isAlive)
        {
            try{
                randGenerator = new Random();
                randChoice = randGenerator.nextInt(9000);
                randSleep = randGenerator.nextInt(3) + 1;
                randAttack = randGenerator.nextInt(3) + 1;
                harvester.updateAllStocks();
               /* if(randChoice < army.calcMilitaryPower())
                {
                    switch(randAttack)
                    {
                        case 1:
                                    //Attack MyKingdom
                            break;
                        case 2: 
                                    //Attack other Botkingdom
                            break;
                        case 3:
                                    //Attack other botkingdom
                            break;
                        case 4:   
                                    //Attack other botkingdom
                            break;
                            
                    }
                    log("War");
                }*/
                if (randChoice < 25 && this.army.militaryHealth > 400 && Misc.DiplomacyDone)
                {
                    //Diplomacy
                    Misc.triggerWar = 2;
                }
                
                else if(randChoice < 1000)
                {
                    if(this.goldHoldBackArmyUpgrade())
                    {
                        if(this.resource.getFood() > resource.getWood())
                        {
                            this.resource.decreaseFood((300*this.army.militaryLvl));
                            this.resource.increaseGold( 150*this.army.militaryLvl);
                        }
                        else
                        {
                            this.resource.decreaseWood((300*this.army.militaryLvl));
                            this.resource.increaseGold( 150*this.army.militaryLvl);
                            
                        }
                        System.out.println("Gold holding back");
                    }
                    if(this.canUpgradeArmyLevel())
                    {
                        this.upgradeArmyLevel();
                        log("Army Upgraded ! ");
                        
                    }
                    else
                    {
                                            System.out.println("Other things holding back");

                    }
                }
                else if(randChoice <2000)
                {
                    if(this.goldHoldBackHarvesterUpgrade())
                    {
                        this.resource.increaseGold(this.resource.getFood()/2);
                        this.resource.decreaseFood(this.resource.getFood());
                        System.out.println("Gold holding back");

                    }
                    if(this.canUpgradeHarvester())
                    {    
                        this.upgradeResourceHarvester();
                        log("Resource harvester upgraded ! ");
                    }
                    else
                    {
                                            System.out.println("Other things holding back");

                    }
                }
                else if(randChoice < 4000)
                {
                    if(resource.getCitizenCount()>0 && resource.getGold()>100 && resource.getWood()>50 && resource.getFood() > 50)
                    {
                        
                        this.createBattalion();
                        log("Batallion created");
                    }
                }
                else if (randChoice < 5500 - 100*harvester.getCitizensCountInFoodProd() - 100*harvester.getCitizensCountInWoodProd())
                {
                    if(resource.getCitizenCount()>0)
                    {
                        if(harvester.getCitizensCountInFoodProd() < harvester.getCitizensCountInWoodProd())
                        {
                            log("Citizen assigned to food produciton");
                            this.assignCitizenToFoodProd();
                        }
                        else 
                        {
                            this.assignCitizenToWoodProd();
                            log("Citizen assigned to wood production");
                        }
                    }
                }
                else if(randChoice < 7000 - 100 * resource.getCitizenCount() )
                {
                    if(resource.getFood()> 100 && resource.getWood() > 50)
                    {   
                        resource.addOneCitizen();
                        resource.decreaseFood(100);
                        resource.decreaseWood(50);
                        log("Citizen Created");

                    }
                }
                else if(randChoice <8000 && (this.resource.getGold() < this.resource.getFood()/2 + this.resource.getWood()/2))
                {
                    System.out.println("Trading gold");
                        this.resource.increaseGold(this.resource.getFood()/2 + this.resource.getWood()/2);
                        this.resource.decreaseFood(this.resource.getFood());
                        this.resource.decreaseWood(this.resource.getWood());
                }
                else
                {
                    resource.increaseFood(harvester.harvestFood());
                    resource.increaseWood(harvester.harvestWood());
                    log("Food and Wood Harvested");
                    
                }   
                Thread.sleep(randSleep * 1000);

            }
            catch(InterruptedException ie)
            {
                System.out.println("Thread Interrupted ! ");
            }
            
        }
        
    }
    public BotKingdom()
    {
        
        
    }
    public void startBotThread()
    {
        botThread = new Thread(this,"Bot");
        botThread.start();
    }
    
    private static void log(String print)
    {
        System.out.println(print);
    }
    
}
