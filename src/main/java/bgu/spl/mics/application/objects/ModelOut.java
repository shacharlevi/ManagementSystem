package bgu.spl.mics.application.objects;

public class ModelOut {
    private String name;
    private OutData data;
    private Model.Status status;
    private Model.Results results;

    public ModelOut(String name, OutData data,Model.Status status, Model.Results results) {
        this.name = name;
        this.data = data;
        this.status = status;
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OutData getData() {
        return data;
    }

    public void setData(OutData data) {
        this.data = data;
    }

    public Model.Status getStatus() {
        return status;
    }

    public void setStatus(Model.Status status) {
        this.status = status;
    }

    public Model.Results getResults() {
        return results;
    }

    public void setResults(Model.Results results) {
        this.results = results;
    }
}
