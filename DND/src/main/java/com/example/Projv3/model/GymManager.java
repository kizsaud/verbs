package com.example.Projv3.model; /**
 This is the Gym Manager which is where the program run function is.
 There are helper methods for adding a member to database, and reading commands.
 This class takes care of adding members, removing members, printing members, adding and dropping memeber from fitness
 class and displaying the fitness Schedule.
 @author Abhijeet Singh, Khizar Saud
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class GymManager {
    MemberDatabase db = new MemberDatabase();
    private final int finalIntClassSize=3;
    FitnessClass[] fitnessClasses = new FitnessClass[finalIntClassSize];
    int classes = 4;
    Family member = new Family();
    ClassSchedule c = new ClassSchedule();


    /**
     This is the main run class.
     This makes and stores all the fitness classes.
     This method also collects all the parts of a command and sends it out to an execute command class.
     */
    public void run() throws FileNotFoundException {
        fitnessClasses[0] = new FitnessClass(db,"Jennifer","Pilates", Time.Pilates, fitnessClasses);
        fitnessClasses[1] = new FitnessClass(db,"Denise","Spinning", Time.Spinning, fitnessClasses);
        fitnessClasses[2] = new FitnessClass(db,"Kim","Cardio", Time.Cardio, fitnessClasses);
        FitnessClass A = new FitnessClass(null, null, null, null, fitnessClasses);
        System.out.println("Gym Manager running...");
        Scanner scan = new Scanner(System.in);
        String command = "";
        String[] commands;
        String user = "";
        int ptr = 0;
        while (scan.hasNext()) {
            command = scan.nextLine();
            commands = command.split("\\s+");
            executeCommands(commands[0], commands);

        }
    }

    /**
     Deletes the person with the given name from the list.
     Does nothing if name doesn't appear in the list.
     @param command This is the command to take care of the first letter command.
     @param commands This is to store the rest of the input if there is any, for inforamtion about member for add/remove
     */
    public void executeCommands(String command, String[] commands) throws FileNotFoundException {
            try {

                if (command.equals("A")) {
                    String tempLocation = commands[commands.length - 1];
                    tempLocation = tempLocation.toUpperCase();
                    if (addMemberToDatabase(commands)) {
                        System.out.println("member added to database");
                    } else if (!(tempLocation.equals("PISCATAWAY") || tempLocation.equals("BRIDGEWATER") || tempLocation.equals("SOMERVILLE") || tempLocation.equals("FRANKLIN") || tempLocation.equals("EDISON"))) {
                        System.out.println("invalid location");
                    } else if (db.contains2(commands) == "FOUND") {
                        System.out.println("Member already in database");
                    }
                } else if (command.equals("R")) {
                    Date date = new Date(commands[3]);
                    Member member = new Member();
                    member.setFname(commands[1]);
                    member.setLname(commands[2]);
                    member.setDob(date);
                    if (db.remove(member) == true) {
                        System.out.println(commands[1] + " " + commands[2] + " removed.");
                    } else {
                        System.out.println(commands[1] + " " + commands[2] + " doesn't exist.");
                    }
                } else if (command.equals("P")) {
                    System.out.println("-list of members-");
                    db.print();
                    System.out.println("-end of list-");
                    System.out.println();
                } else if (command.equals("PC")) {
                    System.out.println("-list of members sorted by county and zipcode-");
                    db.printByCounty();
                    System.out.println("-end of list-");
                    System.out.println();
                } else if (command.equals("PN")) {
                    System.out.println("-list of members sorted by last name, and first name-");
                    db.printByName();
                    System.out.println("-end of list-");
                    System.out.println();
                } else if (command.equals("PD")) {
                    System.out.println("-list of members sorted by membership expiration date-");
                    db.printByExpirationDate();
                    System.out.println("-end of list-");
                    System.out.println();
                } else if (command.equals("Q")) {
                    while(command.equals("Q")) {
                        System.out.println("Gym Manager terminated.");
                        System.exit(0);
                    }
                } else if (command.equals("D")) {
                    try {
                        String fitnessClassName = commands[1];
                        String firstName = commands[2];
                        String lastName = commands[3];
                        Date dob = new Date(commands[4]);
                        Member member = new Member();
                        member.setLname(lastName);
                        member.setFname(firstName);
                        member.setDob(dob);
                        for (int i = 0; i < fitnessClasses.length; i++) {
                            if (fitnessClasses[i].getFitnessName().equalsIgnoreCase(fitnessClassName)) {
                                fitnessClasses[i].dropClass(new Member(firstName, lastName, dob), db);
                                return;
                            }
                        }
                        System.out.println("class doesnt exist");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("BAD LINES!");
                    }
                } else if (command.equals("S")) {
                    System.out.println("-Fitness classes-");
                    for (int i = 0; i < fitnessClasses.length; i++) {
                        fitnessClasses[i].printSchedule();
                    }
                } else if (command.equals("LS")) {
                    String fileName = "classSchedule.txt";
                    String[] userLines;


                    File inputFile = new File("classSchedule.txt");
                    try {

                        Scanner sc = new Scanner(inputFile);
                        String line;
                        int countNumOfLine = 0;
                        while (sc.hasNextLine()) {
                            line = sc.nextLine();
                            if (line == null || line.length() == 0) {
                                break;
                            }
                            countNumOfLine++;
                        }
                        String lines[] = new String[countNumOfLine + 1];
                        lines[0] = Integer.toString(countNumOfLine);
                        sc = new Scanner(inputFile);
                        for (int i = 1; i < lines.length; i++) {
                            lines[i] = sc.nextLine();
                        }
                        userLines = lines;

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    FitnessClass[] IOClasses = new FitnessClass[Integer.parseInt(userLines[0])];
                    int index = 0;
                    for (int i = 1; i < userLines.length - 1; i++) {
                        String aLine = userLines[i];
                        String[] read = aLine.split(" ");
                        String location = read[3].toUpperCase();
                        classTimes classTime = classTimes.valueOf(read[2].toUpperCase());
                        Location classLocation = Location.valueOf(read[3]);
                        boolean flag = false;
                        //Add Location check
                        //  tempMember.getLocation().compareLocations(A.getLocation()) == "Equal";
                        for (Location iterate : Location.values()) {
                            if (iterate.toString().equalsIgnoreCase(location)) {
                                flag = true;
                            }


                        }


                        if (flag == true) {
                            FitnessClass fitnessClass = new FitnessClass(read[0], read[1],
                                    classTime, classLocation);
                            IOClasses[index++] = fitnessClass;
                        } else {
                            System.out.println("Location loaded from text file  doesnt match actual class location");
                        }
                    }
                    c = new ClassSchedule(IOClasses, IOClasses.length);
                    c.printSchedule();
                } else if (command.equals("LM")) {
                    try {
                        File file = new File("memberList.txt");
                        Scanner sc = new Scanner(file);
                        String newCommand = "";
                        String[] newCommands;
                        String user = "";
                        int ptr = 0;
                        while (sc.hasNext()) {
                            newCommand = sc.nextLine();
                            newCommands = newCommand.split("\\s+");
                            addListMembers(newCommands);
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("File Not Found");
                    }
                    System.out.println("-list of members loaded-");
                    db.print();
                    System.out.println("-end of list-");
                } else if (command.equals("AF")) {
                    String tempLocation = commands[commands.length - 1];
                    tempLocation = tempLocation.toUpperCase();
                    if (addFamilyMemberToDatabase(commands)) {
                        System.out.println("member added to database");
                    } else if (!(tempLocation.equals("PISCATAWAY") || tempLocation.equals("BRIDGEWATER") || tempLocation.equals("SOMERVILLE") || tempLocation.equals("FRANKLIN") || tempLocation.equals("EDISON"))) {
                        System.out.println("invalid location");
                    } else {
                        System.out.println("Member already in database");
                    }
                } else if (command.equals("AP")) {
                    String tempLocation = commands[commands.length - 1];
                    tempLocation = tempLocation.toUpperCase();
                    if (addPremiumMemberToDatabase(commands)) {
                        System.out.println("member added to database");
                    } else if (!(tempLocation.equals("PISCATAWAY") || tempLocation.equals("BRIDGEWATER") || tempLocation.equals("SOMERVILLE") || tempLocation.equals("FRANKLIN") || tempLocation.equals("EDISON"))) {
                        System.out.println("invalid location");
                    } else {
                        System.out.println("Member already in database");
                    }
                } else if (command.equals("PF")) {
                    System.out.println("-list of members with membership fees-");
                    db.printByFee();
                    System.out.println("-end of list-");
                } else if (command.equals("CG") || command.equals("DG") || command.equals("C")) {
                    //CG command, family guest check-in for a fitness class; must keep track of the remaining number of guest passes.
                    //CG CLASSNAME GUESTNAME LOCATION FNAME LASTNAME DOB

                    //We set the variables respective to user entry.
                    String className = commands[1];
                    String InstructorName = commands[2];
                    String gLocation = commands[3].toUpperCase();
                    String mFName = commands[4];
                    // System.out.println(mFName);
                    String mLName = commands[5];
                    Date dob = new Date(commands[6]);
                    FitnessClass tempFitness;
                    Location guestLocation = null;
                    String location = "";
                    boolean flag1 = false;
                    int numOfPass = 0;
                    if (!dob.isValid()) {
                        return;
                    } else {
                        Member tempMember = new Member(mFName, mLName, dob);
                        tempMember.setDob(dob);
                        //If it cannot find a index obviously ooes not exist.
                        if (db.contains(tempMember) >= 0) {
                            int tempVal = db.contains(tempMember);
                            tempMember = db.getMember(tempVal);
                            //   System.out.println("AFTER");
                            //System.out.println(tempMember.getFname());

                        } else {
                            System.out.println(mFName + " " + mLName + " " + "Does not exist in database");
                            return;
                        }
                        if (gLocation.equals("BRIDGEWATER") || gLocation.equals("EDISON") || gLocation.equals("FRANKLIN") || gLocation.equals("SOMERVILLE") || gLocation.equals("PISCATAWAY")) {
                            String finalLocation1 = gLocation.substring(1).toLowerCase();
                            String finalLocation2 = gLocation.substring(0, 1).toUpperCase();
                            flag1 = true;

                            //Put it back together here
                            location = finalLocation2 + finalLocation1;

                        } else {
                            System.out.println("location doesnt exsit");
                            flag1 = false;
                            return;
                        }


                        FitnessClass A = new FitnessClass(db, InstructorName, className, null, fitnessClasses);
                        if (flag1 = true) {
                            //System.out.println("HERE");
                            A.setLocation(Location.valueOf(location));
                        }
                        if (command.equals("CG")) {
                            String Cname = A.getFitnessName();
                            String CInstructor = A.getInstructorName();
                            int count = 0;
                            for (int i = 0; i < fitnessClasses.length; i++) {
                                if (fitnessClasses[i].getFitnessName().equalsIgnoreCase(Cname) && fitnessClasses[i].getInstructorName().equalsIgnoreCase(CInstructor))
                                    //System.out.println("Class exists!");
                                    count += 1;
                            }
                            if (count > 0) {
                                //  System.out.println("Count chek");
                                if (tempMember instanceof Family) {
                                    numOfPass = ((Family) tempMember).getNumOfGuestPass();
                                    if (numOfPass == 0) {
                                        System.out.println("NO PASSES! left");
                                    } else {
                                        if (tempMember.getLocation().compareLocations(A.getLocation()) == "Equal") {
                                            ((Family) tempMember).setNumOfGuestPass(-1);
                                            A.addGuest(tempMember);
                                            System.out.println(tempMember.getFname() + " " + tempMember.getLname() + " (guest) checked in " + A.toString());

                                        } else {
                                            System.out.println("Guest Location restriction");
                                        }
                                    }
                                } else {
                                    System.out.println("User only has a standard membership - guest check-in is not allowed.");

                                }
                            }

                        }
                        if (command.equals("DG")) {
                            A.dropGuest(tempMember);
                            System.out.println(tempMember.getFname() + " "+ tempMember.getLname()+ " " + " "+ "Dropped" +" " + A.getFitnessName().toString());
                        }
                        if (command.equals("C")) {


                            if (db.contains(tempMember) != -1) {

                                tempMember = db.getMember(db.contains(tempMember));
                                for (int i = 0; i < fitnessClasses.length; i++) {
                                    if ((fitnessClasses[i].getFitnessName()).equalsIgnoreCase(className)) {
                                        fitnessClasses[i].checkIn(tempMember, db, className, A);

                                        return;
                                    }
                                }
                                System.out.println(className + " class does not exist");
                            } else {
                                System.out.println(tempMember.getFname() + " " + tempMember.getLname() + " is not in the database.");
                            }
                        }


                    }

                }else if(command.equals(" ")|| command.equals("")){
                    System.out.println();
                }
                else {
                    System.out.println(command + " IS a invalid command!");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("COMMAND ENTERED IS BAD!");
                return;
            }
        }

        /**
         * This class adds a List members to the database given commands and returns if the member(s) was added or not.
         * @param commands for executing the add list member(s) function from a .txt file
         * @return true if member was added and false otherwise
         */

        public void addListMembers (String[]commands){
            String location = commands[4];
            Date exp = new Date(commands[3]);
            Date dob = new Date(commands[2]);
            if (dob.isValid() && dob.isAdult() && exp.isValid()) {
                Member member = new Member();
                member.setDob(dob);
                member.setExpire(exp);
                member.setFname(commands[0]);
                member.setLname(commands[1]);

                //Split location back to match enum location
                String finalLocation1 = location.substring(1).toLowerCase();
                String finalLocation2 = location.substring(0, 1).toUpperCase();

                //Put it back together here
                location = finalLocation2 + finalLocation1;
                member.setLocation(Location.valueOf(location));
                db.add(member);
            }
        }

        public boolean addMemberToDatabase (String[]commands){
            String location = commands[4];
            Date exp = new Date();
            exp.setMonth(exp.getMonth() + 3);
            if (exp.getMonth() > 12) {
                exp.setMonth(exp.getMonth() - 12);
                exp.setYear(exp.getYear() + 1);
            }
            Date dob = new Date(commands[3]);
            boolean checkLocation = true;
            location = location.toUpperCase();
            if (location.equals("EDISON")) {
                checkLocation = true;
            } else if (location.equals("BRIDGEWATER")) {
                checkLocation = true;
            } else if (location.equals("SOMERVILLE")) {
                checkLocation = true;
            } else if (location.equals("PISCATAWAY")) {
                checkLocation = true;
            } else if (location.equals("FRANKLIN")) {
                checkLocation = true;
            } else {
                checkLocation = false;
            }
            if (dob.isValid() && dob.isAdult() && exp.isValid() && checkLocation == true) {
                Member member = new Member();
                member.setDob(dob);
                member.setExpire(exp);
                member.setFname(commands[1]);
                member.setLname(commands[2]);


                //Split location back to match enum location
                String finalLocation1 = location.substring(1).toLowerCase();
                String finalLocation2 = location.substring(0, 1).toUpperCase();

                //Put it back together here
                location = finalLocation2 + finalLocation1;
                member.setLocation(Location.valueOf(location));
                return db.add(member);
            } else {
                if (dob.isAdult() == false) {
                    System.out.println("DOB " + dob.getMonth() + "/" + dob.getDay() + "/" + dob.getYear() + ": must be 18 or older to join!");
                } else if (dob.isValid() == false) {
                    System.out.println("DOB " + dob.getMonth() + "/" + dob.getDay() + "/" + dob.getYear() + ": invalid calender date!");
                } else if (exp.isValid() == false) {
                    System.out.println("Expiration date " + exp.getMonth() + "/" + exp.getDay() + "/" + exp.getYear() + ": invalid calender date!");
                } else if (checkLocation == false) {
                    return false;
                }
                return false;
            }

        }

        /**
         * This class adds a member to the database given commands and returns if the member was added or not.
         * @param commands for executing the add member function
         * @return true if member was added and false otherwise
         */
        public boolean addFamilyMemberToDatabase (String[]commands){
            String location = commands[4];
            Date exp = new Date();
            exp.setMonth(exp.getMonth() + 3);
            if (exp.getMonth() > 12) {
                exp.setMonth(exp.getMonth() - 12);
                exp.setYear(exp.getYear() + 1);
            }
            Date dob = new Date(commands[3]);
            boolean checkLocation = true;
            location = location.toUpperCase();
            if (location.equals("EDISON")) {
                checkLocation = true;
            } else if (location.equals("BRIDGEWATER")) {
                checkLocation = true;
            } else if (location.equals("SOMERVILLE")) {
                checkLocation = true;
            } else if (location.equals("PISCATAWAY")) {
                checkLocation = true;
            } else if (location.equals("FRANKLIN")) {
                checkLocation = true;
            } else {
                checkLocation = false;
            }
            if (dob.isValid() && dob.isAdult() && exp.isValid() && checkLocation == true) {
                Family member = new Family();
                member.setDob(dob);
                member.setExpire(exp);
                member.setFname(commands[1]);
                member.setLname(commands[2]);


                //Split location back to match enum location
                String finalLocation1 = location.substring(1).toLowerCase();
                String finalLocation2 = location.substring(0, 1).toUpperCase();

                //Put it back together here
                location = finalLocation2 + finalLocation1;
                member.setLocation(Location.valueOf(location));
                return db.add(member);
            } else {
                if (dob.isAdult() == false) {
                    System.out.println("DOB " + dob.getMonth() + "/" + dob.getDay() + "/" + dob.getYear() + ": must be 18 or older to join!");
                } else if (dob.isValid() == false) {
                    System.out.println("DOB " + dob.getMonth() + "/" + dob.getDay() + "/" + dob.getYear() + ": invalid calender date!");
                } else if (exp.isValid() == false) {
                    System.out.println("Expiration date " + exp.getMonth() + "/" + exp.getDay() + "/" + exp.getYear() + ": invalid calender date!");
                } else if (checkLocation == false) {
                    return false;
                }
                return false;
            }

        }
        /**
         * This class adds a Premium member to the database given commands and returns if the member was added or not.
         * @param commands for executing the add Premium member function
         * @return true if member was added and false otherwise
         */
        public boolean addPremiumMemberToDatabase (String[]commands){
            String location = commands[4];
            Date exp = new Date();
            exp.setYear(exp.getYear() + 1);
            Date dob = new Date(commands[3]);
            boolean checkLocation = true;
            location = location.toUpperCase();
            if (location.equals("EDISON")) {
                checkLocation = true;
            } else if (location.equals("BRIDGEWATER")) {
                checkLocation = true;
            } else if (location.equals("SOMERVILLE")) {
                checkLocation = true;
            } else if (location.equals("PISCATAWAY")) {
                checkLocation = true;
            } else if (location.equals("FRANKLIN")) {
                checkLocation = true;
            } else {
                checkLocation = false;
            }
            if (dob.isValid() && dob.isAdult() && exp.isValid() && checkLocation == true) {
                Premium member = new Premium();
                member.setDob(dob);
                member.setExpire(exp);
                member.setFname(commands[1]);
                member.setLname(commands[2]);

                //Split location back to match enum location
                String finalLocation1 = location.substring(1).toLowerCase();
                String finalLocation2 = location.substring(0, 1).toUpperCase();

                //Put it back together here
                location = finalLocation2 + finalLocation1;
                member.setLocation(Location.valueOf(location));
                return db.add(member);
            } else {
                if (dob.isAdult() == false) {
                    System.out.println("DOB " + dob.getMonth() + "/" + dob.getDay() + "/" + dob.getYear() + ": must be 18 or older to join!");
                } else if (dob.isValid() == false) {
                    System.out.println("DOB " + dob.getMonth() + "/" + dob.getDay() + "/" + dob.getYear() + ": invalid calender date!");
                } else if (exp.isValid() == false) {
                    System.out.println("Expiration date " + exp.getMonth() + "/" + exp.getDay() + "/" + exp.getYear() + ": invalid calender date!");
                } else if (checkLocation == false) {
                    return false;
                }
                return false;
            }

        }


    public void readAndRegisterMembers(){

    }

}