package chess.utils;

import java.util.Objects;

public class PinDetails {
    private boolean isPinned = false;
    private int attackingFigureColumn = -1;
    private int attackingFigureRow = -1;

    public PinDetails() {
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinDetails(int attackingFigureColumn, int attackingFigureRow) {
        this.isPinned = true;
        this.attackingFigureColumn = attackingFigureColumn;
        this.attackingFigureRow = attackingFigureRow;
    }

    public int getAttackingFigureColumn() {
        return attackingFigureColumn;
    }

    public int getAttackingFigureRow() {
        return attackingFigureRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinDetails that = (PinDetails) o;
        return isPinned == that.isPinned && attackingFigureColumn == that.attackingFigureColumn && attackingFigureRow == that.attackingFigureRow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPinned, attackingFigureColumn, attackingFigureRow);
    }
}
