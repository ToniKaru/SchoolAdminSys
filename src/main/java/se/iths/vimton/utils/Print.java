package se.iths.vimton.utils;

import se.iths.vimton.entities.Course;
import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.ProgramType;
import se.iths.vimton.entities.Student;

import java.util.List;
import java.util.Set;

public class Print {



    public static void allCourses (Set<Course> courses) {
        if(!courses.isEmpty()) {
            System.out.println("\n -- Summary of Courses --");
            String summaryFormat = "%-5s %-20s %-20s \n";
            System.out.printf(summaryFormat,
                    "Id", "Name", "Credits");
            System.out.printf(summaryFormat,
                    "--", "----", "-------");
            for (Course course : courses) {
                System.out.printf(summaryFormat,
                        course.getId(), course.getName(), course.getCredits());
            }
        }
        else
            System.out.println("No courses to display");
    }

    public static void allStudents (List<Student> students) {
        if(!students.isEmpty()) {
            System.out.println("\n -- Summary of Students --");
            String summaryFormat = "%-5s %-20s %-20s %16s %-30s \n";
            System.out.printf(summaryFormat,
                    "Id", "First Name", "Last Name", "SSN", "Program");
            System.out.printf(summaryFormat,
                    "--", "----------", "----------","---", "-------");
            for (Student student : students) {
                System.out.printf(summaryFormat,
                        student.getId(), student.getFirstName(), student.getLastName(),
                        student.getSsn(), student.getProgram().getName());
            }
        }
        else
            System.out.println("No programs to display");
    }

    public static void programDetails(List<Program> programs) {
        if(!programs.isEmpty()) {
            System.out.println("\n -- Program Details --");

            String detailedFormat = "%-5s %-25s %-10s %-18s %-40s \n";
            System.out.printf(detailedFormat,
                    "Id", "Name", "Pace", "Program Type", "Description");
            System.out.printf(detailedFormat,
                    "--", "----", "------", "------------", "-----------");
            for (Program program : programs) {
                System.out.printf(detailedFormat,
                        program.getId(), program.getName(), program.getPace(),
                        program.getProgramType().getName(), program.getDescription());
                //todo: add % to pace
            }
        }
        else
            System.out.println("No program(s) to display");
    }

    public static void allPrograms(List<Program> programs) {
        if(!programs.isEmpty()) {

            System.out.println("\n -- Summary of Programs --");
            String summaryFormat = "%-5s %-25s %-10s %-18s \n";
            System.out.printf(summaryFormat,
                    "Id", "Name", "Pace", "Program Type");
            System.out.printf(summaryFormat,
                    "--", "----", "----", "------------");
            for (Program program : programs) {
                System.out.printf(summaryFormat,
                        program.getId(), program.getName(), program.getPace(),
                        program.getProgramType().getName());
            }
        }
        else
            System.out.println("No programs to display");

        //todo: add % to pace
    }

    public static void progTypeDetails(ProgramType programType) {
        if(programType != null) {
            System.out.println("\n -- Program Type Details --");

            System.out.println("Id: " + programType.getId());
            System.out.println("Name: " + programType.getName());
            System.out.println("Credits required: " + programType.getCreditsRequired());
            System.out.println("Accredited: " + programType.isAccredited());
            System.out.println("Programs: ");
            programType.getProgramList().stream()
                    .map(Program::getName)
                    .forEach(System.out::println);
        }
        else
            System.out.println("No type to display");
    }

    public static void allProgTypes(List<ProgramType> progTypes) {
        if (!progTypes.isEmpty()) {
            System.out.println("\n -- Program Type(s) --");

            String summaryFormat = "%-5s %-25s %-20s %-15s \n";
            System.out.printf(summaryFormat,
                    "Id", "Name", "Credits Required", "Accredited");
            System.out.printf(summaryFormat,
                    "--", "----", "--------------------", "----------");
            for (ProgramType programType : progTypes) {
                System.out.printf(summaryFormat,
                        programType.getId(), programType.getName(),
                        programType.getCreditsRequired(), programType.isAccredited());
            }
        }
        else
            System.out.println("No types to display");
    }
}
