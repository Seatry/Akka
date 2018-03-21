package bmstu.hadoop.lab5;

import java.util.ArrayList;

public class ResponseMessage {
    private String packageID;
    private ArrayList<String> results;

    public ResponseMessage (String packageID, ArrayList<String> results) {
        this.packageID = packageID;
        this.results = results;
    }

    public String getPackageID() {
        return packageID;
    }

    public ArrayList<String> getResults() {
        return results;
    }
}
