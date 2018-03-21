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
        "testName",
        "expectedResult",
        "params"
})
public class Test {

    @JsonProperty("testName")
    private String testName;
    @JsonProperty("expectedResult")
    private String expectedResult;
    @JsonProperty("params")
    private List<Integer> params = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("testName")
    public String getTestName() {
        return testName;
    }

    @JsonProperty("testName")
    public void setTestName(String testName) {
        this.testName = testName;
    }

    @JsonProperty("expectedResult")
    public String getExpectedResult() {
        return expectedResult;
    }

    @JsonProperty("expectedResult")
    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    @JsonProperty("params")
    public List<Integer> getParams() {
        return params;
    }

    @JsonProperty("params")
    public void setParams(List<Integer> params) {
        this.params = params;
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