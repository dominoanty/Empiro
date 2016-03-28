
package empiro_pkg;

import java.io.Serializable;

public class ResourceHarvester implements Serializable
{
    int foodStock;
    int woodStock;
    //foodStock and WoodStock keep piling up(depending on prodRate)...and then they get HARVESTED..set to ZERO
    int foodProdRate; //Rate implies-->AMT added per second....if the rate is 1..then 1 Wood added to stock per sec.
    int woodProdRate;
    int CitizensCountInFoodProd;
    int CitizensCountInWoodProd;
    int currLvl;
    
    ResourceHarvester()
    {
        foodStock=0;   //at start,no citizen is assigned to harvest job..so no stock
        woodStock=0;  
        foodProdRate=0; //1 citizen added to job--->Rate=Rate++;
        woodProdRate=0;
        currLvl=1;
    }

  /*  public int getNoCitizensFoodProd() {
        return CitizensCountInFoodProd;
    }

    public void setNoCitizensFoodProd(int noCitizensFoodProd) {
        this.noCitizensFoodProd = noCitizensFoodProd;
    }

    public int getNoCitizensWoodProd() {
        return noCitizensWoodProd;
    }

    public void setNoCitizensWoodProd(int noCitizensWoodProd) {
        this.noCitizensWoodProd = noCitizensWoodProd;
    }*/
    
    public int getCitizensCountInFoodProd()
    {
        return CitizensCountInFoodProd;
    }
    public int getCitizensCountInWoodProd()
    {
        return CitizensCountInWoodProd;
    }
    public int getFoodStock() {
        return foodStock;
    }

    

    public int getWoodStock() {
        return woodStock;
    }

   
    public int getFoodProdRate() {
        return foodProdRate;
    }

    
    public int getWoodProdRate() {
        return woodProdRate;
    }

   
    public int getCurrLvl() {
        return currLvl;
    }

    public boolean hasFoodStock()
    {
        if(foodStock>0)
            return true;
        else
            return false;
    }
    public boolean hasWoodStock()
    {
        if(woodStock>0)
            return true;
        else
            return false;
    }
            
     public boolean hasFoodWorker()
     {
         if(CitizensCountInFoodProd > 0)
             return true;
         else
             return false;
     }
     public boolean hasWoodWorker()
     {
         if(CitizensCountInWoodProd > 0)
             return true;
         else
             return false;
     }
    
    
    
  public  int harvestFood()    //call this....equating it to resource.food
    {
        //...send foodStock to Resource.food
        int FoodToTransport=foodStock;
        foodStock=0;
        return FoodToTransport;
    }
  public int harvestWood()    //call this...equating it to resource.wood
    {
        //...send woodStock to Resource.wood
        int WoodToTransport=woodStock;
        woodStock=0;
        return WoodToTransport;
    }
    
    


    public void updateAllStocks()
    {
        foodStock += foodProdRate;
        woodStock += woodProdRate;
        
    }

}
