import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.*;


public class W101Calculator implements BuffConverter, DamageCalculator, DisplayBuffs {
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal Point0One = BigDecimal.valueOf(0.01);
    private  BigDecimal spellDamage;
    private  BigDecimal playerDamage;
    private  BigDecimal critMod;
    private  BigDecimal pierce;
    private  BigDecimal enemyResist;
    private  BigDecimal aura;
    private  BigDecimal bubble;
    private List<BigDecimal> bladeList;
    private  List<BigDecimal> trapList;
    private  List<BigDecimal> weaknessList;
    private  List<BigDecimal> shieldList;
    private BigDecimal blades;
    private BigDecimal weaknesses;
    private BigDecimal traps;
    private BigDecimal shields;
    private BigDecimal total;

    public W101Calculator() {
        this.spellDamage = ZERO;
        this.playerDamage = ONE;
        this.critMod = ONE;
        this.pierce = ZERO;
        this.enemyResist = ZERO;
        this.aura = ONE;
        this.bubble = ONE;
        this.bladeList = new ArrayList<>();
        this.trapList = new ArrayList<>();
        this.weaknessList = new ArrayList<>();
        this.shieldList = new ArrayList<>();
        this.blades = ONE;
        this.traps = ONE;
        this.weaknesses = ONE;
        this.shields = ZERO;
    }

    public BigDecimal getSpellDamage() {
        return spellDamage;
    }
    public BigDecimal getPlayerDamage() {
        return playerDamage;
    }
    public BigDecimal getCritMod() {
        return critMod;
    }
    public BigDecimal getPierce() {
        return pierce;
    }
    public BigDecimal getEnemyResist() {
        return enemyResist;
    }
    public BigDecimal getAura() {
        return aura;
    }
    public BigDecimal getBubble() {
        return bubble;
    }
    public List<BigDecimal> getBladeList() {
        return bladeList;
    }
    public List<BigDecimal> getTrapList() {
        return trapList;
    }
    public List<BigDecimal> getWeaknessList() {
        return weaknessList;
    }
    public List<BigDecimal> getShieldList() {
        return shieldList;
    }
    public BigDecimal getBlades() {
        return blades;
    }
    public BigDecimal getWeaknesses() {
        return weaknesses;
    }
    public BigDecimal getTraps() {
        return traps;
    }
    public BigDecimal getShields() {
        return shields;
    }
    public BigDecimal getTotal() {
        return calculateTotalDamage();
    }

    public void setSpellDamage(BigDecimal spellDamage) {
        this.spellDamage = spellDamage;
    }

    public void setPlayerDamage(BigDecimal playerDamage) {
        this.playerDamage = convertBuff(playerDamage);
    }

    public void setCritMod(BigDecimal critMod) {
        this.critMod = convertBuff(critMod);
    }

    public void setPierce(BigDecimal pierce) {
        this.pierce = convertPierce(pierce);
    }

    public void setEnemyResist(BigDecimal enemyResist) {
        this.enemyResist = convertPierce(enemyResist);
    }

    public void setAura(BigDecimal aura) {
        this.aura = convertBuff(aura);
    }

    public void setBubble(BigDecimal bubble) {
        this.bubble = convertBuff(bubble);
    }

    public void setBladeList(BigDecimal blade) {
        this.bladeList.add(convertBuff(blade));
        this.blades = arrayMultiplier(bladeList);
    }

    public void setTrapList(BigDecimal trap) {
        this.trapList.add(convertBuff(trap));
        this.traps = arrayMultiplier(trapList);
    }

    public void setWeaknessList(BigDecimal weakness) {
        this.weaknessList.add(convertDebuff(weakness));
        this.weaknesses = arrayMultiplier(weaknessList);
    }

    public void setShieldList(BigDecimal shield) {
        this.shieldList.add(convertPierce(shield));
        this.shields = arrayAdder(shieldList);
    }

    public void removeBlade(){
        if (!bladeList.isEmpty()) {
            this.bladeList.remove(bladeList.size() - 1);
            this.blades = arrayMultiplier(bladeList);
        }
    }

    public void removeTrap() {
        if (!trapList.isEmpty()) {
            this.trapList.remove(trapList.size() - 1);
            this.traps = arrayMultiplier(trapList);
        }
    }

    public void removeWeakness() {
        if (!weaknessList.isEmpty()) {
            this.weaknessList.remove(weaknessList.size() - 1);
            this.weaknesses = arrayMultiplier(weaknessList);
        }
    }

    public void removeShield() {
        if (!shieldList.isEmpty()) {
            this.shieldList.remove(shieldList.size() - 1);
            this.shields = arrayAdder(shieldList);
        }
    }

    public void clearBlades(){
        this.bladeList.clear();
        this.blades = arrayMultiplier(bladeList);
    }

    public void clearTraps(){
        this.trapList.clear();
        this.traps = arrayMultiplier(trapList);
    }

    public void clearWeaknesses(){
        this.weaknessList.clear();
        this.weaknesses = arrayMultiplier(weaknessList);
    }

    public void clearShields(){
        this.shieldList.clear();
        this.shields = arrayAdder(shieldList);
    }

    public void clearAura(){
        this.aura = ONE;
    }
    public void clearBubble(){
        this.bubble = ONE;
    }

    public void clearAll(){
        clearBlades();
        clearTraps();
        clearWeaknesses();
        clearShields();
        clearAura();
        clearBubble();
    }

    public  BigDecimal calculateTotalDamage() {
        final BigDecimal multiply0 = spellDamage.multiply(playerDamage).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply1 = multiply0.multiply(aura).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply2 = multiply1.multiply(bubble).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply3 = multiply2.multiply(blades).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply4 = multiply3.multiply(traps).setScale(0, RoundingMode.DOWN);
        this.total = multiply4.multiply(weaknesses).setScale(0, RoundingMode.DOWN);
        return calculateTotalAfterPiercing();
    }

    public  BigDecimal calculateTotalAfterPiercing() {
        // FIX SHIELDS PAST THE FIRST ONE
            BigDecimal thatPierce = pierce;
            BigDecimal shieldMultiplier;
            ArrayList<BigDecimal> tempShieldList = (ArrayList<BigDecimal>) shieldList;
            ArrayList<BigDecimal> convertedShieldList = applyConvertDebuff(tempShieldList);
            int tempShieldValueIndex = tempShieldList.size() - 1;
            int convertedShieldListIndex = convertedShieldList.size() - 1;

            // Checks if you can pierce all current shields
            if (thatPierce.compareTo(shields) >= 0) {
                thatPierce = thatPierce.subtract(shields);

                // Checks for leftover pierce
                if (thatPierce.compareTo(enemyResist) > 0) {
                    thatPierce = BigDecimal.ZERO;
                    enemyResist = BigDecimal.ZERO;
                }

                // Final calculation if there was leftover pierce from all the shields
                this.total = total.multiply(BigDecimal.ONE.subtract(enemyResist.subtract(thatPierce))).setScale(0, RoundingMode.DOWN);

                // Pierces each shield one by one
            } else {
                do {
                    thatPierce = thatPierce.subtract(tempShieldList.get(tempShieldList.size() - 1));
                    if (thatPierce.compareTo(BigDecimal.ZERO) > 0) {
                        // This section removes shields that no longer have a positive value
                        shieldList.remove(shieldList.size() - 1);
                        tempShieldList.remove(tempShieldList.size() - 1);
                        convertedShieldList.remove(convertedShieldList.size() - 1);
                        tempShieldValueIndex = shieldList.size() - 1;
                        convertedShieldListIndex = convertedShieldList.size() - 1;
                    } else {
                        // Finds the remaining value of the shield after pierce is all used up and changes the value within the arrays
                        BigDecimal piercedShield = thatPierce.abs();
                        tempShieldList.set(tempShieldValueIndex, piercedShield);
                        convertedShieldList.set(convertedShieldListIndex, convertShield(piercedShield));
                    }

                    // New total multiplier for shields
                    shieldMultiplier = arrayMultiplier(convertedShieldList);

                } while (thatPierce.compareTo(BigDecimal.ZERO) > 0);

                // Final damage calculation for the true total
                total = total.multiply(shieldMultiplier).multiply(BigDecimal.ONE.subtract(enemyResist)).setScale(0, RoundingMode.DOWN);
            }
        return total;
    }

    public  void check() {
        System.out.println("Spell Damage: " + spellDamage);
        System.out.println("Player Damage: " + displayBuff(playerDamage));
        System.out.println("Critical Multiplier: " + displayBuff(critMod));
        System.out.println("Pierce: " + revertBuff(pierce));
        System.out.println("Enemy Resist: " + revertBuff(enemyResist));

        if (aura.compareTo(ONE) == 0) {
            System.out.println("No Aura");
        } else {
            BigDecimal auraPercentage = BigDecimal.valueOf(100)
                    .multiply(aura.subtract(ONE))
                    .setScale(0, RoundingMode.FLOOR);
            System.out.println("Aura: " + auraPercentage);
        }

        if (bubble.compareTo(ONE) == 0) {
            System.out.println("No Bubble");
        } else {
            BigDecimal bubblePercentage = BigDecimal.valueOf(100)
                    .multiply(bubble.subtract(ONE))
                    .setScale(0, RoundingMode.FLOOR);
            System.out.println("Bubble: " + bubblePercentage);
        }

        if (!bladeList.isEmpty()) {
            System.out.print("Blades: ");
            for (BigDecimal bigDecimal : bladeList) {
                System.out.printf("%-2.0f " , displayBuff(bigDecimal));
            }
            System.out.println();
        } else {
            System.out.println("No Blades");
        }

        if (!trapList.isEmpty()) {
            System.out.print("Traps: ");
            for (BigDecimal bigDecimal : trapList) {
                System.out.printf("%-2.0f " , displayBuff(bigDecimal));
            }
            System.out.println();
        } else {
            System.out.println("No Traps");
        }

        if (!weaknessList.isEmpty()) {
            System.out.print("Weaknesses: ");
            for (BigDecimal bigDecimal : weaknessList) {
                System.out.printf("%-2.0f " , displayBuff(bigDecimal));
            }
            System.out.println();
        } else {
            System.out.println("No Weaknesses");
        }

        if (!shieldList.isEmpty()) {
            System.out.print("Shields: ");
            shieldList = convertShieldDisplay(shieldList);
            for (BigDecimal bigDecimal : shieldList) {
                System.out.printf("%-2.0f " , displayBuff(bigDecimal));
            }
            System.out.println();
        } else {
            System.out.println("No Shields");
        }
    }


    public static BigDecimal arrayMultiplier(List<BigDecimal> list) {
        BigDecimal product = BigDecimal.valueOf(1.0);
        for (BigDecimal d : list) {
            product = product.multiply(d);
        }
        return product;
    }

    public static BigDecimal arrayAdder(List<BigDecimal> list) {
        BigDecimal sum = BigDecimal.valueOf(0.0);
        for (BigDecimal aDouble : list) {
            sum = sum.add(aDouble);
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return BigDecimal.valueOf(Double.parseDouble(df.format(sum)));
    }

    public static ArrayList<BigDecimal> applyConvertDebuff(ArrayList<BigDecimal> list) {
        ArrayList<BigDecimal> convertedShieldList= new ArrayList<>();
        for (BigDecimal shieldValue : list) {
            BigDecimal updatedValue = convertShield(shieldValue);
            convertedShieldList.add(updatedValue);
        }
        return convertedShieldList;
    }

    public static BigDecimal convertShieldMultiplier (ArrayList<BigDecimal> shieldList){
        ArrayList<BigDecimal> shieldValues = new ArrayList<>();
        for (BigDecimal bigDecimal : shieldList) {
            shieldValues.add(W101Calculator.convertShield(W101Calculator.convertPierce(bigDecimal)));
        }
        return arrayMultiplier(shieldValues);
    }

    public static BigDecimal convertShieldsDecimal (ArrayList<BigDecimal> shieldList){
        ArrayList<BigDecimal> shieldDecimals = new ArrayList<>();
        for (BigDecimal bigDecimal : shieldList) {
            shieldDecimals.add(W101Calculator.convertPierce(bigDecimal));
        }
        return arrayAdder(shieldDecimals);
    }

    public static ArrayList<BigDecimal> convertShieldDisplay (List<BigDecimal> shieldList){
        ArrayList<BigDecimal> shieldDecimals = new ArrayList<>();
        for (BigDecimal bigDecimal : shieldList) {
            shieldDecimals.add(W101Calculator.convertPierce(bigDecimal));
        }
        return shieldDecimals;
    }

    // Implement the BuffConverter interface
    public static BigDecimal convertBuff(BigDecimal num) {
        return ONE.add(num.multiply(Point0One));
    }

    public static BigDecimal convertDebuff(BigDecimal num) {
        return ONE.subtract(num.multiply(Point0One));
    }

    public static BigDecimal convertPierce(BigDecimal num) {
        return num.multiply(Point0One);
    }

    public static BigDecimal convertShield(BigDecimal num) {
        return ONE.subtract(num);
    }

    // Implement the DamageCalculator interface

    public static BigDecimal calculateTotalDamage(BigDecimal spellDamage, BigDecimal damageMultiplier, BigDecimal blades, BigDecimal traps, BigDecimal weaknesses, BigDecimal aura, BigDecimal bubble, ArrayList<BigDecimal> ShieldList, BigDecimal pierce, BigDecimal enemyResist) {
        final BigDecimal multiply0 = spellDamage.multiply(damageMultiplier).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply1 = multiply0.multiply(aura).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply2 = multiply1.multiply(bubble).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply3 = multiply2.multiply(blades).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply4 = multiply3.multiply(traps).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiplyfinal = multiply4.multiply(weaknesses).setScale(0, RoundingMode.DOWN);
        return calculateTotalAfterPiercing(multiplyfinal, ShieldList, pierce, enemyResist);
    }

    public static BigDecimal calculateTotalAfterPiercing(BigDecimal total, ArrayList<BigDecimal> shieldList, BigDecimal pierce, BigDecimal enemyResist) {
        BigDecimal allShields = arrayAdder(shieldList);
        // FIX SHIELDS PAST THE FIRST ONE
        BigDecimal shieldMultiplier;
        ArrayList<BigDecimal> convertedShieldList = applyConvertDebuff(shieldList);
        int tempShieldValueIndex = shieldList.size() - 1;
        int convertedShieldListIndex = convertedShieldList.size() - 1;

        // Checks if you can pierce all current shields
        if (pierce.compareTo(allShields) >= 0) {
            pierce = pierce.subtract(allShields);

            // Checks for leftover pierce
            if (pierce.compareTo(enemyResist) > 0) {
                pierce = BigDecimal.ZERO;
                enemyResist = BigDecimal.ZERO;
            }

            // Final calculation if there was leftover pierce from all the shields
            total = total.multiply(BigDecimal.ONE.subtract(enemyResist.subtract(pierce))).setScale(0, RoundingMode.DOWN);

            // Pierces each shield one by one
        } else {
            do {
                pierce = pierce.subtract(shieldList.get(shieldList.size() - 1));
                if (pierce.compareTo(BigDecimal.ZERO) > 0) {
                    // This section removes shields that no longer have a positive value
                    shieldList.remove(shieldList.size() - 1);
                    shieldList.remove(shieldList.size() - 1);
                    convertedShieldList.remove(convertedShieldList.size() - 1);
                    tempShieldValueIndex = shieldList.size() - 1;
                    convertedShieldListIndex = convertedShieldList.size() - 1;
                } else {
                    // Finds the remaining value of the shield after pierce is all used up and changes the value within the arrays
                    BigDecimal piercedShield = pierce.abs();
                    shieldList.set(tempShieldValueIndex, piercedShield);
                    convertedShieldList.set(convertedShieldListIndex, convertShield(piercedShield));
                }

                // New total multiplier for shields
                shieldMultiplier = arrayMultiplier(convertedShieldList);

            } while (pierce.compareTo(BigDecimal.ZERO) > 0);

            // Final damage calculation for the true total
            total = total.multiply(shieldMultiplier).multiply(BigDecimal.ONE.subtract(enemyResist)).setScale(0, RoundingMode.DOWN);
        }
        return total;
    }

        /* Checks to see if any shields were removed during the pierce section
        if (!shieldsList.isEmpty()) {
            shieldList.set(lastShieldValueIndex, addShields(recentShield));
            shieldsList.set(lastShieldListIndex, recentShield);
        }

        // Resets pierce and enemyResist to the user-imputed values
        pierce = ogPierce;
        enemyResist = ogResist;

        // Makes sure that the arrays are restored to the user-imputed values
        if (!originalShields.equals(shieldList)) {
            shieldList.clear();
            shieldsList.clear();
            for (int i = 0; i <= lastOGShieldIndex; i++) {
                BigDecimal temp = originalShields.get(i);
                shieldList.add(temp);
                shieldsList.add(subtractFromOne(temp));
            }
        }
    }*/

    public static BigDecimal calculateCriticalDamage(BigDecimal total, BigDecimal critMod) {
        return total.multiply(critMod).setScale(0, RoundingMode.DOWN);
    }
    // Implement the BuffDisplay interface
    public static BigDecimal displayBuff(BigDecimal buff) {
        if (buff.compareTo(BigDecimal.ZERO) >= 1) {
            return valueOf(100).multiply(buff.subtract(ONE)).setScale(0,RoundingMode.FLOOR);
        } else {
            return ONE.subtract(valueOf(100).multiply(buff)).setScale(0,RoundingMode.FLOOR);
        }
    }

    public static BigDecimal revertBuff(BigDecimal buff){
        return valueOf(100).multiply(buff).setScale(0,RoundingMode.FLOOR);
    }

    public static void check(BigDecimal spellDamage, BigDecimal playerDamage, BigDecimal critMod, BigDecimal pierce, BigDecimal enemyResist, BigDecimal aura, BigDecimal bubble, ArrayList bladeList, ArrayList trapList, ArrayList weaknessList, ArrayList shieldList) {
        System.out.println("Spell Damage: " + spellDamage);
        System.out.println("Player Damage: " + displayBuff(playerDamage));
        System.out.println("Critical Multiplier: " + displayBuff(critMod));
        System.out.println("Pierce: " + revertBuff(pierce));
        System.out.println("Enemy Resist: " + revertBuff(enemyResist));

        if (aura.compareTo(ONE) == 0) {
            System.out.println("No Aura");
        } else {
            BigDecimal auraPercentage = BigDecimal.valueOf(100)
                    .multiply(aura.subtract(ONE))
                    .setScale(0, RoundingMode.FLOOR);
            System.out.println("Aura: " + auraPercentage);
        }

        if (bubble.compareTo(ONE) == 0) {
            System.out.println("No Bubble");
        } else {
            BigDecimal bubblePercentage = BigDecimal.valueOf(100)
                    .multiply(bubble.subtract(ONE))
                    .setScale(0, RoundingMode.FLOOR);
            System.out.println("Bubble: " + bubblePercentage);
        }

        if (!bladeList.isEmpty()) {
            System.out.print("Blades: ");
            for (Object bigDecimal : bladeList) {
                System.out.printf("%-2.0f " , displayBuff((BigDecimal) bigDecimal));
            }
            System.out.println();
        } else {
            System.out.println("No Blades");
        }

        if (!trapList.isEmpty()) {
            System.out.print("Traps: ");
            for (Object bigDecimal : trapList) {
                System.out.printf("%-2.0f " , displayBuff((BigDecimal) bigDecimal));
            }
            System.out.println();
        } else {
            System.out.println("No Traps");
        }

        if (!weaknessList.isEmpty()) {
            System.out.print("Weaknesses: ");
            for (Object bigDecimal : weaknessList) {
                System.out.printf("%-2.0f " , displayBuff((BigDecimal) bigDecimal));
            }
            System.out.println();
        } else {
            System.out.println("No Weaknesses");
        }

        if (!shieldList.isEmpty()) {
            System.out.print("Shields: ");
            shieldList = convertShieldDisplay(shieldList);
            for (Object bigDecimal : shieldList) {
                System.out.printf("%-2.0f " , displayBuff((BigDecimal) bigDecimal));
            }
            System.out.println();
        } else {
            System.out.println("No Shields");
        }
    }
}
