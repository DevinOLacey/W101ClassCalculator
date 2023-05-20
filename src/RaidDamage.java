import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.math.BigDecimal.valueOf;

public class RaidDamage {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
        new W101Calculator();

        //records all blade values
        ArrayList<BigDecimal> bladeList = new ArrayList<>();
        BigDecimal blades = valueOf(1);

        //records all trap values
        ArrayList<BigDecimal> trapList = new ArrayList<>();
        BigDecimal traps = valueOf(1);

        //records all weakness values
        ArrayList<BigDecimal> weaknessList = new ArrayList<>();
        BigDecimal weaknesses = valueOf(1);

        //records all shield values
        ArrayList<BigDecimal> shieldList = new ArrayList<>();
        //BigDecimal shields = valueOf(0);


        BigDecimal aura = valueOf(1); //only one aura value can be recorded
        BigDecimal bubble = valueOf(1); //only one bubble value can be recorded



        //base damage a spell does (printed damage)
        System.out.print("Spell DMG: ");
        BigDecimal spellDamage = input.nextBigDecimal();

        //the amount of damage from gear/pets/etc...
        System.out.print("DMG: ");
        BigDecimal characterDamage = W101Calculator.convertBuff(input.nextBigDecimal());

        //additional damage received from a critical hit
        System.out.print("Crit Mod: ");
        BigDecimal critMod = W101Calculator.convertBuff(input.nextBigDecimal());

        //pierce given by gear/enchants/pets/etc
        System.out.print("Pierce: ");
        BigDecimal pierce = W101Calculator.convertPierce(input.nextBigDecimal());

        //enemy resist to the spells school of damage
        System.out.print("Enemy Resist: ");
        BigDecimal enemyResist = W101Calculator.convertPierce(input.nextBigDecimal());

        label:
        while (true) {

            BigDecimal total = W101Calculator.calculateTotalDamage(spellDamage, characterDamage, blades, traps, weaknesses, aura, bubble, shieldList, pierce, enemyResist);
            //BigDecimal Crit = DamageCalculator.calculateCriticalDamage(total);  ADD THIS IN THE FORM OF GAMBITS CAUSE FUCK CRITS
            //FIX SHIELDS MORE IDK WHATS WRONG IT FUCKING SUCKS
            System.out.println();
            System.out.println("'help' for list of commands");
            System.out.println();
            System.out.println("Total: " + total);//ADD GAMBIT METHODS AND NEW CHANG GAMBIT CASE
            System.out.println();
            System.out.print("Enter a command: ");
            String command = scanner.nextLine();


            switch (command) {
                case "b": {
                    try {
                        System.out.print("Blade value: ");
                        BigDecimal buff = input.nextBigDecimal();
                        bladeList.add(W101Calculator.convertBuff(buff)); //adds buff to blades list
                        blades = W101Calculator.arrayMultiplier(bladeList);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        input.next();
                        break;
                    }

                }
                case "rb":
                    if (!bladeList.isEmpty()) { // make sure the list is not empty
                        bladeList.remove(bladeList.size() - 1); // remove the last element from the list
                        blades = W101Calculator.arrayMultiplier(bladeList);
                    }
                    break;

                case "0b":
                    bladeList.clear();
                    blades = W101Calculator.arrayMultiplier(bladeList);
                    break;

                case "t": {
                    try {
                        System.out.print("Trap value: ");
                        BigDecimal buff = input.nextBigDecimal();
                        trapList.add(W101Calculator.convertBuff(buff));
                        traps = W101Calculator.arrayMultiplier(trapList);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                }
                case "rt":
                    if (!trapList.isEmpty()) {
                        trapList.remove(trapList.size() - 1);
                        traps = W101Calculator.arrayMultiplier(trapList);
                    }
                    break;

                case "0t":
                    trapList.clear();
                    traps = W101Calculator.arrayMultiplier(trapList);
                    break;

                case "w": {
                    try {
                        System.out.print("Weakness value: ");
                        BigDecimal debuff = input.nextBigDecimal();
                        weaknessList.add(W101Calculator.convertDebuff(debuff));
                        weaknesses = W101Calculator.arrayMultiplier(weaknessList);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                }
                case "rw":
                    if (!weaknessList.isEmpty()) {
                        weaknessList.remove(weaknessList.size() - 1);
                        weaknesses = W101Calculator.arrayMultiplier(weaknessList);
                    }
                    break;

                case "0w":
                    weaknessList.clear();
                    weaknesses = W101Calculator.arrayMultiplier(weaknessList);
                    break;

                case "s": {
                    try {
                        System.out.print("Shield value: ");
                        BigDecimal shield = W101Calculator.convertPierce(input.nextBigDecimal());
                        shieldList.add(shield);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;
                }

                case "rs":
                    if (!shieldList.isEmpty()) {
                        shieldList.remove(shieldList.size() - 1);
                    } else {
                        System.out.println("There are no shields to remove!");
                    }
                    break;

                case "0s":
                    shieldList.clear();
                    break;

                case "a":
                    System.out.print("Aura Value: ");
                    try {
                        aura = W101Calculator.convertBuff(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                case "0a":
                    aura = valueOf(1);
                    break;

                case "bub":
                    System.out.print("Bubble value: ");
                    try {
                        bubble = (W101Calculator.convertBuff(input.nextBigDecimal()));
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                case "0bub":
                    bubble = valueOf(1);
                    break;

                case "00":
                    bladeList.clear();
                    blades = W101Calculator.arrayMultiplier(bladeList);

                    trapList.clear();
                    traps = W101Calculator.arrayMultiplier(trapList);

                    weaknessList.clear();
                    weaknesses = W101Calculator.arrayMultiplier(weaknessList);

                    shieldList.clear();

                    aura = valueOf(1);
                    bubble = valueOf(1);
                    break;

                case "ns":
                    try {
                        System.out.print("Enter new spell damage: ");
                        spellDamage = (input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "nd":
                    try {
                        System.out.print("Enter new DMG: ");
                        characterDamage = W101Calculator.convertBuff(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "np":
                    try {
                        System.out.print("Enter new pierce: ");
                        pierce = W101Calculator.convertPierce(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "nr":
                    try {
                        System.out.print("Enter new enemy resist: ");
                        enemyResist = W101Calculator.convertDebuff(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "nc":
                    try {
                        System.out.println("Enter new crit mod: ");
                        critMod = W101Calculator.convertBuff(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "check":
                    W101Calculator.check(spellDamage,characterDamage,critMod,pierce,enemyResist,aura,bubble,bladeList,trapList,weaknessList,shieldList);

                    break;

                case "q":
                    break label;

                case "help":
                    System.out.println("b\tadds a blade\nrb\tremoves the value of the last blade\n0b\tresets all blade\nt\tadds a trap\nrt\tremoves the value of the last trap\n0t\tresets all traps\nw\tadds a weakness\nrw\tre adds the value lost from the last weakness\n0w\tresets all weaknesses\ns\tadds a shield\nrs\tremoves the value of the last shield\n0s\tresets all shields\na\tadds an aura\n0a\tremoves an aura\nbub\tadds a bubble\n0bub\tremoves a bubble\n00\tresets ALL buffs and weaknesses\nns\tchanges the spells base dmg value\nnd\tchanges the dmg value\nnp\tchanges the pierce value\nnr\tchanges the resist value\nnc\tchanges the crit mod\ncheck\tdisplays all current buffs and debuffs\nq\tstops the code");

                case default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }
}
