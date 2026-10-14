
package entity;

public class ProblemType {
    private int problemTypeId;
    private String problemTypeName;

    public ProblemType() {
    }

    public ProblemType(int problemTypeId, String problemTypeName) {
        this.problemTypeId = problemTypeId;
        this.problemTypeName = problemTypeName;
    }

    public int getProblemTypeId() {
        return problemTypeId;
    }

    public void setProblemTypeId(int problemTypeId) {
        this.problemTypeId = problemTypeId;
    }

    public String getProblemTypeName() {
        return problemTypeName;
    }

    public void setProblemTypeName(String problemTypeName) {
        this.problemTypeName = problemTypeName;
    }

    @Override
    public String toString() {
        return problemTypeName;
    }
}
