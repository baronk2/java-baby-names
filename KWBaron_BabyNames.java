/*
Kevin Baron
1/15/13
Baby Names
*/

import java.awt.*;
import java.util.Scanner;
import java.io.*;

public class KWBaron_BabyNames {
	
	//class constants
   public static final int DECADES = 9;
   public static final int STARTYEAR = 1920;
   public static final String FILENAME = "names2.txt";
   public static final int COLUMNWIDTH = 90;

	/*give an intro, get a name from the user,
	find that name in the database, and work with
	it if it is found*/
   public static void main (String[] args) throws FileNotFoundException {
      intro();
      String searchName = polish(prompt());
      String data = findName(searchName);
      if (data.equals("")) {
         System.out.println("name not found");
      } else {
         graph(searchName, data);
      }//eo if else
   }//eo main

   public static void intro() {
      System.out.println("This program allows you to search through the");
      System.out.println("data from the Social Security Administration");
      System.out.println("to see how popular a particular name has been");
      System.out.println("since " + STARTYEAR + ".\n");
   }//eo intro
	
	//returns the name to be found
   public static String prompt() {
      System.out.print("name? ");
      return new Scanner(System.in).nextLine();
   }//eo prompt
	
	//change from NAME or nAME to Name
   public static String polish(String name) {
      return name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();
   }//eo polishsita
	
	/*return the data for the name if it is found.
	Otherwise, return a blank String*/
   public static String findName(String name) throws FileNotFoundException {
      Scanner database = new Scanner(new File(FILENAME));
      while (database.hasNextLine()) {
         if (name.equals(database.next())) {
            return database.nextLine();
         } else {
            database.nextLine();
         }//eo if else
      }//eo while
      return "";
   }//eo findName
	
	/*create the screen and draw the lines
	that are always the same.
	Then graph the first year(decade).
	Then graph the other years with red
	lines connecting to the previous decade*/
   public static void graph(String name, String data) {
      int screenWidth = DECADES * COLUMNWIDTH;
      Graphics g = new DrawingPanel(screenWidth, 550).getGraphics();
      g.drawLine(0, 25, screenWidth, 25);
      g.drawLine(0, 525, screenWidth, 525);
      Scanner dataScanner = new Scanner(data);
      int popNow = dataScanner.nextInt();
      graphYear(g, name, STARTYEAR, 0, popNow);
      for (int i = 1; i < DECADES; i++) {
         int popLast = popNow;
         popNow = dataScanner.nextInt();
         int x = COLUMNWIDTH * i;
         int xLast = COLUMNWIDTH * (i - 1);
         int year = STARTYEAR + i * 10;
         g.setColor(Color.BLACK);
         graphYear(g, name, year, x, popNow);
         g.setColor(Color.RED);
         g.drawLine(xLast, popAdjuster(popLast), x, popAdjuster(popNow));
      }//eo for
   }//eo graph
	
	//draw one line and two Strings for each year
   public static void graphYear(Graphics g, String name, int year, int x, int popularity) {
      g.drawLine(x, 0, x, 550);
      g.drawString("" + year, x, 550);
      g.setColor(Color.RED);
      g.drawString(name + " " + popularity, x, popAdjuster(popularity));
   }//eo graphPopularity
	
	//tells where to plot popularity based on number 0-1000
   public static int popAdjuster(int popularity) {
      if (popularity == 0) {
         return 525;
      } else {
         return (popularity - 1) / 2 + 25;
      }//eo if else
   }//eo popAdjuster

}//eo class