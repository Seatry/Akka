package bmstu.hadoop.lab5;

public class StoreMessage {
    private String pakcageID, result;

    public StoreMessage(String pakcageID, String result) {
        this.pakcageID = pakcageID;
        this.result = result;
    }

    public String getPakcageID() {
        return pakcageID;
    }

    public String getResult() {
        return result;
    }
}
