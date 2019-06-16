import java.util.Random;

import static java.lang.Math.round;

public class Main {
    static int stuAll;//学生总人数
    static int teacherAll;//老师总人数
    static  int rankingExpect;//排名期望
    static int expectDeviation;//期望偏差
    static int stuMax;//最大可带学生人数
    static float vf;//志愿权重因子
    static  int range;//志愿范围
    static int stuSelected[];

    public static void main(String[] args)
    {
        init();

    }
    //初始化
    static void init(){
        initDate();
        Teacher teacherArrary[]=initTeacher();
        Student studentArrary[]=initStudent();
        Dto dto=new Dto(studentArrary,teacherArrary);
        Common.showStudents(studentArrary);
        Common.showTeachers(teacherArrary);
        Common.firstAssignment(dto);
        Common.showStudents(studentArrary);
        Common.showTeachers(teacherArrary);
        Common.secondAssignment(dto,rankingExpect,range,teacherAll,stuAll);
        Common.showTeachers(teacherArrary);
        Common.showStudents(studentArrary);


    }
    //初始化变量参数
   static void initDate(){
      stuAll=80;
      teacherAll=8;
      rankingExpect=40;
      expectDeviation=10;
      vf=0.2f;
      range=round(stuAll*vf);
      stuMax=stuAll/teacherAll;
    }
    //初始化教师数组
   static Teacher[] initTeacher(){
        int stuNumber=0;
        stuSelected=new int[stuMax];
        Teacher teacherArrary[]=new Teacher[teacherAll];
        for(int i=0;i<teacherAll;i++){
            teacherArrary[i]=new Teacher(stuMax,stuNumber,i,stuSelected);
        }
        return teacherArrary;
    }
    //初始化学生数组
   static Student[] initStudent(){
      Student studentArrary[]=new Student[stuAll];
      boolean status=false;
        Random random=new Random();

       for(int j=0;j<stuAll;j++)
       {
           int volunNumber=random.nextInt(teacherAll);
            studentArrary[j]=new Student(volunNumber,j,status);
       }
       return studentArrary;
    }
}
