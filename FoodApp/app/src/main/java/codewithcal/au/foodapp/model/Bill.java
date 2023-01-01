package codewithcal.au.foodapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bill {
    @SerializedName("accountId")
    @Expose
    private int accountId;
    @SerializedName("foodId")
    @Expose
    private int foodId;
    @SerializedName("quantity")
    @Expose
    private int quantity;

    public Bill() {
    }

    public Bill(int accountId, int foodId, int quantity) {
        this.accountId = accountId;
        this.foodId = foodId;
        this.quantity = quantity;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
