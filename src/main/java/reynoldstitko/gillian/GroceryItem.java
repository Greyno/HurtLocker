package reynoldstitko.gillian;

import java.util.Objects;

/**
 * Created by gillianreynolds-titko on 2/9/17.
 */
public class GroceryItem {

    private String name;
    private String price;
    private String type;
    private String expiration;

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getExpiration() {
        return expiration;
    }

    //Constructor
    public GroceryItem(String name, String price) {
        this.name = name;
        this.price = price;
        this.type = "Food";
    }

    //4. Calculate numeric data
    public Double[] calculatePriceSummaryData(Double priceData){ //get sum of prices

        return null;
    }

    public int calculateGroceryItemCount(GroceryItem groceryItem){ //get count of a grocery item

        return -1;
    }

    @Override
    public String toString(){
        return (name + " " + price);
    }

    @Override
    public boolean equals(Object arg0) {
        // TODO Auto-generated method stub
        boolean flag = false;
        GroceryItem groceryItem = (GroceryItem) arg0;
        if(null!= groceryItem && groceryItem.getPrice().equalsIgnoreCase(price) && groceryItem.getName().equalsIgnoreCase(name)){
            flag = true;
        }
        return flag;
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, price);
    }

}
