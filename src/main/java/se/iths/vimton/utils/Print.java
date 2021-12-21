package se.iths.vimton.utils;

import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.ProgramType;
import se.iths.vimton.entities.Student;

import java.util.List;

public class Print {









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
                    "--", "----", "------", "------------");
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
    }
}
