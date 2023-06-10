import java.util.InputMismatchException;
import java.util.Scanner;

public class RaidDamage {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
        W101Calculator calculator = new W101Calculator();


        //base damage a spell does (printed damage)
        System.out.print("Spell DMG: ");
        calculator.setSpellDamage(input.nextBigDecimal());

        //the amount of damage from gear/pets/etc...
        System.out.print("DMG: ");
        calculator.setPlayerDamage(input.nextBigDecimal());

        //pierce given by gear/enchants/pets/etc
        System.out.print("Pierce: ");
        calculator.setPierce(input.nextBigDecimal());

        //enemy resist to the spells school of damage
        System.out.print("Enemy Resist: ");
        calculator.setEnemyResist(input.nextBigDecimal());

        label:
        while (true) {

            //BigDecimal Crit = DamageCalculator.calculateCriticalDamage(total);  ADD THIS IN THE FORM OF GAMBITS CAUSE FUCK CRITS
            System.out.println();
            System.out.println("'help' for list of commands");
            System.out.println();
            System.out.println("Total: " + calculator.getTotal());//ADD GAMBIT METHODS AND NEW CHANG GAMBIT CASE
            System.out.println();
            System.out.print("Enter a command: ");
            String command = scanner.nextLine();


            switch (command) {
                case "b": {
                    try {
                        System.out.print("Blade value: ");
                        calculator.setBladeList(input.nextBigDecimal());
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        input.next();
                        break;
                    }

                }
                case "rb":
                    calculator.removeBlade();
                    break;

                case "0b":
                    calculator.clearBlades();
                    break;

                case "t": {
                    try {
                        System.out.print("Trap value: ");
                        calculator.setTrapList(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                }
                case "rt":
                    calculator.removeTrap();
                    break;

                case "0t":
                    calculator.clearTraps();
                    break;

                case "w": {
                    try {
                        System.out.println("Weakness value: ");
                        calculator.setWeaknessList(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                }
                case "rw":
                    calculator.removeWeakness();
                    break;

                case "0w":
                    calculator.clearWeaknesses();
                    break;

                case "s": {
                    try {
                        System.out.print("Shield value: ");
                        calculator.setShieldList(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;
                }

                case "rs":
                    calculator.removeShield();
                    break;

                case "0s":
                    calculator.clearShields();
                    break;

                case "a":
                    System.out.print("Aura Value: ");
                    try {
                        calculator.setAura(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                case "0a":
                    calculator.clearAura();
                    break;

                case "bub":
                    System.out.print("Bubble value: ");
                    try {
                        calculator.setBubble(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                case "0bub":
                    calculator.clearBubble();
                    break;

                case "00":
                    calculator.clearAll();
                    break;

                case "ns":
                    try {
                        System.out.print("Enter new spell damage: ");
                        calculator.setSpellDamage(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "nd":
                    try {
                        System.out.print("Enter new DMG: ");
                        calculator.setPlayerDamage(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "np":
                    try {
                        System.out.print("Enter new pierce: ");
                        calculator.setPierce(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "nr":
                    try {
                        System.out.print("Enter new enemy resist: ");
                        calculator.setEnemyResist(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "nc":
                    try {
                        System.out.println("Enter new crit mod: ");
                        calculator.setCritMod(input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "check":
                    calculator.check();
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
