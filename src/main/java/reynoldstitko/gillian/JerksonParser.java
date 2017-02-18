package reynoldstitko.gillian;

import java.util.*;
import java.util.regex.*;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by gillianreynolds-titko on 2/8/17.
 */
public class JerksonParser extends Exception {

    //private ArrayList<String> cleanedStrings = new ArrayList<>();
    private Map<String, String> groceryItems = new HashMap<>();

    public int getCount() {

        return count;
    }

    int count=0;

    //1. split incoming file into individual rows of data
    public String[] splitIncomingStringFile(String inputStringFile, String matcher) {
        String input = inputStringFile;
        String[] splitFile = input.split(matcher);
        return splitFile;
    }


    //Matcher: An engine that performs match operations on a character sequence by interpreting a Pattern.
    //A matcher is created from a pattern by invoking the pattern's matcher method.
    //You have to create a matcher (RegEx) that captures ALL of the input string of interest. You also signify the
    // parts of interest (that you wnat to keep) by enclosing those sections fo the RegEx in (). These will be placed
    // in groups that you can assign to your output file.

    public ArrayList<String> findGroceryItems(String[] inputStringArray) {
        ArrayList<String> itemStrings = new ArrayList<>();
        String matcherText = "(?i)name:([a-z])?\\w*;price:(\\d\\.\\d\\d)?;[a-z]?\\w*:[a-z]?\\w*[\\;|\\^|\\%|\\*|\\!|\\@]\\w*:\\d?\\/\\d?\\d\\/\\d*";
        Pattern p = Pattern.compile(matcherText);
        //do first w/o a try/catch block
        for (String item : inputStringArray) {
            Matcher m = p.matcher(item); //attempt to match the entire input sequence against the pattern
            if(m.find()){
                itemStrings.add(m.group(1)); //Group 1 returns the item.
            }
        }
        return itemStrings;
    }

    public ArrayList<String> findItemPrices(String[] inputStringArray) {
        ArrayList<String> priceStrings = new ArrayList<>();
        String matcherText = "(?i)name:([a-z])?\\w*;price:(\\d\\.\\d\\d)?;[a-z]?\\w*:[a-z]?\\w*[\\;|\\^|\\%|\\*|\\!|\\@]\\w*:\\d?\\/\\d?\\d\\/\\d*";
        Pattern p = Pattern.compile(matcherText);
        //do first w/o a try/catch block
        for (String item : inputStringArray) {
            Matcher m = p.matcher(item); //attempt to match the entire input sequence against the pattern
            if(m.find()){
                priceStrings.add(m.group(2)); //Group 2 returns the item price; add that info to the array
            }
        }
        return priceStrings;
    }

//    public Map<String, String> findItemPricesMap(String[] inputStringArray) {
//        Map<String, String> groceryItems = new HashMap<>();
//        String matcherText = "(?i)name:([a-z])?\\w*;price:(\\d\\.\\d\\d)?;[a-z]?\\w*:[a-z]?\\w*[\\;|\\^|\\%|\\*|\\!|\\@]\\w*:\\d?\\/\\d?\\d\\/\\d*";
//        Pattern p = Pattern.compile(matcherText);
//        //do first w/o a try/catch block
//       for(int i =0; i < inputStringArray.length; i++){
//           Matcher m = p.matcher(inputStringArray[i]); //attempt to match the entire input sequence against the pattern
//           if(m.find()){
//               groceryItems.put(m.group(1), m.group(2)); //Group 2 returns the item price; add that info to the array
//           }
//       }
//        return groceryItems;
//    }

    public ArrayList<GroceryItem> combineItemsAndPrices(ArrayList<String> item, ArrayList<String> price) throws StringMismatchException {
        ArrayList<GroceryItem> groceryItemList = new ArrayList<>();
        for (int i = 0; i < item.size(); i++) {
            try {

                if (item.get(i) == null || price.get(i) == null)
                    throw new StringMismatchException();
                groceryItemList.add(new GroceryItem(refactorNames(item.get(i)), price.get(i)));

            } catch (StringMismatchException e) {
                //System.out.println("An error was found during data intake");
                count = count + 1;
            }
        } return groceryItemList;
    }

    public ArrayList<String> removeNullItemsFromArray(ArrayList<String> arrayToClean) throws StringMismatchException {
        ArrayList<String> cleanedList = new ArrayList<>();
        for (int i = 0; i < arrayToClean.size(); i++) {
            try {
                if (arrayToClean.get(i) == null)
                    throw new StringMismatchException();
                cleanedList.add(arrayToClean.get(i));
                //cleanedList.add(refactorNames(arrayToClean.get(i)));
            } catch (StringMismatchException e) {
                //System.out.println("An error was found during data intake");
                count = count + 1;
            }
        } return cleanedList;
    }

    public HashMap<String, HashMap<String, Integer>> groceryCart(ArrayList<String> groceryItemCleaned, ArrayList<String> itemPriceCleaned) {
        //remove null items in each array and capitalize words

        Integer timesItemSeen = 0;
        Integer timesPriceSeen = 0;

        HashMap<String, HashMap<String, Integer>> finalGroceryCart = new HashMap<String, HashMap<String, Integer>>();
        HashMap<String, Integer> innerMap = new HashMap<String, Integer>();

        //System.out.println(groceryItemCleaned.get(0)); //"Milk"

        for(int i = 0; i < groceryItemCleaned.size()-1; i++){

            //if the item is already there, increment the count of that item - if the price is a repeat, increase the count,
            //otherwise add the price,
            if(finalGroceryCart.containsKey(groceryItemCleaned.get(i))){
                //System.out.println(finalGroceryCart.containsKey(groceryItemCleaned.get(i)));

                timesItemSeen += 1;
                String priceCaptured = itemPriceCleaned.get(i);
                //System.out.println("Price captured: " + priceCaptured);
                //System.out.println("Grocery item :" + finalGroceryCart.get(i).get(priceCaptured));
                System.out.println("Grocery item :" + finalGroceryCart.get(groceryItemCleaned.get(i)));
                //if(finalGroceryCart.get(i).containsValue(priceCaptured))
                if(finalGroceryCart.get(groceryItemCleaned.get(i)).containsValue(priceCaptured)){
                    timesPriceSeen +=1;
                }
                //otherwise add the item and its corresponding price;
            } else {
                innerMap.put(itemPriceCleaned.get(i), timesPriceSeen);
                finalGroceryCart.put(groceryItemCleaned.get(i), innerMap);
            }
            //if the price for that item is already there, increase its count, otherwise, add the price as new for that item
            timesItemSeen += 1;
            innerMap.put(itemPriceCleaned.get(i), timesPriceSeen);
            finalGroceryCart.put(groceryItemCleaned.get(i), innerMap);
        }

        return finalGroceryCart;
    }




    //public HashMap<String GroceryItem, ArrayList<String, String> itemPrices>

    //2. apply regex to each row of data - split into component (GroceryItem) objects;
    // catch errors (missing price, missing name)
//    public GroceryItem[] createGroceryItemsArray(String[] string, String matcher){ //throw errors; get array of [food, price]
//
//        return null;
//    }

    public String refactorNames(String letter){
        switch (letter.toLowerCase()){
            case "b":
                return "Bread";
            case "a":
                return "Apples";
            case "m":
                return "Milk";
            case "c":
                return "Cookies";
            default:
                return letter;
        }
    }


}
