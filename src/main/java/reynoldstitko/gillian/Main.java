package reynoldstitko.gillian;

import org.apache.commons.io.IOUtils;

import java.util.*;

public class Main {



    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws StringMismatchException, Exception{

        JerksonParser jerksonParser = new JerksonParser();
        GroceryItem groceryItem;
        DataPrintout dataPrintout = new DataPrintout();

        String output = (new Main()).readRawDataToString();

        String[] result = jerksonParser.splitIncomingStringFile(output, "##");


        ArrayList<String> output2 = jerksonParser.findItemPrices(result);
        ArrayList<String> output2Cleaned = jerksonParser.removeNullItemsFromArray(output2);

        ArrayList<String> output3 = jerksonParser.findGroceryItems(result);
        ArrayList<String> output3Cleaned = jerksonParser.removeNullItemsFromArray(output3);

        ArrayList output4 = jerksonParser.combineItemsAndPrices(output3Cleaned, output2Cleaned); //arraylist of items and prices

        HashMap<String, HashMap<String, Integer>> finalGroceryCart = new HashMap<String, HashMap<String, Integer>>();
        System.out.println(output3);
        System.out.println(output2);
        finalGroceryCart = jerksonParser.groceryCart(output3, output2);

        System.out.println(finalGroceryCart);


//        System.out.println(output3);
//        System.out.println(output4);
        //int ocurrencies = Collections.frequency(output4, "Bread 1.23");

        Set<GroceryItem> set = new HashSet<GroceryItem>(output4); //Create a new set

        //System.out.println(set);
        System.out.print(dataPrintout.printSummaryTable(set, output4, jerksonParser.getCount()));

        //http://learnfromexamples.com/how-to-find-the-occurrences-of-a-particular-element-in-an-arraylist-in-java/
        //Overwrite equals method in the GroceryItem class

    }

}
