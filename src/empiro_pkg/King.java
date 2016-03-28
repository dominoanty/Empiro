
package empiro_pkg;
import java.io.*;

public class King implements Serializable
{

    /**
     *
     */
    public String kingName;
    float kingPopularity;//0-1
    int kingLvl;//0-infinity
    
   King()
   {
       
   }
   public int getPopularity()
   {
       return (int)(kingPopularity*100);
   }
  public void setName(String name)
   {
       kingName=name;
   }
   public boolean check_END_GAME()
  {
      if(kingPopularity<0.1)
          return true;
      else
          return false;
  }
    
    
    public void increasePopularity(double incPop)
    {
       kingPopularity+=incPop;
       
    }
    public void smallIncPop()
    {
        kingPopularity+=0.02;
    }
    public void smallDecPop()
    {
        kingPopularity-=0.02;
    }
    public void decreasePopularity(double decPop)
    {
        kingPopularity-=decPop;
    }
    
    void  increaseLvl()
    {
        kingLvl+=1;
    }
    
}
