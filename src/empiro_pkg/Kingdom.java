
package empiro_pkg;
import java.io.*;

public class Kingdom implements Serializable,Comparable
{
    String kingdomName;
    protected int GID;
    
    /**
     *
     */
    public King king;
    public Resources resource;
    public ResourceHarvester harvester;
    public Army army;
    public Boolean isAlive;
    public Kingdom ally;
    
    
    public Kingdom()
    {   GID=1;
        isAlive = false;
        king=new King();
        resource=new Resources();
        harvester=new ResourceHarvester();
        army=new Army();
    }

    public void setPlayerGID()             //used only once @ start of game to identify you as a Human player
    {
        GID=0;
    }
    public void setAsAlly(Kingdom KDOM)
    {  KDOM.GID=this.GID;
       this.ally = KDOM;
       KDOM.ally = this;
       if(this.army.calcMilitaryPower() > KDOM.army.calcMilitaryPower())
       {
           this.snatchLoot(KDOM, 0.3f);
           System.err.println("food:" +this.resource.getFood());
           this.king.smallIncPop();
           
       }
       else if(this.army.calcMilitaryPower() < KDOM.army.calcMilitaryPower())

       {
           KDOM.snatchLoot(this, 0.3f);
          System.err.println("food:" +this.resource.getFood());
           this.king.smallDecPop(); 
       }
       
        
    }
   boolean isAlly(Kingdom KDOM)
   {
       if(this.GID == KDOM.GID)
           return true;
       else
           return false;
                   
   }
    public int compareTo(Object kdomBeingCompared)
    {
        float hisPower = ((Kingdom)kdomBeingCompared).army.getMilitaryPower();
        return (int)((this.army.getMilitaryPower() - hisPower)*100);
        
        /*int hisFOOD = ((Kingdom)kdomBeingCompared).resource.getFood();
        return hisFOOD - this.resource.getFood();*/
        
        
    }
    
    public void setKingProperties(String kingname,float popularity,int lvl)
    {
        king.setName(kingname);
        king.kingPopularity=popularity;
        king.kingLvl=lvl;
    }
    
   public String showGainOrLossResources(Kingdom KDOM)
    {  
        Resources rsrc = new Resources();
        if(this.army.calcMilitaryPower() > KDOM.army.calcMilitaryPower())
       {  
           rsrc.setTotalNilResources();
           rsrc.increaseFood((int)(0.3f * KDOM.resource.getFood()));
           rsrc.increaseWood((int)(0.3f * KDOM.resource.getWood()));
           rsrc.increaseGold((int)(0.3f * KDOM.resource.getGold()));
           rsrc.miscString = "<html>For the sake of Diplomatic Relationships,<br>"
                   + "Your Kingdom has Recieved <br>"
                   + rsrc.getGold() +" Gold "
                   + rsrc.getFood() +" Food "
                   + rsrc.getWood() +" Wood "
                   +"And also , few Citizens and Army Units.";
       }
        else if(this.army.calcMilitaryPower() < KDOM.army.calcMilitaryPower())
       {   rsrc.setTotalNilResources();
           rsrc.increaseFood((int)(0.3f * this.resource.getFood()));
           rsrc.increaseWood((int)(0.3f * this.resource.getWood()));
           rsrc.increaseGold((int)(0.3f * this.resource.getGold()));
           rsrc.miscString = "<html>For the sake of Diplomatic Relationships,<br>"
                   + "Your Kingdom has Sacrificed <br>"
                   + rsrc.getGold() +" Gold "
                   + rsrc.getFood() +" Food "
                   + rsrc.getWood() +" Wood "
                   +"And also , few Citizens and Army Units.";
           
       }
        else
        {
            
         rsrc.miscString="<html>Looks Like Both of you are equal in terms of power.<br>"
                  +"No Resource Loss(Diplomacy Fee Waivered) for both of you";
        }
        
        //System.err.println("militaryPower :"+this.army.calcMilitaryPower());
        //System.err.println("militaryPower :"+KDOM.army.calcMilitaryPower());
        
      //  System.out.println("food trnsfredd :"+(int)(0.3f * this.resource.getFood()));
          //((int)0.3f * this.resource.getWood());
         //  rsrc.increaseGold((int)0.3f * this.resource.get
      
        
        return rsrc.miscString;
    }
    
    void snatchLoot(Kingdom Enemy,float share)
    {
        this.resource.append(Enemy.resource, share);
        this.army.append(Enemy.army,share);
        
        Enemy.resource.remove(share);
        Enemy.army.remove(share);
       
    }
    
    
        
   public void assignCitizenToFoodProd() //one guy added to farm
    {
        harvester.CitizensCountInFoodProd +=1;
        harvester.foodProdRate+=2;
        resource.removeOneCitizen();
    }
   public void assignCitizenToWoodProd()  //one guy added to (lumberjacks)
    {
        harvester.CitizensCountInWoodProd +=1;
        harvester.woodProdRate+=2;
        resource.removeOneCitizen();
        
    }
   public void fireCitizenFromFoodProd()
   {
       harvester.CitizensCountInFoodProd -=1;
       harvester.foodProdRate-=2;
       resource.addOneCitizen();
   }
   public void fireCitizenFromWoodProd()
   {
       harvester.CitizensCountInWoodProd -=1;
       harvester.woodProdRate-=2;
       resource.addOneCitizen();
   }
   public void createBattalion()
   {
     resource.decreaseFood(50);
     resource.decreaseGold(100);
     resource.decreaseWood(50);
     army.setMilitaryCount(army.getMilitaryCount()+1);
   }
   public Boolean goldHoldBackHarvesterUpgrade()
   {
       if(resource.getGold()<500*harvester.currLvl && resource.getWood() > 500*harvester.currLvl )
           return true;
       else 
           return false;
            
   }
   public Boolean canUpgradeHarvester()
   {
       if(resource.getGold()>500*harvester.currLvl && resource.getWood() > 500*harvester.currLvl )
           return true;
       else
           return false;
   }
   public void upgradeResourceHarvester()
    {
      if(harvester.currLvl<3)
      {
          
          harvester.foodProdRate+=20;
          harvester.woodProdRate+=20;
          resource.decreaseGold(500*harvester.currLvl );
          resource.decreaseWood(500*harvester.currLvl );
          harvester.currLvl++;
          this.king.smallIncPop();
      }
      else
      {
          System.err.println("Harvester MAXED OUT");
      }
    }
    public Boolean goldHoldBackArmyUpgrade()
    {
         if(resource.getGold() < 300*army.getMilitaryLvl() && resource.getWood() > 200*army.getMilitaryLvl() && resource.getFood() > 200*army.getMilitaryLvl())
            return true;
        else
            return false;
    }
    public Boolean canUpgradeArmyLevel()
    {
        if(resource.getGold() > 300*army.getMilitaryLvl() && resource.getWood() > 200*army.getMilitaryLvl() && resource.getFood() > 200*army.getMilitaryLvl())
            return true;
        else
            return false;
           
    }
    public void upgradeArmyLevel()
    {   
        int lvl=army.getMilitaryLvl();
        resource.decreaseGold(300*lvl*lvl);
        resource.decreaseWood(200*lvl*lvl);
        resource.decreaseFood(200*lvl*lvl);
        army.increaseMilitaryLvl();
                this.king.smallIncPop();
    }
}
