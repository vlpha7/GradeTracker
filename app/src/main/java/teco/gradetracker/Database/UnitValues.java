package teco.gradetracker.Database;

/**
 * Created by loc18 on 13/07/2017.
 */

public class UnitValues {
    private int id;
    private String name;

    //CONSTRUCTORS
    public UnitValues(){

    }
    public UnitValues(String name, int worth, int grade, String unit_name){
        this.name = name;
    }

    //ASSESSORS
    public int getId(){ return id; }

    public String getName(){ return name; }

    //MUTATORS
    public void setId(int id){ this.id = id; }

    public void setName(String name){ this.name = name; }


    @Override
    public String toString(){
        return "AssignmentValues{" +
                "name='" + name + '\'' +
                '}';
    }
}
