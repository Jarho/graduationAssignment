public class Student {
    int volunNumber;//志愿编号组
    int ranking;//成绩排名
    boolean status;//标志位判断是否已选择导师,1为已选，0为未选;
    String teacherNumber="无";

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    Student(){}
    Student(int volunNumber,int ranking,boolean status)
    {
        this.ranking=ranking;
        this.status=status;
        this.volunNumber=volunNumber;
    }

    public int getVolunNumber() {
        return volunNumber;
    }

    public void setVolunNumber(int volunNumber) {
        this.volunNumber = volunNumber;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean statue) {
        this.status = statue;
    }

    @Override
    public String toString() {
        String sta;
        if(status){
            sta="已被选";
        }else {
            sta="未被选";
        }
        return "Student [排名=" + ranking + ", 志愿编号=" + volunNumber +", 选择的老师=" + teacherNumber+ ",当前状态="+sta+"]";
    }
    //学生被选后更新状态
    void updateStudent(int teacherNumber){
        this.teacherNumber=String.valueOf(teacherNumber);
        this.status=true;
    }
}
