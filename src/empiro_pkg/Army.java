

package empiro_pkg;
import java.io.*;

public class Army implements Serializable
{
    int militaryCount;
    int militaryLvl;
    
    float militaryHealth;
    float militaryPower;
    public Army()
    {
        militaryCount = 1;
        militaryLvl = 1;
        militaryHealth = 1000;
        militaryPower = militaryCount*militaryLvl*militaryHealth/100;
    }

    public int getMilitaryLvl() {
        return militaryLvl;
    }

    public void setMilitaryLvl(int militaryLvl) {
        this.militaryLvl = militaryLvl;
    }

    public float getMilitaryHealth() {
        return militaryHealth;
    }

    public void setMilitaryHealth(float militaryHealth) {
        this.militaryHealth = militaryHealth;
    }

    public float getMilitaryPower() {
        return militaryPower;
    }

    public void setMilitaryPower(float militaryPower) {
        this.militaryPower = militaryPower;
    }

    public int getMilitaryCount() {
        return militaryCount;
    }

    public void setMilitaryCount(int militaryCount) {
        this.militaryCount = militaryCount;
    }
    
    void increaseMilitaryCount(int added)
    {
        militaryCount+=added;
    }
    void increaseMilitaryLvl()
    {
        militaryLvl+=1;
    }

    void increaseMilitaryHealth()
    {
        militaryHealth+=0.05;
    }
    void decreaseMilitaryHealth(float slashed)
    {
        militaryHealth-=slashed;
        
        if(militaryHealth<0)               //if totally Ruined...keep a little health
            militaryHealth=(float)0.05;
    }
    
    
    boolean canFight()
    {
        if(militaryHealth>0.4)
            return true;
        else
            return false;
        
    }
    
    public float calcMilitaryPower()
    {
        militaryPower=militaryCount*militaryLvl*militaryHealth/100;
        return militaryPower;
    }
    
    void append(Army WarPrisoners,float share)
    {
        militaryCount+=WarPrisoners.militaryCount*share;
     }
    void remove(float share)
    {
        militaryCount-=militaryCount*share;
    }
}
