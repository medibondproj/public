import java.util.Scanner; 

public class Utils
{
    /**
     *  Halts execution until ENTER
     */
    public static void pressEnterToContinue()
    { 
        System.out.println("Press ENTER to continue...");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }

    /**
     *  Clears the terminal window
     */
    public static void clearTerminalWindow()
    {
        System.out.print('\u000C'); 
    }
    
    
}