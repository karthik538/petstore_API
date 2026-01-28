package utils;

public class TestContext {
	private long petId;
    private String name;
    private String status;
    private String metadata;
    private String fileName;
    private String operation;
    
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
    
    public long getPetId() { return petId; }
    public void setPetId(long petId) { this.petId = petId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public void reset() {
        this.petId = 0;
        this.name = null;
        this.status = null;
        this.metadata = null;
        this.fileName = null;
    }

}
