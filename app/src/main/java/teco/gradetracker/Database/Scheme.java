package teco.gradetracker.Database;

/**
 * Created by loc18 on 13/07/2017.
 */

public class Scheme {
    public static final class Assignment {
        //Table name
        public static final String TABLE_NAME = "assignment";

        //Table Column name
        public static final  String ID = "_id";
        public static final  String NAME = "name";
        public static final  String WORTH = "worth";
        public static final  String GRADE = "grade";
        public static final  String UNIT_NAME = "unit_name";

        private Assignment(){}
    }
    public static final class Unit {
        //Table name
        public static final String TABLE_NAME = "unit";

        //Table Column name
        public static final  String ID = "_id";
        public static final  String NAME = "name";

        private Unit(){}
    }
}
