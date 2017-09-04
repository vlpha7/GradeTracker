package teco.gradetracker.Database;

/**
 * Created by loc18 on 13/07/2017.
 */

public class AssignmentValues {
    private int id;
    private String name;
    private int worth;
    private int grade;
    private int unit_name;

    //CONSTRUCTORS
    public AssignmentValues(){

    }
    public AssignmentValues(String name, int worth, int grade, int unit_name){
        this.name = name;
        this.worth = worth;
        this.grade = grade;
        this.unit_name = unit_name;
    }

    //ASSESSORS
    public int getId(){ return id; }

    public String getName(){ return name; }

    public int getWorth(){ return worth; }

    public int getGrade(){ return grade; }

    public int getUnitName(){ return  unit_name; }

    //MUTATORS
    public void setId(int id){ this.id = id; }

    public void setName(String name){ this.name = name; }

    public void setWorth(int worth){ this.worth = worth; }

    public void setGrade(int grade){ this.grade = grade; }

    public void setUnitName(int unit_name){ this.unit_name = unit_name; }

    @Override
    public String toString(){
        return "AssignmentValues{" +
                "name='" + name + '\'' +
                ", worth=" + worth +
                ", grade=" + grade +
                ", unit_name'" + unit_name + '\'' +
                '}';

    }
}

