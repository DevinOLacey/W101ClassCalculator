import java.math.BigDecimal;
import java.util.ArrayList;

public interface DamageCalculator {
    static BigDecimal calculateTotalDamage(BigDecimal baseDamage, BigDecimal damageMultiplier, BigDecimal blades, BigDecimal traps, BigDecimal weaknesses, BigDecimal aura, BigDecimal bubble, ArrayList ShieldList, BigDecimal pierce, BigDecimal enemyResist) {
        return null;
    }

    static BigDecimal calculateCriticalDamage(BigDecimal total) {
        return null;
    }

    static BigDecimal calculateTotalAfterPiercing(BigDecimal total, ArrayList shieldList, BigDecimal pierce, BigDecimal enemyResist) {
        return null;
    }
}
