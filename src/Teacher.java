public class Teacher {
    int stuNumber;//当前学生人数
    int stuMax;//最大学生人数
    int meanStu;//当前平均排名
    int teacherNumber;//老师编号
    int stuSelected[];//已选的学生
    String selected="无";
    void Teacher()
    {

    }
     Teacher(int stuMax,int stuNumber,int meanStu,int teacherNumber)
    {
        this.stuMax=stuMax;
        this.stuNumber=stuNumber;
        this.meanStu=meanStu;
        this.teacherNumber=teacherNumber;
    }
     Teacher(int stuMax,int stuNumber,int teacherNumber,int[] stuSelected)
    {
        this.stuMax=stuMax;
        this.stuNumber=stuNumber;
        this.teacherNumber=teacherNumber;
        this.stuSelected=stuSelected;
    }


    public int getMeanStu() {
        return meanStu;
    }

    public int getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(int stuNumber) {
        this.stuNumber = stuNumber;
    }

    public int getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(int teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public void setMeanStu(int meanStu) {
        this.meanStu = meanStu;
    }

    public int getStuMax() {
        return stuMax;
    }

    public void setStuMax(int stuMax) {
        this.stuMax = stuMax;
    }

    @Override
    public String toString() {

        return "Teacher [教师编号=" + teacherNumber + ", 可带学生人数=" + stuMax + ",当前学生人数="+stuNumber+",已选学生="+selected+ ", 学生平均排名=" + meanStu +"]";
    }
    void updataTeacher(int ranking)
    {
        if (stuNumber==0||meanStu==0)
        {
            stuSelected[stuNumber]=ranking;
            stuNumber++;
            meanStu=ranking;
        }else{
            stuSelected[stuNumber]=ranking;
            meanStu=(stuNumber*meanStu+ranking)/(stuNumber+1);
            stuNumber++;
        }
        if (stuNumber==0){
            selected="无";
        }else {

                if (stuNumber==1) {
                    selected = ""+ranking;
                }else {
                    selected = selected + "," + ranking;
                }

        }
        for(int i=0;i<stuNumber;i++){
            for(int j=i+1;j<stuNumber;j++)
            if(stuSelected[i]==stuSelected[j]){
                break;
            }

        }
    }

}
