package bgu.spl.mics.application.objects;

/**
 * Passive object representing a Deep Learning model.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Model {

    public enum Status {PreTrained, Training, Trained, Tested}
    public enum Results {None,Good, Bad}
    private String name;
    private Data data;
    private Student student;
    private Status status;
    private Results result;
    private String type;
    private int size;


//    public Model(String name, Data data,Student student){
//        this.name=name;
//        this.data=data;
//        this.student=student;
//        status=Status.PreTrained;
//        result= Results.None;
//    }
    public void init(Student student){
        if(type.equals("Images")){
            this.data=new Data(Data.Type.Images,size);
        }
        else if(type.equals("Tabular")){
            this.data=new Data(Data.Type.Tabular,size);
        }
        else {
            this.data=new Data(Data.Type.Text,size);
        }

        this.student=student;
        status=Status.PreTrained;
        result= Results.None;
    }
    public Model(String name, String stype,int size){
        if(stype.equals("Images")){
            this.data=new Data(Data.Type.Images,size);
        }
        else if(stype.equals("Tabular")){
            this.data=new Data(Data.Type.Tabular,size);
        }
        else {
            this.data=new Data(Data.Type.Text,size);
        }
        this.name=name;
        this.student=null;
        status=Status.PreTrained;
        result= Results.None;
       this.type=null;
       this.size=0;

    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Results getResult() {
        return result;
    }

    public void setResult(Results result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDataSize(){
        return data.getSize();
    }
}
