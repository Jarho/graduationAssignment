import java.util.*;

import static java.lang.Math.round;

public class Common {
    void Common() {
    }

    static Random random = new Random();
    static int count = 0;

    void update() {


    }

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

    static Dto firstAssignment(Dto dto) {
        Teacher[] teachers = dto.teachers;
        Student[] students = dto.students;
        int arrary[][] = new int[teachers.length][80];
        List<Integer> integerArrayList = new ArrayList<>();
        int group;
        int mid;

        for (int i = 0; i < teachers.length; i++)//轮流为每个老师挑选
        {
            for (int j = 0; j < students.length; j++) {
                if (students[j].volunNumber == i) {
                    integerArrayList.add(students[j].ranking);
                }
            }
            if (integerArrayList.size() < 4) {
                mid = integerArrayList.size();
            } else {
                mid = 4;
            }
            for (int m = 0; m < mid; m++) {
                int index = random.nextInt(integerArrayList.size());
                int ranking = integerArrayList.get(index);
                teachers[i].updataTeacher(ranking+1);
                students[ranking].updateStudent();
                integerArrayList.remove(index);
            }
            integerArrayList.clear();
        }
        return dto;
    }

    static Dto secondAssignment(Dto dto, int rankingExpect, int range, int teacherAll, int stuAll) {
        int op;//最适排名
        int opUp, opDown;//最适排名上下范围
        int stuRest = 0;
        Teacher[] teachers = dto.teachers;
        Student[] students = dto.students;
        for (int i = 0; i < students.length; i++) {
            if (!students[i].status) {
                stuRest++;
            }
        }
        System.out.println("剩余人数" + stuRest);
        while (true) {
            for (int i = 0; i < teacherAll; i++) {
                if (teachers[i].stuNumber < teachers[i].stuMax) {
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
                            while (true) {
                                if (!students[opDown].status && students[opDown].volunNumber == teachers[i].teacherNumber) {
                                    teachers[i].updataTeacher(opDown);
                                    students[opDown].updateStudent();
                                    count++;
                                    break;
                                } else if (!students[opUp].status && students[opUp].volunNumber == teachers[i].teacherNumber) {
                                    teachers[i].updataTeacher(opUp);
                                    students[opUp].updateStudent();
                                    count++;
                                    break;
                                } else {
                                    if (!students[opDown].status) {
                                        queue.offer(opDown);
                                    }
                                    if (!students[opUp].status) {
                                        queue.offer(opUp);
                                    }
                                    if (istrue(op, opDown, opUp, queue, midRange)) {
                                        int rank = queue.poll();
                                            teachers[i].updataTeacher(rank);
                                        students[rank].updateStudent();
                                        count++;
                                        queue.clear();
                                        break;
                                    }
                                    if (opUp < stuAll-1) {
                                        opUp++;
                                    }
                                    if (opDown > 0)
                                        opDown--;
                                }
                            }
                        }

                    }
                }
            }
            if (count == stuRest)
                break;
        }
        return dto;
    }

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
}
