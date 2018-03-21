package bmstu.hadoop.lab5;

import java.util.List;

public class JsFunction {
    private String jscript;
    private List<Integer> params;
    private String functionName;
    private String packageID;
    private String expectedResult;

    public JsFunction(String jscript, List<Integer> params, String functionName,
                      String packageID, String expectedResult) {
        this.jscript = jscript;
        this.params = params;
        this.functionName = functionName;
        this.packageID = packageID;
        this.expectedResult = expectedResult;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getJscript() {
        return jscript;
    }

    public List<Integer> getParams() {
        return params;
    }

    public String getPackageID() {
        return packageID;
    }

    public String getExpectedResult() {
        return expectedResult;
    }
}
