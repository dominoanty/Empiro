
package gui_pkg;

import empiro_pkg.*;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;


public class Misc 
{
    static String  DiploString = "<html>The Neighbouring Kingdoms are Sensing your growing power and prosperity...<br>To Ensure a Diplomatic Future with you,"
            + "They hve decided to Hold A DIPLOMATIC MEET in which sides will be chosen.</html>";
    static String warString = "Tensions are brewing on your borders.";
    static String[] KingNamesList = { "Arthur",
                               "Alex",
                               "Frank",
                               "Cesaro",
                               "Miz",
                               "Johnson",
                               "Wyatt",
                               "Bruce",
                               "Shane",
                               "Quinton",
                               "Watson",
                               "Phenom",
                               "DemonKnight",
                               "William",
                               "Henry",
                               "Bruce",
                               "Lee",
                               "Kevin",
                               "Owen",
                               "Heiro",
                               "Posiedon",
                               "Hades",
                               "Zeus",
                               "EvilKnight",
                               "Jacques",
                               "Jack",
                               "Tom",
                               "Harry",
                               "Mustafa",
                               "Ali",
                               "Khan",
                               "Genghis2"
                                  }; 
    
    static void msgPopup(JFrame GF,String dispMsg)
    {
        JOptionPane.showMessageDialog(GF,dispMsg);
    }   
    static String imageLoc()
    {
        //return ".\\images\\";
          return "./images/";
    }
    static String saveFolderLoc()
    {   //return ".\\SavedGames";
        return "./SavedGames";
    }
    static String LeaderBoardFolderLoc()
    {
        //return ".\\FinishedGames";
        return "./FinishedGames";
    }
    static void EmergencyDataSaver(GameFrame GF,Kingdom myKingdom,ArrayList<BotKingdom> bkList,String SAVELOC)
    {
        //File folder = new File(".\\SavedGames");
        File folder=new File(SAVELOC);
        if(!folder.exists())
            folder.mkdirs();
        
        File sav = new File(folder,myKingdom.king.kingName+".sav");
        try{
        if(!sav.exists())    
            sav.createNewFile();
        }catch(IOException ioe)
        {
            System.err.println("Not saved,...dAmn");
        }
        
        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(sav))))
        {
       
        oos.writeObject(myKingdom);
        oos.writeObject(bkList);
        
        ArrayList<Boolean> gameProgression = new ArrayList<>();
        gameProgression.add(Misc.DiplomacyCalled);
        gameProgression.add(Misc.DiplomacyDone);
        gameProgression.add(Misc.onGoingWar);
        gameProgression.add(Misc.warCalled);
        
        oos.writeObject(gameProgression);
        //oos.writeLong(currentTime);
        
        oos.flush();
        oos.close();
        
        
        JOptionPane.showMessageDialog(GF,"GameData has been saved in "+sav.getName());
              
        
        
        }catch(IOException ioe)
        {
            System.err.println("IO Exception...to save");
            ioe.printStackTrace();
        }
        
    }
    
    public static Boolean DiplomacyDone = false;
    public static Boolean DiplomacyCalled = false;
    public static Boolean warCalled = false;
    public static Boolean onGoingWar = false;
    public static int triggerWar = 0;
}
