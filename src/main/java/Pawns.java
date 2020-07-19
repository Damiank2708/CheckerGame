import java.awt.*;

public class Pawns {
    public boolean isBlack;
    public boolean isWhite;
    public boolean isSuperWarrior;
    public Point point;//column, row

    public Pawns(boolean isBlack, boolean isWhite, boolean isSuperWarrior, Point point){
        this.isBlack = isBlack;
        this.isWhite = isWhite;
        this.isSuperWarrior = isSuperWarrior;
        this.point = point;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isSuperWarrior() {
        return isSuperWarrior;
    }

    public Point getPoint() {
        return point;
    }

    public void setSuperWarrior(boolean superWarrior) {
        isSuperWarrior = superWarrior;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object obj) {
        Pawns pawns = (Pawns) obj;
        return pawns.getPoint().equals(this.getPoint());
    }
}
