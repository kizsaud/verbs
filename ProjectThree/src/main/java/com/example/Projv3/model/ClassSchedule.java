package com.example.Projv3.model;

/**
 This class is for making the fitness class Schedule.
 This class involves adding a class to the schedule, and printing whatever class is ther on the schedule.
 @author Abhijeet Singh, Khizar Saud
 */


public class ClassSchedule {
    private FitnessClass[] classes;
    private int numClasses;


    /**
     This is so that any class Schedule instance gets created, and overload of a previous constructor.
     */
    public ClassSchedule(FitnessClass[] fitnessClass, int numofClass){
        this.classes = fitnessClass;
        this.numClasses = numofClass;
    }


    /**
     This is so that any class Schedule instance gets created.
     */
    public ClassSchedule() {
        classes = new FitnessClass[15];
        numClasses = 0;
    }


    /**
     This method is so that a variable of type FitnessClass gets added to classes array and update schedule.
     @param fitnessClass this is a parameter for the class to add.
     */
    public void addFitnessClass(FitnessClass fitnessClass) {
        classes[numClasses++] = fitnessClass;
    }

    /**
     * This method returns the fitness class that is trying to be recieved and null if the class doesn't exist.
     * @param fitnessClass this is a parameter for the class that is trying to be found from the method.
     * @return for a specific fitness class that is being searched.
     */
    public FitnessClass getFitnessClass(FitnessClass fitnessClass) {
        int l =0;
        for ( l = 0; l< numClasses; l++)
            if ((classes[l] != null)){

            if(classes[l].equals(fitnessClass)){


                return classes[l];
            }
    }
        return null;

    }

    /**
     * This returns the fitness class array
     * @return the fitness class array of type FitnessClass
     */
    public FitnessClass[] getClasses() {
        return classes;
    }

    /**
     This method will return how many fitness classes are currently active.
     @return numClasses which returns the number of classes.
     */
    public int getClassNum() {
        return numClasses;
    }


    /**
     This class prints all th fitness classes in the console.
     */
    public void printSchedule() {
        for (FitnessClass fitnessClass : classes)
            if (fitnessClass != null) System.out.println(fitnessClass);
    }
}