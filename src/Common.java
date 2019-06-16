import java.util.*;

import static java.lang.Math.round;

public class Common {
    void Common() {
    }

    static Random random = new Random();//实例化random对象后边使用随机数
    static int count = 0;

    void update() {


    }

    /**
     * 打印当前所以学生的状态
     * @param students
     */
    static void showStudents(Student[] students) {

        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        }
    }

    static void showTeachers(Teacher[] teachers) {
        for (int j = 0; j < teachers.length; j++) {
            System.out.println(teachers[j]);
        }
    }

    /**
     * 第一轮为导师随机分配志愿学生
     * @param dto 教师类和学生类集合的容器
     * @param assignmengNumber 分配的学生个数
     * @return
     */
    static Dto firstAssignment(Dto dto,int assignmengNumber) {
        Teacher[] teachers = dto.teachers;
        Student[] students = dto.students;
        int arrary[][] = new int[teachers.length][80];
        List<Integer> integerArrayList = new ArrayList<>();
        int group;
        int mid;

        for (int i = 0; i < teachers.length; i++)//轮流为每个老师挑选
        {
            //遍历学生集合，将志愿填选了当前导师的学生添加进入list集合
            for (int j = 0; j < students.length; j++) {
                if (students[j].volunNumber == i) {
                    integerArrayList.add(students[j].ranking);
                }
            }
            //如果选择这位学生的志愿数小于将分配的数目，则分配志愿数
            if (integerArrayList.size() < assignmengNumber) {
                mid = integerArrayList.size();
            } else {
                mid = assignmengNumber;
            }
            //采用nextInt随机选取list集合中的学生填入教师已选名单
            for (int m = 0; m < mid; m++) {
                int index = random.nextInt(integerArrayList.size());
                int ranking = integerArrayList.get(index);
                teachers[i].updataTeacher(ranking+1);//学生被选后，更新教师的学生名单
                students[ranking].updateStudent(teachers[i].teacherNumber);//学生被选后，更新学生的状态
                integerArrayList.remove(index);//list集合中被选中的学生移出
            }
            integerArrayList.clear();
        }
        return dto;
    }

    /**
     * 第二次分配学生，以平均排名的均匀和志愿符合的概率作为优化目标
     * @param dto 学生集合和教师集合的容器
     * @param rankingExpect 全班的平均期望排名
     * @param range     志愿搜寻范围
     * @param teacherAll 教师总人数
     * @param stuAll   学生总人数
     * @return
     */
    static Dto secondAssignment(Dto dto, int rankingExpect, int range, int teacherAll, int stuAll) {
        int op;//通过当前学生平均排名和平均期望得出的最适排名
        int opUp, opDown;//最适排名上下范围搜索，降低一半的复杂度，提高志愿率
        int stuRest = 0;
        Teacher[] teachers = dto.teachers;
        Student[] students = dto.students;
        //遍历学生集合，统计还未被选的学生人数
        for (int i = 0; i < students.length; i++) {
            if (!students[i].status) {
                stuRest++;
            }
        }
        System.out.println("剩余人数" + stuRest);
        //最外层循环，确保所有学生都被选中才会跳出循环
        while (true) {
            //第二层循环，让老师轮询选择
            for (int i = 0; i < teacherAll; i++) {
                //如果导师名额已满则跳出
                if (teachers[i].stuNumber < teachers[i].stuMax) {
                    //第三层循环，确保老师在挑中学生后重新搜寻最佳位置
                    for (int k = count; k == count; ) {
                        int midRange = round(range / 2);
                        {
                            Queue<Integer> queue = new LinkedList<Integer>();
                            op = (teachers[i].stuNumber + 1) * rankingExpect - (teachers[i].stuNumber * teachers[i].meanStu);
                            if (op + range > stuAll || op > stuAll) {
                                op = stuAll - midRange;
                            } else if (op < range || op - range < 0) {
                                op = 0 + midRange;
                            } else {
                            }
                            opUp = op + 1;
                            opDown = op;
                            //第四层循环确保搜寻当前轮老师能选中学生
                            while (true) {
                                //在下范围内如果学生恰巧选择了当前老师为志愿，则直接选择，结束循环
                                if (!students[opDown].status && students[opDown].volunNumber == teachers[i].teacherNumber) {
                                    teachers[i].updataTeacher(opDown);
                                    students[opDown].updateStudent(teachers[i].teacherNumber);
                                    count++;
                                    break;
                                    //在上范围内如果学生恰巧选择了当前老师为志愿，则直接选择，结束循环
                                } else if (!students[opUp].status && students[opUp].volunNumber == teachers[i].teacherNumber) {
                                    teachers[i].updataTeacher(opUp);
                                    students[opUp].updateStudent(teachers[i].teacherNumber);
                                    count++;
                                    break;
                                } else {
                                    //如果下范围的学生的志愿不是当前老师，但是还没有被选，则加入预备队列
                                    if (!students[opDown].status) {
                                        queue.offer(opDown);
                                    }
                                    //如果上范围的学生的志愿不是当前老师，但是还没有被选，则加入预备队列
                                    if (!students[opUp].status) {
                                        queue.offer(opUp);
                                    }
                                    //每当经过一次范围周期，进行弹出队列
                                    if (istrue(op, opDown, opUp, queue, midRange)) {
                                        //将志愿不符合但是最靠近最佳位置的学生弹出
                                        int rank = queue.poll();
                                        teachers[i].updataTeacher(rank);
                                        students[rank].updateStudent(teachers[i].teacherNumber);
                                        count++;
                                        queue.clear();
                                        break;
                                    }
                                    //确保数组下标不会越界并且能搜寻完
                                    if (opUp < stuAll-1) {
                                        opUp++;
                                    }
                                    //确保数组下标不会越界并且能搜寻完
                                    if (opDown > 0)
                                        opDown--;
                                }
                            }
                        }

                    }
                }
            }
            if (count == stuRest)//采用count计算被选学生个数与剩余学生作比较，保证每个学生被选中
                break;
        }
        return dto;
    }
    //判断当经过一个范围周期时，如果队列不为空，弹出队尾学生
    static boolean istrue(int op, int opDown, int opUp, Queue queue, int midrange) {
        if (op == opDown || op == opUp) {
            return false;
        } else if (queue.size() == 0) {
            return false;
        } else if (((op - opDown) % midrange) == 0) {
            return true;
        } else if (((opUp - op) % midrange) == 0) {
            return true;
        } else {
            return false;
        }
    }
    static void matchRateAndVarianceMean (Dto dto,int rankingExpect)
    {
        Teacher[] teachers = dto.teachers;
        Student[] students = dto.students;
        int stusAll=students.length;
        int teachersAll=teachers.length;
        double matchNumber=0;
        double variance=0;
        for (int i=0;i<stusAll;i++)
        {
            int teacherNumber= Integer.valueOf(students[i].teacherNumber);
            if (teacherNumber==students[i].volunNumber)
            {
                matchNumber++;
            }
        }
        for (int j=0;j<teachersAll;j++)
        {
            double meanStu=teachers[j].meanStu;
            if (teachers[j].meanStu>=rankingExpect)
                variance=variance+(meanStu-rankingExpect);
            else {
                variance=variance+(rankingExpect-meanStu);
            }
        }
        double matchRate=round(matchNumber/stusAll*100);
        double varianceMean=variance/teachersAll;
        System.out.println("学生排名平均方差："+varianceMean);
        System.out.println("学生志愿符合率："+matchRate+"%");
    }

}
