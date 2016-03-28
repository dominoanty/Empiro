
package empiro_pkg;
import java.io.Serializable;
import java.util.Scanner;

public class Resources implements Serializable
{
    private int food;
    private int wood;
    private int gold;
    private int citizens;
    String miscString;
    
    public Boolean hasEnoughResources(int gold, int wood, int food){    
        if(this.gold > gold && this.wood > wood && this.food > food)
            return true;
        return false;
    }
    Resources()
    {
        food = 200;
        wood = 100;
        gold = 100;
        citizens = 5;
    }
    
    public void addOneCitizen()
    {
        citizens++;
    }
    public void removeOneCitizen()
    {
        citizens--;
    }

    public int getFood() {
        return food;
    }

   

    public int getWood() {
        return wood;
    }

    

    public int getGold() {
        return gold;
    }

   public void setTotalNilResources()
   {
       gold=0;
       wood=0;
       food=0;
       citizens=0;
   }

    public int getCitizens() {
        return citizens;
    }
    public boolean hasCitizens(){
        if(citizens>0)
           return true;
        else
           return false;
    }

   public  boolean createCitizen()
    {
        if(food>=100 && wood>=50)
        {
            citizens++;
            food-=100;
            wood-=50;
            return true;
        }
        else
            return false;
    }
    
    void addCitizens(int noCitizensToBeAdded)
    {
        citizens+=noCitizensToBeAdded;
    }
    public long calcResource()
    {
        return (food+1)*(wood+1)*(gold+1);
    }
    void exportCitizensToArmy(int sendGuys)
    {
        if(sendGuys<=citizens)
        {
            citizens-=sendGuys;
            //...
            //...
        }
        else
        {
            System.out.println(" Army Requirement Exceeds Citizen count!");
        }
    }
    
   public void increaseFood(int plusFood)
    {
        food+=plusFood;
    }
   public void increaseWood(int plusWood)
    {
        wood+=plusWood;
    }
    public void increaseGold(int plusGold)
    {
        gold+=plusGold;
    }
    
    public void decreaseFood(int minusFood)
    {
        food-=minusFood;
    }
    public void decreaseWood(int minusWood)
    {
        wood-=minusWood;
    }
    public void decreaseGold(int minusGold)
    {
        gold-=minusGold;
    }
    
    
    int getFoodCount()
    {
        return food;
    }
    int getWoodCount()
    {
        return wood;
    }
    int getGoldCount()
    {
        return gold;
    }
    int getCitizenCount()
    {
        return citizens;
    }
    
    
    
    void getRevenueFromCitizens(int RevenueAmount)
    {
        gold+=citizens*RevenueAmount;
    }
    
    
    public void append(Resources Booty,double share)
    {
        this.food+=(int)(Booty.food*share);
        this.wood+=(int)(Booty.wood*share);
        this.gold+=(int)(Booty.gold*share);
        this.citizens+=(int)(Booty.citizens*share);
    }
    
    public void remove(double share)
    {
        food-=(int)(food*share);
        wood-=(int)(wood*share);
        gold-=(int)(gold*share);
        citizens-=(int)(citizens*share);
    }
    
   
   
}


