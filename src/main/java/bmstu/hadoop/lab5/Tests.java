package bmstu.hadoop.lab5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "packageId",
        "jsScript",
        "functionName",
        "tests"
})
public class Tests {

    @JsonProperty("packageId")
    private String packageId;
    @JsonProperty("jsScript")
    private String jsScript;
    @JsonProperty("functionName")
    private String functionName;
    @JsonProperty("tests")
    private List<Test> tests = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public int getSize() {
        return tests.size();
    }

    @JsonProperty("packageId")
    public String getPackageId() {
        return packageId;
    }

    @JsonProperty("packageId")
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    @JsonProperty("jsScript")
    public String getJsScript() {
        return jsScript;
    }

    @JsonProperty("jsScript")
    public void setJsScript(String jsScript) {
        this.jsScript = jsScript;
    }

    @JsonProperty("functionName")
    public String getFunctionName() {
        return functionName;
    }

    @JsonProperty("functionName")
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @JsonProperty("tests")
    public List<Test> getTests() {
        return tests;
    }

    @JsonProperty("tests")
    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
